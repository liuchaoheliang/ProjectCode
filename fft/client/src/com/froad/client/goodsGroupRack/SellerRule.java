
package com.froad.client.goodsGroupRack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sellerRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sellerRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bankFeeRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cashRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftFeeRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsRule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "sellerRule", propOrder = {
    "bankFeeRule",
    "cashRule",
    "createTime",
    "fftFeeRule",
    "fftPointsRule",
    "id",
    "remark",
    "ruleType",
    "state",
    "updateTime"
})
public class SellerRule {

    protected String bankFeeRule;
    protected String cashRule;
    protected String createTime;
    protected String fftFeeRule;
    protected String fftPointsRule;
    protected Integer id;
    protected String remark;
    protected String ruleType;
    protected String state;
    protected String updateTime;

    /**
     * Gets the value of the bankFeeRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankFeeRule() {
        return bankFeeRule;
    }

    /**
     * Sets the value of the bankFeeRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankFeeRule(String value) {
        this.bankFeeRule = value;
    }

    /**
     * Gets the value of the cashRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashRule() {
        return cashRule;
    }

    /**
     * Sets the value of the cashRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashRule(String value) {
        this.cashRule = value;
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
     * Gets the value of the fftFeeRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftFeeRule() {
        return fftFeeRule;
    }

    /**
     * Sets the value of the fftFeeRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftFeeRule(String value) {
        this.fftFeeRule = value;
    }

    /**
     * Gets the value of the fftPointsRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsRule() {
        return fftPointsRule;
    }

    /**
     * Sets the value of the fftPointsRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsRule(String value) {
        this.fftPointsRule = value;
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
