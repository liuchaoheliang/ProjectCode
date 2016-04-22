package com.froad.timer.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;

import com.froad.db.mongo.TicketMongoService;
import com.froad.db.mongo.impl.TicketMongoServiceImpl;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.Ticket;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.RefundService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.refund.RefundTicketVo;
import com.froad.thrift.vo.refund.RefundTicketsRequestVo;
import com.froad.thrift.vo.refund.RefundTicketsResponseVo;
import com.froad.util.JSonUtil;
import com.froad.util.ThriftConfig;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 
 * @ClassName: TaskHandleExpiredTicket 
 * @Description: 处理过期团购券
 * @author: huangyihao
 * @modify: lf 20150527
 * @date: 2015年4月28日
 */
public class TaskHandleExpiredTicket implements Runnable {
	
	private TicketMongoService ticketMongoService = new TicketMongoServiceImpl();
	/**
	 * 监控服务
	 * */
	private static MonitorService monitorService = new MonitorManager();
	private final static String TYPE = "type";
	private final static String STATUS = "status";
	private final static String EXPIRE_TIME = "expire_time";
	private final static String SUB_ORDER_ID = "sub_order_id";
	private final static String COMMAND_SET = "$set";
	
	private final static String refund_reson = "团购券过期自动退款";

	@Override
	public void run() {
		LogCvt.debug("定时任务: 过期团购ticket券处理------开始------");
//                           _ooOoo_
//                          o8888888o
//                          88" . "88
//                          (| -_- |)
//                           O\ = /O
//                       ____/`---'\____
		// *************************************************
		// *                                               *
		// *         ①从mongo中把过期的团购券和预售券查出来            *
		// *         ②把状态改成已过期                                               *
		// *         ③组装数据调用退款接口                                         *
		// *                                               *
		// *************************************************
		
		try{
			
			RefundService.Iface refundClient = (RefundService.Iface)ThriftClientProxyFactory.createIface(RefundService.class.getName(), RefundService.class.getSimpleName(), ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
			List<Ticket> ttl = null;
			List<RefundTicketVo> ticketVos = null;
			RefundTicketsRequestVo requestVo = null;
			RefundTicketsResponseVo responseVo = null;
			
//			①从mongo中把过期的团购券和预售券查出来
			DBObject qObj = getQueryObjectOfExpiredTicket();
			DBObject setObject = new BasicDBObject();
			List<Ticket> ticketList = ticketMongoService.findByCondition(qObj);
			if( ticketList == null || ticketList.size() <= 0 ){
				
				LogCvt.debug("定时任务: 过期团购ticket券处理------无过期的团购券信息");
				return;
			}else{
				
				// 得到不重复的所有子订单编号 - 字段单与团购券的关系是1-1|N
				Set<String> sois = filterSubOrderId(ticketList);
				for( String subOrderId : sois ){
					
					LogCvt.debug("子订单["+subOrderId+"]的过期券 ----------- 处理开始 ---");
					
//					②把状态改成已过期
					qObj.put(SUB_ORDER_ID, subOrderId);
					setObject.put(STATUS, TicketStatus.expired.getCode());
					Boolean result = ticketMongoService.updateTicket(setObject, qObj, COMMAND_SET);
					LogCvt.debug("子订单["+subOrderId+"]的过期券 更新状态为过期 结果:"+(result?"成功":"失败"));
					if( result ){
						
//						③组装数据调用退款接口
						ttl = filterTicketListBySubOrderId(ticketList, subOrderId);
						requestVo = new RefundTicketsRequestVo();
						ticketVos = new ArrayList<RefundTicketVo>();
						for( Ticket ticket : ttl ){
							ticketVos.add(changeTicketVo(ticket));
						}
						requestVo.setTicketList(ticketVos);
						LogCvt.debug("子订单["+subOrderId+"]的过期券 开始进行退款处理");
						// 过期券退款
						responseVo = refundClient.refundTickets(requestVo);
						// 显示退款结果
						showRefundResult(responseVo, subOrderId);
						
					}
					LogCvt.debug("子订单["+subOrderId+"]的过期券 ----------- 处理结束 ---");
				}
			}
			
		} catch(Exception e) {
			LogCvt.error("定时任务: 过期团购ticket券处理------系统异常------");
			LogCvt.error(e.getMessage(), e);
			// TODO 过期券退款处理不成功 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Handle_Expired_Ticket_Failure);
		} finally {
			LogCvt.debug("定时任务: 过期团购ticket券处理------结束------");
		}
	}
	
	// 显示退款结果
	private static void showRefundResult(RefundTicketsResponseVo responseVo, String subOrderId){
		ResultVo resultVo = responseVo.getResultVo();
		LogCvt.debug("子订单[subOrderId="+subOrderId+"]团购券自动退款处理结果: 返回码[resultCode="+resultVo.getResultCode()+"]|返回信息[resultDesc="+resultVo.getResultDesc()+"]");
		if( !ResultCode.success.getCode().equals(resultVo.getResultCode()) ){
			// TODO 过期券退款处理不成功 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Handle_Expired_Ticket_Failure);
		}
		List<RefundTicketVo> succList = responseVo.getSuccessList();
		List<RefundTicketVo> failList = responseVo.getFailedList();
		if(CollectionUtils.isNotEmpty(succList)){
			LogCvt.debug("成功退款的券信息集合["+JSonUtil.toJSonString(succList)+"]");
		}
		
		if(CollectionUtils.isNotEmpty(failList)){
			LogCvt.error("失败退款的券信息集合["+JSonUtil.toJSonString(failList)+"]");
		}
	}
	
	// 转换成退款券对象
	private static RefundTicketVo changeTicketVo(Ticket ticket){
		RefundTicketVo ticketVo = new RefundTicketVo();
		ticketVo.setSuborderId(ticket.getSubOrderId());
		ticketVo.setProductId(ticket.getProductId());
		ticketVo.setTicketId(ticket.getTicketId());
		ticketVo.setQuantity(ticket.getQuantity());
		ticketVo.setRemark(refund_reson);
		ticketVo.setClientId(ticket.getClientId());
		return ticketVo;
	}
	
	// 根据子订单编号 得到过滤的团购券信息
	private static List<Ticket> filterTicketListBySubOrderId(List<Ticket> tl, String subOrderId){
		List<Ticket> ttl = new ArrayList<Ticket>();
		String type = SubOrderType.group_merchant.getCode();
		for( Ticket ticket : tl ){
			if( subOrderId.equals(ticket.getSubOrderId()) && type.equals(ticket.getType()) ){
				ttl.add(ticket);
			}
		}
		return ttl;
	}
	
	// 得到不重复的所有子订单编号 - 字段单与团购券的关系是1-1|N
	private static Set<String> filterSubOrderId(List<Ticket> tl){
		Set<String> set = new TreeSet<String>();
		String subOrderId = null;
		for( Ticket ticket : tl ){
			subOrderId = ticket.getSubOrderId();
			if( subOrderId != null && !"".equals(subOrderId) ){
				set.add(subOrderId);
			}
		}
		return set;
	}
	
	/**
	 * 得到查询过期的团购券和预售券的 DBObject 对象
	 * 1-状态为已发送 2-类型为团购 3-过期时间小于当前时间
	 * 
	 * @author lf 2015.03.30
	 * @modify lf 2015.06.25
	 * */
	private DBObject getQueryObjectOfExpiredTicket(){
		
		DBObject queryObj = new BasicDBObject();
		
		queryObj.put(STATUS, TicketStatus.sent.getCode()); // 已发送
		// 类型 - 团购 & 预售
		BasicDBList typeObject = new BasicDBList();
		typeObject.add(new BasicDBObject(TYPE, SubOrderType.group_merchant.getCode())); // 团购
		typeObject.add(new BasicDBObject(TYPE, SubOrderType.presell_org.getCode())); // 预售
		queryObj.put(QueryOperators.OR, typeObject);
		queryObj.put(EXPIRE_TIME, new BasicDBObject(QueryOperators.LT, System.currentTimeMillis()));
		
		return queryObj;
	}
	
}
