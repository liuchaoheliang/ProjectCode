package com.froad.worker.thread.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.enums.MonitorPointEnum;
import com.froad.logback.LogCvt;
import com.froad.logic.SeckillProductLogic;
import com.froad.logic.impl.SeckillProductLogicImpl;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.mq.beanstalkd.BeanstalkdService;
import com.froad.mq.beanstalkd.SeckillBeanstalkdManager;
import com.froad.po.AcceptOrder;
import com.froad.worker.thread.PlaceOrderWorker;
import com.froad.thrift.client.ThriftService;
import com.froad.thrift.service.SeckillOrderService;
import com.froad.thrift.vo.order.AddDeliveryInfoVoReq;
import com.froad.thrift.vo.order.AddSeckillOrderVoReq;
import com.froad.thrift.vo.order.AddSeckillOrderVoRes;
import com.froad.util.JSonUtil;
import com.trendrr.beanstalk.BeanstalkJob;

/**
 * 下单工人
 * 
 * @author wangzhangxu
 * @date 2015年4月23日 下午8:38:13
 * @version v1.0
 */
public class PlaceOrderWorkerImpl implements PlaceOrderWorker {
	
	private SeckillProductLogic seckillProductLogic;
	
	/** 序号 */
	private int index;
	
	private String name;
	
	private long speedLimitPs = 40;
	
	private String localIp;
	
	/** 总执行次数 */
	private long count = 0;
	
	/** 命中执行次数 */
	private long hitCount = 0;
	
	private boolean isStop;
	
	static final String charset = "utf-8";
	
	static final String NAME_PREFIX = "SeckillWorker_";
	
	private SeckillBeanstalkdManager beanstalkdManager = new SeckillBeanstalkdManager();
	
	private BeanstalkdService beanstalkdService;
	
	private SeckillOrderService.Iface seckillOrderService;
	
	private MonitorService monitorService;
	
	public PlaceOrderWorkerImpl(){
		localIp = getLocalIp();
		
		beanstalkdService = beanstalkdManager.getClient();
		seckillProductLogic = new SeckillProductLogicImpl();
		
		seckillOrderService = ThriftService.getSeckillOrderService();
		monitorService = new MonitorManager();
	}
	
	@Override
	public void run() {
		isStop = false;
		
		work();
	}

	@Override
	public void work() {
		
		do {
			count++;
			
			int clients = beanstalkdService.currentPoolClients();
			LogCvt.debug(getName() + "：当前使用Beanstalkd集群实例(" + beanstalkdService.currentIndex() + "), 连接池共有" + clients + "个连接.");
			
			try {
				
				BeanstalkJob job = beanstalkdService.get(60);
				if (job == null) {
					Thread.sleep(5 * 1000);
					continue;
				}
				
				String json = new String(job.getData(), Charset.forName(charset));
				LogCvt.info(getName() + ": JobId=" + job.getId() + ", JobMessage=" + json);
				
				AcceptOrder acceptOrder = null;
				try{
					acceptOrder = JSonUtil.toObject(json, AcceptOrder.class);
				}catch(Exception e){}
				
				if (acceptOrder == null) {
					// 队列管道中删除该无效的任务
					beanstalkdService.delete(job.getId());
					LogCvt.error(getName() + ": Json to Object is null. [jobId=" + job.getId() + ", json=" + json + "]");
					continue;
				} 
				
				// ========== 限速逻辑 =============
				String time = (System.currentTimeMillis() / 1000) + "";
				long currentCount = seckillProductLogic.getCurrentPersecondCount(localIp, time);
				if (currentCount < speedLimitPs) { // 小于限速，则正常执行
					
					seckillProductLogic.incrCurrentPersecondCount(localIp, time);
					
					// 调用交易模块，完成下单操作
					doPlaceOrder(acceptOrder, job);
					
					// 统计处理任务数量
					monitorService.send(MonitorPointEnum.Worker_Process_Job_Count);
				} else {
					
					// 释放回队列中
					beanstalkdService.release(job);
				}
				
			} catch (Exception e) {
				LogCvt.error(getName() + ": " + e.getMessage(), e);
			}
		} while(!isStop);
		
		LogCvt.info(getName() + ": count=" + count + " & hitCount=" + hitCount);
	}
	
	/**
	 * 初始化请求
	 */
	public AddSeckillOrderVoReq initReq(AcceptOrder order){
		AddSeckillOrderVoReq req = new AddSeckillOrderVoReq();
		req.setReqId(order.getReqId());
		req.setClientId(order.getClientId());
		req.setMerchantId(order.getMerchantId());
		req.setProductId(order.getProductId());
		req.setProductName(order.getProductName());
		req.setProductType(order.getProductType());
		req.setProductImage(order.getProductImage());
		req.setDeliveryOption(order.getDeliveryOption());
		req.setMemberCode(order.getMemberCode());
		req.setMemberName(order.getMemberName());
		req.setPayType(order.getPayType());
		req.setCashType(order.getCashType() == null ? 0 : order.getCashType());
		req.setPointOrgNo(order.getPointOrgNo());
		req.setPointAmount(order.getPointAmount());
		req.setCashOrgNo(order.getCashOrgNo());
		req.setCashAmount(order.getCashAmount());
		req.setFoilCardNum(order.getFoilCardNum());
		req.setDeliveryMoney(order.getDeliveryMoney());
		req.setCreateSource(order.getCreateSource());
		
		req.setMoney(order.getSecPrice());
		req.setVipMoney(order.getVipSecPrice());
		
		AddDeliveryInfoVoReq adiReqVo = new AddDeliveryInfoVoReq();
		adiReqVo.setRecvId(order.getRecvId());
		adiReqVo.setDeliverId(order.getRecvId());
		adiReqVo.setDeliveryType(order.getDeliveryType());
		adiReqVo.setPhone(order.getPhone());
		adiReqVo.setOrgCode(order.getOrgCode());
		adiReqVo.setOrgName(order.getOrgName());
		req.setAddDeliveryInfoVoReq(adiReqVo);
		
		if (order.isVip()) {
			req.setQuantity(0);
			req.setVipQuantity(order.getBuyNum());
		} else {
			req.setQuantity(order.getBuyNum());
			req.setVipQuantity(0);
		}
		return req;
	} 
	
	/**
	 * 调用下单
	 */
	public void doPlaceOrder(AcceptOrder acceptOrder, BeanstalkJob job){
		hitCount++;
		
		AddSeckillOrderVoReq orderReq = initReq(acceptOrder);
		long start = System.currentTimeMillis();
		try {
			AddSeckillOrderVoRes orderRes = seckillOrderService.addOrder(orderReq);
			long end = System.currentTimeMillis(), time = end - start;
			// 监控点
			monitorService.send(MonitorPointEnum.Worker_Thrift_Timeout, time + "");
			
			if (orderRes != null && ResultCode.success.getCode().equals(orderRes.getResultCode())){
				// 统计订单生成数量
				monitorService.send(MonitorPointEnum.Worker_Order_Create_Count);
				
				LogCvt.info(getName() + ": JobId=" + job.getId() 
						+ "，orderId=" + orderRes.getOrderId() 
						+ "，订单已创建，支付结果请查看订单状态。");
			} else {
				LogCvt.error(getName() + ": JobId=" + job.getId() + "，订单状态异常。[resultCode=" + orderRes.getResultCode() + ", resultDesc=" + orderRes.getResultDesc() + "]");
			}
			
		} catch (TException e) {
			
			LogCvt.error("调用Thrift秒杀下单接口异常" + acceptOrder.toString(), e);
			monitorService.send(MonitorPointEnum.Worker_Thrift_Error_Failure);
		}
		
		// 删除处理完成的任务
		boolean isDeleted = beanstalkdService.delete(job.getId());
		if (isDeleted) {
			// 任务数减1
			seckillProductLogic.subQueueJobCount();
		}
	}
	
	private String getLocalIp(){
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
			return ia.getHostAddress();  
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
        return "";  
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
		this.name = NAME_PREFIX + this.index;
	}
	
	public void stop(){
		this.isStop = true;
	}
	
	public String getName(){
		return name;
	}
	
}
