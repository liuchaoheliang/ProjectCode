package com.froad.thirdparty.dto.response.pe;

import java.util.Date;


public class MemberInfo {

    private Long memberCode;            // 会员号
    private Integer memberType;         // 会员类型 1：个人会员，2：企业会员
    private String loginID;             // 登录ID
    private String loginPwd;            // 密码
    private Integer loginType;          // 登录类别
    private String uname;               // 用户姓名
    private String email;               // 邮箱
    private Integer status;             // 用户活动状态1正常状态(已激活)，2.未激活，3已冻结
    private String mobile;              // 电话号码
    private String identityKey;         // 身份证号
    private String sex;                 // 性别0：男 1:女性2：保密
    private Integer age;                // 年龄
    private Date createTime;            // 注册时间
    private Date beginTime;             // 注册时间
    private Date endTime;               // 注册时间
    private Date updateTime;            // 修改时间
    private String createChannel;       // 创建渠道：FFT 分分通,MALL 商城，FFT_MC 分分通手机客户端，MAll_MC 商城手机客户端
    private String address;             // ADDRESS
    private String zipCode;             // 邮编
    private String orgCode;             // 部门
    private String dutyCode;            // 职位
    private String serviceLevelCode;    // 服务级别
    private String lastLoginIP;         // 最近登录IP地址
    private Date lastLoginTime;         // 最近登录时间
    private Integer loginFailureCount;  // 登陆失败限制次数，默认为0
    private Date lockDate;              // 账号冻结日期
    private String allowIP;             // 允许登录的IP
    private String userID;              // 用户ID
    private Date birthday;              // 生日
    private Date firstTryTime;          // 首次失败时间
    private Integer tryLoginCount;      // 连续登陆失败次数，默认为0
    private String registerIP;
    private String loginFlag;           // 成功登录标识
    private String introduce;           // 个人介绍
    private Boolean isBindEmail;        // 是否绑定邮箱 0：未绑定，1：已绑定
    private Boolean isBindMobile;       // 是否绑定手机 0：未绑定，1：已绑定
    private Boolean isBindBank;         // 是否绑定银行 0：未绑定，1：已绑定
    private Boolean isValidBank;        // 是否验证绑定的银行 0：未验证，1：已验证
    private String batchNo;             // 批次号
    private String userBankID;          // 用户跟踪号
    private String bankGroupId;         // 银行组号
    private String bankName;            // 银行名称
    private String bankOrgNo;           // 银行标识
    private String introducer;          // 介绍人login_id..
    private Long introducerMemberCode;  // 介绍人MemberCode
    private String pwd;                 // 密码
    
    
    //联合登录token
    private String token;
    
    /**
     * 用户VIP信息
     */
    private MemberVipInfo userVipInfo;
    
    public Long getMemberCode() {
        return memberCode;
    }
    public void setMemberCode(Long memberCode) {
        this.memberCode = memberCode;
    }
    public Integer getMemberType() {
        return memberType;
    }
    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }
    public String getLoginID() {
        return loginID;
    }
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }
    public String getLoginPwd() {
        return loginPwd;
    }
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
    public Integer getLoginType() {
        return loginType;
    }
    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getIdentityKey() {
        return identityKey;
    }
    public void setIdentityKey(String identityKey) {
        this.identityKey = identityKey;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getCreateChannel() {
        return createChannel;
    }
    public void setCreateChannel(String createChannel) {
        this.createChannel = createChannel;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getDutyCode() {
        return dutyCode;
    }
    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }
    public String getServiceLevelCode() {
        return serviceLevelCode;
    }
    public void setServiceLevelCode(String serviceLevelCode) {
        this.serviceLevelCode = serviceLevelCode;
    }
    public String getLastLoginIP() {
        return lastLoginIP;
    }
    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }
    public Date getLastLoginTime() {
        return lastLoginTime;
    }
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }
    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }
    public Date getLockDate() {
        return lockDate;
    }
    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }
    public String getAllowIP() {
        return allowIP;
    }
    public void setAllowIP(String allowIP) {
        this.allowIP = allowIP;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public Date getFirstTryTime() {
        return firstTryTime;
    }
    public void setFirstTryTime(Date firstTryTime) {
        this.firstTryTime = firstTryTime;
    }
    public Integer getTryLoginCount() {
        return tryLoginCount;
    }
    public void setTryLoginCount(Integer tryLoginCount) {
        this.tryLoginCount = tryLoginCount;
    }
    public String getRegisterIP() {
        return registerIP;
    }
    public void setRegisterIP(String registerIP) {
        this.registerIP = registerIP;
    }
    public String getLoginFlag() {
        return loginFlag;
    }
    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public Boolean getIsBindEmail() {
        return isBindEmail;
    }
    public void setIsBindEmail(Boolean isBindEmail) {
        this.isBindEmail = isBindEmail;
    }
    public Boolean getIsBindMobile() {
        return isBindMobile;
    }
    public void setIsBindMobile(Boolean isBindMobile) {
        this.isBindMobile = isBindMobile;
    }
    public Boolean getIsBindBank() {
        return isBindBank;
    }
    public void setIsBindBank(Boolean isBindBank) {
        this.isBindBank = isBindBank;
    }
    public Boolean getIsValidBank() {
        return isValidBank;
    }
    public void setIsValidBank(Boolean isValidBank) {
        this.isValidBank = isValidBank;
    }
    public String getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    public String getUserBankID() {
        return userBankID;
    }
    public void setUserBankID(String userBankID) {
        this.userBankID = userBankID;
    }
    public String getBankGroupId() {
        return bankGroupId;
    }
    public void setBankGroupId(String bankGroupId) {
        this.bankGroupId = bankGroupId;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankOrgNo() {
        return bankOrgNo;
    }
    public void setBankOrgNo(String bankOrgNo) {
        this.bankOrgNo = bankOrgNo;
    }
    public String getIntroducer() {
        return introducer;
    }
    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }
    public Long getIntroducerMemberCode() {
        return introducerMemberCode;
    }
    public void setIntroducerMemberCode(Long introducerMemberCode) {
        this.introducerMemberCode = introducerMemberCode;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public MemberVipInfo getUserVipInfo() {
        return userVipInfo;
    }
    public void setUserVipInfo(MemberVipInfo userEngineVipDto) {
        this.userVipInfo = userEngineVipDto;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
}
