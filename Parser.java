package parser;
import java.util.Scanner;
public final class Parser {
	
	//Token Types
	public static final int NONE=0;
	public static final int DELIMITER=1;
	public final int VARIABLE=2;
	public static final int NUMBER=3;
	
	//Types of errors which can occur during parsing
	public static final int SYNTAX=0;
	public static final int UNBALANCED_PARANS=1;
	public static final int NOEXP=2;
	public static final int DIVBYZERO=3;
	
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
	
	
	//Array of values for variables, A-Z
	double[] variables=new double[26];
	
	public Parser(String expression) 
	{
		this.exp=expression;
	}
	
	//Empty constructor to allow changing the expression to be parsed after a default initialization
	public Parser() 
	{
		this.exp="";
	}
	
	//Set or change the expression the parser should evaluate, soft-resets the parser, but does not affect assigned variables
	public void setExpression(String exp) 
	{
		this.exp=exp;
		this.expIdx=0;
	    this.token="";
	    this.tokType=Parser.NONE;
	}
	
	//Finds and returns the value for variables A-Z
	private double findValue(String name) throws ParserException 
	{
		if(!Character.isLetter(name.charAt(0))) 
		{
			handleError(Parser.SYNTAX);
            return 0.0;		
		}
		double value=variables[Character.toUpperCase(name.charAt(0))-'A'];
		return value;
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
//	String expression="A +100-(B*C)/2";
////		String expression="1000+B=C";
//	if(expression.trim().isEmpty()) {
//		System.out.println("Empty expression");
//		return;
//	}
//	for(char c:expression.toCharArray()) 
//	{
//		if(Parser.isIllegalCharacter(c)) 
//		{
//			System.err.println("Illegal character detected!");
//			return;
//		}
//	}
//	Parser parser=new Parser(expression);
//	parser.getToken();
//	while(!parser.token.equals(EOE)) 
//	{
//		System.out.println(parser.token+"   "+type(parser.tokType));
//		parser.getToken();
//	}
//
//	}
		
		Parser obj=new Parser();
		Scanner sc=new Scanner(System.in);
		PARSER_LOOP:
		while(true)
		{
		System.out.println("Input expression to be evaluated:");
		String expression=sc.nextLine();
		obj.setExpression(expression);
		
		try {
		double result=obj.evaluate();
		System.out.println(expression +"="+result);
		}
		catch(ParserException ex) 
		{
			sc.close();
			ex.printStackTrace();
			return;
		}
		
		System.out.println("Continue? y/n");
		if(!sc.nextLine().equalsIgnoreCase("y")) 
		{
			break PARSER_LOOP;
		}
//		System.out.println();
		}
		sc.close();
	}
	
	
	
	
	//throws a ParserException whenever the parser encounters a predefined error.
	private static  void handleError(int error) throws ParserException 
	{
	  String[] err_list= {
			  "Synatx error in Expression!",
			  "Unbalanced/Unmatched Paranthesis!",
			  "Empty Expression!",
			  "Division by Zero",
			  
	  };
	  throw new ParserException(err_list[error]);
	}
	
	//Parses a number 
	private double atom() throws ParserException
	{
		double result=0.0;
		switch(tokType) 
		{
		case NUMBER:
			try 
			{
				result= Double.parseDouble(token);
			}
			catch(NumberFormatException ex)
			{
				handleError(SYNTAX);
			}
			getToken();
			break;
		case VARIABLE:
			result=this.findValue(token);//index variable and find it's value
			getToken();
			break;
		default:
			handleError(SYNTAX);
		}
		return result;
	}
	
	//evaluates an assignment, if one is present. If not, it simply continues evaluation via add/sub eval.
	//lowest priority, 1st in recursive descent parsing.
	private double evaluateAssignment() throws ParserException
	{
		double result;
		int var_index;
		int tempType;
		String tempToken;
		
		if(this.tokType==VARIABLE) 
		{
		  //save the token, in case next operator is not assignment(=)
			tempToken=new String(this.token);
			tempType=this.tokType;
			//index at which to store assignment value.
			var_index=Character.toUpperCase(this.token.charAt(0))-'A';
			getToken(); //Proceed to next Token
			//If next token is an assignment, proceed, if not put back the token and move the index pointer back the required number of
			//spaces
			if(!token.equals("=")) 
			{
				//Restore index pointer for expression
				if(token!=EOE) 
				{
					for(int i=0;i<token.length();++i) 
					{
						this.expIdx--;
					}
				}
				
				//restore old token along with it's type
				this.token=new String(tempToken);
				this.tokType=tempType;
			}
			else 
			{
				getToken();//get expression after assignment operator to evaluate
				result=this.evaluateAddSub();
				this.variables[var_index]=result;
				return result;
				
				
			}
		
		}
		return this.evaluateAddSub();
		
	}
	
	//Evaluates Parenthesis in an expression-6th in order for recursive descent calls
	private double evaluateParans() throws ParserException
	{
		double result=0.0;
		
		if(token.equals("(")) 
		{
			getToken();
			result=evaluateAddSub();
			if(!token.equals(")")) 
			{
				//Expression had an opening parenthesis but no closing parenthesis to match.
				handleError(Parser.UNBALANCED_PARANS);
			}
			getToken();//Move to next Token
		}
		else 
		{
			//Token is not a parenthesis and all other operations have already been checked in the recursion chain,
			//so it must be a number
			result=atom();
		}
		return result;
	}
	
	
	//Evaluate a unary operator (+/-), 5th in order for recursive descent calls
	private double evaluateUnary() throws ParserException
	{
		double result=0.0;
		String operator="";
		if(tokType==Parser.DELIMITER&&(token.equals("+")||token.equals("-"))) 
		{
			operator=this.token;
			getToken();
		}
		result=this.evaluateParans();//Recursive call to 6th method for evaluating paranthesis,higher priority than 
		//unary operators.
		if(operator.equals("-")) 
		{
			result=-result;
		}
		return result;
	}
	
	//Evaluate an exponent,4th in order for recursive descent calls
	private double evaluateExponent() throws ParserException 
	{
		double result=0.0;
		double partialResult=0.0;                              
		result=this.evaluateUnary();//call to 5th method in the recursive descent parser chain.
        if(token.equals("^")) 
        {
        	getToken();//exponent can be invoked multiple times
        	partialResult=evaluateExponent();//recursive call to calculate exponent, if exponentiation symbol repeats itself.
            result=Math.pow(result, partialResult);//Final calculation for exponentiation. 
        }
		return result;	
	}
	
	//Evaluates multiplication and division, 3rd in the parse tree chain
	private double evaluateMultiDiv() throws ParserException
	{
		char op;
		double result;
		double partialResult;
		result=this.evaluateExponent();
		while(((op=token.charAt(0))=='*')||(op=='/')||(op=='%')) 
		{
			getToken();
			partialResult=evaluateExponent();
			switch(op)
			{
			case '*':
				result=result*partialResult;
				break;
			case '/':
				if(partialResult==0) 
				{
					Parser.handleError(DIVBYZERO);
				}
				result=result/partialResult;
				break;
			case '%':
				if(partialResult==0) 
				{
					Parser.handleError(DIVBYZERO);
				}				
				result=result%partialResult;
		    }
			
		}
		
		return result;
	}
	
	//Evaluate Addition and Subtraction-This function is second in the chain of recursive calls.
	private double evaluateAddSub() throws ParserException
	{
		double result=0.0;
		double partialResult=0.0;
		char op;
		result=this.evaluateMultiDiv();//Call to higher priority multiplication and division evaluation.
		while(((op=token.charAt(0))=='+')||(op=='-')) 
		{
			getToken();
			partialResult=this.evaluateMultiDiv();
			switch(op) 
			{
			case '+':
				result= result+partialResult;
				break;
			case '-':
				result=result-partialResult;
				break;
				
			}
		}
		return result;
		
	}
	
	//Evaluate the mathematical expression defined within this Parser Object
	public double evaluate() throws ParserException 
	{
		double result=0.0;
		getToken(); //Get the first token
		if(token.equals(Parser.EOE)) 
		{
	     handleError(Parser.NOEXP);
		}
		result=this.evaluateAssignment();
		if(!token.equals(EOE)) 
		{
			Parser.handleError(SYNTAX);
		}
		return result;
	}
	
	
	
	

}
