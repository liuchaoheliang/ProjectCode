
package com.froad.client.sellers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fundsChannel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fundsChannel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bankGatheringAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankGatheringAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelFullName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelShortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftAllowanceAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftAllowanceAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftGatheringAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftGatheringAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsPoundageAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsPoundageAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fundsChannelNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="payOrg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointsWithdrawAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointsWithdrawAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointsWithdrawPoundageAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointsWithdrawPoundageAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "fundsChannel", propOrder = {
    "bankGatheringAccountName",
    "bankGatheringAccountNumber",
    "channelFullName",
    "channelShortName",
    "channelType",
    "createTime",
    "currency",
    "fftAllowanceAccountName",
    "fftAllowanceAccountNumber",
    "fftGatheringAccountName",
    "fftGatheringAccountNumber",
    "fftPointsAccountName",
    "fftPointsAccountNumber",
    "fftPointsPoundageAccountName",
    "fftPointsPoundageAccountNumber",
    "fundsChannelNo",
    "id",
    "payOrg",
    "pointsWithdrawAccountName",
    "pointsWithdrawAccountNumber",
    "pointsWithdrawPoundageAccountName",
    "pointsWithdrawPoundageAccountNumber",
    "remark",
    "state",
    "updateTime"
})
public class FundsChannel {

    protected String bankGatheringAccountName;
    protected String bankGatheringAccountNumber;
    protected String channelFullName;
    protected String channelShortName;
    protected String channelType;
    protected String createTime;
    protected String currency;
    protected String fftAllowanceAccountName;
    protected String fftAllowanceAccountNumber;
    protected String fftGatheringAccountName;
    protected String fftGatheringAccountNumber;
    protected String fftPointsAccountName;
    protected String fftPointsAccountNumber;
    protected String fftPointsPoundageAccountName;
    protected String fftPointsPoundageAccountNumber;
    protected String fundsChannelNo;
    protected Integer id;
    protected String payOrg;
    protected String pointsWithdrawAccountName;
    protected String pointsWithdrawAccountNumber;
    protected String pointsWithdrawPoundageAccountName;
    protected String pointsWithdrawPoundageAccountNumber;
    protected String remark;
    protected String state;
    protected String updateTime;

    /**
     * Gets the value of the bankGatheringAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankGatheringAccountName() {
        return bankGatheringAccountName;
    }

    /**
     * Sets the value of the bankGatheringAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankGatheringAccountName(String value) {
        this.bankGatheringAccountName = value;
    }

    /**
     * Gets the value of the bankGatheringAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankGatheringAccountNumber() {
        return bankGatheringAccountNumber;
    }

    /**
     * Sets the value of the bankGatheringAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankGatheringAccountNumber(String value) {
        this.bankGatheringAccountNumber = value;
    }

    /**
     * Gets the value of the channelFullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelFullName() {
        return channelFullName;
    }

    /**
     * Sets the value of the channelFullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelFullName(String value) {
        this.channelFullName = value;
    }

    /**
     * Gets the value of the channelShortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelShortName() {
        return channelShortName;
    }

    /**
     * Sets the value of the channelShortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelShortName(String value) {
        this.channelShortName = value;
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
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the fftAllowanceAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftAllowanceAccountName() {
        return fftAllowanceAccountName;
    }

    /**
     * Sets the value of the fftAllowanceAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftAllowanceAccountName(String value) {
        this.fftAllowanceAccountName = value;
    }

    /**
     * Gets the value of the fftAllowanceAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftAllowanceAccountNumber() {
        return fftAllowanceAccountNumber;
    }

    /**
     * Sets the value of the fftAllowanceAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftAllowanceAccountNumber(String value) {
        this.fftAllowanceAccountNumber = value;
    }

    /**
     * Gets the value of the fftGatheringAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftGatheringAccountName() {
        return fftGatheringAccountName;
    }

    /**
     * Sets the value of the fftGatheringAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftGatheringAccountName(String value) {
        this.fftGatheringAccountName = value;
    }

    /**
     * Gets the value of the fftGatheringAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftGatheringAccountNumber() {
        return fftGatheringAccountNumber;
    }

    /**
     * Sets the value of the fftGatheringAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftGatheringAccountNumber(String value) {
        this.fftGatheringAccountNumber = value;
    }

    /**
     * Gets the value of the fftPointsAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsAccountName() {
        return fftPointsAccountName;
    }

    /**
     * Sets the value of the fftPointsAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsAccountName(String value) {
        this.fftPointsAccountName = value;
    }

    /**
     * Gets the value of the fftPointsAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsAccountNumber() {
        return fftPointsAccountNumber;
    }

    /**
     * Sets the value of the fftPointsAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsAccountNumber(String value) {
        this.fftPointsAccountNumber = value;
    }

    /**
     * Gets the value of the fftPointsPoundageAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsPoundageAccountName() {
        return fftPointsPoundageAccountName;
    }

    /**
     * Sets the value of the fftPointsPoundageAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsPoundageAccountName(String value) {
        this.fftPointsPoundageAccountName = value;
    }

    /**
     * Gets the value of the fftPointsPoundageAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsPoundageAccountNumber() {
        return fftPointsPoundageAccountNumber;
    }

    /**
     * Sets the value of the fftPointsPoundageAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsPoundageAccountNumber(String value) {
        this.fftPointsPoundageAccountNumber = value;
    }

    /**
     * Gets the value of the fundsChannelNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFundsChannelNo() {
        return fundsChannelNo;
    }

    /**
     * Sets the value of the fundsChannelNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFundsChannelNo(String value) {
        this.fundsChannelNo = value;
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
     * Gets the value of the payOrg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayOrg() {
        return payOrg;
    }

    /**
     * Sets the value of the payOrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayOrg(String value) {
        this.payOrg = value;
    }

    /**
     * Gets the value of the pointsWithdrawAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsWithdrawAccountName() {
        return pointsWithdrawAccountName;
    }

    /**
     * Sets the value of the pointsWithdrawAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsWithdrawAccountName(String value) {
        this.pointsWithdrawAccountName = value;
    }

    /**
     * Gets the value of the pointsWithdrawAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsWithdrawAccountNumber() {
        return pointsWithdrawAccountNumber;
    }

    /**
     * Sets the value of the pointsWithdrawAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsWithdrawAccountNumber(String value) {
        this.pointsWithdrawAccountNumber = value;
    }

    /**
     * Gets the value of the pointsWithdrawPoundageAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsWithdrawPoundageAccountName() {
        return pointsWithdrawPoundageAccountName;
    }

    /**
     * Sets the value of the pointsWithdrawPoundageAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsWithdrawPoundageAccountName(String value) {
        this.pointsWithdrawPoundageAccountName = value;
    }

    /**
     * Gets the value of the pointsWithdrawPoundageAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsWithdrawPoundageAccountNumber() {
        return pointsWithdrawPoundageAccountNumber;
    }

    /**
     * Sets the value of the pointsWithdrawPoundageAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsWithdrawPoundageAccountNumber(String value) {
        this.pointsWithdrawPoundageAccountNumber = value;
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
