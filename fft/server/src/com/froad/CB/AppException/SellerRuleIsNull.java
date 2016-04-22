package com.froad.CB.AppException;

public class SellerRuleIsNull  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public SellerRuleIsNull(String message) {
        super(message);
    }

    public SellerRuleIsNull(String message, Throwable cause) {
        super(message, cause);
    }

    public SellerRuleIsNull(Throwable cause) {
        super(cause);
    }
}
