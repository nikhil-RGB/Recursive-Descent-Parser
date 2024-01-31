# Recursive-Descent-Parser:

### Description:

  The recursive descent parser aims at solving basic mathematical expressions via recursive calls to functions dedicated to evaluating particular 
  operations, such as addition, multiplication and division.
  The program can also evaluate variables, variables must start with A-Z and can be of any length, but only the first letter will be 
  significant/considered for evaluation.
  
  <br>
  
  An example of an expression which can be evaluated is ```(3+4)/7^2/4+(4+5)/95```, ```result=0.13045112781954887```.

  An example of using variables for evaluation is as follows:
 <br>
 ```
Input expression to be evaluated:
A=(2*2^2)/(3+5)
A=(2*2^2)/(3+5)=1.0
Continue? y/n
y
Input expression to be evaluated:
B=(7+7)/2
B=(7+7)/2=7.0
Continue? y/n
y
Input expression to be evaluated:
C=A+B
C=A+B=8.0
Continue? y/n
y
Input expression to be evaluated:
C
C=8.0
Continue? y/n
y
Input expression to be evaluated:
C=(5)
C=(5)=5.0
Continue? y/n
```
  
  <br>

> [!CAUTION]
> Do NOT attempt to use variables while assigning them(as is common practice in most programming languages), expressions such as ```(A=(5+7))/8```
> will cause the parser to fail with a ```ParserException(Unmatched Paranthesis)```.
> Using a variable without assigning it a value will cause it to hold a value of 0.0, which may cause operations such as number/variable to cause parser failure.
  
The parser works by recursive calls to functions which execute a particular operation, such as addition/subtraction,multiplication/division,exponentiation,etc.
  Laws of mathematical precedence are followed by calling the lowest level operation first(assignment evaluation), then one of higher precedence(addition/subtraction), which then calls a function of higher precedence again (in this case, multiplication/division) and so on- before actually performing
  it's own operation, using the recursive result of the operation as a partial result to complete the current operation. The function calls work recursively as: 

<br>

  ```evaluate()->evaluateAssignment()->evaluateAddSub()->evaluateMultiDiv()->evaluateExponent()->evaluateUnary()->evaluateParans()->atom()```

<br>

```evaluateParans()``` calls ```evaluateAddSub()``` again after it completes evaluating paranthesis to continue calculations. It aslo calles ```atom()``` to parse a number or variable for calculations if a bracket is not found.

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
    <br>
    <img width="431" alt="image" src="https://github.com/nikhil-RGB/Recursive-Descent-Parser/assets/68727041/b2cfd497-e028-4b34-a056-35f469583eea">
    <br>
    <img width="528" alt="image" src="https://github.com/nikhil-RGB/Recursive-Descent-Parser/assets/68727041/27512b5b-4726-4b29-9a7e-0136888d9fd0">

