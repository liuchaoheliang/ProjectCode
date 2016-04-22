package com.froad.exception.transaction;

public class IsNotSellerRuleUsedByTransAllowance   extends RuntimeException {

    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

	/**
     * <p>Description:"没有卖家规则应用于收款交易流转信息计算"</p>
     * @param message 错误信息
     */
    public IsNotSellerRuleUsedByTransAllowance(String message) {
        super(message);
    }

    /**
     * <p>Description:更新的实体为空异常</p>
     * @param message 错误消息
     * @param cause 异常
     */
    public IsNotSellerRuleUsedByTransAllowance(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Description:更新的实体为空异常</p>
     * @param cause 异常
     */
    public IsNotSellerRuleUsedByTransAllowance(Throwable cause) {
        super(cause);
    }

}