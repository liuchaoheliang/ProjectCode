
package com.froad.client.presellBuyInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for presellBuyInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="presellBuyInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsPresellRack" type="{http://service.CB.froad.com/}goodsPresellRack" minOccurs="0"/>
 *         &lt;element name="goodsPresellRackId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="memberCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presellDelivery" type="{http://service.CB.froad.com/}presellDelivery" minOccurs="0"/>
 *         &lt;element name="presellDeliveryId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="totalGoodsNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "presellBuyInfo", propOrder = {
    "createTime",
    "goodsPresellRack",
    "goodsPresellRackId",
    "id",
    "memberCode",
    "presellDelivery",
    "presellDeliveryId",
    "totalGoodsNum",
    "transId",
    "updateTime"
})
public class PresellBuyInfo
    extends Pager
{

    protected String createTime;
    protected GoodsPresellRack goodsPresellRack;
    protected Integer goodsPresellRackId;
    protected Integer id;
    protected String memberCode;
    protected PresellDelivery presellDelivery;
    protected Integer presellDeliveryId;
    protected String totalGoodsNum;
    protected Integer transId;
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
     * Gets the value of the goodsPresellRack property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsPresellRack }
     *     
     */
    public GoodsPresellRack getGoodsPresellRack() {
        return goodsPresellRack;
    }

    /**
     * Sets the value of the goodsPresellRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsPresellRack }
     *     
     */
    public void setGoodsPresellRack(GoodsPresellRack value) {
        this.goodsPresellRack = value;
    }

    /**
     * Gets the value of the goodsPresellRackId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGoodsPresellRackId() {
        return goodsPresellRackId;
    }

    /**
     * Sets the value of the goodsPresellRackId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGoodsPresellRackId(Integer value) {
        this.goodsPresellRackId = value;
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
     * Gets the value of the presellDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link PresellDelivery }
     *     
     */
    public PresellDelivery getPresellDelivery() {
        return presellDelivery;
    }

    /**
     * Sets the value of the presellDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link PresellDelivery }
     *     
     */
    public void setPresellDelivery(PresellDelivery value) {
        this.presellDelivery = value;
    }

    /**
     * Gets the value of the presellDeliveryId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPresellDeliveryId() {
        return presellDeliveryId;
    }

    /**
     * Sets the value of the presellDeliveryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPresellDeliveryId(Integer value) {
        this.presellDeliveryId = value;
    }

    /**
     * Gets the value of the totalGoodsNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalGoodsNum() {
        return totalGoodsNum;
    }

    /**
     * Sets the value of the totalGoodsNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalGoodsNum(String value) {
        this.totalGoodsNum = value;
    }

    /**
     * Gets the value of the transId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTransId() {
        return transId;
    }

    /**
     * Sets the value of the transId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTransId(Integer value) {
        this.transId = value;
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
