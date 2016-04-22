package com.froad.timer.task;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.froad.db.redis.TimePaymentRedisService;
import com.froad.enums.MonitorPointEnum;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.ServiceFactory;
import com.froad.util.ThriftConfig;

/**
 * 
 * @ClassName: TaskCheckTransResults 
 * @Description: 定时核对支付系统结果
 * @author: huangyihao
 * @date: 2015年3月24日
 */
public class TaskCheckTransResults implements Runnable {
	
	private TimePaymentRedisService timePaymentRedisService = ServiceFactory.getTimePaymentRedisService();
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	
	@Override
	public void run() {
		
		LogCvt.debug("定时任务: 定时核对支付系统结果------开始------");
		
		try {
			PaymentService.Iface paymentClient = 
					(PaymentService.Iface)ThriftClientProxyFactory.createIface(PaymentService.class.getName(), PaymentService.class.getSimpleName(), ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
			
			Set<String> paymentInfo = timePaymentRedisService.getPaymentInfo();
			if(CollectionUtils.isEmpty(paymentInfo)){
				LogCvt.debug("定时任务: 定时核对支付系统结果------完成(缓存 cbbank:time_payment无支付单信息)------");
				return;
			}
			Iterator<String> iterator = paymentInfo.iterator();
			int count = 0;
			LogCvt.debug("定时任务: 定时核对支付系统结果------缓存 cbbank:time_payment 中共有 "+paymentInfo.size()+" 个支付单需要核对");
			while(iterator.hasNext()){
				String paymentId = iterator.next();
				LogCvt.debug("支付单号[paymentId="+paymentId+"]核对系统结果 --- 开始");
				//调用支付系统核对接口
				try{
					ResultVo result = paymentClient.verifyPaymentResultForTask(paymentId);;
					LogCvt.debug("支付单号[paymentId="+paymentId+"]核对支付系统结果处理结果: 返回码[resultCode="+result.getResultCode()+"]|返回信息[resultDesc="+result.getResultDesc()+"]");
				} catch (Exception e) {
					LogCvt.error("定时任务: 定时核对支付系统结果------调用接口异常------");
					LogCvt.error(e.getMessage(), e);
					// TODO 调用接口异常 - 发送监控
					monitorService.send(MonitorPointEnum.Timertask_Check_TransResults_Failure);
				}
				LogCvt.debug("支付单号[paymentId="+paymentId+"]核对系统结果 --- 结束");
				// 控制查询频率
				count ++;
				if(count % 100 == 0){
					Thread.sleep(1000);
				}
			}
			
		} catch (Exception e) {
			LogCvt.error("定时任务: 定时核对支付系统结果------系统异常------");
			LogCvt.error(e.getMessage(), e);
			// TODO 确认支付结果处理不成功 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Check_TransResults_Failure);
		} finally {
			LogCvt.debug("定时任务: 定时核对支付系统结果------结束------");
		}
		
	}
	
}
