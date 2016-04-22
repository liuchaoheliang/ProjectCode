
package com.froad.client.clientGoodsExchangeRack;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transRule">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}rule">
 *       &lt;sequence>
 *         &lt;element name="bankFactorageFormula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyFormula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftFactorageFormula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="pointsFormula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointsType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleDetailList" type="{http://service.CB.froad.com/}ruleDetail" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ruleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transRule", propOrder = {
    "bankFactorageFormula",
    "createTime",
    "currency",
    "currencyFormula",
    "fftFactorageFormula",
    "id",
    "pointsFormula",
    "pointsType",
    "priceType",
    "remark",
    "ruleDesc",
    "ruleDetailList",
    "ruleType",
    "startTime",
    "state",
    "transType",
    "updateTime"
})
public class TransRule
    extends Rule
{

    protected String bankFactorageFormula;
    protected String createTime;
    protected String currency;
    protected String currencyFormula;
    protected String fftFactorageFormula;
    protected Integer id;
    protected String pointsFormula;
    protected String pointsType;
    protected String priceType;
    protected String remark;
    protected String ruleDesc;
    @XmlElement(nillable = true)
    protected List<RuleDetail> ruleDetailList;
    protected String ruleType;
    protected String startTime;
    protected String state;
    protected String transType;
    protected String updateTime;

    /**
     * Gets the value of the bankFactorageFormula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankFactorageFormula() {
        return bankFactorageFormula;
    }

    /**
     * Sets the value of the bankFactorageFormula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankFactorageFormula(String value) {
        this.bankFactorageFormula = value;
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
     * Gets the value of the currencyFormula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyFormula() {
        return currencyFormula;
    }

    /**
     * Sets the value of the currencyFormula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyFormula(String value) {
        this.currencyFormula = value;
    }

    /**
     * Gets the value of the fftFactorageFormula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftFactorageFormula() {
        return fftFactorageFormula;
    }

    /**
     * Sets the value of the fftFactorageFormula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftFactorageFormula(String value) {
        this.fftFactorageFormula = value;
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
     * Gets the value of the pointsFormula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsFormula() {
        return pointsFormula;
    }

    /**
     * Sets the value of the pointsFormula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsFormula(String value) {
        this.pointsFormula = value;
    }

    /**
     * Gets the value of the pointsType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsType() {
        return pointsType;
    }

    /**
     * Sets the value of the pointsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsType(String value) {
        this.pointsType = value;
    }

    /**
     * Gets the value of the priceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceType() {
        return priceType;
    }

    /**
     * Sets the value of the priceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceType(String value) {
        this.priceType = value;
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
     * Gets the value of the ruleDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleDesc() {
        return ruleDesc;
    }

    /**
     * Sets the value of the ruleDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleDesc(String value) {
        this.ruleDesc = value;
    }

    /**
     * Gets the value of the ruleDetailList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ruleDetailList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRuleDetailList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RuleDetail }
     * 
     * 
     */
    public List<RuleDetail> getRuleDetailList() {
        if (ruleDetailList == null) {
            ruleDetailList = new ArrayList<RuleDetail>();
        }
        return this.ruleDetailList;
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

}
