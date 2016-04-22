
package com.froad.client.memberCollect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for merchantProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merchantProduct">
 *   &lt;complexContent>
 *     &lt;extension base="{http://user.service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="array" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="merchantId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="photoFormat1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="photoFormat2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="photoUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pointRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preferentialRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productPriority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txt1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txt2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txt3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "merchantProduct", propOrder = {
    "array",
    "createTime",
    "id",
    "merchantId",
    "photoFormat1",
    "photoFormat2",
    "photoUrl",
    "pointRate",
    "preferentialRate",
    "price",
    "productPriority",
    "remark",
    "state",
    "txt1",
    "txt2",
    "txt3",
    "updateTime"
})
public class MerchantProduct
    extends Pager
{

    protected String array;
    protected String createTime;
    protected Integer id;
    protected String merchantId;
    protected String photoFormat1;
    protected String photoFormat2;
    protected String photoUrl;
    protected String pointRate;
    protected String preferentialRate;
    protected String price;
    protected String productPriority;
    protected String remark;
    protected String state;
    protected String txt1;
    protected String txt2;
    protected String txt3;
    protected String updateTime;

    /**
     * Gets the value of the array property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArray() {
        return array;
    }

    /**
     * Sets the value of the array property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArray(String value) {
        this.array = value;
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
     * Gets the value of the photoFormat1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhotoFormat1() {
        return photoFormat1;
    }

    /**
     * Sets the value of the photoFormat1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhotoFormat1(String value) {
        this.photoFormat1 = value;
    }

    /**
     * Gets the value of the photoFormat2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhotoFormat2() {
        return photoFormat2;
    }

    /**
     * Sets the value of the photoFormat2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhotoFormat2(String value) {
        this.photoFormat2 = value;
    }

    /**
     * Gets the value of the photoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Sets the value of the photoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhotoUrl(String value) {
        this.photoUrl = value;
    }

    /**
     * Gets the value of the pointRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointRate() {
        return pointRate;
    }

    /**
     * Sets the value of the pointRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointRate(String value) {
        this.pointRate = value;
    }

    /**
     * Gets the value of the preferentialRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferentialRate() {
        return preferentialRate;
    }

    /**
     * Sets the value of the preferentialRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferentialRate(String value) {
        this.preferentialRate = value;
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
     * Gets the value of the productPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductPriority() {
        return productPriority;
    }

    /**
     * Sets the value of the productPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductPriority(String value) {
        this.productPriority = value;
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
     * Gets the value of the txt1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxt1() {
        return txt1;
    }

    /**
     * Sets the value of the txt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxt1(String value) {
        this.txt1 = value;
    }

    /**
     * Gets the value of the txt2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxt2() {
        return txt2;
    }

    /**
     * Sets the value of the txt2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxt2(String value) {
        this.txt2 = value;
    }

    /**
     * Gets the value of the txt3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxt3() {
        return txt3;
    }

    /**
     * Sets the value of the txt3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxt3(String value) {
        this.txt3 = value;
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
