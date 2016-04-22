
package com.froad.client.merchant;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for merchantUserSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merchantUserSet">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="beCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beCodepwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="belongStoreId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isAccountLocked" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isAdmin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastTryTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lockedDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginFailureCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="loginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "merchantUserSet", propOrder = {
    "beCode",
    "beCodepwd",
    "beName",
    "beSpec",
    "belongStoreId",
    "createTime",
    "id",
    "isAccountLocked",
    "isAdmin",
    "lastTryTime",
    "lockedDate",
    "loginFailureCount",
    "loginName",
    "merchantId",
    "remark",
    "roleType",
    "state",
    "updateTime",
    "userId"
})
public class MerchantUserSet
    extends Pager
{

    protected String beCode;
    protected String beCodepwd;
    protected String beName;
    protected String beSpec;
    protected String belongStoreId;
    protected String createTime;
    protected Integer id;
    protected String isAccountLocked;
    protected String isAdmin;
    protected String lastTryTime;
    protected String lockedDate;
    protected Integer loginFailureCount;
    protected String loginName;
    protected String merchantId;
    protected String remark;
    protected String roleType;
    protected String state;
    protected String updateTime;
    protected String userId;

    /**
     * Gets the value of the beCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeCode() {
        return beCode;
    }

    /**
     * Sets the value of the beCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeCode(String value) {
        this.beCode = value;
    }

    /**
     * Gets the value of the beCodepwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeCodepwd() {
        return beCodepwd;
    }

    /**
     * Sets the value of the beCodepwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeCodepwd(String value) {
        this.beCodepwd = value;
    }

    /**
     * Gets the value of the beName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeName() {
        return beName;
    }

    /**
     * Sets the value of the beName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeName(String value) {
        this.beName = value;
    }

    /**
     * Gets the value of the beSpec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeSpec() {
        return beSpec;
    }

    /**
     * Sets the value of the beSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeSpec(String value) {
        this.beSpec = value;
    }

    /**
     * Gets the value of the belongStoreId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBelongStoreId() {
        return belongStoreId;
    }

    /**
     * Sets the value of the belongStoreId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBelongStoreId(String value) {
        this.belongStoreId = value;
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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
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
     * Gets the value of the isAdmin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets the value of the isAdmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAdmin(String value) {
        this.isAdmin = value;
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
     * Gets the value of the loginName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Sets the value of the loginName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginName(String value) {
        this.loginName = value;
    }

    /**
     * Gets the value of the merchantId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * Sets the value of the merchantId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantId(String value) {
        this.merchantId = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the roleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * Sets the value of the roleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleType(String value) {
        this.roleType = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
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
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

}
