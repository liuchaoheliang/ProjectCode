package com.froad.thirdparty.dto.request.openapi;

import com.froad.thirdparty.enums.MobileTokenType;

public class CreateMobileTokenApiReq {
	private String bankCardNo;// 银行卡卡号
	private String mobilePhone;// 手机号码
	private MobileTokenType type;// 验证码类型
	private String remark;// 备注信息
	private String payOrg;// 机构代号
	private String partnerID;// 合作伙伴ID

	public CreateMobileTokenApiReq(String bankCardNo, String mobilePhone,MobileTokenType type, String remark, String payOrg, String partnerID) {
		this.bankCardNo = bankCardNo;
		this.mobilePhone = mobilePhone;
		this.type = type;
		this.remark = remark;
		this.payOrg = payOrg;
		this.partnerID = partnerID;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public MobileTokenType getType() {
		return type;
	}

	public String getRemark() {
		return remark;
	}

	public String getPayOrg() {
		return payOrg;
	}

	public String getPartnerID() {
		return partnerID;
	}
}
