package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 类描述：商户占比相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-2下午4:18:43 
 */
public class MerchantTypePercentReq implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 6754590887021480694L;
	/**
	 * 开始时间
	 */
	private String beginDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	/**
	 * 机构号
	 */
	@NotEmpty(value = "组织编码不能为空")
	private String orgCode;
	/**
	 * 商户类型
	 */
	private String type;
	/**
	 * 商户类型占比
	 */
	private BigDecimal percent;
	private String clientId;
	/**
	 * 是否默认查询
	 */
	@NotEmpty(value = "flag不能为空")
	private String flag;
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	} 
	
	
}
