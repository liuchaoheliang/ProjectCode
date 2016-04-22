package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月27日 下午3:08:41
 * @desc 启用或禁用商户推荐活动标签req
 */
public class EnableMerchantReqVo {
	
	@NotEmpty(value="id不能为空")
	private long Id;
	@NotEmpty(value="客户端不能为空")
	private String clientId;
	@NotEmpty(value="状态不能为空")
	private String status;
	private String operator;

	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
