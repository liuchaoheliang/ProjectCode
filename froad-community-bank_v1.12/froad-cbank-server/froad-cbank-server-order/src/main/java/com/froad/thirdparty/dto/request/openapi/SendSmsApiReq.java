/**
 * 文件名称:SendSmsApiReq.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-9
 * 历史修改:  
 */
package com.froad.thirdparty.dto.request.openapi;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class SendSmsApiReq {
	private String mobilePhone;// 手机号
	private Date createTime; // 发送时间，内部创建
	private String remark; // 短信内容
	private String payOrg; // 机构代号
	private String partnerID;// 合作伙伴ID
	private SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

	public SendSmsApiReq(String mobilePhone, String remark, String payOrg,
			String partnerID) {
		this.mobilePhone = mobilePhone;
		this.remark = remark;
		this.payOrg = payOrg;
		this.partnerID = partnerID;
		this.createTime = new Date();
	}

	public String getMobilePhone() {
		return mobilePhone;
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

	public String getCreateTime() {
		return sf.format(createTime);
	}
}
