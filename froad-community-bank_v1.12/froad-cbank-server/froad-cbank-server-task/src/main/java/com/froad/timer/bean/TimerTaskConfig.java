package com.froad.timer.bean;

import org.apache.commons.lang.time.DateUtils;

import com.froad.logback.LogCvt;
import com.froad.util.TaskTimeUtil;

public class TimerTaskConfig {
	
	private static final String DELAY_TIME_DEFAULT = "60"; // 默认延迟启动时长 60分钟
	
	private Runnable timerTask;
	
	private long delay;
	
	private long period;
	
	private String timeConfigType;
	
	public TimerTaskConfig(TaskBean task){
		try {
			timerTask = (Runnable)Class.forName(task.getTaskClass()).newInstance();
			timeConfigType = task.getTimeConfigType();
			if( TaskBean.TIME_CONFIG_TYPE_DELAY.equals(timeConfigType) ){
				delay = TaskTimeUtil.getAccumulateDelayTime(task.getTime());
			}else if( TaskBean.TIME_CONFIG_TYPE_FIXED.equals(timeConfigType) ){
				delay = TaskTimeUtil.getDistanceTime(task.getTime());
			}else{
				delay = TaskTimeUtil.getAccumulateDelayTime(DELAY_TIME_DEFAULT);
			}
			
			period = task.getPeriod() * DateUtils.MILLIS_PER_MINUTE;
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
	}

	public Runnable getTimerTask() {
		return timerTask;
	}

	public void setTimerTask(Runnable timerTask) {
		this.timerTask = timerTask;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}
	
}
