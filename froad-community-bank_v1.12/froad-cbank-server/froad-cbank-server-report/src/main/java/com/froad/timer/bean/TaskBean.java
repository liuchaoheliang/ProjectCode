package com.froad.timer.bean;

import org.apache.commons.lang.time.DateUtils;

import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.TaskTimeUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("task")
public class TaskBean {
	
	@XStreamAlias("taskName")
	private String taskName;
	
	@XStreamAlias("taskClass")
	private String taskClass;
	
	@XStreamAlias("time")
	private String time;
	
	@XStreamAlias("period")
	private String period;
	
	@XStreamAlias("taskNext")
	private String taskNext;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getTaskNext() {
		return taskNext;
	}

	public void setTaskNext(String taskNext) {
		this.taskNext = taskNext;
	}

	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("任务配置: \n")
			.append("任务名称[taskName = " + taskName + "] \n")
			.append("任务类[taskClass = " + taskClass + "] \n")
			.append("任务起始时间[time = " + (Checker.isNotEmpty(time) ? DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, TaskTimeUtil.getConfigDate(time)) : "无") + "] \n")
			.append("任务周期[period = " + (Checker.isNotEmpty(period) ? Long.valueOf(period)*DateUtils.MILLIS_PER_MINUTE : 0) + " ms] \n")
			.append("下个任务为[taskNext = " + (Checker.isEmpty(taskNext) ? "无" : taskNext) + "] \n");
		return buffer.toString();
	}
}
