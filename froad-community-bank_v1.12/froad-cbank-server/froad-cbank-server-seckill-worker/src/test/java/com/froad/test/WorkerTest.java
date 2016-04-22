package com.froad.test;

import java.nio.charset.Charset;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.mq.beanstalkd.BeanstalkdService;
import com.froad.mq.beanstalkd.SeckillBeanstalkdManager;
import com.froad.po.AcceptOrder;
import com.froad.thrift.client.ThriftService;
import com.froad.thrift.service.SeckillOrderService;
import com.froad.thrift.vo.order.AddSeckillOrderVoReq;
import com.froad.thrift.vo.order.AddSeckillOrderVoRes;
import com.froad.util.JSonUtil;
import com.froad.worker.thread.impl.PlaceOrderWorkerImpl;
import com.trendrr.beanstalk.BeanstalkJob;

/**
 * 秒杀worker测试
 * 
 * @author wangzhangxu
 * @date 2015年5月12日 下午6:37:24
 * @version v1.0
 */
public class WorkerTest {
	
	public static void main(String[] args) {
		//testPutData();
		
		testPlaceOrder();
	}
	
	public static void testPlaceOrder(){
		SeckillBeanstalkdManager beanstalkdManager = new SeckillBeanstalkdManager();
		BeanstalkdService beanstalkdService = beanstalkdManager.getClient();
		
		BeanstalkJob job = beanstalkdService.get(60);
		if (job == null) {
			System.out.println("队列为空");
			return;
		} 
		
		String json = new String(job.getData(), Charset.forName("utf-8"));
		System.out.println("队列消息数据：" + json);
		
		AcceptOrder acceptOrder = null;
		try{
			acceptOrder = JSonUtil.toObject(json, AcceptOrder.class);
		}catch(Exception e){
			beanstalkdService.delete(job.getId());
		}
		
		if (acceptOrder == null) {
			// 队列管道中删除该无效的任务
			beanstalkdService.delete(job.getId());
			System.out.println("数据转换异常");
		} else {
			PlaceOrderWorkerImpl work = new PlaceOrderWorkerImpl();
			AddSeckillOrderVoReq voReq = work.initReq(acceptOrder);
			SeckillOrderService.Iface seckillOrderService = ThriftService.getSeckillOrderService();
			try {
				AddSeckillOrderVoRes orderRes = seckillOrderService.addOrder(voReq);
				if (orderRes != null && ResultCode.success.getCode().equals(orderRes.getResultCode())){
					System.out.println("JobId=" + job.getId() + "，订单已创建并支付成功。");
				} else {
					System.out.println("JobId=" + job.getId() + "，订单状态异常。[resultCode=" + orderRes.getResultCode() + ", resultDesc=" + orderRes.getResultDesc() + "]");
				}
				beanstalkdService.delete(job.getId());
			} catch (TException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void testPutData(){
		SeckillBeanstalkdManager beanstalkdManager = new SeckillBeanstalkdManager();
		BeanstalkdService beanstalkdService = beanstalkdManager.getClient();
		//beanstalkdService.useTube("test");
		String data = "{\"buyNum\":1,\"cashAmount\":0,\"clientId\":\"anhui\",\"createSource\":\"100\",\"createDate\":1431412278001,\"deliveryMoney\":2,\"foilCardNum\":\"\",\"memberCode\":40000000936,\"memberName\":\"dvip66\",\"merchantId\":\"01783B320000\",\"payType\":2,\"pointAmount\":6,\"pointOrgNo\":\"100010002\",\"productId\":\"02C7AA738000\",\"deliveryOption\":\"0\",\"productImage\":\"/froad-cb/coremodule/20150511/cc09f45a-3531-46d1-9421-d55f1ddb6918-thumbnail.jpg\",\"productName\":\"秒杀商品测试2\",\"productType\":\"2\",\"reqId\":\"AO02E11E318000\",\"secPrice\":6,\"vip\":false,\"vipSecPrice\":6}";
		long jobId = beanstalkdService.put(data);
		System.out.println("放入队列成功，jobId=" + jobId);
	}
}
