package com.froad.CB.AppException;

public class RuleStateIsNull  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public RuleStateIsNull(String message) {
        super(message);
    }

    public RuleStateIsNull(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleStateIsNull(Throwable cause) {
        super(cause);
    }
}