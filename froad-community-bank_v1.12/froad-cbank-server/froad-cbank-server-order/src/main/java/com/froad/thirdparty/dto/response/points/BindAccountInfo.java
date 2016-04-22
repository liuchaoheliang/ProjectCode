
	 /**
  * 文件名：BindAccountInfo.java
  * 版本信息：Version 1.0
  * 日期：2014年7月25日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.thirdparty.dto.response.points;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年7月25日 上午11:55:12 
 */
public class BindAccountInfo {
	
	private String bankId;//银行编号
	private String bankCard;//银行卡号
	private String orgNo;//银行编号
	private String bankName;//银行名称
	private String status;//签约状态
	
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
