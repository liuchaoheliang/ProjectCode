package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 关联商品删除
 * 
 * @ClassName ProductDelReq
 * @author zxl
 * @date 2015年10月28日 下午2:43:01
 */
public class ProductDelReq {

	/**
	 * 商品关联id *
	 */
	@NotEmpty(value="权重关联ID不能为空")
	private String id; 
	/**
	 * 活动编号 *
	 */
	@NotEmpty(value="活动编号不能为空")
	private String activityNo; 
	/**
	 * 客户端号 *
	 */
	@NotEmpty(value="客户端不能为空")
	private String clientId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	
}
