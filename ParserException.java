package parser;
class ParserException extends Exception
{
	private static final long serialVersionUID=-998765L;

	public ParserException() 
	{
		super();
	}
    public ParserException(String mssg) 
    {
    	super(mssg);
    }
}