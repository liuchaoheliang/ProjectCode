
package com.froad.client.ruleDetailTemplet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruleDetailTemplet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ruleDetailTemplet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="calculateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clazzName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detailRuleFormulaFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="detailRuleFormulaFromType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="formulaOfDetailRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromAccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fromRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="paramOfFormula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paramOfFormulaFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleDetailCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleDetailType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seqNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="settleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toAccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "ruleDetailTemplet", propOrder = {
    "calculateType",
    "clazzName",
    "createTime",
    "description",
    "detailRuleFormulaFrom",
    "detailRuleFormulaFromType",
    "formulaOfDetailRule",
    "fromAccountType",
    "fromRole",
    "id",
    "paramOfFormula",
    "paramOfFormulaFrom",
    "remark",
    "ruleDetailCategory",
    "ruleDetailType",
    "ruleType",
    "seqNo",
    "settleType",
    "state",
    "toAccountType",
    "toRole",
    "updateTime",
    "useTime"
})
public class RuleDetailTemplet {

    protected String calculateType;
    protected String clazzName;
    protected String createTime;
    protected String description;
    protected String detailRuleFormulaFrom;
    protected String detailRuleFormulaFromType;
    protected String formulaOfDetailRule;
    protected String fromAccountType;
    protected String fromRole;
    protected Integer id;
    protected String paramOfFormula;
    protected String paramOfFormulaFrom;
    protected String remark;
    protected String ruleDetailCategory;
    protected String ruleDetailType;
    protected String ruleType;
    protected Integer seqNo;
    protected String settleType;
    protected String state;
    protected String toAccountType;
    protected String toRole;
    protected String updateTime;
    protected String useTime;

    /**
     * Gets the value of the calculateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalculateType() {
        return calculateType;
    }

    /**
     * Sets the value of the calculateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalculateType(String value) {
        this.calculateType = value;
    }

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
     * Gets the value of the detailRuleFormulaFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailRuleFormulaFrom() {
        return detailRuleFormulaFrom;
    }

    /**
     * Sets the value of the detailRuleFormulaFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailRuleFormulaFrom(String value) {
        this.detailRuleFormulaFrom = value;
    }

    /**
     * Gets the value of the detailRuleFormulaFromType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailRuleFormulaFromType() {
        return detailRuleFormulaFromType;
    }

    /**
     * Sets the value of the detailRuleFormulaFromType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailRuleFormulaFromType(String value) {
        this.detailRuleFormulaFromType = value;
    }

    /**
     * Gets the value of the formulaOfDetailRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormulaOfDetailRule() {
        return formulaOfDetailRule;
    }

    /**
     * Sets the value of the formulaOfDetailRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormulaOfDetailRule(String value) {
        this.formulaOfDetailRule = value;
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
     * Gets the value of the ruleDetailCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleDetailCategory() {
        return ruleDetailCategory;
    }

    /**
     * Sets the value of the ruleDetailCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleDetailCategory(String value) {
        this.ruleDetailCategory = value;
    }

    /**
     * Gets the value of the ruleDetailType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleDetailType() {
        return ruleDetailType;
    }

    /**
     * Sets the value of the ruleDetailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleDetailType(String value) {
        this.ruleDetailType = value;
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
     * Gets the value of the seqNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSeqNo() {
        return seqNo;
    }

    /**
     * Sets the value of the seqNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSeqNo(Integer value) {
        this.seqNo = value;
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
