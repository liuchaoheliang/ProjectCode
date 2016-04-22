
package com.froad.client.merchantUserSeller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for merchantUserSeller complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merchantUserSeller">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchantUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reserveFieldFive" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="reserveFieldFour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reserveFieldOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reserveFieldThree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reserveFieldTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "merchantUserSeller", propOrder = {
    "createTime",
    "id",
    "merchantId",
    "merchantUserId",
    "remark",
    "reserveFieldFive",
    "reserveFieldFour",
    "reserveFieldOne",
    "reserveFieldThree",
    "reserveFieldTwo",
    "sellerId",
    "sellerType",
    "state",
    "updateTime"
})
public class MerchantUserSeller
    extends Pager
{

    protected String createTime;
    protected Integer id;
    protected String merchantId;
    protected String merchantUserId;
    protected String remark;
    protected Integer reserveFieldFive;
    protected String reserveFieldFour;
    protected String reserveFieldOne;
    protected String reserveFieldThree;
    protected String reserveFieldTwo;
    protected String sellerId;
    protected String sellerType;
    protected String state;
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
     * Gets the value of the merchantUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantUserId() {
        return merchantUserId;
    }

    /**
     * Sets the value of the merchantUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantUserId(String value) {
        this.merchantUserId = value;
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
     * Gets the value of the reserveFieldFive property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReserveFieldFive() {
        return reserveFieldFive;
    }

    /**
     * Sets the value of the reserveFieldFive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReserveFieldFive(Integer value) {
        this.reserveFieldFive = value;
    }

    /**
     * Gets the value of the reserveFieldFour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserveFieldFour() {
        return reserveFieldFour;
    }

    /**
     * Sets the value of the reserveFieldFour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserveFieldFour(String value) {
        this.reserveFieldFour = value;
    }

    /**
     * Gets the value of the reserveFieldOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserveFieldOne() {
        return reserveFieldOne;
    }

    /**
     * Sets the value of the reserveFieldOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserveFieldOne(String value) {
        this.reserveFieldOne = value;
    }

    /**
     * Gets the value of the reserveFieldThree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserveFieldThree() {
        return reserveFieldThree;
    }

    /**
     * Sets the value of the reserveFieldThree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserveFieldThree(String value) {
        this.reserveFieldThree = value;
    }

    /**
     * Gets the value of the reserveFieldTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserveFieldTwo() {
        return reserveFieldTwo;
    }

    /**
     * Sets the value of the reserveFieldTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserveFieldTwo(String value) {
        this.reserveFieldTwo = value;
    }

    /**
     * Gets the value of the sellerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * Sets the value of the sellerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerId(String value) {
        this.sellerId = value;
    }

    /**
     * Gets the value of the sellerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerType() {
        return sellerType;
    }

    /**
     * Sets the value of the sellerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerType(String value) {
        this.sellerType = value;
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
