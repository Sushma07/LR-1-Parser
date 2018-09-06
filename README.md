# LR-1-Parser

This takes an LR(1) Parse Table as input(Sample can be seen in ParseTable.txt) and does the LR(1) parsing and accepts it the given input is as per the Grammar.Else, it rejects.

Grammar considered for testing was :
S1 -> S
S -> L = R 
S -> R
L -> * R
L -> id
R -> L

Dependencies:
1.	Productions.txt : a file with all a production in a new line and different symbols separated by “ “ [space]

2.	ParseTable.txt  : a file with parse table In the order ( State, ACTIONS ["=" , "*", "id","$" ], GOTO ["S","L","R" ]in order . If a value is unknown , it has to be filled with “-“ (instead of leaving blank)

3.	Input.txt : Typically, lexical analyzer has to tokenize the input and feed the system. But as the question doesn’t mention much about the non-terminals being considered, the input is directly taken by the parser here. This has to terminate with a “$” symbol

Input : Absolute Paths for all the 3 files

Output : at each step, the stack top and input pointer and the action/ goto being performed.
If the action is a reduction, it prints the rule being used to reduce.

Algorithm :

Variables maintained : A stack to store the states, the parse Table, an input pointer
Intialize stack with the start state : 0 and input pointer pointing to first input symbol.

While(input pointer != input.size()  && stack is not empty){
 ACT  = ACTION ( stack Top, current input symbol )
  If ACT == (Si) 
	Push i onto the stack 
move the input pointer

Else if ACT = Ri
	If rule i is of form A -> B
	Pop |B| number of elements from stack
	If state j = GOTO (new stack Top, current input symbol)
          	Push j on to the stack;

Else if ACT = accept
 	Accept the String
	Break;
 Else If the entry is null 
		reject
}



