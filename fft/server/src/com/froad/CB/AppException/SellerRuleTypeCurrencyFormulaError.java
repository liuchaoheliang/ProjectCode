package com.froad.CB.AppException;

public class SellerRuleTypeCurrencyFormulaError  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public SellerRuleTypeCurrencyFormulaError(String message) {
        super(message);
    }

    public SellerRuleTypeCurrencyFormulaError(String message, Throwable cause) {
        super(message, cause);
    }

    public SellerRuleTypeCurrencyFormulaError(Throwable cause) {
        super(cause);
    }
}
