package com.froad.cbank.coremodule.normal.boss.pojo.member;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 会员查询条件实体
 * @author: yfy
 */
public class MemberReperVo extends Page implements Serializable{
	
	private static final long serialVersionUID = -7109643516507218342L;

	private String uname;			//用户姓名
	private String identityType;	//身份证件类别
	private String identityKey;		//身份证件号       
	private String mobile;		    //电话号码
	private String email;			//申诉邮箱 
	private String cardNo;          //银行卡号
	private String remark;          //申诉备注
	private String imageUrl;        //证件图片
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityKey() {
		return identityKey;
	}
	public void setIdentityKey(String identityKey) {
		this.identityKey = identityKey;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
