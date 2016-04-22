package com.froad.CB.po.bill;

import java.io.Serializable;


	/**
	 * 类描述：账户校验响应实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jan 9, 2013 5:58:22 PM 
	 */
public class AccountCheck implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String checkOrg;//资金机构号

	private String checkResultCode;
	
	/**
	 * 存  在 62006123456678|张三|首选,62003400521945|张三|已解约
       不存在 NONE|不存在
     *
    **/
	private String checkResultContent;

	
	private String resultCode;
	
	private String signType;
	
	private String signMsg;
	
	private String checkType;
	
	private String checkContent;
	
	private String remark;
	
	public AccountCheck(){}

	public String getCheckResultCode() {
		return checkResultCode;
	}

	public void setCheckResultCode(String checkResultCode) {
		this.checkResultCode = checkResultCode;
	}

	public String getCheckResultContent() {
		return checkResultContent;
	}

	public void setCheckResultContent(String checkResultContent) {
		this.checkResultContent = checkResultContent;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckOrg() {
		return checkOrg;
	}

	public void setCheckOrg(String checkOrg) {
		this.checkOrg = checkOrg;
	}
	
	
}
