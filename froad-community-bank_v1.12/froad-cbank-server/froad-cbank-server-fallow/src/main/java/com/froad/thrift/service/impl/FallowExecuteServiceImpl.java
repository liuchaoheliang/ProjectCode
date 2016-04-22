/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: TaskImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.FallowExecuteLogic;
import com.froad.logic.impl.FallowExecuteLogicImpl;
import com.froad.monitor.AuditInstanceMonitor;
import com.froad.po.ExecuteCreateInstanceReq;
import com.froad.po.ExecuteTaskReq;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.vo.CreateInstanceReqVo;
import com.froad.thrift.vo.CreateInstanceResVo;
import com.froad.thrift.vo.ExecuteTaskReqVo;
import com.froad.thrift.vo.ExecuteTaskResVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: TaskImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class FallowExecuteServiceImpl extends BizMonitorBaseService implements FallowExecuteService.Iface {
	private FallowExecuteLogic fallowExecuteLogic = new FallowExecuteLogicImpl();
	public FallowExecuteServiceImpl() {}
	public FallowExecuteServiceImpl(String name, String version) {super(name, version);}
	
	@Override
	public CreateInstanceResVo createInstance(CreateInstanceReqVo req) throws TException {
		LogCvt.info("启动流程接口createInstance,请求参数:" + JSonUtil.toJSonString(req));
		CreateInstanceResVo res = (CreateInstanceResVo) BeanUtil.copyProperties(CreateInstanceResVo.class, fallowExecuteLogic.createInstance((ExecuteCreateInstanceReq) BeanUtil.copyProperties(ExecuteCreateInstanceReq.class, req)));
		if (!StringUtils.equals(res.getResult().getResultCode(), ResultCode.success.getCode())) {// 接口执行失败,调用告警
			AuditInstanceMonitor.sendCreateInstanceServiceFaildNumber();
		}
		LogCvt.info("执行审核接口executeTask,返回参数:" + JSonUtil.toJSonString(res));
		return res;
	}
	
	@Override
	public ExecuteTaskResVo executeTask(ExecuteTaskReqVo req) throws TException {
		LogCvt.info("执行审核接口executeTask,请求参数:" + JSonUtil.toJSonString(req));
		ExecuteTaskResVo resp = (ExecuteTaskResVo) BeanUtil.copyProperties(ExecuteTaskResVo.class, fallowExecuteLogic.executeTask((ExecuteTaskReq) BeanUtil.copyProperties(ExecuteTaskReq.class, req)));
		if (!StringUtils.equals(resp.getResult().getResultCode(), ResultCode.success.getCode())) {// 接口执行失败,调用告警
			AuditInstanceMonitor.sendExecuteTaskServiceFaildNumber();
		}
		LogCvt.info("执行审核接口executeTask,返回参数:" + JSonUtil.toJSonString(resp));
		return resp;
	}




}
