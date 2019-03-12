package Core;

import java.util.ArrayList;
import java.util.List;

public class TestCase {
	
	private String testname;		//测试用例标题
	private String author;		//用例设计人
	private List<String> expected=new ArrayList<String>();		//期望结果
//	private String[] step;
	private List<String> step=new ArrayList<String>();		//步骤
	private String version;		//测试用例版本
	private String status;		//状态
	private String imp;			//测试用例重要性
	private String exetype;		//测试方式
	private String exetime;		//执行时间
	private String summary;		//摘要
	private String prec;		//前提
	private int no;		//测试用例编号
	private String prefix;		//项目前缀
	
	public void SetTestName(String s){
		this.testname=s;
	}
	public void SetAuthor(String s){
		this.author=s;
	}
	public void SetExpected(String s){
		this.expected.add(s);
	}
//	public void SetStep(String[] s){
//		System.arraycopy(s, 0, this.step, 0, s.length);
//	}
	public void SetStep(String s){
		this.step.add(s);
	}
	public void SetStatus(String s){
		this.status=s;
	}
	public void SetImp(String s){
		this.imp=s;
	}
	public void SetExeType(String s){
		this.exetype=s;
	}
	public void SetExeTime(String s){
		this.exetime=s;
	}
	public void SetVersion(String s){
		this.version=s;
	}
	public void SetSummary(String s){
		this.summary=s;
	}
	public void SetPrec(String s){
		this.prec=s;
	}
	public void SetPrefix(String s){
		this.prefix=s;
	}
	public void SetNo(int i){
		this.no=i;
	}
	
	public String GetTestName(){
		return this.testname;
	}
	public String GetAuthor(){
		return this.author;
	}
	public List<String> GetExpected(){
		return this.expected;
	}
	public List<String> GetStep(){
		return this.step;
	}
	public int GetNo(){
		return this.no;
	}
	public String GetPrefix(){
		return this.prefix;
	}
	public String GetVersion(){
		return this.version;
	}
	public String GetSummary(){
		return this.summary;
	}
	public String GetPrec(){
		return this.prec;
	}
	public String GetExetype(){
		return this.exetype;
	}
	public String GetImp(){
		return this.imp;
	}
	public String GetExetime(){
		return this.exetime;
	}
	public String GetStatus(){
		return this.status;
	}
}
