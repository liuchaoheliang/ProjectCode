package com.froad.fft.support;

import java.util.Date;


	/**
	 * 类描述：团购和精品预售的共同属性
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月29日 下午4:35:04 
	 */
public class GroupCommonBean {

	private Integer perNumber;//最大购买数量
	
	private Integer perMinNumber;//最小购买数量
	
	private Date startTime;//团购开始时间
	
	private Date endTime;//团购结束时间

	public Integer getPerNumber() {
		return perNumber;
	}

	public void setPerNumber(Integer perNumber) {
		this.perNumber = perNumber;
	}

	public Integer getPerMinNumber() {
		return perMinNumber;
	}

	public void setPerMinNumber(Integer perMinNumber) {
		this.perMinNumber = perMinNumber;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
