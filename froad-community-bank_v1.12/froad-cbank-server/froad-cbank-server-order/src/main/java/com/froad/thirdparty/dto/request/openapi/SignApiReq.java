/**
 * 文件名称:SignApiReq.java
 * 文件描述: 快速支付加签
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-9
 * 历史修改:  
 */
package com.froad.thirdparty.dto.request.openapi;

import com.froad.thirdparty.enums.BankCardType;
import com.froad.thirdparty.enums.CertificateType;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class SignApiReq {

	private String memberId;// 会员ID
	private String accountName;// 银行卡户名
	private String bankCardNo;// 银行卡卡号
	private BankCardType bankCardType;// 银行卡类型
	private CertificateType certificateType;// 证件类型
	private String certificateNo;// 证件号码
	private String mobilePhone;// 手机号码
	private String mobileToken;// 手机验证码
	private String payOrg;// 机构代号
	private String partnerID;// 合作伙伴ID
	private String singlePenLimit;// 单笔限额
	private String dailyLimit;// 每日限额
	private String monthlyLimit;// 每月限额

	public SignApiReq(String memberId, String accountName, String bankCardNo,
			BankCardType bankCardType, CertificateType certificateType,
			String certificateNo, String mobilePhone, String mobileToken,
			String payOrg, String partnerID, String singlePenLimit,
			String dailyLimit, String monthlyLimit) {
		this.memberId = memberId;
		this.accountName = accountName;
		this.bankCardNo = bankCardNo;
		this.bankCardType = bankCardType;
		this.certificateType = certificateType;
		this.certificateNo = certificateNo;
		this.mobilePhone = mobilePhone;
		this.mobileToken = mobileToken;
		this.payOrg = payOrg;
		this.partnerID = partnerID;
		this.dailyLimit = dailyLimit;
		this.singlePenLimit = singlePenLimit;
		this.monthlyLimit = monthlyLimit;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public BankCardType getBankCardType() {
		return bankCardType;
	}

	public CertificateType getCertificateType() {
		return certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getMobileToken() {
		return mobileToken;
	}

	public String getPayOrg() {
		return payOrg;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public String getSinglePenLimit() {
		return singlePenLimit;
	}

	public String getDailyLimit() {
		return dailyLimit;
	}

	public String getMonthlyLimit() {
		return monthlyLimit;
	}
}
