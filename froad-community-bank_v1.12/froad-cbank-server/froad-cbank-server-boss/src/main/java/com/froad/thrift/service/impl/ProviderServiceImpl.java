/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:ProviderServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015年12月2日下午4:44:02
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ProviderLogic;
import com.froad.logic.impl.ProviderLogicImpl;
import com.froad.po.Provider;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ProviderService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.provider.ProviderListVo;
import com.froad.thrift.vo.provider.ProviderVo;
import com.froad.util.BeanUtil;

/**
 * ClassName:ProviderServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月2日 下午4:44:02
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProviderServiceImpl extends BizMonitorBaseService implements ProviderService.Iface {
	
	private ProviderLogic logic = new ProviderLogicImpl();
	
	public ProviderServiceImpl(String name, String version){
		super(name, version);
	}
	
	@Override
	public ProviderListVo findAllProviders() throws TException {
		LogCvt.info("[查询供应商列表]-开始");
		ProviderListVo listVo = new ProviderListVo();
		ResultVo resultVo = new ResultVo();
		try {
			List<Provider> list = logic.findAllProviders();
			List<ProviderVo> voList = (List<ProviderVo>) BeanUtil.copyProperties(ProviderVo.class, list);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			listVo.setResultVo(resultVo);
			listVo.setProviderList(voList);
		} catch (Exception e) {
			LogCvt.error("[查询供应商列表]-异常", e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			listVo.setResultVo(resultVo);
			listVo.setProviderList(new ArrayList<ProviderVo>());
		}
		LogCvt.info("[查询供应商列表]-结束");
		return listVo;
	}
	
	
}
