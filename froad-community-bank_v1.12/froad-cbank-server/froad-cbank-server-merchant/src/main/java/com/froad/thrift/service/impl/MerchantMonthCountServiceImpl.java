package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantMonthCountLogic;
import com.froad.logic.impl.MerchantMonthCountLogicImpl;
import com.froad.po.MerchantMonthCount;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantMonthCountService;
import com.froad.thrift.vo.MerchantMonthCountVo;
import com.froad.util.BeanUtil;

/**
 * 
 * <p>@Title: MerchantMonthCountServiceImpl.java</p>
 * <p>Description: 商户月度销售统计 </p> 
 * @author lf 
 * @version 1.0
 * @created 2015年4月11日
 */
public class MerchantMonthCountServiceImpl extends BizMonitorBaseService  implements MerchantMonthCountService.Iface{

	private MerchantMonthCountLogic merchantMonthCountLogic = new MerchantMonthCountLogicImpl();
	
	public MerchantMonthCountServiceImpl() {}
	public MerchantMonthCountServiceImpl(String name, String version) {
		super(name, version);
	}
	
	/**
     * 查询 MerchantMonthCountVo
     * @param merchantMonthCountVo
     * @return MerchantMonthCountVo
     */
	@Override
	public MerchantMonthCountVo getMerchantMonthCount(
			MerchantMonthCountVo merchantMonthCountVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询单个MerchantMonthCount");
		
		MerchantMonthCount merchantMonthCount = (MerchantMonthCount)BeanUtil.copyProperties(MerchantMonthCount.class, merchantMonthCountVo);
		merchantMonthCount = merchantMonthCountLogic.getOutletCommentById(merchantMonthCount);
		if( merchantMonthCount == null ){
			return new MerchantMonthCountVo();
		}
		merchantMonthCountVo = (MerchantMonthCountVo)BeanUtil.copyProperties(MerchantMonthCountVo.class, merchantMonthCount);
		LogCvt.info("查询单个MerchantMonthCount结果:"+JSON.toJSONString(merchantMonthCountVo));
		return merchantMonthCountVo;
	}

}
