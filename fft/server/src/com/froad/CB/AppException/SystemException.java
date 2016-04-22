
package com.froad.CB.AppException;


public class SystemException extends RuntimeException {

    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

	/**
     * <p>Description:系统异常</p>
     * @param message 错误信息
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * <p>Description:系统异常</p>
     * @param message 错误消息
     * @param cause 异常
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Description:系统异常</p>
     * @param cause 异常
     */
    public SystemException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>Discription: 判断异常是否SystemException　类型</p>
     * @param throwable 异常
     * @return
     * @author       : goujw
     * @update       : 
     */
    public static boolean isSystemException(Throwable throwable) {
        if (throwable instanceof SystemException) {
            return true;
        }
        return false;
    }
}