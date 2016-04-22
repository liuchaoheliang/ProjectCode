/**
 * 文件名称:SignCancelApiReq.java
 * 文件描述: 快捷支付认证取消
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-9
 * 历史修改:  
 */
package com.froad.thirdparty.dto.request.openapi;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class SignCancelApiReq {

	private String memberId;// 会员ID
	private String bankCardNo;// 银行卡卡号
	private String payOrg;// 机构代号
	private String partnerID;// 合作伙伴ID

	public SignCancelApiReq(String memberId, String bankCardNo, String payOrg,
			String partnerID) {
		this.memberId = memberId;
		this.bankCardNo = bankCardNo;
		this.payOrg = payOrg;
		this.partnerID = partnerID;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public String getPayOrg() {
		return payOrg;
	}

	public String getPartnerID() {
		return partnerID;
	}
}
