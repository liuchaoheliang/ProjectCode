package com.froad.CB.common;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.froad.CB.dao.impl.PayDaoImpl;
import com.froad.CB.dao.impl.TransDaoImpl;
import com.froad.CB.po.Pay;
import com.froad.CB.service.impl.TransServiceImpl;



	/**
	 * 类描述：定时任务
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Aug 12, 2013 3:16:46 PM 
	 */
public class AutoTask extends QuartzJobBean {
	private static Logger logger = Logger.getLogger(AutoTask.class);

	public AutoTask() {
	}

	
	public void everyQuarter() {
		
	}
	
	
	public void everyHour() {
		
	}
	
	
	public void everyDay(){
		logger.info("=====开始执行everyDay定时任务=====");
		TransDaoImpl transDaoImpl=(TransDaoImpl)SpringContextUtil.getBean("transDaoImpl");
		PayDaoImpl payDaoImpl=(PayDaoImpl) SpringContextUtil.getBean("payDaoImpl");
		
		logger.info("开始执行关闭超时的现金交易");
		int num=transDaoImpl.closeTimeoutCashTrans();
		logger.info("结束执行关闭超时的现金交易，关闭订单数量为："+num);
		
		HashSet<String> allTransId=new HashSet<String>();
		HashSet<String> removeTransId=new HashSet<String>();
		List<String> transId=new ArrayList<String>();
		
		logger.info("开始执行关闭只下订单未做任何支付的Trans");
		List<Pay> list=payDaoImpl.getAllWithoutPays();
		if(list != null && list.size()>0){
			for(Pay pay:list){
				allTransId.add(pay.getTransId());
				//1001 pay记录状态为等待买家付款
				if(!"1001".equals(pay.getState())){
					removeTransId.add(pay.getTransId());
				}
			}
			logger.info("一共查出含有1001状态的Trans记录："+allTransId.size()+"条");
			logger.info("其中不需要关闭的Trans记录："+removeTransId.size()+"条");
			allTransId.removeAll(removeTransId);
			Iterator<String> it=allTransId.iterator();
			while(it.hasNext()){
				String tranId=it.next();
				transId.add(tranId);
				logger.info("将要关闭Trans记录的ID："+tranId);
			}
			int count=0;
			if(transId!=null && transId.size()>0){
				count=transDaoImpl.closeWithoutPayTrans(transId); 
			}
			logger.info("成功关闭Trans记录"+count+"条");
			
			
		}
		logger.info("结束执行关闭只下订单未做任何支付的Trans");
		
		
		TransServiceImpl transServiceImpl=(TransServiceImpl)SpringContextUtil.getBean("transService");
		
		transServiceImpl.closePresellTransOutofTime();
		
		transServiceImpl.refundPointsTask();
		logger.info("=====结束执行everyDay定时任务=====");
	}
	
	public void doClusterTask(){
		logger.info("=====开始执行精品预售成团定时任务=====");
		TransServiceImpl transServiceImpl=(TransServiceImpl)SpringContextUtil.getBean("transService");
		transServiceImpl.cluster();
		logger.info("=====结束执行精品预售成团定时任务=====");
	}
	
	public void everyWeek() {
		
	}
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
	}

}
