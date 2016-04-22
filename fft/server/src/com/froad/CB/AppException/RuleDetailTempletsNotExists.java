package com.froad.CB.AppException;

public class RuleDetailTempletsNotExists extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public RuleDetailTempletsNotExists(String message) {
        super(message);
    }

    public RuleDetailTempletsNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleDetailTempletsNotExists(Throwable cause) {
        super(cause);
    }
}
