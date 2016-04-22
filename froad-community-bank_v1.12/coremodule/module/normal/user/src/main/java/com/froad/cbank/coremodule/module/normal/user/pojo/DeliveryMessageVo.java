package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * 物流信息跟踪
 * @author yfy
 *
 */
public class DeliveryMessageVo {

	/**
	 * 时间
	 */
	private String createTime;
	/**
	 * 事件
	 */
	private String msg;
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
