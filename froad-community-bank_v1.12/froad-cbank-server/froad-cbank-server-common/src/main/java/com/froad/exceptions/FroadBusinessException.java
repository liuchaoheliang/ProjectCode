package com.froad.exceptions;

/**
 * 自定义业务异常
 */
public class FroadBusinessException extends Exception {

    private static final long serialVersionUID = 5730339086217985460L;

    private String            code;
    private String            msg;

    public FroadBusinessException() {
        super();
    }

    public FroadBusinessException(Throwable e) {
        super(e);
    }

    public FroadBusinessException(String errorCode) {
        super();
        this.code = errorCode;
    }

    public FroadBusinessException(String code, Throwable e) {
        super(e);
        this.code = code;
    }

    public FroadBusinessException(String code, String msg) {
        // super(message);
        this.msg = msg;
        this.code = code;
    }

    public FroadBusinessException(String code, String message, Throwable e) {
        super(message, e);
        this.code = code;
        this.msg = message;
    }

    public String getCode() {
        return code;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     *            the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

}
