package com.froad.cbank.coremodule.normal.boss.pojo.external;

/**
 * 物流信息进度
 * @author yfy
 */
public class ResultItem {

	/**
	 * 时间，原始格式
	 */
	private String time;
	/**
	 * 内容
	 */
	private String context;
	/**
	 * 格式化后时间
	 */
	private String ftime;
	/**
	 * 地区
	 */
	private String areaCode;
	/**
	 * 地区名称
	 */
    private String areaName;
    /**
     * 运输状态
     */
    private String status;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFtime() {
		return ftime;
	}
	public void setFtime(String ftime) {
		this.ftime = ftime;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
