package IOXml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class IOXml {

	private String path;
	private Document doc;
	
	public IOXml(String s){
		this.path=s;
	}
	
	public void WriteXml(){
		doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("utf-8");
//		doc.addComment("EtoXConvert Version 1.0;Author wanyan");
		Element root = doc.addElement("testcases");
		Element testcase = root.addElement("testcase")
				.addAttribute("internalid", "21")
				.addAttribute("name", "测试用例标题");
		Element node_order=testcase.addElement("node_order");
		node_order.setText("<![CDATA[1021]]>");
		Element externalid=testcase.addElement("externalid");
		externalid.setText("<![CDATA[4]]>");
		Element fullexternalid=testcase.addElement("fullexternalid");
		fullexternalid.setText("<![CDATA[北斗爱车-4]]>");
		Element version=testcase.addElement("version");
		version.setText("<![CDATA[1]]>");
		Element summary=testcase.addElement("summary");
		summary.setText("<![CDATA[<p>摘要-北斗爱车App-电子围栏-摘要</p>]]>");
		Element preconditions=testcase.addElement("preconditions");
		preconditions.setText("<![CDATA[<p>前提-北斗爱车App-正确的账号</p>]]>");
		Element execution_type=testcase.addElement("execution_type");
		execution_type.setText("<![CDATA[1]]>");
		Element importance=testcase.addElement("importance");
		importance.setText("<![CDATA[3]]>");
		Element estimated_exec_duration=testcase.addElement("estimated_exec_duration");
		estimated_exec_duration.setText("30.00");
		Element status=testcase.addElement("status");
		status.setText("7");
		Element is_open=testcase.addElement("is_open");
		is_open.setText("1");
		Element active=testcase.addElement("active");
		active.setText("1");
		Element steps=testcase.addElement("steps");
		Element step1=steps.addElement("step");
		Element step_number=step1.addElement("step_number");
		step_number.setText("<![CDATA[1]]>");
		Element actions=step1.addElement("actions");
		actions.setText("<![CDATA[<p>步骤1-打开App</p>]]>");
		Element expectedresults=step1.addElement("expectedresults");
		expectedresults.setText("<![CDATA[<p>期望结果-正常打开App</p>]]>");
		Element execution_type1=step1.addElement("execution_type");
		execution_type1.setText("<![CDATA[1]]>");	
	}
	
	public void MakeXml(){
		OutputFormat format = OutputFormat.createPrettyPrint(); 
		format.setEncoding("UTF-8");
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		xw.setEscapeText(false); 
//		FileWriter out=null;
		BufferedWriter fw = null;
		try {
			xw.write(doc);
			xw.flush(); 
            xw.close(); 
//            out=new FileWriter(new File(path));
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path,false),"UTF-8"));
            fw.write(sw.toString());
            fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 IOXml xml=new IOXml("E:\\dd.xml");
		 xml.WriteXml();
		 xml.MakeXml();
	 }
}
