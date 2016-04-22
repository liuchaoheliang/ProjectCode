
package com.froad.client.pay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pay complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pay">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="channelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromAccountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromAccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fundsChannel" type="{http://service.CB.froad.com/}fundsChannel" minOccurs="0"/>
 *         &lt;element name="goodsId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isIntime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleDetailId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="step" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toAccountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toAccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trackNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="typeDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pay", propOrder = {
    "channelId",
    "channelType",
    "createTime",
    "fromAccountName",
    "fromAccountNo",
    "fromAccountType",
    "fromId",
    "fromPhone",
    "fromRole",
    "fromUsername",
    "fundsChannel",
    "goodsId",
    "id",
    "isIntime",
    "payValue",
    "remark",
    "resultCode",
    "resultDesc",
    "ruleDetailId",
    "ruleId",
    "ruleType",
    "state",
    "step",
    "toAccountName",
    "toAccountNo",
    "toAccountType",
    "toId",
    "toPhone",
    "toRole",
    "toUsername",
    "trackNo",
    "transId",
    "type",
    "typeDetail",
    "updateTime"
})
public class Pay {

    protected String channelId;
    protected String channelType;
    protected String createTime;
    protected String fromAccountName;
    protected String fromAccountNo;
    protected String fromAccountType;
    protected String fromId;
    protected String fromPhone;
    protected String fromRole;
    protected String fromUsername;
    protected FundsChannel fundsChannel;
    protected String goodsId;
    protected Integer id;
    protected String isIntime;
    protected String payValue;
    protected String remark;
    protected String resultCode;
    protected String resultDesc;
    protected String ruleDetailId;
    protected String ruleId;
    protected String ruleType;
    protected String state;
    protected String step;
    protected String toAccountName;
    protected String toAccountNo;
    protected String toAccountType;
    protected String toId;
    protected String toPhone;
    protected String toRole;
    protected String toUsername;
    protected String trackNo;
    protected String transId;
    protected String type;
    protected String typeDetail;
    protected String updateTime;

    /**
     * Gets the value of the channelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Sets the value of the channelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelId(String value) {
        this.channelId = value;
    }

    /**
     * Gets the value of the channelType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * Sets the value of the channelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelType(String value) {
        this.channelType = value;
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
     * Gets the value of the fromAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromAccountName() {
        return fromAccountName;
    }

    /**
     * Sets the value of the fromAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromAccountName(String value) {
        this.fromAccountName = value;
    }

    /**
     * Gets the value of the fromAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromAccountNo() {
        return fromAccountNo;
    }

    /**
     * Sets the value of the fromAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromAccountNo(String value) {
        this.fromAccountNo = value;
    }

    /**
     * Gets the value of the fromAccountType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromAccountType() {
        return fromAccountType;
    }

    /**
     * Sets the value of the fromAccountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromAccountType(String value) {
        this.fromAccountType = value;
    }

    /**
     * Gets the value of the fromId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * Sets the value of the fromId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromId(String value) {
        this.fromId = value;
    }

    /**
     * Gets the value of the fromPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromPhone() {
        return fromPhone;
    }

    /**
     * Sets the value of the fromPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromPhone(String value) {
        this.fromPhone = value;
    }

    /**
     * Gets the value of the fromRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromRole() {
        return fromRole;
    }

    /**
     * Sets the value of the fromRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromRole(String value) {
        this.fromRole = value;
    }

    /**
     * Gets the value of the fromUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromUsername() {
        return fromUsername;
    }

    /**
     * Sets the value of the fromUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromUsername(String value) {
        this.fromUsername = value;
    }

    /**
     * Gets the value of the fundsChannel property.
     * 
     * @return
     *     possible object is
     *     {@link FundsChannel }
     *     
     */
    public FundsChannel getFundsChannel() {
        return fundsChannel;
    }

    /**
     * Sets the value of the fundsChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link FundsChannel }
     *     
     */
    public void setFundsChannel(FundsChannel value) {
        this.fundsChannel = value;
    }

    /**
     * Gets the value of the goodsId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * Sets the value of the goodsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsId(String value) {
        this.goodsId = value;
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
     * Gets the value of the isIntime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsIntime() {
        return isIntime;
    }

    /**
     * Sets the value of the isIntime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsIntime(String value) {
        this.isIntime = value;
    }

    /**
     * Gets the value of the payValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayValue() {
        return payValue;
    }

    /**
     * Sets the value of the payValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayValue(String value) {
        this.payValue = value;
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
     * Gets the value of the resultCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Sets the value of the resultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultCode(String value) {
        this.resultCode = value;
    }

    /**
     * Gets the value of the resultDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     * Sets the value of the resultDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultDesc(String value) {
        this.resultDesc = value;
    }

    /**
     * Gets the value of the ruleDetailId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleDetailId() {
        return ruleDetailId;
    }

    /**
     * Sets the value of the ruleDetailId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleDetailId(String value) {
        this.ruleDetailId = value;
    }

    /**
     * Gets the value of the ruleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleId() {
        return ruleId;
    }

    /**
     * Sets the value of the ruleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleId(String value) {
        this.ruleId = value;
    }

    /**
     * Gets the value of the ruleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * Sets the value of the ruleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleType(String value) {
        this.ruleType = value;
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
     * Gets the value of the step property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStep() {
        return step;
    }

    /**
     * Sets the value of the step property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStep(String value) {
        this.step = value;
    }

    /**
     * Gets the value of the toAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToAccountName() {
        return toAccountName;
    }

    /**
     * Sets the value of the toAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToAccountName(String value) {
        this.toAccountName = value;
    }

    /**
     * Gets the value of the toAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToAccountNo() {
        return toAccountNo;
    }

    /**
     * Sets the value of the toAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToAccountNo(String value) {
        this.toAccountNo = value;
    }

    /**
     * Gets the value of the toAccountType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToAccountType() {
        return toAccountType;
    }

    /**
     * Sets the value of the toAccountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToAccountType(String value) {
        this.toAccountType = value;
    }

    /**
     * Gets the value of the toId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToId() {
        return toId;
    }

    /**
     * Sets the value of the toId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToId(String value) {
        this.toId = value;
    }

    /**
     * Gets the value of the toPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToPhone() {
        return toPhone;
    }

    /**
     * Sets the value of the toPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToPhone(String value) {
        this.toPhone = value;
    }

    /**
     * Gets the value of the toRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRole() {
        return toRole;
    }

    /**
     * Sets the value of the toRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRole(String value) {
        this.toRole = value;
    }

    /**
     * Gets the value of the toUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToUsername() {
        return toUsername;
    }

    /**
     * Sets the value of the toUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToUsername(String value) {
        this.toUsername = value;
    }

    /**
     * Gets the value of the trackNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackNo() {
        return trackNo;
    }

    /**
     * Sets the value of the trackNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackNo(String value) {
        this.trackNo = value;
    }

    /**
     * Gets the value of the transId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransId() {
        return transId;
    }

    /**
     * Sets the value of the transId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransId(String value) {
        this.transId = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the typeDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeDetail() {
        return typeDetail;
    }

    /**
     * Sets the value of the typeDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeDetail(String value) {
        this.typeDetail = value;
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

}
