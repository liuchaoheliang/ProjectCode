package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-8下午5:28:50 
 */
public class MerchantBusiReq implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 4065764442274404844L;
	private String  clientId;
	@NotEmpty(value = "组织编码不能为空")
	private String orgCode;
	/**
	 * 开始时间
	 */
	private String beginDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	private String userId;
	private String token;
	@NotEmpty(value = "flag不能为空")
	private String flag;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
