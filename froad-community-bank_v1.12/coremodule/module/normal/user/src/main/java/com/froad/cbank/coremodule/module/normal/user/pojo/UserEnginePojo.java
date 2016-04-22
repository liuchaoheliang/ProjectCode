package com.froad.cbank.coremodule.module.normal.user.pojo;

public class UserEnginePojo {
		/**
		 * memberCode
		 */
		private Long memberCode;

		/**
		 * 帐号
		 */
		private String loginId;
		
		/**
		 * 手机号码
		 */
		private String mobile;

		/**
		 * 邮箱
		 */
		private String email;

		/**
		 * 是否绑定手机号
		 */
		private boolean isBindMobile;
		
		/**
		 * 是否设置支付密码
		 */
		private boolean isSetPayPwd;
		
		/**
		 * 是否绑定银行卡
		 */
		private boolean isSignCard;

		/**
		 * 是否设置安全问题
		 */
		private boolean isSetQuestion;
		
		/**
		 * 会员等级
		 */
		private String vipLevel;
		
		/**
		 * 会员状态
		 */
		private String vipStatus;
		
		/**
		 * 密码安全等级
		 */
		private Boolean isSimpleLoginPwd;

		/**
		 * 是否是白名单用户
		 */
		private Boolean isInWhiteList;


		public UserEnginePojo() {
		}

		public UserEnginePojo(Long memberCode, String loginId, String mobile, String email, boolean isBindMobile, boolean isSetPayPwd, boolean isSetQuestion, boolean isSignCard, String vipLevel, String vipStatus, Boolean isSimpleLoginPwd,Boolean isInWhiteList){
			this.memberCode = memberCode;
			this.loginId = loginId;
			this.mobile = mobile;
			this.email = email;
			this.isBindMobile = isBindMobile;
			this.isSetPayPwd = isSetPayPwd;
			this.isSetQuestion = isSetQuestion;
			this.isSignCard = isSignCard;
			this.vipLevel = vipLevel;
			this.vipStatus = vipStatus;
			this.isSimpleLoginPwd = isSimpleLoginPwd;
			this.isInWhiteList = isInWhiteList;
		}

		public Boolean getIsInWhiteList() {
			return isInWhiteList;
		}

		public void setIsInWhiteList(Boolean isInWhiteList) {
			this.isInWhiteList = isInWhiteList;
		}

		public Long getMemberCode() {
			return memberCode;
		}

		public void setMemberCode(Long memberCode) {
			this.memberCode = memberCode;
		}

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

		

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public boolean isIsBindMobile() {
			return isBindMobile;
		}

		public void setIsBindMobile(boolean isBindMobile) {
			this.isBindMobile = isBindMobile;
		}

		public boolean isIsSetPayPwd() {
			return isSetPayPwd;
		}

		public void setIsSetPayPwd(boolean isSetPayPwd) {
			this.isSetPayPwd = isSetPayPwd;
		}

		public String getVipLevel() {
			return vipLevel;
		}

		public void setVipLevel(String vipLevel) {
			this.vipLevel = vipLevel;
		}

		public String getVipStatus() {
			return vipStatus;
		}

		public void setVipStatus(String vipStatus) {
			this.vipStatus = vipStatus;
		}

		public boolean isIsSignCard() {
			return isSignCard;
		}

		public void setIsSignCard(boolean isSignCard) {
			this.isSignCard = isSignCard;
		}

		public boolean isIsSetQuestion() {
			return isSetQuestion;
		}

		public void setIsSetQuestion(boolean isSetQuestion) {
			this.isSetQuestion = isSetQuestion;
		}

		public Boolean getIsSimpleLoginPwd() {
			return isSimpleLoginPwd;
		}

		public void setIsSimpleLoginPwd(Boolean isSimpleLoginPwd) {
			this.isSimpleLoginPwd = isSimpleLoginPwd;
		}

		public boolean isBindMobile() {
			return isBindMobile;
		}

		public void setBindMobile(boolean isBindMobile) {
			this.isBindMobile = isBindMobile;
		}

		public boolean isSetPayPwd() {
			return isSetPayPwd;
		}

		public void setSetPayPwd(boolean isSetPayPwd) {
			this.isSetPayPwd = isSetPayPwd;
		}

		public boolean isSignCard() {
			return isSignCard;
		}

		public void setSignCard(boolean isSignCard) {
			this.isSignCard = isSignCard;
		}

		public boolean isSetQuestion() {
			return isSetQuestion;
		}

		public void setSetQuestion(boolean isSetQuestion) {
			this.isSetQuestion = isSetQuestion;
		}
		
		
		
}
