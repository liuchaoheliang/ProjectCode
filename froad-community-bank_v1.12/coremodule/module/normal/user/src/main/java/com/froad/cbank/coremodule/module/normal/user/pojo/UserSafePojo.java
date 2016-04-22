package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

import com.froad.cbank.coremodule.module.normal.user.vo.SafeQuestionPojo;

public class UserSafePojo {
	
	private String loginId;
	
	private String mobile;
	
	private String newPwd;
	
	private String oldPwd;
	
	private String ciphertextPwd;
			
	private String ciphertextPwdTemp;
	
	private String ciphertextPwdOld;
	
	private String createSource;

	private String code;
	
	private String imgCode;
	
	private String token;
	
	private String imgToken;
	
//	private Integer questionID;
//	
//	private String questionName;
//	
//	private String answer;
	
	private List<SafeQuestionPojo> safeQuestionPojoList;
	
	private String email;
	
	private String authToken;
	
	private Long memberCode;
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getCiphertextPwd() {
		return ciphertextPwd;
	}

	public void setCiphertextPwd(String ciphertextPwd) {
		this.ciphertextPwd = ciphertextPwd;
	}

	public String getCiphertextPwdTemp() {
		return ciphertextPwdTemp;
	}

	public void setCiphertextPwdTemp(String ciphertextPwdTemp) {
		this.ciphertextPwdTemp = ciphertextPwdTemp;
	}

	public String getCiphertextPwdOld() {
		return ciphertextPwdOld;
	}

	public void setCiphertextPwdOld(String ciphertextPwdOld) {
		this.ciphertextPwdOld = ciphertextPwdOld;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImgToken() {
		return imgToken;
	}

	public void setImgToken(String imgToken) {
		this.imgToken = imgToken;
	}

	public List<SafeQuestionPojo> getSafeQuestionPojoList() {
		return safeQuestionPojoList;
	}

	public void setSafeQuestionPojoList(List<SafeQuestionPojo> safeQuestionPojoList) {
		this.safeQuestionPojoList = safeQuestionPojoList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	
	
}
