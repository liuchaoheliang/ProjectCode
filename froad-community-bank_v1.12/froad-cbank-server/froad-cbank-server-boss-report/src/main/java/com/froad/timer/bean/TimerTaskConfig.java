package com.froad.timer.bean;

import org.apache.commons.lang.time.DateUtils;

import com.froad.logback.LogCvt;
import com.froad.timer.task.ITask;
import com.froad.util.TaskTimeUtil;

public class TimerTaskConfig {
	
	private ITask task;
	
	private long delay;
	
	private long period;
	
	public TimerTaskConfig(TaskBean task){
		try {
			this.task = (ITask)Class.forName(task.getTaskClass()).getConstructor(String.class, TaskBean.class).newInstance(task.getTaskName(), task);
			this.delay = TaskTimeUtil.getDistanceTime(task.getTime());
			this.period = Long.valueOf(task.getPeriod()) * DateUtils.MILLIS_PER_MINUTE;
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
	}

	public ITask getTask() {
		return task;
	}

	public void setTask(ITask task) {
		this.task = task;
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
