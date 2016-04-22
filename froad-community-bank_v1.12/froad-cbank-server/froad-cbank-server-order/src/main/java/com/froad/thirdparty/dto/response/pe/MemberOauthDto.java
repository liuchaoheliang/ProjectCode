package com.froad.thirdparty.dto.response.pe;

import java.io.Serializable;

public class MemberOauthDto implements Serializable{
	private Integer id;

	private Long memberCode; // 会员号

	private String openId; // 第三方登录名

	private String nickName; // 昵称

	private String oauthType; // 第三方类别

	private Integer status; // 状态

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOauthType() {
		return oauthType;
	}

	public void setOauthType(String oauthType) {
		this.oauthType = oauthType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
