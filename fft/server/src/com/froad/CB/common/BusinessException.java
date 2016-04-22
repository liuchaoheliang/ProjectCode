package com.froad.CB.common;

public class BusinessException extends Exception {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * 当你要创建原生业务异常时，你可以使用此构造函数
	 * 
	 * @param errorCode
	 *            int
	 */
	public BusinessException(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 当你要创建原生业务异常时，你可以使用此构造函数
	 * 
	 * @param message
	 *            String
	 * @deprecated 不推荐使用此构造子函数，因用此构造函数，将使用业务异常无法区分错误编码
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * 当你要创建原生业务异常时，你可以使用此构造函数
	 * 
	 * @param errorCode
	 *            int
	 * @param message
	 *            String
	 */
	public BusinessException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * 当你要创建原生业务异常时，你可以使用此构造函数
	 * 
	 * @param errorCode
	 *            int
	 * @param message
	 *            String
	 * @param cause
	 *            Throwable
	 */
	public BusinessException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * <p>
	 * Discription: 返回错误代码
	 * </p>
	 * 
	 * @return
	 * @author : goujw
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * <p>
	 * Discription:错误代码
	 * </p>
	 */
	private int errorCode = 0;

	/**
	 * <p>
	 * Discription: 判断是否是BusinessException 类型
	 * </p>
	 * 
	 * @param throwable
	 * @return
	 * @author : goujw
	 * @update :
	 */
	public static boolean isBusinessException(Throwable throwable) {
		if (throwable instanceof BusinessException) {
			return true;
		}
		return false;
	}
}
