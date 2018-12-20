package IOExcel;

public class TestCase {
	
	private String testname;
	private String author;
	private String expected;
//	private String[] step;
	private String step;
	
	public void SetTestName(String s){
		this.testname=s;
	}
	public void SetAuthor(String s){
		this.author=s;
	}
	public void SetExpected(String s){
		this.expected=s;
	}
//	public void SetStep(String[] s){
//		System.arraycopy(s, 0, this.step, 0, s.length);
//	}
	public void SetStep(String s){
		this.step=s;
	}
	
	public String GetTestName(){
		return this.testname;
	}
	public String GetAuthor(){
		return this.author;
	}
	public String GetExpected(){
		return this.expected;
	}
	public String GetStep(){
		return this.step;
	}

}
