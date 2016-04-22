
package com.froad.client.transDetails;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for goodsExchangeRack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="goodsExchangeRack">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="bankPointCashPricingDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankPointCashRatioPricingDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankPointPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankPointPricingDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankpointCashPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankpointcashRatioPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cashPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cashPricingDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="days" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="fftPointCashPricingDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointCashRatioPricingDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftPointPricingDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftpointCashPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fftpointcashRatioPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goods" type="{http://service.CB.froad.com/}goods" minOccurs="0"/>
 *         &lt;element name="goodsCategoryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsExchangeRackImages" type="{http://service.CB.froad.com/}goodsExchangeRackImages" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="goodsId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsRackDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsRackDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsRackName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="indexPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="inspectors" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isBankPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isBankpointCash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isBankpointcashRatioPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isCash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isFftPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isFftpointCash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isFftpointcashRatioPricing" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isIndexShow" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isPresentPoints" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isRack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marketTotalNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="merchant" type="{http://service.CB.froad.com/}merchant" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="rackTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seller" type="{http://service.CB.froad.com/}seller" minOccurs="0"/>
 *         &lt;element name="sellerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "goodsExchangeRack", propOrder = {
    "bankPointCashPricingDesc",
    "bankPointCashRatioPricingDesc",
    "bankPointPricing",
    "bankPointPricingDesc",
    "bankpointCashPricing",
    "bankpointcashRatioPricing",
    "cashPricing",
    "cashPricingDesc",
    "createTime",
    "days",
    "fftPointCashPricingDesc",
    "fftPointCashRatioPricingDesc",
    "fftPointPricing",
    "fftPointPricingDesc",
    "fftpointCashPricing",
    "fftpointcashRatioPricing",
    "goods",
    "goodsCategoryId",
    "goodsExchangeRackImages",
    "goodsId",
    "goodsRackDesc",
    "goodsRackDetail",
    "goodsRackName",
    "id",
    "indexPriority",
    "inspectors",
    "isBankPoint",
    "isBankpointCash",
    "isBankpointcashRatioPricing",
    "isCash",
    "isFftPoint",
    "isFftpointCash",
    "isFftpointcashRatioPricing",
    "isIndexShow",
    "isPresentPoints",
    "isRack",
    "marketTotalNumber",
    "merchant",
    "merchantId",
    "priority",
    "rackTime",
    "remark",
    "seller",
    "sellerId",
    "state",
    "updateTime"
})
public class GoodsExchangeRack
    extends Pager
{

    protected String bankPointCashPricingDesc;
    protected String bankPointCashRatioPricingDesc;
    protected String bankPointPricing;
    protected String bankPointPricingDesc;
    protected String bankpointCashPricing;
    protected String bankpointcashRatioPricing;
    protected String cashPricing;
    protected String cashPricingDesc;
    protected String createTime;
    protected Integer days;
    protected String fftPointCashPricingDesc;
    protected String fftPointCashRatioPricingDesc;
    protected String fftPointPricing;
    protected String fftPointPricingDesc;
    protected String fftpointCashPricing;
    protected String fftpointcashRatioPricing;
    protected Goods goods;
    protected String goodsCategoryId;
    @XmlElement(nillable = true)
    protected List<GoodsExchangeRackImages> goodsExchangeRackImages;
    protected String goodsId;
    protected String goodsRackDesc;
    protected String goodsRackDetail;
    protected String goodsRackName;
    protected Integer id;
    protected Integer indexPriority;
    protected String inspectors;
    protected String isBankPoint;
    protected String isBankpointCash;
    protected String isBankpointcashRatioPricing;
    protected String isCash;
    protected String isFftPoint;
    protected String isFftpointCash;
    protected String isFftpointcashRatioPricing;
    protected Integer isIndexShow;
    protected Integer isPresentPoints;
    protected String isRack;
    protected Integer marketTotalNumber;
    protected Merchant merchant;
    protected String merchantId;
    protected Integer priority;
    protected String rackTime;
    protected String remark;
    protected Seller seller;
    protected Integer sellerId;
    protected String state;
    protected String updateTime;

    /**
     * Gets the value of the bankPointCashPricingDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointCashPricingDesc() {
        return bankPointCashPricingDesc;
    }

    /**
     * Sets the value of the bankPointCashPricingDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointCashPricingDesc(String value) {
        this.bankPointCashPricingDesc = value;
    }

    /**
     * Gets the value of the bankPointCashRatioPricingDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointCashRatioPricingDesc() {
        return bankPointCashRatioPricingDesc;
    }

    /**
     * Sets the value of the bankPointCashRatioPricingDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointCashRatioPricingDesc(String value) {
        this.bankPointCashRatioPricingDesc = value;
    }

    /**
     * Gets the value of the bankPointPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointPricing() {
        return bankPointPricing;
    }

    /**
     * Sets the value of the bankPointPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointPricing(String value) {
        this.bankPointPricing = value;
    }

    /**
     * Gets the value of the bankPointPricingDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankPointPricingDesc() {
        return bankPointPricingDesc;
    }

    /**
     * Sets the value of the bankPointPricingDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankPointPricingDesc(String value) {
        this.bankPointPricingDesc = value;
    }

    /**
     * Gets the value of the bankpointCashPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankpointCashPricing() {
        return bankpointCashPricing;
    }

    /**
     * Sets the value of the bankpointCashPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankpointCashPricing(String value) {
        this.bankpointCashPricing = value;
    }

    /**
     * Gets the value of the bankpointcashRatioPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankpointcashRatioPricing() {
        return bankpointcashRatioPricing;
    }

    /**
     * Sets the value of the bankpointcashRatioPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankpointcashRatioPricing(String value) {
        this.bankpointcashRatioPricing = value;
    }

    /**
     * Gets the value of the cashPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashPricing() {
        return cashPricing;
    }

    /**
     * Sets the value of the cashPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashPricing(String value) {
        this.cashPricing = value;
    }

    /**
     * Gets the value of the cashPricingDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashPricingDesc() {
        return cashPricingDesc;
    }

    /**
     * Sets the value of the cashPricingDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashPricingDesc(String value) {
        this.cashPricingDesc = value;
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
     * Gets the value of the days property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDays() {
        return days;
    }

    /**
     * Sets the value of the days property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDays(Integer value) {
        this.days = value;
    }

    /**
     * Gets the value of the fftPointCashPricingDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointCashPricingDesc() {
        return fftPointCashPricingDesc;
    }

    /**
     * Sets the value of the fftPointCashPricingDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointCashPricingDesc(String value) {
        this.fftPointCashPricingDesc = value;
    }

    /**
     * Gets the value of the fftPointCashRatioPricingDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointCashRatioPricingDesc() {
        return fftPointCashRatioPricingDesc;
    }

    /**
     * Sets the value of the fftPointCashRatioPricingDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointCashRatioPricingDesc(String value) {
        this.fftPointCashRatioPricingDesc = value;
    }

    /**
     * Gets the value of the fftPointPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointPricing() {
        return fftPointPricing;
    }

    /**
     * Sets the value of the fftPointPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointPricing(String value) {
        this.fftPointPricing = value;
    }

    /**
     * Gets the value of the fftPointPricingDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftPointPricingDesc() {
        return fftPointPricingDesc;
    }

    /**
     * Sets the value of the fftPointPricingDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftPointPricingDesc(String value) {
        this.fftPointPricingDesc = value;
    }

    /**
     * Gets the value of the fftpointCashPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftpointCashPricing() {
        return fftpointCashPricing;
    }

    /**
     * Sets the value of the fftpointCashPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftpointCashPricing(String value) {
        this.fftpointCashPricing = value;
    }

    /**
     * Gets the value of the fftpointcashRatioPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFftpointcashRatioPricing() {
        return fftpointcashRatioPricing;
    }

    /**
     * Sets the value of the fftpointcashRatioPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFftpointcashRatioPricing(String value) {
        this.fftpointcashRatioPricing = value;
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
     * Gets the value of the goodsExchangeRackImages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the goodsExchangeRackImages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGoodsExchangeRackImages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GoodsExchangeRackImages }
     * 
     * 
     */
    public List<GoodsExchangeRackImages> getGoodsExchangeRackImages() {
        if (goodsExchangeRackImages == null) {
            goodsExchangeRackImages = new ArrayList<GoodsExchangeRackImages>();
        }
        return this.goodsExchangeRackImages;
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
     * Gets the value of the goodsRackDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsRackDesc() {
        return goodsRackDesc;
    }

    /**
     * Sets the value of the goodsRackDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsRackDesc(String value) {
        this.goodsRackDesc = value;
    }

    /**
     * Gets the value of the goodsRackDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsRackDetail() {
        return goodsRackDetail;
    }

    /**
     * Sets the value of the goodsRackDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsRackDetail(String value) {
        this.goodsRackDetail = value;
    }

    /**
     * Gets the value of the goodsRackName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsRackName() {
        return goodsRackName;
    }

    /**
     * Sets the value of the goodsRackName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsRackName(String value) {
        this.goodsRackName = value;
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
     * Gets the value of the indexPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndexPriority() {
        return indexPriority;
    }

    /**
     * Sets the value of the indexPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndexPriority(Integer value) {
        this.indexPriority = value;
    }

    /**
     * Gets the value of the inspectors property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectors() {
        return inspectors;
    }

    /**
     * Sets the value of the inspectors property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectors(String value) {
        this.inspectors = value;
    }

    /**
     * Gets the value of the isBankPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBankPoint() {
        return isBankPoint;
    }

    /**
     * Sets the value of the isBankPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBankPoint(String value) {
        this.isBankPoint = value;
    }

    /**
     * Gets the value of the isBankpointCash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBankpointCash() {
        return isBankpointCash;
    }

    /**
     * Sets the value of the isBankpointCash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBankpointCash(String value) {
        this.isBankpointCash = value;
    }

    /**
     * Gets the value of the isBankpointcashRatioPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBankpointcashRatioPricing() {
        return isBankpointcashRatioPricing;
    }

    /**
     * Sets the value of the isBankpointcashRatioPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBankpointcashRatioPricing(String value) {
        this.isBankpointcashRatioPricing = value;
    }

    /**
     * Gets the value of the isCash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsCash() {
        return isCash;
    }

    /**
     * Sets the value of the isCash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsCash(String value) {
        this.isCash = value;
    }

    /**
     * Gets the value of the isFftPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFftPoint() {
        return isFftPoint;
    }

    /**
     * Sets the value of the isFftPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFftPoint(String value) {
        this.isFftPoint = value;
    }

    /**
     * Gets the value of the isFftpointCash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFftpointCash() {
        return isFftpointCash;
    }

    /**
     * Sets the value of the isFftpointCash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFftpointCash(String value) {
        this.isFftpointCash = value;
    }

    /**
     * Gets the value of the isFftpointcashRatioPricing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFftpointcashRatioPricing() {
        return isFftpointcashRatioPricing;
    }

    /**
     * Sets the value of the isFftpointcashRatioPricing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFftpointcashRatioPricing(String value) {
        this.isFftpointcashRatioPricing = value;
    }

    /**
     * Gets the value of the isIndexShow property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsIndexShow() {
        return isIndexShow;
    }

    /**
     * Sets the value of the isIndexShow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsIndexShow(Integer value) {
        this.isIndexShow = value;
    }

    /**
     * Gets the value of the isPresentPoints property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsPresentPoints() {
        return isPresentPoints;
    }

    /**
     * Sets the value of the isPresentPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsPresentPoints(Integer value) {
        this.isPresentPoints = value;
    }

    /**
     * Gets the value of the isRack property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRack() {
        return isRack;
    }

    /**
     * Sets the value of the isRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRack(String value) {
        this.isRack = value;
    }

    /**
     * Gets the value of the marketTotalNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMarketTotalNumber() {
        return marketTotalNumber;
    }

    /**
     * Sets the value of the marketTotalNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMarketTotalNumber(Integer value) {
        this.marketTotalNumber = value;
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
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriority(Integer value) {
        this.priority = value;
    }

    /**
     * Gets the value of the rackTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRackTime() {
        return rackTime;
    }

    /**
     * Sets the value of the rackTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRackTime(String value) {
        this.rackTime = value;
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
     * Gets the value of the seller property.
     * 
     * @return
     *     possible object is
     *     {@link Seller }
     *     
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * Sets the value of the seller property.
     * 
     * @param value
     *     allowed object is
     *     {@link Seller }
     *     
     */
    public void setSeller(Seller value) {
        this.seller = value;
    }

    /**
     * Gets the value of the sellerId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSellerId() {
        return sellerId;
    }

    /**
     * Sets the value of the sellerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSellerId(Integer value) {
        this.sellerId = value;
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
