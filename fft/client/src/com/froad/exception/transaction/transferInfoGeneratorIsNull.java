package com.froad.exception.transaction;

public class transferInfoGeneratorIsNull  extends RuntimeException {

    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

	/**
     * <p>Description:更新的实体为空异常</p>
     * @param message 错误信息
     */
    public transferInfoGeneratorIsNull(String message) {
        super(message);
    }

    /**
     * <p>Description:更新的实体为空异常</p>
     * @param message 错误消息
     * @param cause 异常
     */
    public transferInfoGeneratorIsNull(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Description:更新的实体为空异常</p>
     * @param cause 异常
     */
    public transferInfoGeneratorIsNull(Throwable cause) {
        super(cause);
    }

}