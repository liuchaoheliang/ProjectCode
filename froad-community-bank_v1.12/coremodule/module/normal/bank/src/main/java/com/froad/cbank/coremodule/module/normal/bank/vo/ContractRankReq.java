package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-3下午5:06:59 
 */
public class ContractRankReq implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -717105880752013764L;
	private String clientId;
	@NotEmpty(value = "组织编码不能为空")
	private String orgCode;
	private String constractStaff;
	private String count;
	private String sort;
	private String flag;
	public String getConstractStaff() {
		return constractStaff;
	}
	public void setConstractStaff(String constractStaff) {
		this.constractStaff = constractStaff;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
