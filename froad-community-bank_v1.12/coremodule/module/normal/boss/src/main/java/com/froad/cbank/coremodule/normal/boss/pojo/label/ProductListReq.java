package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 关联商品查询请求
 * @ClassName ProductListReq
 * @author zxl
 * @date 2015年10月28日 下午2:43:01
 */
public class ProductListReq extends Page{
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 *活动编号
	 */
	private String activityNo;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	
	
}
