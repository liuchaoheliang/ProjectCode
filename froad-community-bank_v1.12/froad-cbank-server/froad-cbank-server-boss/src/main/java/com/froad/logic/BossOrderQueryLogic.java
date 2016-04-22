package com.froad.logic;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.BossPrefPayOrderDetailInfo;
import com.froad.po.BossPrefPayOrderPageReq;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.BossGroupOrderListReq;
import com.froad.thrift.vo.order.BossPointOrderListReq;
import com.froad.thrift.vo.order.BossPointOrderListRes;
import com.froad.thrift.vo.order.BossPresellBillListReq;
import com.froad.thrift.vo.order.BossPresellDetialRes;
import com.froad.thrift.vo.order.BossPresellOrderListReq;

/**
 * 报表订单相关业务实现接口
 * @author kevin
 *
 */
public interface BossOrderQueryLogic {

	/**
	 * 团购交易查询接口(以券为单位)
	 * @param req
	 * @return
	 */
	public ResultBean getBossGroupOrderList(BossGroupOrderListReq req) ;
	
	/**
	 * 预售交易查询接口（以券为单位）
	 * @param req
	 * @return
	 */
	public ResultBean getBossPresellOrderList(BossPresellOrderListReq req);
	
	/**
	 * 查询线上积分兑换订单列表
	 * @
	 */
	public BossPointOrderListRes queryPointOrderList(BossPointOrderListReq req);
	
	/**
	 * 预售交易详情
	 * @param clientId
	 * @param ticketId
	 * @return
	 */
	public BossPresellDetialRes getPresellDetial(String clientId, String ticketId);
	
	/**
	 * 预售交易账单查询
	 * getBossPresellBillList:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年8月17日 下午1:46:49
	 * @param req
	 * @return
	 *
	 */
	public ResultBean getBossPresellBillList(BossPresellBillListReq req);
	
	/**
	 * 预售交易账单导出
	 * getBossPresellBillExport:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年8月17日 下午1:47:37
	 * @param req
	 * @return
	 *
	 */
	public ExportResultRes getBossPresellBillExport(BossPresellBillListReq req) ;
	
	/**
	 * 交易订单查询
	 * getOrderList:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年8月28日 下午1:34:24
	 * @param req
	 * @return
	 *
	 */
	public ResultBean getOrderList(OrderQueryByBossCondition condition, PageVo pageVo, String flag)  throws FroadBusinessException;
	
	/**
	 * 交易订单报表导出
	 * exportOrderList:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年8月28日 下午1:34:55
	 * @param req
	 * @return
	 *
	 */
	public ExportResultRes exportOrderList(OrderQueryByBossCondition condition);
	
	/**
	 * 查询订单详情
	 * getSubOrderDetailOfBossByOrderId:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年9月2日 下午1:36:59
	 * @param clientId
	 * @param subOrderId
	 * @return
	 *
	 */
	public ResultBean getSubOrderDetailOfBossByOrderId(String clientId, String orderId) throws FroadBusinessException;
	
	
	/**
	 * 
	 * queryPrefPayOrderList:(面对面惠付订单列表查询).
	 *
	 * @author huangyihao
	 * 2015年12月29日 下午2:02:42
	 * @param req
	 * @param page
	 * @return
	 *
	 */
	public MongoPage queryPrefPayOrderList(BossPrefPayOrderPageReq req, MongoPage page) throws Exception;
	
	
	/**
	 * 
	 * getPrefPayOrderDetail:(面对面惠付订单详情).
	 *
	 * @author huangyihao
	 * 2015年12月29日 下午2:03:28
	 * @param clientId
	 * @param orderId
	 * @return
	 *
	 */
	public BossPrefPayOrderDetailInfo getPrefPayOrderDetail(String clientId, String orderId) throws Exception;
	
}
