package Core;

public interface Status {
	public static final String DRAFT  = "1";
	public static final String PENDING_REVIEW  = "2"; 
	public static final String REVIEW  = "3";
	public static final String  REDO = "4";
	public static final String DISCARD  = "5";
	public static final String FUTURE  = "6";
	public static final String FINAL  = "7";
	public static final String UNKOWN  = "0";
}
