package com.froad.cbank.coremodule.module.normal.bank.util;

import org.apache.thrift.TException;
import org.springframework.ui.ModelMap;

import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;

/**
 * 异常响应
 * @ClassName RespError
 * @author zxl
 * @date 2015年3月23日 上午9:19:53
 */
public class RespError {
	
	/**
	 * 系统异常 Exception
	 * @param model
	 */
	public RespError(ModelMap model){
		model.clear();
		model.put("code", EnumTypes.syserr.getCode());
		model.put("message", EnumTypes.syserr.getMessage());
	}
	
	/**
	 * 商户异常 MerchantException
	 * @param model
	 * @param e
	 */
	public RespError(ModelMap model,BankException e){
		model.clear();
		model.put("code", e.getCode());
		model.put("message", e.getMessage());
	}
	/**
	 * 通用异常处理
	 * @param model
	 * @param e
	 */
	public RespError(ModelMap model,Exception e){
		model.clear();
		String code = "",message = "";
		if(e instanceof BankException){
			code = ((BankException)e).getCode();;
			message = ((BankException)e).getMessage();
		}else if(e instanceof TException){
			code = EnumTypes.thrift_err.getCode();
			message = EnumTypes.thrift_err.getMessage();
		}else{
			code = EnumTypes.syserr.getCode();
			message = EnumTypes.syserr.getMessage();
		}
		model.put("code", code);
		model.put("message", message);
	}
}
