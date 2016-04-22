package com.froad.cbank.coremodule.normal.boss.pojo.order;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 积分报表
 * @author liaopeixin
 *	@date 2016年1月21日 下午6:20:26
 */
public class PointReportReq extends Page{

	/**
	 * 客户端
	 */
	private String clientId;
	/**
	 * 是否面对面1：是 ，0：否
	 */
	private String isQrcode;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getIsQrcode() {
		return isQrcode;
	}
	public void setIsQrcode(String isQrcode) {
		this.isQrcode = isQrcode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
