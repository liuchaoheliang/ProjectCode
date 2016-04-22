package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-3下午5:16:30 
 */
public class MerchantAddTypePercentReq implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -685875893710113278L;
	/**
	 * 开始时间
	 */
	private String beginDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	private String type;
	private BigDecimal percent;
	private String clientId;
	@NotEmpty(value = "组织编码不能为空")
	private String orgCode;
	@NotEmpty(value = "flag不能为空")
	private String flag;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
