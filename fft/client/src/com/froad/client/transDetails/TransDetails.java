
package com.froad.client.transDetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bankPointsValueAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankPointsValueRealAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyersRuleDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyersRuleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientGoodsCarryRack" type="{http://service.CB.froad.com/}clientGoodsCarryRack" minOccurs="0"/>
 *         &lt;element name="clientGoodsExchangeRack" type="{http://service.CB.froad.com/}clientGoodsExchangeRack" minOccurs="0"/>
 *         &lt;element name="clientGoodsGatherRack" type="{http://service.CB.froad.com/}clientGoodsGatherRack" minOccurs="0"/>
 *         &lt;element name="clientGoodsGroupRack" type="{http://service.CB.froad.com/}clientGoodsGroupRack" minOccurs="0"/>
 *         &lt;element name="costpriceTotal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyValueReal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsValueAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointsValueRealAll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goods" type="{http://service.CB.froad.com/}goods" minOccurs="0"/>
 *         &lt;element name="goodsCarryRack" type="{http://service.CB.froad.com/}goodsCarryRack" minOccurs="0"/>
 *         &lt;element name="goodsCategoryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsExchangeRack" type="{http://service.CB.froad.com/}goodsExchangeRack" minOccurs="0"/>
 *         &lt;element name="goodsGatherRack" type="{http://service.CB.froad.com/}goodsGatherRack" minOccurs="0"/>
 *         &lt;element name="goodsGroupRack" type="{http://service.CB.froad.com/}goodsGroupRack" minOccurs="0"/>
 *         &lt;element name="goodsNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsPresellRack" type="{http://service.CB.froad.com/}goodsPresellRack" minOccurs="0"/>
 *         &lt;element name="goodsRackId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerRuleDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sellerRuleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "transDetails", propOrder = {
    "bankPointsValueAll",
    "bankPointsValueRealAll",
    "buyersRuleDesc",
    "buyersRuleId",
    "clientGoodsCarryRack",
    "clientGoodsExchangeRack",
    "clientGoodsGatherRack",
    "clientGoodsGroupRack",
    "costpriceTotal",
    "createTime",
    "currency",
    "currencyValue",
    "currencyValueReal",
    "fftPointsValueAll",
    "fftPointsValueRealAll",
    "goods",
    "goodsCarryRack",
    "goodsCategoryId",
    "goodsExchangeRack",
    "goodsGatherRack",
    "goodsGroupRack",
    "goodsNumber",
    "goodsPresellRack",
    "goodsRackId",
    "id",
    "remark",
    "sellerRuleDesc",
    "sellerRuleId",
    "state",
    "transId",
    "updateTime"
})
public class TransDetails {

    protected String bankPointsValueAll;
    protected String bankPointsValueRealAll;
    protected String buyersRuleDesc;
    protected String buyersRuleId;
    protected ClientGoodsCarryRack clientGoodsCarryRack;
    protected ClientGoodsExchangeRack clientGoodsExchangeRack;
    protected ClientGoodsGatherRack clientGoodsGatherRack;
    protected ClientGoodsGroupRack clientGoodsGroupRack;
    protected String costpriceTotal;
    protected String createTime;
    protected String currency;
    protected String currencyValue;
    protected String currencyValueReal;
    protected String fftPointsValueAll;
    protected String fftPointsValueRealAll;
    protected Goods goods;
    protected GoodsCarryRack goodsCarryRack;
    protected String goodsCategoryId;
    protected GoodsExchangeRack goodsExchangeRack;
    protected GoodsGatherRack goodsGatherRack;
    protected GoodsGroupRack goodsGroupRack;
    protected String goodsNumber;
    protected GoodsPresellRack goodsPresellRack;
    protected String goodsRackId;
    protected Integer id;
    protected String remark;
    protected String sellerRuleDesc;
    protected String sellerRuleId;
    protected String state;
    protected Integer transId;
    protected String updateTime;

    /**
     * Gets the value of the bankPointsValueAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointsValueAll() {
        return bankPointsValueAll;
    }

    /**
     * Sets the value of the bankPointsValueAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointsValueAll(String value) {
        this.bankPointsValueAll = value;
    }

    /**
     * Gets the value of the bankPointsValueRealAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointsValueRealAll() {
        return bankPointsValueRealAll;
    }

    /**
     * Sets the value of the bankPointsValueRealAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointsValueRealAll(String value) {
        this.bankPointsValueRealAll = value;
    }

    /**
     * Gets the value of the buyersRuleDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyersRuleDesc() {
        return buyersRuleDesc;
    }

    /**
     * Sets the value of the buyersRuleDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyersRuleDesc(String value) {
        this.buyersRuleDesc = value;
    }

    /**
     * Gets the value of the buyersRuleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyersRuleId() {
        return buyersRuleId;
    }

    /**
     * Sets the value of the buyersRuleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyersRuleId(String value) {
        this.buyersRuleId = value;
    }

    /**
     * Gets the value of the clientGoodsCarryRack property.
     * 
     * @return
     *     possible object is
     *     {@link ClientGoodsCarryRack }
     *     
     */
    public ClientGoodsCarryRack getClientGoodsCarryRack() {
        return clientGoodsCarryRack;
    }

    /**
     * Sets the value of the clientGoodsCarryRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientGoodsCarryRack }
     *     
     */
    public void setClientGoodsCarryRack(ClientGoodsCarryRack value) {
        this.clientGoodsCarryRack = value;
    }

    /**
     * Gets the value of the clientGoodsExchangeRack property.
     * 
     * @return
     *     possible object is
     *     {@link ClientGoodsExchangeRack }
     *     
     */
    public ClientGoodsExchangeRack getClientGoodsExchangeRack() {
        return clientGoodsExchangeRack;
    }

    /**
     * Sets the value of the clientGoodsExchangeRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientGoodsExchangeRack }
     *     
     */
    public void setClientGoodsExchangeRack(ClientGoodsExchangeRack value) {
        this.clientGoodsExchangeRack = value;
    }

    /**
     * Gets the value of the clientGoodsGatherRack property.
     * 
     * @return
     *     possible object is
     *     {@link ClientGoodsGatherRack }
     *     
     */
    public ClientGoodsGatherRack getClientGoodsGatherRack() {
        return clientGoodsGatherRack;
    }

    /**
     * Sets the value of the clientGoodsGatherRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientGoodsGatherRack }
     *     
     */
    public void setClientGoodsGatherRack(ClientGoodsGatherRack value) {
        this.clientGoodsGatherRack = value;
    }

    /**
     * Gets the value of the clientGoodsGroupRack property.
     * 
     * @return
     *     possible object is
     *     {@link ClientGoodsGroupRack }
     *     
     */
    public ClientGoodsGroupRack getClientGoodsGroupRack() {
        return clientGoodsGroupRack;
    }

    /**
     * Sets the value of the clientGoodsGroupRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientGoodsGroupRack }
     *     
     */
    public void setClientGoodsGroupRack(ClientGoodsGroupRack value) {
        this.clientGoodsGroupRack = value;
    }

    /**
     * Gets the value of the costpriceTotal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCostpriceTotal() {
        return costpriceTotal;
    }

    /**
     * Sets the value of the costpriceTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCostpriceTotal(String value) {
        this.costpriceTotal = value;
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
     * Gets the value of the currencyValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyValue() {
        return currencyValue;
    }

    /**
     * Sets the value of the currencyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyValue(String value) {
        this.currencyValue = value;
    }

    /**
     * Gets the value of the currencyValueReal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyValueReal() {
        return currencyValueReal;
    }

    /**
     * Sets the value of the currencyValueReal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyValueReal(String value) {
        this.currencyValueReal = value;
    }

    /**
     * Gets the value of the fftPointsValueAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsValueAll() {
        return fftPointsValueAll;
    }

    /**
     * Sets the value of the fftPointsValueAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsValueAll(String value) {
        this.fftPointsValueAll = value;
    }

    /**
     * Gets the value of the fftPointsValueRealAll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointsValueRealAll() {
        return fftPointsValueRealAll;
    }

    /**
     * Sets the value of the fftPointsValueRealAll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointsValueRealAll(String value) {
        this.fftPointsValueRealAll = value;
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
     * Gets the value of the goodsCarryRack property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsCarryRack }
     *     
     */
    public GoodsCarryRack getGoodsCarryRack() {
        return goodsCarryRack;
    }

    /**
     * Sets the value of the goodsCarryRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsCarryRack }
     *     
     */
    public void setGoodsCarryRack(GoodsCarryRack value) {
        this.goodsCarryRack = value;
    }

    /**
     * Gets the value of the goodsCategoryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    /**
     * Sets the value of the goodsCategoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsCategoryId(String value) {
        this.goodsCategoryId = value;
    }

    /**
     * Gets the value of the goodsExchangeRack property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsExchangeRack }
     *     
     */
    public GoodsExchangeRack getGoodsExchangeRack() {
        return goodsExchangeRack;
    }

    /**
     * Sets the value of the goodsExchangeRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsExchangeRack }
     *     
     */
    public void setGoodsExchangeRack(GoodsExchangeRack value) {
        this.goodsExchangeRack = value;
    }

    /**
     * Gets the value of the goodsGatherRack property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsGatherRack }
     *     
     */
    public GoodsGatherRack getGoodsGatherRack() {
        return goodsGatherRack;
    }

    /**
     * Sets the value of the goodsGatherRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsGatherRack }
     *     
     */
    public void setGoodsGatherRack(GoodsGatherRack value) {
        this.goodsGatherRack = value;
    }

    /**
     * Gets the value of the goodsGroupRack property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsGroupRack }
     *     
     */
    public GoodsGroupRack getGoodsGroupRack() {
        return goodsGroupRack;
    }

    /**
     * Sets the value of the goodsGroupRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsGroupRack }
     *     
     */
    public void setGoodsGroupRack(GoodsGroupRack value) {
        this.goodsGroupRack = value;
    }

    /**
     * Gets the value of the goodsNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * Sets the value of the goodsNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsNumber(String value) {
        this.goodsNumber = value;
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
     * Gets the value of the goodsRackId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsRackId() {
        return goodsRackId;
    }

    /**
     * Sets the value of the goodsRackId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsRackId(String value) {
        this.goodsRackId = value;
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
     * Gets the value of the sellerRuleDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerRuleDesc() {
        return sellerRuleDesc;
    }

    /**
     * Sets the value of the sellerRuleDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerRuleDesc(String value) {
        this.sellerRuleDesc = value;
    }

    /**
     * Gets the value of the sellerRuleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSellerRuleId() {
        return sellerRuleId;
    }

    /**
     * Sets the value of the sellerRuleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSellerRuleId(String value) {
        this.sellerRuleId = value;
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
