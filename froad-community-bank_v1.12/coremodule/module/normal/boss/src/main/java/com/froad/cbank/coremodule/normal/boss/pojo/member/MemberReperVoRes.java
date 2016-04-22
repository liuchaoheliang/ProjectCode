package com.froad.cbank.coremodule.normal.boss.pojo.member;

import java.io.Serializable;

/**
 * 会员查询条件实体
 * @author: yfy
 */
public class MemberReperVoRes implements Serializable{
	
	private static final long serialVersionUID = -7109643516507218342L;

	private String loginID;         //用户名
	private Long msgID;           //申诉ID
	private Long memberCode;	  //会员号
    private String yourName;      //真实姓名
    private String identifyType;  //证件类型
    private String identifyTypeName;//证件类型名称
    private String identifyNo;    //证件号码
    private String identifyPic;   //证件图片
	private String mobile;		  //申诉手机好
	private String bindMobile;    //绑定手机号码
	private String email;		  //邮箱 
    private String cardNo;        //绑定银行卡号
    private String demo;          //备注
    private String state;         //审核状态
    private String createTime;    //创建时间
	private String clientChannelName;//客户端
	private String reason;        //审核意见
    
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public Long getMsgID() {
		return msgID;
	}
	public void setMsgID(Long msgID) {
		this.msgID = msgID;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBindMobile() {
		return bindMobile;
	}
	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getYourName() {
		return yourName;
	}
	public void setYourName(String yourName) {
		this.yourName = yourName;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
	public String getIdentifyTypeName() {
		return identifyTypeName;
	}
	public void setIdentifyTypeName(String identifyTypeName) {
		this.identifyTypeName = identifyTypeName;
	}
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
	public String getIdentifyPic() {
		return identifyPic;
	}
	public void setIdentifyPic(String identifyPic) {
		this.identifyPic = identifyPic;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getClientChannelName() {
		return clientChannelName;
	}
	public void setClientChannelName(String clientChannelName) {
		this.clientChannelName = clientChannelName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
