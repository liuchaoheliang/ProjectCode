
package com.froad.client.activityLotteryResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getActivityLotteryResultByPagerResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getActivityLotteryResultByPagerResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://activity.service.CB.froad.com/}activityLotteryResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getActivityLotteryResultByPagerResponse", propOrder = {
    "_return"
})
public class GetActivityLotteryResultByPagerResponse {

    @XmlElement(name = "return")
    protected ActivityLotteryResult _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ActivityLotteryResult }
     *     
     */
    public ActivityLotteryResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivityLotteryResult }
     *     
     */
    public void setReturn(ActivityLotteryResult value) {
        this._return = value;
    }

}
