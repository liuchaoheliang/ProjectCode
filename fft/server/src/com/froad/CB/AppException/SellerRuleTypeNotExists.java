package com.froad.CB.AppException;

public class SellerRuleTypeNotExists  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public SellerRuleTypeNotExists(String message) {
        super(message);
    }

    public SellerRuleTypeNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public SellerRuleTypeNotExists(Throwable cause) {
        super(cause);
    }
}
