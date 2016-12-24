package simplecalculatorapp;

import java.util.ArrayDeque;
import java.util.Deque;



/*
 *Next phase in calculator project: allow it to read infix
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
 * The algorithm it uses to do so is called Diijkstra's Shunting Yard Algorithm.
 */

public class PostfixGenerator {

	private Deque<Token> infixStack = new ArrayDeque<Token>(); 	// The inputed stack of Tokens
	private final Deque<Token> operatorStack = new ArrayDeque<Token>();  // The stack of waiting operators
	private final Deque<Token> postfixStack = new ArrayDeque<Token>(); // The desired, reordered result!
	
	/*
	 * This method clears all the stacks out, to ensure consistent behavior!
	 */
	public void reset() {
		infixStack.clear();
		operatorStack.clear();
		postfixStack.clear();
	}
	
	public Deque<Token> getPostfix( Deque<Token> inputTokens) {
		infixStack = inputTokens;
		int infixCounter = infixStack.size();
		int operatorCounter = 0;
		Token lastOperator; // where I'll store operator Tokens I'm pulling off the operator Stack
		while ( infixCounter > 0 ) {
			
		/*	Some print statements to see what all is going on inside this horrible logic.
		 * 
			System.out.println( "infixStack: ");
			for (Token i : infixStack) {
  			System.out.println( String.valueOf( i.getValue() ) );
  		}
			System.out.println( "operatorStack: ");
			for (Token j : operatorStack) {
				System.out.println( String.valueOf( j.getValue() ) );
			}
  		System.out.println( " postfixStack: ");
  		for (Token k : postfixStack) {
  			System.out.println( String.valueOf( k.getValue() ) );
  		}
			System.out.println("\n");
		
		*/
  		
			/*
			 * This logic is horrible to read, but remember it implements a well-known algorithm
			 * the Shunting Yard. 
			 */
			final Token currToken = infixStack.removeFirst();
			infixCounter--;
			switch( currToken.getType() ) {
				case NUMBER: {
					postfixStack.addFirst(currToken);
					break;
				}
				case LEFT_PARENTHESIS: {
					operatorStack.addFirst(currToken);
					operatorCounter++;
					break;
				}
				case RIGHT_PARENTHESIS: {
					while (operatorCounter > 0) {
						lastOperator = operatorStack.removeFirst();
						operatorCounter--;
						// TODO System.out.println(lastOperator.getType());
						if ( lastOperator.getType() == TokenType.LEFT_PARENTHESIS ) {
							break;
						}
						else if (lastOperator.getType() == TokenType.OPERATOR) {
							postfixStack.addFirst(lastOperator);
						}
					}
					break;
					// TODO code here for what to return if there is no matching parenthesis?
				}
				case OPERATOR: {
					if (operatorCounter > 0) {
						while(operatorCounter > 0) {
							lastOperator = operatorStack.removeFirst();
							operatorCounter--;
							if ( currToken.getStrength() > lastOperator.getStrength() )  {
								postfixStack.addFirst( lastOperator );
							}
							else {
								operatorStack.addFirst( lastOperator); // it didn't go to output, so continue storing in OpStack
								operatorStack.addFirst( currToken );
								operatorCounter += 2;
								break;
							}
						}
					} 
					else {
						operatorStack.addFirst( currToken );
						operatorCounter++;
					}
				}
				default: {
					//TODO warn user if there's an equals sign in there or UNKNOWN token?
					break;
				}
			}
			
		}
		while (operatorCounter > 0 ) {
			postfixStack.addFirst( operatorStack.removeFirst() );
			operatorCounter--;
		}
		return postfixStack;
	}

}
