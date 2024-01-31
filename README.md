# Recursive-Descent-Parser:

### Description:

  The recursive descent parser aims at solving basic mathematical expressions via recursive calls to functions dedicated to evaluating particular operations, such as addition, multiplication and division.
  The program currently cannot evaluate variables, but this will be changed in the future. 
  
  <br>
  
  An example of an expresion which can be evaluated is ```(3+4)/7^2/4+(4+5)/95```, ```result=0.13045112781954887```.
  
  <br>
  
  The parser works by recursive calls to functions which execute a particular operation, such as addition/subtraction,multiplication/division,exponentiation,etc.
  Laws of mathematical precedence are followed by calling the lowest level operation first(addition/subtraction), which then calls a function of higher precedence(in this case, multiplication/division) before actually performing
  it's own operation, using the recursive result of the operation as a partial result to complete the current operation. The function calls work recursively as: 

<br>

  ```evaluate()->evaluateAddSub()->evaluateMultiDiv()->evaluateExponent()->evaluateUnary()->evaluateParans()->atom()```

<br>

```evaluateParans()``` calls ```evaluateAddSub()``` again after it completes evaluating paranthesis to continue calculations. It aslo calles ```atom()``` to parse a number for calculations if a bracket is not found.
  A more detailed analysis will be provided when a variable evaluation function is added.

  > [!NOTE]  
> This program does not construct a  Parse Tree to solve the expression!

  

  ### How to use:

  - Install JAVA 8 on the system.
  - Clone this repository with ```git clone```.
  - Execute the ```main()``` function of ```Parser.java```.
  - Input the mathematical expression to be solved.

    Example:

    <br>

    <img width="305" alt="image" src="https://github.com/nikhil-RGB/Recursive-Descent-Parser/assets/68727041/4c5f00cf-bf63-4132-9e43-b79be029d6b3">
