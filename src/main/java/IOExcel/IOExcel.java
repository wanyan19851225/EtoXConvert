package IOExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import Core.ExeType;
import Core.Importance;
import Core.Status;
import Core.TestCase;

public class IOExcel {

	private Workbook wb;
	private static final String EXCEL_XLS = "xls";  
    private static final String EXCEL_XLSX = "xlsx"; 
	
	public IOExcel(File file) throws EncryptedDocumentException, InvalidFormatException, IOException{ 
        FileInputStream in = new FileInputStream(file); // 文件流  
		wb=WorkbookFactory.create(in);
	}
		
	public int GetSheetCount(){
		return wb.getNumberOfSheets();
	}
	
	public Sheet GetSheet(int shnum){
		return wb.getSheetAt(shnum);
	}
	
	public int GetRowCount(Sheet sh){
//		Sheet sh=this.GetSheet(shnum);
		return sh.getLastRowNum();
	}
	
	public List<String> GetTitle(Sheet sh){
		List<String> title=new ArrayList<String>();
		Row frow=sh.getRow(0);
		int start=frow.getFirstCellNum();
		int end=frow.getPhysicalNumberOfCells();
		for(int i=start;i<end;i++){
			String s=this.GetCellValue(frow.getCell(i));
			if(!s.equals(""))
				title.add(s);
		}
		return title;
	}
	
    public List<CellRangeAddress> GetCombineCell(Sheet sh){ 
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();   
        int sheetmergerCount = sh.getNumMergedRegions();  
        //遍历所有的合并单元格  
        for(int i = 0; i<sheetmergerCount;i++){  
            //获得合并单元格保存进list中  
            CellRangeAddress ca = sh.getMergedRegion(i);  
            list.add(ca);  
        }  
        return list;  
    }

	public String GetMergedRegionValue(Sheet sh,List<CellRangeAddress> mcell,int mcellnum,Cell cell) {
		String value="";
		if(cell!=null){
			for (int i = 0; i < mcellnum; i++) {  
				CellRangeAddress range = mcell.get(i); 
				int fcolumn = range.getFirstColumn();  
				int lastColumn = range.getLastColumn();  
				int frow = range.getFirstRow();  
				int lastRow = range.getLastRow();  
				if(cell.getRowIndex()>= frow && cell.getRowIndex()<= lastRow){  
					if(cell.getColumnIndex()>= fcolumn &&cell.getColumnIndex()<= lastColumn){  
						Row row = sh.getRow(frow);  
						Cell fcell = row.getCell(fcolumn);
						value=this.GetCellValue(fcell);
						return value;
					}  
				}
			}
			value=this.GetCellValue(cell);		
		}
		else
			value=this.GetCellValue(cell);
		return value;  //返回null该单元格不是合并单元格，如果该单元格是合并单元格，返回合并单元格的值
	}
	
	public String GetCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null)  
            return cellValue;  
        if(cell.getCellType()==CellType.NUMERIC){		        //把数字当成String来读，避免出现1读成1.0的情况 
            cell.setCellType(CellType.STRING);  
        }   
        switch (cell.getCellType()){  
            case NUMERIC://数字  
                cellValue = String.valueOf(cell.getNumericCellValue());  
                break;  
            case STRING: //字符串  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case FORMULA: //公式  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case BLANK: //空值   
                cellValue = "";  
                break;  
            case ERROR: //故障  
                cellValue = "非法字符";  
                break;  
            default:  
                cellValue = "未知类型";  
                break;  
        }  
        return cellValue;  
    }

    public TestCase ReadExcel(Sheet sh,int rownum,List<String> title,int titlenum,List<CellRangeAddress> mcell,int mcellnum){
		Map<String,String> r=new HashMap<String,String>();
		Row row=sh.getRow(rownum);
		for(int i=0;i<titlenum;i++){
			Cell cell=row.getCell(i,Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
			String value=this.GetMergedRegionValue(sh, mcell, mcellnum,cell);
			r.put(title.get(i),value);
		}
		
		TestCase t=new TestCase();
		
		for(String key:r.keySet()){
			if(r.get(key).equals("")){
				t.SetNo(-1);
				return t;
			}
		}
		
		t.SetTestName(r.get("测试用例名称"));
		t.SetAuthor(r.get("用例设计人"));
		t.SetExpected(r.get("预期结果"));
		t.SetStep(r.get("用例步骤"));
		String satuts=r.get("状态");
		if(satuts.equals("草稿"))
			t.SetStatus(Status.DRAFT);
		else if(satuts.equals("待评审"))
			t.SetStatus(Status.PENDING_REVIEW);
		else if(satuts.equals("评审中"))
			t.SetStatus(Status.REVIEW);
		else if(satuts.equals("重做"))
			t.SetStatus(Status.REDO);
		else if(satuts.equals("废弃"))
			t.SetStatus(Status.DISCARD);
		else if(satuts.equals("Future"))
			t.SetStatus(Status.FUTURE);
		else if(satuts.equals("终稿"))
			t.SetStatus(Status.FINAL);
		else
			t.SetStatus(Status.UNKOWN);
		
		String imp=r.get("重要性");
		if(imp.equals("高"))
			t.SetImp(Importance.IMPORTANT);
		else if(imp.equals("中"))
			t.SetImp(Importance.MEDIUM);
		else if(imp.equals("低"))
			t.SetImp(Importance.LOW);
		else
			t.SetImp(Importance.UNKOWN);

		String exetype=r.get("测试方式");
		if(exetype.equals("手工"))
			t.SetExeType(ExeType.HAND);
		else if(exetype.equals("自动的"))
			t.SetExeType(ExeType.AUTO);
		else
			t.SetExeType(ExeType.UNKOWN);

		t.SetExeTime(r.get("执行时间"));
		t.SetVersion(r.get("版本"));
		t.SetSummary(r.get("摘要"));
		t.SetPrec(r.get("前提"));
		t.SetPrefix(r.get("项目名称"));
		t.SetNo(Integer.valueOf(r.get("编号")));
		return t;
	}
	
	public Boolean CheckFile(File file){
		Boolean f;
		if(!file.exists()||!file.isFile())
			f=false;
		else if(!file.getName().endsWith(IOExcel.EXCEL_XLS)||!file.getName().endsWith(IOExcel.EXCEL_XLSX))
			f=false;
		else
			f=true;
		return f;
	}
	
	public String CheckTestCase(Sheet sh,int rownum,List<String> title,int titlenum,List<CellRangeAddress> mcell,int mcellnum){
		StringBuilder sb=new StringBuilder();
		Row row=sh.getRow(rownum);
		for(int i=0;i<titlenum;i++){
			Cell cell=row.getCell(i,Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
			String value=this.GetMergedRegionValue(sh, mcell, mcellnum,cell);
			if(value.equals(""))
				sb.append(title.get(i)+" 空"+",");
			else if(title.get(i).equals("状态"))
				if(!value.matches("(草稿)|(待评审)|(评审中)|(重做)|(废弃)|(Future)|(终稿)"))
					sb.append(title.get(i)+" 未知"+",");
			else if(title.get(i).equals("重要性"))
				if(!value.matches("[高中低]"))
					sb.append(title.get(i)+" 未知"+",");
			else if(title.get(i).equals("测试方式"))
				if(!value.matches("(手工)|(自动的)"))
					sb.append(title.get(i)+" 未知"+",");	
		}
		if(sb.length()>0)
			sb.insert(0,"TestCase:"+rownum+" ");
		return sb.toString();
	}
	
	 public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException{
		 File file=new File("E:\\Bacy\\智慧城市项目\\测试用例XLS文件\\北斗爱车App_20181121.xlsx");
		 IOExcel excel=new IOExcel(file);
		 Sheet sh=excel.GetSheet(1);
		 List<String> title=excel.GetTitle(sh);
		 List<CellRangeAddress> mcell=excel.GetCombineCell(sh);
		 int rows=excel.GetRowCount(sh);
		 for(int i=1;i<=rows;i++){
			 TestCase t=excel.ReadExcel(sh,i,title,title.size(),mcell,mcell.size());
			 if(t.GetNo()==-1)
				 System.out.println("行数:"+(i+1)+" "+"error");
			 else
				 System.out.println(t.GetNo()+" "+t.GetTestName()+" "+t.GetAuthor()+" "+t.GetStep()+" "+t.GetExpected()+" "+t.GetStatus()+" "+t.GetImp()+" "+t.GetExetype()+" "+t.GetExetime()+" "
						 +t.GetVersion()+" "+t.GetSummary()+" "+t.GetPrec()+" "+t.GetPrefix());
//			 String sb=excel.CheckTestCase(sh,i,title,title.size(),mcell,mcell.size());
//			 if(sb.length()>0)
//				 System.out.println(sb);
		 } 
	 }
}
