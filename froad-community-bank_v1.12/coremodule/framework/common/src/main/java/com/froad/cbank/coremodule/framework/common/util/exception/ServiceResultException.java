package com.froad.cbank.coremodule.framework.common.util.exception;


public class ServiceResultException extends BaseException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 服务器端返回的结果不满足组件接口约定时抛出
	 * @param message	应该满足的约束条件说明
	 */
	
	public ServiceResultException(String message) {
		super(message);
	}

}
