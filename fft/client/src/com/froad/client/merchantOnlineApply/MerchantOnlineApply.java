
package com.froad.client.merchantOnlineApply;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for merchantOnlineApply complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merchantOnlineApply">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cooperationModel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cooperativePurpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isRelation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="linkman" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mechantIndustryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relationAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relationMark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relationTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stateList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "merchantOnlineApply", propOrder = {
    "address",
    "cooperationModel",
    "cooperativePurpose",
    "createTime",
    "id",
    "isRelation",
    "linkman",
    "mechantIndustryId",
    "merchantName",
    "mobile",
    "phone",
    "relationAccount",
    "relationMark",
    "relationTime",
    "remark",
    "state",
    "stateList",
    "updateTime",
    "userId"
})
public class MerchantOnlineApply
    extends Pager
{

    protected String address;
    protected String cooperationModel;
    protected String cooperativePurpose;
    protected String createTime;
    protected Integer id;
    protected String isRelation;
    protected String linkman;
    protected String mechantIndustryId;
    protected String merchantName;
    protected String mobile;
    protected String phone;
    protected String relationAccount;
    protected String relationMark;
    protected String relationTime;
    protected String remark;
    protected String state;
    @XmlElement(nillable = true)
    protected List<String> stateList;
    protected String updateTime;
    protected String userId;

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the cooperationModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCooperationModel() {
        return cooperationModel;
    }

    /**
     * Sets the value of the cooperationModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCooperationModel(String value) {
        this.cooperationModel = value;
    }

    /**
     * Gets the value of the cooperativePurpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCooperativePurpose() {
        return cooperativePurpose;
    }

    /**
     * Sets the value of the cooperativePurpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCooperativePurpose(String value) {
        this.cooperativePurpose = value;
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
     * Gets the value of the isRelation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRelation() {
        return isRelation;
    }

    /**
     * Sets the value of the isRelation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRelation(String value) {
        this.isRelation = value;
    }

    /**
     * Gets the value of the linkman property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * Sets the value of the linkman property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkman(String value) {
        this.linkman = value;
    }

    /**
     * Gets the value of the mechantIndustryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMechantIndustryId() {
        return mechantIndustryId;
    }

    /**
     * Sets the value of the mechantIndustryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMechantIndustryId(String value) {
        this.mechantIndustryId = value;
    }

    /**
     * Gets the value of the merchantName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * Sets the value of the merchantName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantName(String value) {
        this.merchantName = value;
    }

    /**
     * Gets the value of the mobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the value of the mobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the relationAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationAccount() {
        return relationAccount;
    }

    /**
     * Sets the value of the relationAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationAccount(String value) {
        this.relationAccount = value;
    }

    /**
     * Gets the value of the relationMark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationMark() {
        return relationMark;
    }

    /**
     * Sets the value of the relationMark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationMark(String value) {
        this.relationMark = value;
    }

    /**
     * Gets the value of the relationTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationTime() {
        return relationTime;
    }

    /**
     * Sets the value of the relationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationTime(String value) {
        this.relationTime = value;
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
     * Gets the value of the stateList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stateList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStateList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStateList() {
        if (stateList == null) {
            stateList = new ArrayList<String>();
        }
        return this.stateList;
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
