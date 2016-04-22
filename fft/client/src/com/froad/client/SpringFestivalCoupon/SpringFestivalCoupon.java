
package com.froad.client.SpringFestivalCoupon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for springFestivalCoupon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="springFestivalCoupon">
 *   &lt;complexContent>
 *     &lt;extension base="{http://activity.service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="awardLevel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="beCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consumeTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expireTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gotTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isConsume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isGot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="memberCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o2oInfoId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securitiesNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="worthCash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "springFestivalCoupon", propOrder = {
    "awardLevel",
    "beCode",
    "beName",
    "consumeTime",
    "createTime",
    "expireTime",
    "gotTime",
    "id",
    "isConsume",
    "isGot",
    "memberCode",
    "merchantId",
    "o2OInfoId",
    "securitiesNo",
    "state",
    "type",
    "updateTime",
    "worthCash"
})
public class SpringFestivalCoupon
    extends Pager
{

    protected Integer awardLevel;
    protected String beCode;
    protected String beName;
    protected String consumeTime;
    protected String createTime;
    protected String expireTime;
    protected String gotTime;
    protected Integer id;
    protected String isConsume;
    protected String isGot;
    protected String memberCode;
    protected String merchantId;
    @XmlElement(name = "o2oInfoId")
    protected String o2OInfoId;
    protected String securitiesNo;
    protected String state;
    protected String type;
    protected String updateTime;
    protected String worthCash;

    /**
     * Gets the value of the awardLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAwardLevel() {
        return awardLevel;
    }

    /**
     * Sets the value of the awardLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAwardLevel(Integer value) {
        this.awardLevel = value;
    }

    /**
     * Gets the value of the beCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeCode() {
        return beCode;
    }

    /**
     * Sets the value of the beCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeCode(String value) {
        this.beCode = value;
    }

    /**
     * Gets the value of the beName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeName() {
        return beName;
    }

    /**
     * Sets the value of the beName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeName(String value) {
        this.beName = value;
    }

    /**
     * Gets the value of the consumeTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumeTime() {
        return consumeTime;
    }

    /**
     * Sets the value of the consumeTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumeTime(String value) {
        this.consumeTime = value;
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
     * Gets the value of the expireTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * Sets the value of the expireTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpireTime(String value) {
        this.expireTime = value;
    }

    /**
     * Gets the value of the gotTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGotTime() {
        return gotTime;
    }

    /**
     * Sets the value of the gotTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGotTime(String value) {
        this.gotTime = value;
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
     * Gets the value of the isConsume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsConsume() {
        return isConsume;
    }

    /**
     * Sets the value of the isConsume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsConsume(String value) {
        this.isConsume = value;
    }

    /**
     * Gets the value of the isGot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsGot() {
        return isGot;
    }

    /**
     * Sets the value of the isGot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsGot(String value) {
        this.isGot = value;
    }

    /**
     * Gets the value of the memberCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemberCode() {
        return memberCode;
    }

    /**
     * Sets the value of the memberCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemberCode(String value) {
        this.memberCode = value;
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
     * Gets the value of the o2OInfoId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getO2OInfoId() {
        return o2OInfoId;
    }

    /**
     * Sets the value of the o2OInfoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setO2OInfoId(String value) {
        this.o2OInfoId = value;
    }

    /**
     * Gets the value of the securitiesNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecuritiesNo() {
        return securitiesNo;
    }

    /**
     * Sets the value of the securitiesNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecuritiesNo(String value) {
        this.securitiesNo = value;
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
     * Gets the value of the worthCash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorthCash() {
        return worthCash;
    }

    /**
     * Sets the value of the worthCash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorthCash(String value) {
        this.worthCash = value;
    }

}
