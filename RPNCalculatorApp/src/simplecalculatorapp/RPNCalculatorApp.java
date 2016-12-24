package simplecalculatorapp;

import java.util.Deque;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.Scanner;
import java.util.Objects;
import static java.lang.String.format;


public class RPNCalculatorApp {
	
	private static final Set<String> EXIT_COMMANDS;
	private static final Set<String> HELP_COMMANDS;
	private static final String HELP_MESSAGE;
	
	/*
	 * Adapted from 
	 * http://stackoverflow.com/a/26446609
	 * 
	 * Notes to self: SortedSet is an interface, TreeSet is a class that implements 
	 * that interface. "String.CASE_INSENSITIVE_ORDER" is a comparator for the TreeSet 
	 * ecmds, which is created empty.
	 * 
	 * In Java, List is an interface, Array is the class that implements it.
	 * Therefore, Arrays.asList returns a fixed-size list "backed by the specified array."
	 * 
	 * Collections.unmodifiableSet "Returns an unmodifiable view of the specified sorted set."
	 */
	
	static
    {
        final SortedSet<String> ecmds = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        ecmds.addAll(Arrays.asList("exit", "done", "quit", "end"));
        EXIT_COMMANDS = Collections.unmodifiableSortedSet(ecmds);
        
        final SortedSet<String> hcmds = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        hcmds.addAll(Arrays.asList("help", "?"));
        HELP_COMMANDS = Collections.unmodifiableSet(hcmds);
        
        HELP_MESSAGE = format("You're using a very simple calculator! " +
        		"" +
        		"\nPlease enter an expression followed by '=' " +
        		"\nor enter one of the following commands to exit %s", EXIT_COMMANDS);
    }
	
    private static void output(final String format, final Object... args) {
        System.out.println(format(format, args));
    }
	
	public static void main(String[] args) {

		final Scanner scan = new Scanner(System.in);
		final StringBuilder sendToTokenizer = new StringBuilder();
        output(HELP_MESSAGE);
        
        while (scan.hasNext()) {
        	final String userIn = scan.next();
        	
        	if (EXIT_COMMANDS.contains(userIn)) {
        		output("Exit command %s issued, exiting!", userIn);
        		break;
                }
        	else if (HELP_COMMANDS.contains(userIn)) { 
        		output(HELP_MESSAGE); 
        	}
        	else {
        		sendToTokenizer.append(userIn);
        	}  
        	
        	if ( Objects.equals( userIn, "=") ) {
        		final Tokenizer tokenizer = new Tokenizer( sendToTokenizer.toString() );
        		Deque<Token> mathTokens = tokenizer.getTokens();
        		
        		final PostfixGenerator pfg = new PostfixGenerator();
        		mathTokens = pfg.getPostfix( mathTokens );
        		/*
        		for (Token i : mathTokens) {
        			System.out.println( String.valueOf( i.getValue() ) );
        		}
        		*/

        		final Calculator calc = new Calculator( mathTokens );
        		calc.calculate();
        		output("The answer is: %f", calc.answerMe() );
        		sendToTokenizer.delete( 0 , sendToTokenizer.length() );
        		
        		// TODO send the LinkedList of tokens  to a shunting yard algorithm which converts
        		// TODO infix into RPN, and then to a RPN calculator for computation. Return result of
        		// TODO computation here.
        	}
        }
        scan.close();
        System.exit(0);
        
        /*   This will close the underlying Readable, in this case System.in, and free those resources.
         *   You will not be to read from System.in anymore after this you call .close().
         *   If you wanted to use System.in for something else, then don't close the Scanner.
         */  
        
	}
}
