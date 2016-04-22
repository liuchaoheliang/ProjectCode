package com.froad.CB.AppException;

public class RuleStateNotExists  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public RuleStateNotExists(String message) {
        super(message);
    }

    public RuleStateNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleStateNotExists(Throwable cause) {
        super(cause);
    }
}