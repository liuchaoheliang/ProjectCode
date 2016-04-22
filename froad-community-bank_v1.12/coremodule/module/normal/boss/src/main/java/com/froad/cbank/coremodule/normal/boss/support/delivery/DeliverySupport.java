package com.froad.cbank.coremodule.normal.boss.support.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliveryVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliveryVoRes;
import com.froad.thrift.service.OrderService;

/**
 * 
 * <p>标题: </p>
 * <p>说明: </p>
 * <p>创建时间：2015年4月25日下午4:30:55</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class DeliverySupport {
	
	@Resource
	OrderService.Iface orderService;
	/**
	 * 
	 * <p>功能简述：查询配送订单</p> 
	 * <p>使用说明：调用server查询配送订单接口</p> 
	 * <p>创建时间：2015年4月25日下午4:31:04</p>
	 * <p>作者: 陈明灿</p>
	 * @param req
	 * @return
	 */
	public ArrayList<DeliveryVoRes> list(DeliveryVoReq req) {
		ArrayList<DeliveryVoRes> list = null;
		// TODO 后台提供接口
		return list;
	}
	/**
	 * 
	 * <p>功能简述：查询配送订单详情</p> 
	 * <p>使用说明：根据订单号查询订单详情</p> 
	 * <p>创建时间：2015年4月27日上午10:23:37</p>
	 * <p>作者: 陈明灿</p>
	 * @param orderCode
	 * @return
	 */
	public Map<String, Object> queryOrderByCode(String orderCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO 后台提供接口
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：更新配送订单</p> 
	 * <p>使用说明：更新配送订单</p> 
	 * <p>创建时间：2015年4月27日下午1:52:58</p>
	 * <p>作者: 陈明灿</p>
	 * @param req
	 * @return
	 */
	public Map<String, Object> update(DeliveryVoReq req) {
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO 后台提供接口
		return map;
	}

}
