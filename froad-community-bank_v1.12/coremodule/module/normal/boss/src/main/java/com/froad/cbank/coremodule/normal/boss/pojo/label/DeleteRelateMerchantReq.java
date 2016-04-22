package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月28日 上午10:09:15
 * @desc 删除商户关联请求vo
 */
public class DeleteRelateMerchantReq {
	
	/** 商户关联id **/
	@NotEmpty(value="id不能为空")
	private long id;
	/** 客户端ID **/
	@NotEmpty(value="客户端不能为空")
	private String clientId;
	/** 活动编号 **/
	@NotEmpty(value="活动编号不能为空")
	private  String activityNo;
	/** 当前操作人 **/
	private String operator;

	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
