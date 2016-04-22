package com.froad.timer.task;

import com.froad.logback.LogCvt;
import com.froad.timer.task.support.PaymentAccountCheckingDaily;

/**
 * 每日对账需求处理
* <p>Function: TaskPaymentAccountCheckingDaily</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015年7月30日 上午10:14:24
* @version 1.0
 */
public class TaskPaymentAccountCheckingDaily implements Runnable{

	PaymentAccountCheckingDaily accountChecking = new PaymentAccountCheckingDaily();
	
	@Override
	public void run() {
		LogCvt.debug("定时任务: 定时获取对账文件------开始------");
		accountChecking.taskStart();
	}

}
