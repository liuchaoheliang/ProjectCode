package com.froad.fft.persistent.entity;

/**
 * 会员
 * @author FQ
 *
 */
public class Member extends BaseEntity{
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Long memberCode;//账户服务ID
	private String username;
	private String mobile;

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
