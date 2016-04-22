package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 类描述：相关业务类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-6-2下午4:04:19
 */
public class MerchantTrendReq extends BaseVo implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -6686406864546084241L;
	private String clientId;
	@NotEmpty(value = "机构号不能为空")
	private String orgCode;
	private int week;
	private int addCount;
	private String beginDate;
	private String flag;// 默认查询:0,条件查询:1
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getAddCount() {
		return addCount;
	}
	public void setAddCount(int addCount) {
		this.addCount = addCount;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
