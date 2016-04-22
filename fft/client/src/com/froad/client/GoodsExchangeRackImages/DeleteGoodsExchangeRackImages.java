
package com.froad.client.GoodsExchangeRackImages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteGoodsExchangeRackImages complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteGoodsExchangeRackImages">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://service.CB.froad.com/}goodsExchangeRackImages" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteGoodsExchangeRackImages", propOrder = {
    "arg0"
})
public class DeleteGoodsExchangeRackImages {

    protected GoodsExchangeRackImages arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsExchangeRackImages }
     *     
     */
    public GoodsExchangeRackImages getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsExchangeRackImages }
     *     
     */
    public void setArg0(GoodsExchangeRackImages value) {
        this.arg0 = value;
    }

}
