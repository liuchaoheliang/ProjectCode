
package com.froad.client.goodsCarryRack;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for goods complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="goods">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="bigUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brandId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="freezeStore" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="goodsCategory" type="{http://service.CB.froad.com/}goodsCategory" minOccurs="0"/>
 *         &lt;element name="goodsCategoryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsImageListStore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsRackAttrList" type="{http://service.CB.froad.com/}goodsRackAttribute" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="goodsRockId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsSn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isAuto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isBest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isHot" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isNew" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="keywords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marketPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="merchant" type="{http://service.CB.froad.com/}merchant" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smallUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="store" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="table" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thumbnailUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="warnNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weightUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "goods", propOrder = {
    "bigUrl",
    "brandId",
    "createTime",
    "freezeStore",
    "goodsCategory",
    "goodsCategoryId",
    "goodsDesc",
    "goodsImageListStore",
    "goodsName",
    "goodsRackAttrList",
    "goodsRockId",
    "goodsSn",
    "goodsType",
    "id",
    "isAuto",
    "isBest",
    "isHot",
    "isNew",
    "keywords",
    "marketPrice",
    "merchant",
    "merchantId",
    "originPrice",
    "price",
    "remark",
    "smallUrl",
    "sourceUrl",
    "state",
    "store",
    "table",
    "thumbnailUrl",
    "updateTime",
    "warnNumber",
    "weight",
    "weightUnit"
})
public class Goods
    extends Pager
{

    protected String bigUrl;
    protected String brandId;
    protected String createTime;
    protected Integer freezeStore;
    protected GoodsCategory goodsCategory;
    protected String goodsCategoryId;
    protected String goodsDesc;
    protected String goodsImageListStore;
    protected String goodsName;
    @XmlElement(nillable = true)
    protected List<GoodsRackAttribute> goodsRackAttrList;
    protected String goodsRockId;
    protected String goodsSn;
    protected String goodsType;
    protected Integer id;
    protected String isAuto;
    protected String isBest;
    protected String isHot;
    protected String isNew;
    protected String keywords;
    protected String marketPrice;
    protected Merchant merchant;
    protected String merchantId;
    protected String originPrice;
    protected String price;
    protected String remark;
    protected String smallUrl;
    protected String sourceUrl;
    protected String state;
    protected Integer store;
    protected String table;
    protected String thumbnailUrl;
    protected String updateTime;
    protected String warnNumber;
    protected String weight;
    protected String weightUnit;

    /**
     * Gets the value of the bigUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBigUrl() {
        return bigUrl;
    }

    /**
     * Sets the value of the bigUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBigUrl(String value) {
        this.bigUrl = value;
    }

    /**
     * Gets the value of the brandId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * Sets the value of the brandId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandId(String value) {
        this.brandId = value;
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
     * Gets the value of the freezeStore property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFreezeStore() {
        return freezeStore;
    }

    /**
     * Sets the value of the freezeStore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFreezeStore(Integer value) {
        this.freezeStore = value;
    }

    /**
     * Gets the value of the goodsCategory property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsCategory }
     *     
     */
    public GoodsCategory getGoodsCategory() {
        return goodsCategory;
    }

    /**
     * Sets the value of the goodsCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsCategory }
     *     
     */
    public void setGoodsCategory(GoodsCategory value) {
        this.goodsCategory = value;
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
     * Gets the value of the goodsDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsDesc() {
        return goodsDesc;
    }

    /**
     * Sets the value of the goodsDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsDesc(String value) {
        this.goodsDesc = value;
    }

    /**
     * Gets the value of the goodsImageListStore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsImageListStore() {
        return goodsImageListStore;
    }

    /**
     * Sets the value of the goodsImageListStore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsImageListStore(String value) {
        this.goodsImageListStore = value;
    }

    /**
     * Gets the value of the goodsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Sets the value of the goodsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsName(String value) {
        this.goodsName = value;
    }

    /**
     * Gets the value of the goodsRackAttrList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the goodsRackAttrList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGoodsRackAttrList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GoodsRackAttribute }
     * 
     * 
     */
    public List<GoodsRackAttribute> getGoodsRackAttrList() {
        if (goodsRackAttrList == null) {
            goodsRackAttrList = new ArrayList<GoodsRackAttribute>();
        }
        return this.goodsRackAttrList;
    }

    /**
     * Gets the value of the goodsRockId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsRockId() {
        return goodsRockId;
    }

    /**
     * Sets the value of the goodsRockId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsRockId(String value) {
        this.goodsRockId = value;
    }

    /**
     * Gets the value of the goodsSn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsSn() {
        return goodsSn;
    }

    /**
     * Sets the value of the goodsSn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsSn(String value) {
        this.goodsSn = value;
    }

    /**
     * Gets the value of the goodsType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsType() {
        return goodsType;
    }

    /**
     * Sets the value of the goodsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsType(String value) {
        this.goodsType = value;
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
     * Gets the value of the isAuto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAuto() {
        return isAuto;
    }

    /**
     * Sets the value of the isAuto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAuto(String value) {
        this.isAuto = value;
    }

    /**
     * Gets the value of the isBest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBest() {
        return isBest;
    }

    /**
     * Sets the value of the isBest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBest(String value) {
        this.isBest = value;
    }

    /**
     * Gets the value of the isHot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHot() {
        return isHot;
    }

    /**
     * Sets the value of the isHot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHot(String value) {
        this.isHot = value;
    }

    /**
     * Gets the value of the isNew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsNew() {
        return isNew;
    }

    /**
     * Sets the value of the isNew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsNew(String value) {
        this.isNew = value;
    }

    /**
     * Gets the value of the keywords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Sets the value of the keywords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeywords(String value) {
        this.keywords = value;
    }

    /**
     * Gets the value of the marketPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketPrice() {
        return marketPrice;
    }

    /**
     * Sets the value of the marketPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketPrice(String value) {
        this.marketPrice = value;
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
     * Gets the value of the originPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginPrice() {
        return originPrice;
    }

    /**
     * Sets the value of the originPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginPrice(String value) {
        this.originPrice = value;
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
     * Gets the value of the smallUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmallUrl() {
        return smallUrl;
    }

    /**
     * Sets the value of the smallUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmallUrl(String value) {
        this.smallUrl = value;
    }

    /**
     * Gets the value of the sourceUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * Sets the value of the sourceUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceUrl(String value) {
        this.sourceUrl = value;
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
     * Gets the value of the store property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStore() {
        return store;
    }

    /**
     * Sets the value of the store property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStore(Integer value) {
        this.store = value;
    }

    /**
     * Gets the value of the table property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the value of the table property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTable(String value) {
        this.table = value;
    }

    /**
     * Gets the value of the thumbnailUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * Sets the value of the thumbnailUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbnailUrl(String value) {
        this.thumbnailUrl = value;
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
     * Gets the value of the warnNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarnNumber() {
        return warnNumber;
    }

    /**
     * Sets the value of the warnNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarnNumber(String value) {
        this.warnNumber = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeight(String value) {
        this.weight = value;
    }

    /**
     * Gets the value of the weightUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeightUnit() {
        return weightUnit;
    }

    /**
     * Sets the value of the weightUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeightUnit(String value) {
        this.weightUnit = value;
    }

}
