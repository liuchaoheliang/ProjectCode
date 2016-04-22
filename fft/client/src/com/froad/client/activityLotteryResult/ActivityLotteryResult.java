
package com.froad.client.activityLotteryResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for activityLotteryResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="activityLotteryResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://activity.service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="activityCustInfo" type="{http://activity.service.CB.froad.com/}activityCustInfo" minOccurs="0"/>
 *         &lt;element name="beCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consumeTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expireTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="field1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="field2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goods" type="{http://activity.service.CB.froad.com/}goods" minOccurs="0"/>
 *         &lt;element name="goodsId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isConsume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lotteryCustId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchant" type="{http://activity.service.CB.froad.com/}merchant" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="point" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securitiesNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smsNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="smsTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="user" type="{http://activity.service.CB.froad.com/}user" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activityLotteryResult", propOrder = {
    "activityCustInfo",
    "beCode",
    "beName",
    "consumeTime",
    "createTime",
    "expireTime",
    "field1",
    "field2",
    "goods",
    "goodsId",
    "id",
    "isConsume",
    "lotteryCustId",
    "merchant",
    "merchantId",
    "point",
    "price",
    "remark",
    "securitiesNo",
    "smsNumber",
    "smsTime",
    "state",
    "type",
    "updateTime",
    "user",
    "userId"
})
public class ActivityLotteryResult
    extends Pager
{

    protected ActivityCustInfo activityCustInfo;
    protected String beCode;
    protected String beName;
    protected String consumeTime;
    protected String createTime;
    protected String expireTime;
    protected String field1;
    protected String field2;
    protected Goods goods;
    protected String goodsId;
    protected Integer id;
    protected String isConsume;
    protected String lotteryCustId;
    protected Merchant merchant;
    protected String merchantId;
    protected String point;
    protected String price;
    protected String remark;
    protected String securitiesNo;
    protected Integer smsNumber;
    protected String smsTime;
    protected String state;
    protected String type;
    protected String updateTime;
    protected User user;
    protected String userId;

    /**
     * Gets the value of the activityCustInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ActivityCustInfo }
     *     
     */
    public ActivityCustInfo getActivityCustInfo() {
        return activityCustInfo;
    }

    /**
     * Sets the value of the activityCustInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivityCustInfo }
     *     
     */
    public void setActivityCustInfo(ActivityCustInfo value) {
        this.activityCustInfo = value;
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
     * Gets the value of the field1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField1() {
        return field1;
    }

    /**
     * Sets the value of the field1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField1(String value) {
        this.field1 = value;
    }

    /**
     * Gets the value of the field2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField2() {
        return field2;
    }

    /**
     * Sets the value of the field2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField2(String value) {
        this.field2 = value;
    }

    /**
     * Gets the value of the goods property.
     * 
     * @return
     *     possible object is
     *     {@link Goods }
     *     
     */
    public Goods getGoods() {
        return goods;
    }

    /**
     * Sets the value of the goods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Goods }
     *     
     */
    public void setGoods(Goods value) {
        this.goods = value;
    }

    /**
     * Gets the value of the goodsId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * Sets the value of the goodsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsId(String value) {
        this.goodsId = value;
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
     * Gets the value of the lotteryCustId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotteryCustId() {
        return lotteryCustId;
    }

    /**
     * Sets the value of the lotteryCustId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotteryCustId(String value) {
        this.lotteryCustId = value;
    }

    /**
     * Gets the value of the merchant property.
     * 
     * @return
     *     possible object is
     *     {@link Merchant }
     *     
     */
    public Merchant getMerchant() {
        return merchant;
    }

    /**
     * Sets the value of the merchant property.
     * 
     * @param value
     *     allowed object is
     *     {@link Merchant }
     *     
     */
    public void setMerchant(Merchant value) {
        this.merchant = value;
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
     * Gets the value of the point property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoint() {
        return point;
    }

    /**
     * Sets the value of the point property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoint(String value) {
        this.point = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrice(String value) {
        this.price = value;
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
     * Gets the value of the smsNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSmsNumber() {
        return smsNumber;
    }

    /**
     * Sets the value of the smsNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSmsNumber(Integer value) {
        this.smsNumber = value;
    }

    /**
     * Gets the value of the smsTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsTime() {
        return smsTime;
    }

    /**
     * Sets the value of the smsTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsTime(String value) {
        this.smsTime = value;
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
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

}
