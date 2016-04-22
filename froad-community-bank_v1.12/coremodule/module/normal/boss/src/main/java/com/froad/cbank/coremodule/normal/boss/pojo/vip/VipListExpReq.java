package com.froad.cbank.coremodule.normal.boss.pojo.vip;

/**
 * 明细列表导出请求
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月30日 上午10:30:38
 */
public class VipListExpReq {
	private Long beginTime;
	private Long endTime;
	private String createChannel;
	private String labelId;
	private String createType;
	private String bankChannel;
	
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getCreateChannel() {
		return createChannel;
	}
	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getCreateType() {
		return createType;
	}
	public void setCreateType(String createType) {
		this.createType = createType;
	}
	public String getBankChannel() {
		return bankChannel;
	}
	public void setBankChannel(String bankChannel) {
		this.bankChannel = bankChannel;
	}
}
