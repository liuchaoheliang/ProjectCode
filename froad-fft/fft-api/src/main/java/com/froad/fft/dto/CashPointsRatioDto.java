
	 /**
  * 文件名：CashPointsRatioDto.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.dto;

import java.util.Date;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午3:15:58 
 */
public class CashPointsRatioDto {
	private Long id;
	
	private Date createTime;
	
	private String fftPoints;//现金和分分通积分的比例(如果 1元=0.5积分,该值为0.5;1元=3积分,该值为3)
	
	private String bankPoints;//现金和银行积分的比例
	
	private Long sysClientId;//客户端编号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFftPoints() {
		return fftPoints;
	}

	public void setFftPoints(String fftPoints) {
		this.fftPoints = fftPoints;
	}

	public String getBankPoints() {
		return bankPoints;
	}

	public void setBankPoints(String bankPoints) {
		this.bankPoints = bankPoints;
	}

	public Long getSysClientId() {
		return sysClientId;
	}

	public void setSysClientId(Long sysClientId) {
		this.sysClientId = sysClientId;
	}	

}
