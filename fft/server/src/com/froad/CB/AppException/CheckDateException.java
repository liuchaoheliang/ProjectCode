package com.froad.CB.AppException;

public class CheckDateException extends RuntimeException {

    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

	/**
     * <p>Description:主键为空异常</p>
     * @param message 错误信息
     */
    public CheckDateException(String message) {
        super(message);
    }

    /**
     * <p>Description:主键为空异常</p>
     * @param message 错误消息
     * @param cause 异常
     */
    public CheckDateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Description:主键为空异常</p>
     * @param cause 异常
     */
    public CheckDateException(Throwable cause) {
        super(cause);
    }

}