package com.froad.timer.task;

import com.froad.logback.LogCvt;
import com.froad.timer.task.support.TaskPointSettlementStatisticsSupport;

/**
 * 每日对账需求处理
* <p>Function: TaskPaymentAccountCheckingDaily</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015年7月30日 上午10:14:24
* @version 1.0
 */
public class TaskPointSettlementStatistics implements Runnable{

	TaskPointSettlementStatisticsSupport support = new TaskPointSettlementStatisticsSupport();
	
	@Override
	public void run() {
		long time = System.currentTimeMillis();
		LogCvt.info("定时任务: 积分结算明细统计------开始------");
		
		int count = support.taskStart();
		
		long s = (System.currentTimeMillis() - time)/1000;
		LogCvt.info("定时任务: 积分结算明细统计------结束------,耗时" + s + "s,共处理结算记录" + count + "条");
	}

}
