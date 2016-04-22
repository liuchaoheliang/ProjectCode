package com.froad.CB.AppException;

public class SellerRuleTypePointsFormulaError  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public SellerRuleTypePointsFormulaError(String message) {
        super(message);
    }

    public SellerRuleTypePointsFormulaError(String message, Throwable cause) {
        super(message, cause);
    }

    public SellerRuleTypePointsFormulaError(Throwable cause) {
        super(cause);
    }
}
