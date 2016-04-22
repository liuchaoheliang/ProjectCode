package com.froad.timer.bean;

import org.apache.commons.lang.time.DateUtils;

import com.froad.util.DateUtil;
import com.froad.util.TaskTimeUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("task")
public class TaskBean {
	
	public static final String TIME_CONFIG_TYPE_DELAY = "delay";
	public static final String TIME_CONFIG_TYPE_FIXED = "fixed";
	
	@XStreamAlias("taskName")
	private String taskName;
	
	@XStreamAlias("taskClass")
	private String taskClass;
	
	@XStreamAlias("time")
	private String time;
	
	@XStreamAlias("period")
	private Long period;
	
	@XStreamAlias("timeConfigType")
	private String timeConfigType;

	@XStreamAlias("taskNameCN")
	private String taskNameCN;
	
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

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("任务配置: \n")
			.append("任务名称[taskName = "+taskName+"-"+taskNameCN+"] \n")
			.append("任务类[taskClass = "+taskClass+"] \n");
		if( TIME_CONFIG_TYPE_FIXED.equals(timeConfigType) ){
			buffer.append("任务起始时间[time = "+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, TaskTimeUtil.getConfigDate(time))+"] \n");
		}else{
			buffer.append("任务起始时间[time = "+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, TaskTimeUtil.getConfigDate2(time))+"] \n");
		}
//			.append("任务起始时间[time = "+DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, TaskTimeUtil.getConfigDate2(time))+"] \n")
			buffer.append("任务周期[period = "+period+" 分钟]再次运行 \n");
		return buffer.toString();
	}

	public String getTimeConfigType() {
		return timeConfigType;
	}

	public void setTimeConfigType(String timeConfigType) {
		this.timeConfigType = timeConfigType;
	}

	public String getTaskNameCN() {
		return taskNameCN;
	}

	public void setTaskNameCN(String taskNameCN) {
		this.taskNameCN = taskNameCN;
	}
	
	
}
