
package com.froad.client.point;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sellerRuleDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sellerRuleDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clazzName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detailRuleFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="formula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromAccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="intervalTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paramOfFormula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paramOfFormulaFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerRuleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="settleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toAccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="useTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sellerRuleDetail", propOrder = {
    "clazzName",
    "countType",
    "createTime",
    "description",
    "detailRuleFrom",
    "endTime",
    "formula",
    "fromAccountType",
    "fromRole",
    "id",
    "intervalTime",
    "paramOfFormula",
    "paramOfFormulaFrom",
    "paymentStartTime",
    "remark",
    "ruleType",
    "sellerRuleId",
    "settleType",
    "startTime",
    "state",
    "toAccountType",
    "toRole",
    "transType",
    "updateTime",
    "useTime"
})
public class SellerRuleDetail {

    protected String clazzName;
    protected String countType;
    protected String createTime;
    protected String description;
    protected String detailRuleFrom;
    protected String endTime;
    protected String formula;
    protected String fromAccountType;
    protected String fromRole;
    protected Integer id;
    protected String intervalTime;
    protected String paramOfFormula;
    protected String paramOfFormulaFrom;
    protected String paymentStartTime;
    protected String remark;
    protected String ruleType;
    protected String sellerRuleId;
    protected String settleType;
    protected String startTime;
    protected String state;
    protected String toAccountType;
    protected String toRole;
    protected String transType;
    protected String updateTime;
    protected String useTime;

    /**
     * Gets the value of the clazzName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazzName() {
        return clazzName;
    }

    /**
     * Sets the value of the clazzName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazzName(String value) {
        this.clazzName = value;
    }

    /**
     * Gets the value of the countType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountType() {
        return countType;
    }

    /**
     * Sets the value of the countType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountType(String value) {
        this.countType = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the detailRuleFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailRuleFrom() {
        return detailRuleFrom;
    }

    /**
     * Sets the value of the detailRuleFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailRuleFrom(String value) {
        this.detailRuleFrom = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the formula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormula() {
        return formula;
    }

    /**
     * Sets the value of the formula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormula(String value) {
        this.formula = value;
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
     * Gets the value of the intervalTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntervalTime() {
        return intervalTime;
    }

    /**
     * Sets the value of the intervalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntervalTime(String value) {
        this.intervalTime = value;
    }

    /**
     * Gets the value of the paramOfFormula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamOfFormula() {
        return paramOfFormula;
    }

    /**
     * Sets the value of the paramOfFormula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamOfFormula(String value) {
        this.paramOfFormula = value;
    }

    /**
     * Gets the value of the paramOfFormulaFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamOfFormulaFrom() {
        return paramOfFormulaFrom;
    }

    /**
     * Sets the value of the paramOfFormulaFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamOfFormulaFrom(String value) {
        this.paramOfFormulaFrom = value;
    }

    /**
     * Gets the value of the paymentStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentStartTime() {
        return paymentStartTime;
    }

    /**
     * Sets the value of the paymentStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentStartTime(String value) {
        this.paymentStartTime = value;
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
     * Gets the value of the sellerRuleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerRuleId() {
        return sellerRuleId;
    }

    /**
     * Sets the value of the sellerRuleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerRuleId(String value) {
        this.sellerRuleId = value;
    }

    /**
     * Gets the value of the settleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSettleType() {
        return settleType;
    }

    /**
     * Sets the value of the settleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSettleType(String value) {
        this.settleType = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
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
     * Gets the value of the transType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransType() {
        return transType;
    }

    /**
     * Sets the value of the transType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransType(String value) {
        this.transType = value;
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
     * Gets the value of the useTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseTime() {
        return useTime;
    }

    /**
     * Sets the value of the useTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseTime(String value) {
        this.useTime = value;
    }

}
