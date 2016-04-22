package com.froad.logic.impl;

import java.util.HashSet;
import java.util.Set;

import com.froad.enums.ActiveItemType;
import com.froad.logback.LogCvt;
import com.froad.logic.ActivitiesLogic;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.PutFullCutReqVo;
import com.froad.util.GoodsConstants;
import com.alibaba.fastjson.JSON;

public class ActivitiesLogicImpl implements ActivitiesLogic {

	@Override
	public ResultVo putFullCut(String clientId, String productId) throws Exception {
		ActiveRunService.Iface activeRunServiceClient = (ActiveRunService.Iface)ThriftClientProxyFactory.createIface(ActiveRunService.class, 
                GoodsConstants.THRIFT_ACTIVITIES_HOST, Integer.valueOf(GoodsConstants.THRIFT_ACTIVITIES_PORT), 60000);
		
		PutFullCutReqVo putFullCutReqVo = new PutFullCutReqVo();
		
		Set<String> productIds = new HashSet<String>();
		productIds.add(productId);
		
		putFullCutReqVo.setClientId(clientId);
		putFullCutReqVo.setProductIds(productIds);
		putFullCutReqVo.setItemType(ActiveItemType.unLimint.getCode());
		putFullCutReqVo.setItemId("");
		
		ResultVo resultVo = activeRunServiceClient.putFullCut(putFullCutReqVo);
		
		LogCvt.debug("参加满减活动的商品需要调用满减通知接口 查询参数:putFullCutReqVo:"+JSON.toJSONString(putFullCutReqVo)+",结果:"+JSON.toJSONString(resultVo));
		
		return resultVo;
	}

}
