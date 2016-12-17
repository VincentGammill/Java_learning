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
	
	private final Deque<Double> numbers = new ArrayDeque<Double>(); // This is where the calculator stores numbers
	private byte countStack = 0; 		// A counter for the Stack so I'm not constantly whether it's empty or not.
	
	public Calculator( ) {
		/*
		 * Empty constructor for now...not sure what, if anything, should go here.
		 * One possibility: rewrite this class so that it extends ArrayDeque??
		 */
	}
	
	//A method to pass the Calculator a double for it to stick on top of the Stack.
	public void add( final double d) {
		numbers.addFirst( d );
		countStack++;   // Must increment countStack!
	}
	
	public void operate( final String s) {
		final String operation = s;	// The string representing the binary operation to be performed
		if (countStack >= 2) {
	    	double numA = 0;  // The leading number in a binary arithmetic operation
	    	double numB = 0;  // The final number in a binary arithmetic operation
			numA = numbers.removeFirst();
			numB = numbers.removeFirst();
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
    			numbers.addFirst( numA / numB );
    			break;
        	}
    		countStack--;   // All binary operations reduce the Stack count by 1
		}
		else {
			/* The user input was screwed up or the Scanner logic is screwed up. Either way,
			 * just start the calculator over.
			 */
			numbers.clear();
			countStack = 0;
			System.out.println("Error: not enough numbers on the stack.");
		}

	}
	
	public double print() {
		if (countStack > 0 ) {
			double result = numbers.removeFirst();
			
			/*
			 * Once the calculator has given an answer, clear out the entire stack, but leave previous
			 * answer on there (for a "do this to the previous answer" neat-o feature.) Watch this for
			 * unwanted side-effects.
			 */
			
			numbers.clear();
			numbers.addFirst(result);
			countStack = 1;
			return result;
		}
		else return 0;
	}


	

}
