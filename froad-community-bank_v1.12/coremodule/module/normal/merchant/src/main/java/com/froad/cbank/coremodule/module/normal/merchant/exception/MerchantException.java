package com.froad.cbank.coremodule.module.normal.merchant.exception;

import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;

/**
 * 商户异常
 * 
 * @ClassName MerchantException
 * @author zxl
 * @date 2015年3月23日 上午9:19:18
 */
public class MerchantException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private String code;

	/**
	 * 错误信息
	 */
	private String msg;

	/**
	 * 错误次数
	 */

	private Integer loginFailureCount;

	public MerchantException() {
		super();
	}

	public MerchantException(EnumTypes e) {
		super(e.getMsg());
		this.code = e.getCode();
		this.msg = e.getMsg();
	}

	public MerchantException(String msg) {
		super(msg);
		this.code = "1";
		this.msg = msg;
	}

	public MerchantException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public MerchantException(String code, String msg, Integer loginFailureCount) {
		super();
		this.code = code;
		this.msg = msg;
		this.loginFailureCount = loginFailureCount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getFailsCount() {
		return loginFailureCount;
	}

	public void setFailsCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

}
