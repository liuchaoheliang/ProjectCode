
package com.froad.client.goodsExchangeRack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for user complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="user">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="activateURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="activatecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actprojectID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="authoritiesId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstLogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identityKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isAccountLocked" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isPointActivateAccountLocked" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="joinedActprojectID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastPointActivateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastTryTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lockedDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginFailureCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="memberCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="memberID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="memberType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="mobilephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msgCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointActivateFailureCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="pointActivateLockedDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registerIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SPpassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userCertification" type="{http://service.CB.froad.com/}userCertification" minOccurs="0"/>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="user_authorities_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userrole" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
    "activateURL",
    "activatecode",
    "actprojectID",
    "age",
    "authoritiesId",
    "balance",
    "bank",
    "channelNo",
    "createChannel",
    "createTime",
    "email",
    "firstLogin",
    "identityKey",
    "isAccountLocked",
    "isPointActivateAccountLocked",
    "joinedActprojectID",
    "lastIP",
    "lastPointActivateTime",
    "lastTime",
    "lastTryTime",
    "lockedDate",
    "loginFailureCount",
    "memberCode",
    "memberID",
    "memberType",
    "mobilephone",
    "msgCode",
    "password",
    "pointActivateFailureCount",
    "pointActivateLockedDate",
    "registerIP",
    "respCode",
    "respMsg",
    "sPpassword",
    "securityCode",
    "sex",
    "sn",
    "status",
    "uname",
    "updateTime",
    "userCertification",
    "userID",
    "userAuthoritiesId",
    "username",
    "userrole"
})
public class User
    extends Pager
{

    protected String activateURL;
    protected String activatecode;
    protected String actprojectID;
    protected Integer age;
    protected String authoritiesId;
    protected String balance;
    protected String bank;
    protected String channelNo;
    protected String createChannel;
    protected String createTime;
    protected String email;
    protected String firstLogin;
    protected String identityKey;
    protected String isAccountLocked;
    protected String isPointActivateAccountLocked;
    protected String joinedActprojectID;
    protected String lastIP;
    protected String lastPointActivateTime;
    protected String lastTime;
    protected String lastTryTime;
    protected String lockedDate;
    protected Integer loginFailureCount;
    protected Long memberCode;
    protected String memberID;
    protected Integer memberType;
    protected String mobilephone;
    protected Integer msgCode;
    protected String password;
    protected Integer pointActivateFailureCount;
    protected String pointActivateLockedDate;
    protected String registerIP;
    protected String respCode;
    protected String respMsg;
    @XmlElement(name = "SPpassword")
    protected String sPpassword;
    protected String securityCode;
    protected String sex;
    protected String sn;
    protected String status;
    protected String uname;
    protected String updateTime;
    protected UserCertification userCertification;
    protected String userID;
    @XmlElement(name = "user_authorities_id")
    protected String userAuthoritiesId;
    protected String username;
    protected Integer userrole;

    /**
     * Gets the value of the activateURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivateURL() {
        return activateURL;
    }

    /**
     * Sets the value of the activateURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivateURL(String value) {
        this.activateURL = value;
    }

    /**
     * Gets the value of the activatecode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivatecode() {
        return activatecode;
    }

    /**
     * Sets the value of the activatecode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivatecode(String value) {
        this.activatecode = value;
    }

    /**
     * Gets the value of the actprojectID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActprojectID() {
        return actprojectID;
    }

    /**
     * Sets the value of the actprojectID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActprojectID(String value) {
        this.actprojectID = value;
    }

    /**
     * Gets the value of the age property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAge(Integer value) {
        this.age = value;
    }

    /**
     * Gets the value of the authoritiesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthoritiesId() {
        return authoritiesId;
    }

    /**
     * Sets the value of the authoritiesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthoritiesId(String value) {
        this.authoritiesId = value;
    }

    /**
     * Gets the value of the balance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalance(String value) {
        this.balance = value;
    }

    /**
     * Gets the value of the bank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBank() {
        return bank;
    }

    /**
     * Sets the value of the bank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBank(String value) {
        this.bank = value;
    }

    /**
     * Gets the value of the channelNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelNo() {
        return channelNo;
    }

    /**
     * Sets the value of the channelNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelNo(String value) {
        this.channelNo = value;
    }

    /**
     * Gets the value of the createChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateChannel() {
        return createChannel;
    }

    /**
     * Sets the value of the createChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateChannel(String value) {
        this.createChannel = value;
    }

    /**
     * Gets the value of the createTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Sets the value of the createTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateTime(String value) {
        this.createTime = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the firstLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstLogin() {
        return firstLogin;
    }

    /**
     * Sets the value of the firstLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstLogin(String value) {
        this.firstLogin = value;
    }

    /**
     * Gets the value of the identityKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentityKey() {
        return identityKey;
    }

    /**
     * Sets the value of the identityKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentityKey(String value) {
        this.identityKey = value;
    }

    /**
     * Gets the value of the isAccountLocked property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAccountLocked() {
        return isAccountLocked;
    }

    /**
     * Sets the value of the isAccountLocked property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAccountLocked(String value) {
        this.isAccountLocked = value;
    }

    /**
     * Gets the value of the isPointActivateAccountLocked property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPointActivateAccountLocked() {
        return isPointActivateAccountLocked;
    }

    /**
     * Sets the value of the isPointActivateAccountLocked property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPointActivateAccountLocked(String value) {
        this.isPointActivateAccountLocked = value;
    }

    /**
     * Gets the value of the joinedActprojectID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoinedActprojectID() {
        return joinedActprojectID;
    }

    /**
     * Sets the value of the joinedActprojectID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoinedActprojectID(String value) {
        this.joinedActprojectID = value;
    }

    /**
     * Gets the value of the lastIP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastIP() {
        return lastIP;
    }

    /**
     * Sets the value of the lastIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastIP(String value) {
        this.lastIP = value;
    }

    /**
     * Gets the value of the lastPointActivateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastPointActivateTime() {
        return lastPointActivateTime;
    }

    /**
     * Sets the value of the lastPointActivateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastPointActivateTime(String value) {
        this.lastPointActivateTime = value;
    }

    /**
     * Gets the value of the lastTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastTime() {
        return lastTime;
    }

    /**
     * Sets the value of the lastTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastTime(String value) {
        this.lastTime = value;
    }

    /**
     * Gets the value of the lastTryTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastTryTime() {
        return lastTryTime;
    }

    /**
     * Sets the value of the lastTryTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastTryTime(String value) {
        this.lastTryTime = value;
    }

    /**
     * Gets the value of the lockedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockedDate() {
        return lockedDate;
    }

    /**
     * Sets the value of the lockedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockedDate(String value) {
        this.lockedDate = value;
    }

    /**
     * Gets the value of the loginFailureCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    /**
     * Sets the value of the loginFailureCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLoginFailureCount(Integer value) {
        this.loginFailureCount = value;
    }

    /**
     * Gets the value of the memberCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMemberCode() {
        return memberCode;
    }

    /**
     * Sets the value of the memberCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMemberCode(Long value) {
        this.memberCode = value;
    }

    /**
     * Gets the value of the memberID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemberID() {
        return memberID;
    }

    /**
     * Sets the value of the memberID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemberID(String value) {
        this.memberID = value;
    }

    /**
     * Gets the value of the memberType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMemberType() {
        return memberType;
    }

    /**
     * Sets the value of the memberType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMemberType(Integer value) {
        this.memberType = value;
    }

    /**
     * Gets the value of the mobilephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * Sets the value of the mobilephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilephone(String value) {
        this.mobilephone = value;
    }

    /**
     * Gets the value of the msgCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMsgCode() {
        return msgCode;
    }

    /**
     * Sets the value of the msgCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMsgCode(Integer value) {
        this.msgCode = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the pointActivateFailureCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPointActivateFailureCount() {
        return pointActivateFailureCount;
    }

    /**
     * Sets the value of the pointActivateFailureCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPointActivateFailureCount(Integer value) {
        this.pointActivateFailureCount = value;
    }

    /**
     * Gets the value of the pointActivateLockedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointActivateLockedDate() {
        return pointActivateLockedDate;
    }

    /**
     * Sets the value of the pointActivateLockedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointActivateLockedDate(String value) {
        this.pointActivateLockedDate = value;
    }

    /**
     * Gets the value of the registerIP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisterIP() {
        return registerIP;
    }

    /**
     * Sets the value of the registerIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisterIP(String value) {
        this.registerIP = value;
    }

    /**
     * Gets the value of the respCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * Sets the value of the respCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespCode(String value) {
        this.respCode = value;
    }

    /**
     * Gets the value of the respMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespMsg() {
        return respMsg;
    }

    /**
     * Sets the value of the respMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespMsg(String value) {
        this.respMsg = value;
    }

    /**
     * Gets the value of the sPpassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPpassword() {
        return sPpassword;
    }

    /**
     * Sets the value of the sPpassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPpassword(String value) {
        this.sPpassword = value;
    }

    /**
     * Gets the value of the securityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * Sets the value of the securityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityCode(String value) {
        this.securityCode = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * Gets the value of the sn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSn() {
        return sn;
    }

    /**
     * Sets the value of the sn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSn(String value) {
        this.sn = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the uname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUname() {
        return uname;
    }

    /**
     * Sets the value of the uname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUname(String value) {
        this.uname = value;
    }

    /**
     * Gets the value of the updateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the value of the updateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateTime(String value) {
        this.updateTime = value;
    }

    /**
     * Gets the value of the userCertification property.
     * 
     * @return
     *     possible object is
     *     {@link UserCertification }
     *     
     */
    public UserCertification getUserCertification() {
        return userCertification;
    }

    /**
     * Sets the value of the userCertification property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserCertification }
     *     
     */
    public void setUserCertification(UserCertification value) {
        this.userCertification = value;
    }

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the userAuthoritiesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAuthoritiesId() {
        return userAuthoritiesId;
    }

    /**
     * Sets the value of the userAuthoritiesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAuthoritiesId(String value) {
        this.userAuthoritiesId = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the userrole property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUserrole() {
        return userrole;
    }

    /**
     * Sets the value of the userrole property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUserrole(Integer value) {
        this.userrole = value;
    }

}
