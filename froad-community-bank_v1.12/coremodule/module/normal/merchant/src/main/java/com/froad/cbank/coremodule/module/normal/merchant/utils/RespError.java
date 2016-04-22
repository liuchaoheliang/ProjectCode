package com.froad.cbank.coremodule.module.normal.merchant.utils;

import org.apache.thrift.TException;
import org.springframework.ui.ModelMap;

import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;

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
		model.put("message", EnumTypes.syserr.getMsg());
	}
	
	/**
	 * 商户异常 MerchantException
	 * @param model
	 * @param e
	 */
	public RespError(ModelMap model,MerchantException e){
		model.clear();
		model.put("code", e.getCode());
		model.put("message", e.getMsg());
	}
	/**
	 * 通用异常处理
	 * @param model
	 * @param e
	 */
	public RespError(ModelMap model,Exception e){
		model.clear();
		String code = "",msg = "";
		if(e instanceof MerchantException){
			code = ((MerchantException)e).getCode();;
			msg = ((MerchantException)e).getMsg();
		}else if(e instanceof TException){
			code = EnumTypes.thrift_err.getCode();
			msg = EnumTypes.thrift_err.getMsg();
		}else{
			code = EnumTypes.syserr.getCode();
			msg = EnumTypes.syserr.getMsg();
		}
		model.put("code", code);
		model.put("message", msg);
	}
}
