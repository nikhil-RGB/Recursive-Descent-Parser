package parser;

public final class Parser {
	//Token Types
	final int NONE=0;
	final int DELIMITER=1;
	final int VARIABLE=2;
	final int NUMBER=3;
	
	//Types of errors which can occur during parsing
	final int SYNTAX=0;
	final int UNBALANCED_PARANS=1;
	final int NOEXP=2;
	final int DIVBYZERO=3;
	
	//Token to indicate end of expression, indicating the parser should stop.
	final String EOE="\0";
	
	//Expression reference String
	private String exp;
	//Next token index into the expression to be parsed
	private int expIdx;
	//Current Token
	private String token;
	//Token type, [NONE,DELIMITER,VARIABLE,NUMBER]
	private int tokType;
	
	
    //main method for parser execution
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	

}
