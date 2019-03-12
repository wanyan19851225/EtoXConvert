package IOXml;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import Core.TestCase;

public class IOXml {

	private String path;
	private Document doc;
	private Element root;

	public IOXml(String s){
		this.path=s;
		doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("utf-8");
		doc.addComment("EtoXConvert Version 1.0;Author wanyan");
		root = doc.addElement("testcases");
	}
	
	public void WriteXml(TestCase t){
		String no=String.valueOf(t.GetNo());
		String name=t.GetTestName();
		String prefix=t.GetPrefix()+"-"+no;
		String v=t.GetVersion();
		String sum="<p>"+t.GetSummary()+"</p>";
		String prec="<p>"+t.GetPrec()+"</p>";
		String step="<p>"+t.GetStep()+"</p>";
		String exepect="<p>"+t.GetExpected()+"</p>";

		Element testcase = root.addElement("testcase")
				.addAttribute("internalid",no)
				.addAttribute("name", name);
		Element node_order=testcase.addElement("node_order");
		node_order.setText(this.UpdateString(no));
		Element externalid=testcase.addElement("externalid");
		externalid.setText(this.UpdateString(no));
		Element fullexternalid=testcase.addElement("fullexternalid");
		fullexternalid.setText(this.UpdateString(prefix));
		Element version=testcase.addElement("version");
		version.setText(this.UpdateString(v));
		Element summary=testcase.addElement("summary");
		summary.setText(this.UpdateString(sum));
		Element preconditions=testcase.addElement("preconditions");
		preconditions.setText(this.UpdateString(prec));
		Element execution_type=testcase.addElement("execution_type");
		execution_type.setText(this.UpdateString(t.GetExetype()));
		Element importance=testcase.addElement("importance");
		importance.setText(this.UpdateString(t.GetImp()));
		Element estimated_exec_duration=testcase.addElement("estimated_exec_duration");
		estimated_exec_duration.setText(t.GetExetime());
		Element status=testcase.addElement("status");
		status.setText(t.GetStatus());
		Element is_open=testcase.addElement("is_open");
		is_open.setText("1");
		Element active=testcase.addElement("active");
		active.setText("1");
		Element steps=testcase.addElement("steps");
		int n=t.GetStep().size();
		for(int i=0;i<n;i++){
			Element step1=steps.addElement("step");
			Element step_number=step1.addElement("step_number");
			step_number.setText(this.UpdateString(String.valueOf(i+1)));
			Element actions=step1.addElement("actions");
			actions.setText(this.UpdateString("<p>"+t.GetStep().get(i)+"</p>"));
			Element expectedresults=step1.addElement("expectedresults");
			expectedresults.setText(this.UpdateString("<p>"+t.GetExpected().get(i)+"</p>"));
			Element execution_type1=step1.addElement("execution_type");
			execution_type1.setText("<![CDATA[1]]>");
		}	
	}
	
	public void MakeXml(){
		OutputFormat format = OutputFormat.createPrettyPrint(); 
		format.setEncoding("UTF-8");
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		xw.setEscapeText(false); 
		BufferedWriter fw = null;
		try {
			xw.write(doc);
			xw.flush(); 
            xw.close(); 
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path,true),"UTF-8"));
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
	
	public String UpdateString(String s){
		StringBuilder sb=new StringBuilder();
		sb.append("<![CDATA[");
		sb.append(s);
		sb.append("]]>");
		return sb.toString();
	}
	
//	 public static void main(String[] args){
//		 IOXml xml=new IOXml("E:\\dd.xml");
//		 xml.WriteXml(new TestCase());
//		 xml.MakeXml();
//	 }
}
