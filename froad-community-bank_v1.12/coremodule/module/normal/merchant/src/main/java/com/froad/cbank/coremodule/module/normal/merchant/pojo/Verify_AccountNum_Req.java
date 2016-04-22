package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

public class Verify_AccountNum_Req extends BasePojo {
	@NotEmpty(value = "收款账户名称不能为空")
	@Regular(reg = "^[\u4e00-\u9fa5]{1,30}$", value = "请输入正确的收款账户名")
	private String acctName;

	@NotEmpty(value = "收款账号不能为空")
	@Regular(reg = "^[0-9]{1,24}$", value = "请输入正确的收款账户号")
	private String acctNumber;
	private String openingBank;
	private String outletId;
	private String certificateType;//证件类型
	private String certificateNo;//证件号码
	
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

}
