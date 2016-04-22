package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OrderQueryForBossService;
import com.froad.thrift.vo.order.GroupOrderListReq;
import com.froad.thrift.vo.order.GroupOrderListRes;
import com.froad.thrift.vo.order.PresellOrderListReq;
import com.froad.thrift.vo.order.PresellOrderListRes;

/**
 * Boss订单（交易）查询实现
 * 
 * @author wangzhangxu
 * @date 2015年8月4日 下午1:25:09
 * @version v1.0
 */
public class OrderQueryForBossServiceImpl extends BizMonitorBaseService implements OrderQueryForBossService.Iface {

	@Override
	public GroupOrderListRes queryGroupOrderList(GroupOrderListReq req)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PresellOrderListRes queryPresellOrderList(PresellOrderListReq req)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}
