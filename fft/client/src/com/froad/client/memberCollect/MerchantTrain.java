
package com.froad.client.memberCollect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for merchantTrain complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merchantTrain">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clickes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="collectes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dcHyperlink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recommendValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerRulesDesces" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sendInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeLogoFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeLogoUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeShortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storeZip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagsCassifyHyperlinkA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagsClassifyHyperlinkB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagsDistrictHyperlinkA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagsDistrictHyperlinkB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "merchantTrain", propOrder = {
    "clickes",
    "collectes",
    "createTime",
    "dcHyperlink",
    "id",
    "merchantId",
    "recommendValue",
    "remark",
    "sellerRulesDesces",
    "sendInfo",
    "state",
    "storeAddress",
    "storeFax",
    "storeLogoFormat",
    "storeLogoUrl",
    "storeShortName",
    "storeTel",
    "storeZip",
    "tagsCassifyHyperlinkA",
    "tagsClassifyHyperlinkB",
    "tagsDistrictHyperlinkA",
    "tagsDistrictHyperlinkB",
    "updateTime"
})
public class MerchantTrain {

    protected String clickes;
    protected String collectes;
    protected String createTime;
    protected String dcHyperlink;
    protected Integer id;
    protected String merchantId;
    protected String recommendValue;
    protected String remark;
    protected String sellerRulesDesces;
    protected String sendInfo;
    protected String state;
    protected String storeAddress;
    protected String storeFax;
    protected String storeLogoFormat;
    protected String storeLogoUrl;
    protected String storeShortName;
    protected String storeTel;
    protected String storeZip;
    protected String tagsCassifyHyperlinkA;
    protected String tagsClassifyHyperlinkB;
    protected String tagsDistrictHyperlinkA;
    protected String tagsDistrictHyperlinkB;
    protected String updateTime;

    /**
     * Gets the value of the clickes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClickes() {
        return clickes;
    }

    /**
     * Sets the value of the clickes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClickes(String value) {
        this.clickes = value;
    }

    /**
     * Gets the value of the collectes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectes() {
        return collectes;
    }

    /**
     * Sets the value of the collectes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectes(String value) {
        this.collectes = value;
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
     * Gets the value of the dcHyperlink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDcHyperlink() {
        return dcHyperlink;
    }

    /**
     * Sets the value of the dcHyperlink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDcHyperlink(String value) {
        this.dcHyperlink = value;
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
     * Gets the value of the recommendValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommendValue() {
        return recommendValue;
    }

    /**
     * Sets the value of the recommendValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommendValue(String value) {
        this.recommendValue = value;
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
     * Gets the value of the sellerRulesDesces property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerRulesDesces() {
        return sellerRulesDesces;
    }

    /**
     * Sets the value of the sellerRulesDesces property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerRulesDesces(String value) {
        this.sellerRulesDesces = value;
    }

    /**
     * Gets the value of the sendInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendInfo() {
        return sendInfo;
    }

    /**
     * Sets the value of the sendInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendInfo(String value) {
        this.sendInfo = value;
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
     * Gets the value of the storeAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreAddress() {
        return storeAddress;
    }

    /**
     * Sets the value of the storeAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreAddress(String value) {
        this.storeAddress = value;
    }

    /**
     * Gets the value of the storeFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreFax() {
        return storeFax;
    }

    /**
     * Sets the value of the storeFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreFax(String value) {
        this.storeFax = value;
    }

    /**
     * Gets the value of the storeLogoFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreLogoFormat() {
        return storeLogoFormat;
    }

    /**
     * Sets the value of the storeLogoFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreLogoFormat(String value) {
        this.storeLogoFormat = value;
    }

    /**
     * Gets the value of the storeLogoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreLogoUrl() {
        return storeLogoUrl;
    }

    /**
     * Sets the value of the storeLogoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreLogoUrl(String value) {
        this.storeLogoUrl = value;
    }

    /**
     * Gets the value of the storeShortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreShortName() {
        return storeShortName;
    }

    /**
     * Sets the value of the storeShortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreShortName(String value) {
        this.storeShortName = value;
    }

    /**
     * Gets the value of the storeTel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreTel() {
        return storeTel;
    }

    /**
     * Sets the value of the storeTel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreTel(String value) {
        this.storeTel = value;
    }

    /**
     * Gets the value of the storeZip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreZip() {
        return storeZip;
    }

    /**
     * Sets the value of the storeZip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreZip(String value) {
        this.storeZip = value;
    }

    /**
     * Gets the value of the tagsCassifyHyperlinkA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagsCassifyHyperlinkA() {
        return tagsCassifyHyperlinkA;
    }

    /**
     * Sets the value of the tagsCassifyHyperlinkA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagsCassifyHyperlinkA(String value) {
        this.tagsCassifyHyperlinkA = value;
    }

    /**
     * Gets the value of the tagsClassifyHyperlinkB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagsClassifyHyperlinkB() {
        return tagsClassifyHyperlinkB;
    }

    /**
     * Sets the value of the tagsClassifyHyperlinkB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagsClassifyHyperlinkB(String value) {
        this.tagsClassifyHyperlinkB = value;
    }

    /**
     * Gets the value of the tagsDistrictHyperlinkA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagsDistrictHyperlinkA() {
        return tagsDistrictHyperlinkA;
    }

    /**
     * Sets the value of the tagsDistrictHyperlinkA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagsDistrictHyperlinkA(String value) {
        this.tagsDistrictHyperlinkA = value;
    }

    /**
     * Gets the value of the tagsDistrictHyperlinkB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagsDistrictHyperlinkB() {
        return tagsDistrictHyperlinkB;
    }

    /**
     * Sets the value of the tagsDistrictHyperlinkB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagsDistrictHyperlinkB(String value) {
        this.tagsDistrictHyperlinkB = value;
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
