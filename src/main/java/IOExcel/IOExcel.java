package IOExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class IOExcel {

	private Workbook wb;
	private static final String EXCEL_XLS = "xls";  
    private static final String EXCEL_XLSX = "xlsx"; 
	
	public IOExcel(File file) throws EncryptedDocumentException, InvalidFormatException, IOException{ 
        FileInputStream in = new FileInputStream(file); // 文件流  
		wb=WorkbookFactory.create(in);
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
	
	public int GetSheetCount(){
		return wb.getNumberOfSheets();
	}
	
	public Sheet GetSheet(int i){
		return wb.getSheetAt(i);
	}
	
	public int GetRowCount(int i){
		Sheet sh=this.GetSheet(i);
		return sh.getLastRowNum();
	}
	
	public TestCase ReadExcel(int sheetnum,int rownum){
		Map<String,String> r=new HashMap<String,String>();
		Sheet sh=this.GetSheet(sheetnum);
		Row row=sh.getRow(rownum);
		Row frow=sh.getRow(0);
		int start=row.getFirstCellNum();
		int end=row.getPhysicalNumberOfCells();
		for(int j=start;j<=end;j++){
			Cell cell=row.getCell(j);
			String title=this.GetCellValue(frow.getCell(j));
			r.put(title,this.GetCellValue(cell));
		}
		TestCase t=new TestCase();
		t.SetTestName(r.get("测试用例名称"));
		t.SetAuthor(r.get("用例设计人"));
		t.SetExpected(r.get("预期结果"));
		t.SetStep(r.get("用例步骤"));
		
		return t;
	}
	
	public String GetCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  
        //把数字当成String来读，避免出现1读成1.0的情况  
//        if(cell.getCellTypeEnu==CellType.NUMERIC){  
//            cell.setCellType(Cell.CELL_TYPE_STRING);  
//        }  
        //判断数据的类型 
        switch (cell.getCellTypeEnum()){  
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
	
	 public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException{
		 File file=new File("E:\\Bacy\\智慧城市项目\\测试用例XLS文件\\北斗爱车App_20181121.xlsx");
		 IOExcel excel=new IOExcel(file);
		 int rows=excel.GetRowCount(1);
		 for(int i=1;i<=rows;i++){
			 TestCase t=excel.ReadExcel(1,i);
			 System.out.println(t.GetTestName()+" "+t.GetAuthor()+" "+t.GetExpected()+" "+t.GetStep());
		 }
	 }
}
