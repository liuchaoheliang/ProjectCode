package com.froad.exception.transaction;

public class FormulaParaException extends RuntimeException {

    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

	/**
     * <p>Description:公式参数异常</p>
     * @param message 错误信息
     */
    public FormulaParaException(String message) {
        super(message);
    }

    /**
     * <p>Description:公式参数异常</p>
     * @param message 错误消息
     * @param cause 异常
     */
    public FormulaParaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Description:公式参数异常</p>
     * @param cause 异常
     */
    public FormulaParaException(Throwable cause) {
        super(cause);
    }

}
