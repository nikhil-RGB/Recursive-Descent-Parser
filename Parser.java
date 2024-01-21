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
	static final String EOE="\0";
	
	//Expression reference String
	private String exp;
	//Next token index into the expression to be parsed
	private int expIdx;
	//Current Token
	private String token;
	//Token type, [NONE,DELIMITER,VARIABLE,NUMBER]
	private int tokType;
	
	public Parser(String expression) 
	{
		this.exp=expression;
	}
	
	//Checks if input character is a delimiter
	private static boolean isDelimiter(char ch) 
	{
		if(" +-/*^=() ".indexOf(ch)!=-1) 
		{
			return true;
		}
		return false;
	}
	
	//Gets the next token, by accessing it via 
	private void getToken()  
	{
		this.token="";
		this.tokType=NONE;
		if(expIdx==exp.length()) 
		{
			//End of expression reached
			this.token=EOE;
			return;
		}
		//skip over all whitespaces after ensuring EOE is not already reached
		while(expIdx<exp.length()&&Character.isWhitespace(exp.charAt(expIdx))) 
		{
			++expIdx;
		}
		if(expIdx==exp.length()) 
		{
			//The expression ended with a whitespace.
			this.token=EOE;
			return;
		}
		
		if(isDelimiter(exp.charAt(expIdx))) 
		{
			this.token=exp.charAt(expIdx)+"";
			this.tokType=DELIMITER;
			++expIdx;
		}
		else if(Character.isLetter(exp.charAt(expIdx))) 
		{
			while(!isDelimiter(exp.charAt(expIdx))) 
			{
				if(Character.isDigit(exp.charAt(expIdx))) 
				{
					this.token=EOE;
					//this.tokType=NONE;
					//ERROR: INVALID SYNTAX
					System.err.println("Invalid syntax");
					return;
				}
				this.token+=exp.charAt(expIdx);
				++expIdx;
				if(expIdx>=exp.length()) 
				{
					break;
				}
			}
			this.tokType=VARIABLE;
		  
		}
		else if(Character.isDigit(exp.charAt(expIdx))) 
		{
			while(!isDelimiter(exp.charAt(expIdx))) 
			{
				if(Character.isLetter(exp.charAt(expIdx))) 
				{
					this.token=EOE;
					//this.tokType=NONE;
					//ERROR: INVALID SYNTAX
					System.err.println("Invalid syntax");
					return;
				}
				this.token+=exp.charAt(expIdx);
				++expIdx;
				if(expIdx>=exp.length()) 
				{
					break;
				}
				
			}
			this.tokType=NUMBER;
		}
		else 
		{
			//Invalid character
			this.token=EOE;
			//ERROR:INVALID SYNTAX
			System.err.println("Illegal character");
			return;
		}
	}
	
	//returns the type associated with <int> token type
	private static String type(int tktype) 
	{
		switch(tktype)
		{
		case 0:
		 return "NONE";
		case 1:
		 return "DELIMITER";
		case 2:
		 return "VARIABLE";
		case 3:
		 return "NUMBER";
		default:
		 return null;	 
		}
	}
	
	
	private static boolean isIllegalCharacter(char c) 
	{
		if(!(Character.isDigit(c)||Character.isLetter(c)||Parser.isDelimiter(c)||Character.isWhitespace(c)||c=='.')) 
		{
			return true;
		}
		return false;
	}
	
    //main method for parser execution
	public static void main(String[] args) {
	//Test Tokenization:
	String expression="A +100-(B*C)/2";
//		String expression="1000+B=C";
	if(expression.trim().isEmpty()) {
		System.out.println("Empty expression");
		return;
	}
	for(char c:expression.toCharArray()) 
	{
		if(Parser.isIllegalCharacter(c)) 
		{
			System.err.println("Illegal character detected!");
			return;
		}
	}
	Parser parser=new Parser(expression);
	parser.getToken();
	while(!parser.token.equals(EOE)) 
	{
		System.out.println(parser.token+"   "+type(parser.tokType));
		parser.getToken();
	}

	}
	
	

}
