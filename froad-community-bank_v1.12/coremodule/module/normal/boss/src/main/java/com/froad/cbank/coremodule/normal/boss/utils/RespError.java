package com.froad.cbank.coremodule.normal.boss.utils;

import org.apache.thrift.TException;
import org.springframework.ui.ModelMap;

import com.froad.cbank.coremodule.normal.boss.exception.BossException;

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
		model.put("code", ErrorEnums.syserr.getCode());
		model.put("message", ErrorEnums.syserr.getMsg());
	}
	
	public RespError(ModelMap model,String msg){
		model.clear();
		model.put("code", ErrorEnums.syserr.getCode());
		model.put("message", msg);
	}
	
	public RespError(ModelMap model,BossException e){
		model.clear();
		model.put("code", e.getCode());
		model.put("message", e.getMsg());
	}
	
	
	public RespError(ModelMap model,Exception e){
		model.clear();
		String code = "",msg = "";
		if(e instanceof BossException){
			code = ((BossException)e).getCode();;
			msg = ((BossException)e).getMsg();
		}else if(e instanceof TException){
			code = ErrorEnums.thrift_err.getCode();
			msg = ErrorEnums.thrift_err.getMsg();
		}else{
			code = ErrorEnums.syserr.getCode();
			msg = ErrorEnums.syserr.getMsg();
		}
		model.put("code", code);
		model.put("message", msg);
	}
}
