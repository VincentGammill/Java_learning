package simplecalculatorapp;

//code borrowed from and inspired by Heletek at https://gist.github.com/heletek


import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.LinkedList;


class Tokenizer {
	
	private static final Set<Character> OPERATIONS;
    private String input;
    private LinkedList<Token> tokens = new LinkedList<Token>();
    
    static {		
    	// this is a static block, executed when class is loaded into JVM. Huh.
    	final SortedSet<Character> ops = new TreeSet<Character>();
        ops.addAll( Arrays.asList('+', '-', '*', '/', '^') );
        OPERATIONS = Collections.unmodifiableSortedSet(ops);
    }
    
    
    public Tokenizer(String input) {
    	this.input = input;
    }
    
    public LinkedList<Token> getTokens()
    {
        StringBuilder currentlyBuilt = new StringBuilder();
        TokenType lastType = null;
        boolean checkForOthers = true;
        boolean currentlyNoDecimals = true;
        
        for (int i = 0; i < input.length(); i++)
        {
            char currentChar = input.charAt(i);
            
            if (Character.isDigit(currentChar) || (lastType != TokenType.NUMBER && currentChar == '-') || (currentChar == '.' && currentlyNoDecimals))
            {
            	if (currentChar == '.')
            		currentlyNoDecimals = false;
            	
            	checkForOthers = false;
                currentlyBuilt.append(currentChar);
                lastType = TokenType.NUMBER;
            }
            else if (lastType == TokenType.NUMBER)
            {
            	currentlyNoDecimals = true;
            	
                tokens.add(new Token(currentlyBuilt.toString(), TokenType.NUMBER));
                currentlyBuilt.delete(0, currentlyBuilt.length());
            }
            
            if (checkForOthers)
            {
	            if (currentChar == ')')
	            	lastType = TokenType.RIGHT_PARENTHESIS;
	            else if (currentChar == '(') {
	            	lastType = TokenType.LEFT_PARENTHESIS;
	            }
	            else if (OPERATIONS.contains(currentChar))
	                lastType = TokenType.OPERATOR;
	            else if (currentChar == '=')
	            	lastType = TokenType.EQUALS;
	            else
	            	lastType = TokenType.UNKNOWN;

	            tokens.add(new Token(currentChar, lastType));
            }
            
            checkForOthers = true;
        }
        
        if (currentlyBuilt.length() > 0)
            tokens.add(new Token(currentlyBuilt.toString(), TokenType.NUMBER));
        
        return tokens;
    }
}