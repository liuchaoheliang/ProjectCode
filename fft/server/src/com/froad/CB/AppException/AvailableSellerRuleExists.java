package com.froad.CB.AppException;

public class AvailableSellerRuleExists  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public AvailableSellerRuleExists(String message) {
        super(message);
    }

    public AvailableSellerRuleExists(String message, Throwable cause) {
        super(message, cause);
    }

    public AvailableSellerRuleExists(Throwable cause) {
        super(cause);
    }
}
