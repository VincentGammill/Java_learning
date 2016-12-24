package simplecalculatorapp;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * This class performs a single binary arithmetic operation on a Stack of
 * doubles (actually a Deque implemented as an ArrayDeque), maintains that Stack,
 * pulls numbers off the Stack and performs operations on them. It has a method to return
 * the top number of the stack.
 * 
 * Also note this Calculator is designed to implement Reverse Polish Notation.
 * It's may be possible that it can do other things, but that isn't my goal.
 */

public class Calculator {
	/*
	 * numbers is where the calculator puts doubles to hold for calculations.
	 */
	private final Deque<Double> numbers = new ArrayDeque<Double>(); 
	private final Deque<Token> postfixDeque;
	private int numCount = 0; 		// Counter for the Stack so constantly checking if empty
	private int postfixCount;
	
	public Calculator( Deque<Token> postfixDeque ) {
		this.postfixDeque = postfixDeque;
		this.postfixCount = postfixDeque.size();
	}
	
	/*
	 * A little method to store the number value of a token
	 */
	
	public void clearMem() {
		numbers.clear();
		postfixDeque.clear();
		numCount = 0;
		postfixCount = 0;
	}
	
	private void store(Token numberToken) {
		numbers.addFirst( Double.parseDouble( numberToken.getValue() ) );	
		numCount++;
		//System.out.println("Number " + numberToken.getValue() + " added to number stack");
	}
	
	public double answerMe() {
		if (numCount > 0 ) {
			double result = numbers.removeFirst();
			
			/*
			 * Once the calculator has given an answer, clear out the entire stack, but leave previous
			 * answer on there (for a "do this to the previous answer" neat-o feature.) Watch this for
			 * unwanted side-effects.
			 */
			
			numbers.clear();
			numCount = 0;
			return result;
		}
		else return 0;
	}
	
	public void calculate() {
		while(postfixCount > 0) {
			Token currToken = postfixDeque.removeLast();
			postfixCount--;
			if ( currToken.getType() == TokenType.NUMBER ) {
				store(currToken);
			}
			else if (currToken.getType() == TokenType.OPERATOR ) {
				operate( currToken.getValue() );
				// TODO remove this: System.out.println("Operation \'" + currToken.getValue() + "\' performed");
			}
			// TODO if it's not a number or operator, should do something to warn user
		}
	}
	/*
	 * A method that performs a single (binary) operation and pushes the resulting
	 * number back onto the numbers Deque.
	 */
	public void operate( final String s ) {
		final String operation = s;	// The string representing the binary operation to be performed
		if (numCount >= 2) {
			double numA = numbers.removeFirst();
			double numB = numbers.removeFirst();
			switch (operation) {
				case "+":
					numbers.addFirst( numA + numB );
					break;
    		case "-":
    			numbers.addFirst( numB - numA );	// order is important here! Reading left-to-right means
    			break;								// that the 2nd number popped from Stack is the 1st number
    		case "*":								// in the subtract operation.
    			numbers.addFirst( numA * numB );
    			break;
    		case "/":
    			numbers.addFirst( numB / numA );
    			break;
    		case "^":
    			numbers.addFirst( Math.pow(numB, numA));
			}
    		numCount--;   // All binary operations reduce the Stack count by 1
		}
		else {
			/* The user input was screwed up or the Scanner logic is screwed up. Either way,
			 * just start the calculator over.
			 */
			numbers.clear();
			numCount = 0;
			System.out.println("Error: not enough numbers on the stack.");
		}

	}
	
}
