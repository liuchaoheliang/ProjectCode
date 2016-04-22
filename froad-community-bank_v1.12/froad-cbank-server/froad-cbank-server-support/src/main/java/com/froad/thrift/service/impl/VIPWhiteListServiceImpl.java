/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.logback.LogCvt;
import com.froad.logic.VIPWhiteListLogic;
import com.froad.logic.impl.VIPWhiteListLogicImpl;
import com.froad.po.AddVIPWhiteListReq;
import com.froad.po.CheckVIPExistWhiteListReq;
import com.froad.po.RemoveVIPWhiteListReq;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.VIPWhiteListService;
import com.froad.thrift.vo.AddVIPWhiteListReqVo;
import com.froad.thrift.vo.CheckVIPExistWhiteListReqVo;
import com.froad.thrift.vo.CheckVIPExistWhiteListRespVo;
import com.froad.thrift.vo.RemoveVIPWhiteListReqVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;

public class VIPWhiteListServiceImpl extends BizMonitorBaseService implements VIPWhiteListService.Iface {
	private final VIPWhiteListLogic vipWhiteListLogic = new VIPWhiteListLogicImpl();

	public VIPWhiteListServiceImpl() {
	}

	public VIPWhiteListServiceImpl(String name, String version) {
		super(name, version);
	}
	
	@Override
	public CheckVIPExistWhiteListRespVo checkVIPExistWhiteList(CheckVIPExistWhiteListReqVo reqVo) throws TException {
		LogCvt.info("检查VIP白名单接口checkVIPExistWhiteList,请求参数:" + JSonUtil.toJSonString(reqVo));
		CheckVIPExistWhiteListRespVo res = (CheckVIPExistWhiteListRespVo) BeanUtil.copyProperties(CheckVIPExistWhiteListRespVo.class, vipWhiteListLogic.checkVIPExistWhiteList((CheckVIPExistWhiteListReq) BeanUtil.copyProperties(CheckVIPExistWhiteListReq.class, reqVo)));
		LogCvt.info("检查VIP白名单接口checkVIPExistWhiteList,返回参数:" + JSonUtil.toJSonString(res));
		return res;
	}

	@Override
	public ResultVo addVIPWhiteList(AddVIPWhiteListReqVo reqVo) throws TException {
		LogCvt.info("添加VIP白名单接口addVIPWhiteList,请求参数:" + JSonUtil.toJSonString(reqVo));
		ResultVo res = (ResultVo) BeanUtil.copyProperties(ResultVo.class, vipWhiteListLogic.addVIPWhiteList((AddVIPWhiteListReq) BeanUtil.copyProperties(AddVIPWhiteListReq.class, reqVo)));
		LogCvt.info("添加VIP白名单接口addVIPWhiteList,返回参数:" + JSonUtil.toJSonString(res));
		return res;
	}

	@Override
	public ResultVo removeVIPWhiteList(RemoveVIPWhiteListReqVo reqVo) throws TException {
		LogCvt.info("移除VIP白名单接口removeVIPWhiteList,请求参数:" + JSonUtil.toJSonString(reqVo));
		ResultVo res = (ResultVo) BeanUtil.copyProperties(ResultVo.class, vipWhiteListLogic.removeVIPWhiteList((RemoveVIPWhiteListReq) BeanUtil.copyProperties(RemoveVIPWhiteListReq.class, reqVo)));
		LogCvt.info("移除VIP白名单接口removeVIPWhiteList,返回参数:" + JSonUtil.toJSonString(res));
		return res;
	}

}
