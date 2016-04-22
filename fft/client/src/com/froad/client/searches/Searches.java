
package com.froad.client.searches;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for searches complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="searches">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="searchCount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="searchKeywords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagClassifyAId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagClassifyAValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagClassifyBId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagDistrictAId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagDistrictBId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagDistrictBValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mPreferentialType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searches", propOrder = {
    "createTime",
    "id",
    "remark",
    "searchCount",
    "searchKeywords",
    "state",
    "tagClassifyAId",
    "tagClassifyAValue",
    "tagClassifyBId",
    "tagDistrictAId",
    "tagDistrictBId",
    "tagDistrictBValue",
    "updateTime",
    "mPreferentialType"
})
public class Searches {

    protected String createTime;
    protected Integer id;
    protected String remark;
    protected String searchCount;
    protected String searchKeywords;
    protected String state;
    protected String tagClassifyAId;
    protected String tagClassifyAValue;
    protected String tagClassifyBId;
    protected String tagDistrictAId;
    protected String tagDistrictBId;
    protected String tagDistrictBValue;
    protected String updateTime;
    protected String mPreferentialType;

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
     * Gets the value of the searchCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchCount() {
        return searchCount;
    }

    /**
     * Sets the value of the searchCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchCount(String value) {
        this.searchCount = value;
    }

    /**
     * Gets the value of the searchKeywords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchKeywords() {
        return searchKeywords;
    }

    /**
     * Sets the value of the searchKeywords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchKeywords(String value) {
        this.searchKeywords = value;
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
     * Gets the value of the tagClassifyAValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagClassifyAValue() {
        return tagClassifyAValue;
    }

    /**
     * Sets the value of the tagClassifyAValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagClassifyAValue(String value) {
        this.tagClassifyAValue = value;
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
     * Gets the value of the tagDistrictBValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagDistrictBValue() {
        return tagDistrictBValue;
    }

    /**
     * Sets the value of the tagDistrictBValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagDistrictBValue(String value) {
        this.tagDistrictBValue = value;
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
     * Gets the value of the mPreferentialType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMPreferentialType() {
        return mPreferentialType;
    }

    /**
     * Sets the value of the mPreferentialType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMPreferentialType(String value) {
        this.mPreferentialType = value;
    }

}
