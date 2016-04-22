package com.froad.CB.po.transaction;

import java.io.Serializable;


	/**
	 * 类描述：积分账户信息
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jan 5, 2013 3:52:39 PM 
	 */
public class PointsAccount implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String accountId;
	private String orgNo;
	private String orgName;
	private String points;
	private String frozenPoints;
	
	public PointsAccount(){}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getFrozenPoints() {
		return frozenPoints;
	}
	public void setFrozenPoints(String frozenPoints) {
		this.frozenPoints = frozenPoints;
	}

}
