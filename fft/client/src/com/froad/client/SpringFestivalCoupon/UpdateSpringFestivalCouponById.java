
package com.froad.client.SpringFestivalCoupon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateSpringFestivalCouponById complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateSpringFestivalCouponById">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://activity.service.CB.froad.com/}springFestivalCoupon" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateSpringFestivalCouponById", propOrder = {
    "arg0"
})
public class UpdateSpringFestivalCouponById {

    protected SpringFestivalCoupon arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link SpringFestivalCoupon }
     *     
     */
    public SpringFestivalCoupon getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpringFestivalCoupon }
     *     
     */
    public void setArg0(SpringFestivalCoupon value) {
        this.arg0 = value;
    }

}
