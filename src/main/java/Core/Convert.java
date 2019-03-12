package Core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import IOExcel.IOExcel;
import IOXml.IOXml;

public class Convert {
	
	
	public void Done(String path) throws EncryptedDocumentException, InvalidFormatException, IOException{
		File file=new File(path);
		String fname=path.substring(0,path.lastIndexOf("."));
		IOExcel excel=new IOExcel(file);
		Sheet sh=excel.GetSheet(1);
		List<String> title=excel.GetTitle(sh);
		List<CellRangeAddress> mcell=excel.GetCombineCell(sh);
		int rows=excel.GetRowCount(sh);
		IOXml xml=new IOXml(fname+".xml");
		TestCase tmp=new TestCase();
		for(int i=1;i<=rows;i++){
			TestCase t=excel.ReadExcel(sh,i,title,title.size(),mcell,mcell.size());
			if(t.GetNo()==-1)
				System.out.println("行数:"+(i+1)+" "+"error");
			else if(t.GetStatus().equals("0")){
				System.out.println("编号:"+t.GetNo()+"测试用例导入失败，原因:状态未知值");
			}else if(t.GetExetype().equals("0")){
				System.out.println("编号:"+t.GetNo()+"测试用例导入失败，原因:测试方式未知值");
			}else if(t.GetImp().equals("0")){
				System.out.println("编号:"+t.GetNo()+"测试用例导入失败，原因:重要性未知值");
			}
			else if(t.GetNo()==tmp.GetNo()){
				 tmp.SetStep(t.GetStep().get(0));
				 tmp.SetExpected(t.GetExpected().get(0));
			}
			else{
				if(i!=1)
					xml.WriteXml(tmp);
				tmp=t;
			 }
			
		}
		xml.MakeXml();
	}
}
