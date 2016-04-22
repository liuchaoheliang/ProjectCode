
package com.froad.client.SpringFestivalCoupon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSpringFestivalCouponByPagerResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSpringFestivalCouponByPagerResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://activity.service.CB.froad.com/}springFestivalCoupon" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSpringFestivalCouponByPagerResponse", propOrder = {
    "_return"
})
public class GetSpringFestivalCouponByPagerResponse {

    @XmlElement(name = "return")
    protected SpringFestivalCoupon _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link SpringFestivalCoupon }
     *     
     */
    public SpringFestivalCoupon getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpringFestivalCoupon }
     *     
     */
    public void setReturn(SpringFestivalCoupon value) {
        this._return = value;
    }

}
