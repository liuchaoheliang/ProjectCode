
package com.froad.client.clientGoodsGatherRack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tagMAP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tagMAP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="siteValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagClassifyA" type="{http://service.CB.froad.com/}tagClassifyA" minOccurs="0"/>
 *         &lt;element name="tagClassifyAId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagClassifyB" type="{http://service.CB.froad.com/}tagClassifyB" minOccurs="0"/>
 *         &lt;element name="tagClassifyBId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagDistrictA" type="{http://service.CB.froad.com/}tagDistrictA" minOccurs="0"/>
 *         &lt;element name="tagDistrictAId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagDistrictB" type="{http://service.CB.froad.com/}tagDistrictB" minOccurs="0"/>
 *         &lt;element name="tagDistrictBId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "tagMAP", propOrder = {
    "createTime",
    "id",
    "merchantId",
    "merchantName",
    "remark",
    "siteValue",
    "state",
    "tagClassifyA",
    "tagClassifyAId",
    "tagClassifyB",
    "tagClassifyBId",
    "tagDistrictA",
    "tagDistrictAId",
    "tagDistrictB",
    "tagDistrictBId",
    "tagValue",
    "updateTime"
})
public class TagMAP {

    protected String createTime;
    protected Integer id;
    protected String merchantId;
    protected String merchantName;
    protected String remark;
    protected String siteValue;
    protected String state;
    protected TagClassifyA tagClassifyA;
    protected String tagClassifyAId;
    protected TagClassifyB tagClassifyB;
    protected String tagClassifyBId;
    protected TagDistrictA tagDistrictA;
    protected String tagDistrictAId;
    protected TagDistrictB tagDistrictB;
    protected String tagDistrictBId;
    protected String tagValue;
    protected String updateTime;

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
     * Gets the value of the siteValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteValue() {
        return siteValue;
    }

    /**
     * Sets the value of the siteValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteValue(String value) {
        this.siteValue = value;
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
     * Gets the value of the tagClassifyA property.
     * 
     * @return
     *     possible object is
     *     {@link TagClassifyA }
     *     
     */
    public TagClassifyA getTagClassifyA() {
        return tagClassifyA;
    }

    /**
     * Sets the value of the tagClassifyA property.
     * 
     * @param value
     *     allowed object is
     *     {@link TagClassifyA }
     *     
     */
    public void setTagClassifyA(TagClassifyA value) {
        this.tagClassifyA = value;
    }

    /**
     * Gets the value of the tagClassifyAId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagClassifyAId() {
        return tagClassifyAId;
    }

    /**
     * Sets the value of the tagClassifyAId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagClassifyAId(String value) {
        this.tagClassifyAId = value;
    }

    /**
     * Gets the value of the tagClassifyB property.
     * 
     * @return
     *     possible object is
     *     {@link TagClassifyB }
     *     
     */
    public TagClassifyB getTagClassifyB() {
        return tagClassifyB;
    }

    /**
     * Sets the value of the tagClassifyB property.
     * 
     * @param value
     *     allowed object is
     *     {@link TagClassifyB }
     *     
     */
    public void setTagClassifyB(TagClassifyB value) {
        this.tagClassifyB = value;
    }

    /**
     * Gets the value of the tagClassifyBId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagClassifyBId() {
        return tagClassifyBId;
    }

    /**
     * Sets the value of the tagClassifyBId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagClassifyBId(String value) {
        this.tagClassifyBId = value;
    }

    /**
     * Gets the value of the tagDistrictA property.
     * 
     * @return
     *     possible object is
     *     {@link TagDistrictA }
     *     
     */
    public TagDistrictA getTagDistrictA() {
        return tagDistrictA;
    }

    /**
     * Sets the value of the tagDistrictA property.
     * 
     * @param value
     *     allowed object is
     *     {@link TagDistrictA }
     *     
     */
    public void setTagDistrictA(TagDistrictA value) {
        this.tagDistrictA = value;
    }

    /**
     * Gets the value of the tagDistrictAId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagDistrictAId() {
        return tagDistrictAId;
    }

    /**
     * Sets the value of the tagDistrictAId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagDistrictAId(String value) {
        this.tagDistrictAId = value;
    }

    /**
     * Gets the value of the tagDistrictB property.
     * 
     * @return
     *     possible object is
     *     {@link TagDistrictB }
     *     
     */
    public TagDistrictB getTagDistrictB() {
        return tagDistrictB;
    }

    /**
     * Sets the value of the tagDistrictB property.
     * 
     * @param value
     *     allowed object is
     *     {@link TagDistrictB }
     *     
     */
    public void setTagDistrictB(TagDistrictB value) {
        this.tagDistrictB = value;
    }

    /**
     * Gets the value of the tagDistrictBId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagDistrictBId() {
        return tagDistrictBId;
    }

    /**
     * Sets the value of the tagDistrictBId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagDistrictBId(String value) {
        this.tagDistrictBId = value;
    }

    /**
     * Gets the value of the tagValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagValue() {
        return tagValue;
    }

    /**
     * Sets the value of the tagValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagValue(String value) {
        this.tagValue = value;
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
