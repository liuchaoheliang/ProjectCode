package com.froad.cbank.coremodule.normal.boss.support.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliverGoodsReq;
import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliverGoodsRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.OrderQueryService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.QueryRecvInfoForProductByBossReq;
import com.froad.thrift.vo.order.QueryRecvInfoForProductByBossRes;
import com.froad.thrift.vo.order.RecvInfoForProductVo;

/**
 * 发货管理
 * @ClassName DeliverGoodsSupport
 * @author zxl
 * @date 2015年6月15日 下午3:54:32
 */
@Service
public class DeliverGoodsSupport {
	
	@Resource
	OrderQueryService.Iface orderQueryService;
	
	/**
	 * 发货列表
	 * @tilte list
	 * @author zxl
	 * @date 2015年6月15日 下午4:25:08
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> list(DeliverGoodsReq po) throws Exception{
		HashMap<String,Object> map = new HashMap<String, Object>();
		QueryRecvInfoForProductByBossReq req = new QueryRecvInfoForProductByBossReq();
		
		PageVo page = new PageVo();
		page.setPageSize(po.getPageSize());
		page.setPageNumber(po.getPageNumber());
		page.setFirstRecordTime(po.getFirstRecordTime());
		page.setLastRecordTime(po.getLastRecordTime());
		page.setLastPageNumber(po.getLastPageNumber());
		req.setPageVo(page);
		req.setClientId(po.getClientId());
		req.setFOrgCode(po.getFOrgCode());
		req.setSOrgCode(po.getSOrgCode());
		
		if(StringUtils.isNotBlank(po.getOrderStartTime())){
			req.setOrderStartTime(PramasUtil.DateFormat(po.getOrderStartTime()));
		}
		if(StringUtils.isNotBlank(po.getOrderEndTime())){
			req.setOrderEndTime(PramasUtil.DateFormatEnd(po.getOrderEndTime()));
		}
		if(StringUtils.isNotBlank(po.getPresellStartTime())){
			req.setPresellStartTime(PramasUtil.DateFormat(po.getPresellStartTime()));
		}
		if(StringUtils.isNotBlank(po.getPresellEndTime())){
			req.setPresellEndTime(PramasUtil.DateFormatEnd(po.getPresellEndTime()));
		}
		
		QueryRecvInfoForProductByBossRes res = orderQueryService.queryRecvInfoForProductByBoss(req);
		if(!Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}
		
		Page pageRes = new Page();
		BeanUtils.copyProperties(pageRes, res.getPageVo());
		map.put("page", pageRes);
		
		List<DeliverGoodsRes> list = new ArrayList<DeliverGoodsRes>();
		for(RecvInfoForProductVo vo : res.getRecvList()){
			DeliverGoodsRes r = new DeliverGoodsRes();
			BeanUtils.copyProperties(r, vo);
			list.add(r);
		}
		
		map.put("list", list);
		return map;
	}
}
