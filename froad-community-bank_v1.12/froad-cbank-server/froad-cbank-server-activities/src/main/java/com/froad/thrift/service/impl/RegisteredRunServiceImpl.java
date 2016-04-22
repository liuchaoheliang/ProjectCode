package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.logback.LogCvt;
import com.froad.logic.RegisteredRunLogic;
import com.froad.logic.impl.RegisteredRunLogicImpl;
import com.froad.po.RegisteredHandsel;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.RegisteredRunService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.RegisteredHandselVo;
import com.froad.util.BeanUtil;

public class RegisteredRunServiceImpl extends BizMonitorBaseService implements RegisteredRunService.Iface {

	private RegisteredRunLogic registeredRunLogic = new RegisteredRunLogicImpl();
	
	public RegisteredRunServiceImpl(){}
	public RegisteredRunServiceImpl(String n, String v){
		super(n, v);
	}
	
	 /**
	  * @Title: registeredHandsel
	  * @Description: TODO
	  * @author: Joker 2015年12月8日
	  * @modify: Joker 2015年12月8日
	  * @param arg0
	  * @return
	  * @throws TException
	  * @see com.froad.thrift.service.RegisteredRunService.Iface#registeredHandsel(com.froad.thrift.vo.active.RegisteredHandselVo)
	  */
	
	
	@Override
	public ResultVo registeredHandsel(RegisteredHandselVo registeredHandselVo)
			throws TException {
		LogCvt.info("注册赠送 registeredHandsel 参数:");
		LogCvt.info(JSON.toJSONString(registeredHandselVo));
		
		RegisteredHandsel registeredHandsel = (RegisteredHandsel)BeanUtil.copyProperties(RegisteredHandsel.class, registeredHandselVo);
		
		Result result = registeredRunLogic.registeredHandsel(registeredHandsel);
		
		ResultVo resultVo = (ResultVo)BeanUtil.copyProperties(ResultVo.class, result);
		return resultVo;
	}

}
