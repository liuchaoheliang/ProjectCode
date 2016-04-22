package com.froad.CB.AppException;

public class RuleTypeIsNull  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public RuleTypeIsNull(String message) {
        super(message);
    }

    public RuleTypeIsNull(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleTypeIsNull(Throwable cause) {
        super(cause);
    }
}