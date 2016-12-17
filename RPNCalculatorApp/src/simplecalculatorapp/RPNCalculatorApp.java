package simplecalculatorapp;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.Objects;
import static java.lang.String.format;


public class RPNCalculatorApp {
	
	private static final Set<String> EXIT_COMMANDS;
	private static final Set<String> HELP_COMMANDS;
	private static final Set<String> OPERATIONS;
	private static final String HELP_MESSAGE;
	
	/*
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
        
        final SortedSet<String> ops = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        ops.addAll(Arrays.asList("+", "-", "*", "/", "^"));
        OPERATIONS = Collections.unmodifiableSortedSet(ops);
        
        HELP_MESSAGE = format("You're using a very simple calculator! " +
        		"" +
        		"\nPlease enter a computation in Reverse Polish Notation" +
        		"\nor enter one of the following commands to exit %s", EXIT_COMMANDS);
    }
	

	public static boolean isValidOperator(char maybeOperator) {
		if (OPERATIONS.contains( maybeOperator )) {
			return true;
		}
		else { 
			return false;
		}
	}
	
    private static void output(final String format, final Object... args)
    {
        System.out.println(format(format, args));
    }
	
	public static void main(String[] args) {
        
		final Calculator calc = new Calculator();
		final Scanner scan = new Scanner(System.in);
        output(HELP_MESSAGE);
        while (scan.hasNext())
        {
            if (scan.hasNextInt())
            {
                final double next = scan.nextInt();
                calc.add(next);
            }
            else if (scan.hasNextLong())
            {
                final double next = scan.nextLong();
                calc.add(next);
            }
            else if (scan.hasNextDouble())
            {
                final double next = scan.nextDouble();
                calc.add(next);
            }
            
            else if (scan.hasNextBoolean())
            {
                final boolean nextBool = scan.nextBoolean();
                final double next = nextBool ? 1.0 : 0;
                calc.add(next);
            }
            
            /*
             * Currently no plan for what to do with BigIntegers...
             * but the calculator currently just tells the user and
             * politely ignores them.
             */
            
            //else if (scan.hasNext("\\d+")) {
            //	final BigInteger nextBigInt = scan.nextBigInteger();
            //	output("You entered a BigInteger: %s", nextBigInt);
            //}
            
   
            else // not a number
            {
                final String next = scan.next();
             
                if (EXIT_COMMANDS.contains(next)) {
                        output("Exit command %s issued, exiting!", next);
                        break;
                }
                else if (HELP_COMMANDS.contains(next)) { 
                	output(HELP_MESSAGE); 
                }
                else if (OPERATIONS.contains(next)) {
                	calc.operate(next);

                }  
                else if ( Objects.equals( next, "=") ) {
                	final double result = calc.print();
                	output("The answer is: %f", result); 
                }
                // unclassified
                
                else { 
                	output("You entered an unclassified String: %s", next); 
                }
            }

        }
		
		

        /*   This will close the underlying Readable, in this case System.in, and free those resources.
         *   You will not be to read from System.in anymore after this you call .close().
         *   If you wanted to use System.in for something else, then don't close the Scanner.
         */  

     scan.close();
     System.exit(0);
	}

}
