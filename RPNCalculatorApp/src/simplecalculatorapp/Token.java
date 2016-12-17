package simplecalculatorapp;

// code borrowed from and inspired by Heletek at https://gist.github.com/heletek

enum TokenType {
	OPERATOR, RIGHT_PARENTHESIS, LEFT_PARENTHESIS, NUMBER, EQUALS, UNKNOWN
}

public class Token {

	private final TokenType type;
	private final String value;
	
	public Token(String value, TokenType type) {
		this.type = type;
		this.value = value;
	}
	
	public Token(char value, TokenType type) {
		this.type = type ;
		this.value = String.valueOf(value);
	}
	
	public TokenType getToken() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	// For now I'll leave this here
	/*
	 * getStrength checks operator precedence basically. Higher precedence
	 * triggers the operator stack to dump in my shunting algorithm.
	 */
	
    public int getStrength() {
        if (type == TokenType.OPERATOR) {
            switch (value.charAt(0)) {
                case '+':
                case '-':
                    return 1;
                case '/':
                case '*':
                    return 2;
                case '^':
                    return 3;
                default:
                    return -1;
            }
        }
        else if (type == TokenType.RIGHT_PARENTHESIS || type == TokenType.LEFT_PARENTHESIS)
            return 4;
        
        return -1;
    }
    
}




