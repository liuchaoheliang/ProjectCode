package com.froad.CB.AppException;

public class RuleTypeNotExists  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public RuleTypeNotExists(String message) {
        super(message);
    }

    public RuleTypeNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleTypeNotExists(Throwable cause) {
        super(cause);
    }
}