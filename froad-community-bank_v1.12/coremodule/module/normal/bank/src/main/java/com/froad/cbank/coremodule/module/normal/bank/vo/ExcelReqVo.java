package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class ExcelReqVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6673272590963570246L;
	/** 开始时间 */
	private String beginDate;
	/** 结束时间 */
	// private String endDate;
	@NotEmpty(value = "组织编码不能为空!")
	/** 机构号 */
	private String orgCode;
	/** 用户id */
	private String userId;
	/** 安全令牌 */
	private String token;
	@NotEmpty(value = "flag不能为空")
	private String flag;
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
