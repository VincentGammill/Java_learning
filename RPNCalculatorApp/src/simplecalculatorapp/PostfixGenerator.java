package simplecalculatorapp;

import java.util.ArrayDeque;
import java.util.Deque;



/*
 * Next phase in calculator project: allow it to read infix
 * commands and parse parentheses.
 * 
 * Another goal is to enable the CalculatorApp to deal with users
 * who don't want to put spaces between everything.
 * 
 * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 * 
 * http://stackoverflow.com/questions/26446599/how-to-use-java-util-scanner-to-correctly-read-user-input-from-system-in-and-act
 * 
 */

/*
 * This class reorders a Deque (as ArrayDeque) of Tokens representing infix notation
 * into a Deque of Tokens ordered in reverse polish notation (operator last).
 */

public class PostfixGenerator {

	private final Deque<Token> infixStack;
	private final int infixStackCounter;
	private final Deque<Token> operators = new ArrayDeque();  // The stack of waiting operators
	private final Deque<Token> postfixStack = new ArrayDeque(); // The desired, reordered result!
	
	
	public PostfixGenerator(ArrayDeque<Token> infixStack) {
		this.infixStack = infixStack;
		this.infixStackCounter = infixStack.size();
	}
	
	public void reorder() {
		Token currToken;
		while ( infixStackCounter > 0 ) {
			currToken = infixStack.removeFirst();
			switch( currToken.getToken() ) {
				case NUMBER: 
					postfixStack.addFirst(currToken);
				case 
			}
			
		}
	}

}
