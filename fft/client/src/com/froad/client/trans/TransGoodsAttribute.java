
package com.froad.client.trans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transGoodsAttribute complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transGoodsAttribute">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="element" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsRackAttribute" type="{http://service.CB.froad.com/}goodsRackAttribute" minOccurs="0"/>
 *         &lt;element name="goodsRackAttributeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transGoodsAttribute", propOrder = {
    "element",
    "goodsRackAttribute",
    "goodsRackAttributeId",
    "transId"
})
public class TransGoodsAttribute {

    protected String element;
    protected GoodsRackAttribute goodsRackAttribute;
    protected String goodsRackAttributeId;
    protected String transId;

    /**
     * Gets the value of the element property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElement() {
        return element;
    }

    /**
     * Sets the value of the element property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElement(String value) {
        this.element = value;
    }

    /**
     * Gets the value of the goodsRackAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsRackAttribute }
     *     
     */
    public GoodsRackAttribute getGoodsRackAttribute() {
        return goodsRackAttribute;
    }

    /**
     * Sets the value of the goodsRackAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsRackAttribute }
     *     
     */
    public void setGoodsRackAttribute(GoodsRackAttribute value) {
        this.goodsRackAttribute = value;
    }

    /**
     * Gets the value of the goodsRackAttributeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsRackAttributeId() {
        return goodsRackAttributeId;
    }

    /**
     * Sets the value of the goodsRackAttributeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsRackAttributeId(String value) {
        this.goodsRackAttributeId = value;
    }

    /**
     * Gets the value of the transId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransId() {
        return transId;
    }

    /**
     * Sets the value of the transId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransId(String value) {
        this.transId = value;
    }

}
