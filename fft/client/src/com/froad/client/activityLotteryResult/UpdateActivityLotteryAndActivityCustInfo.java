
package com.froad.client.activityLotteryResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateActivityLotteryAndActivityCustInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateActivityLotteryAndActivityCustInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://activity.service.CB.froad.com/}activityCustInfo" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://activity.service.CB.froad.com/}activityLotteryResult" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg3" type="{http://activity.service.CB.froad.com/}merchant" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateActivityLotteryAndActivityCustInfo", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3"
})
public class UpdateActivityLotteryAndActivityCustInfo {

    protected ActivityCustInfo arg0;
    protected ActivityLotteryResult arg1;
    protected String arg2;
    protected Merchant arg3;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link ActivityCustInfo }
     *     
     */
    public ActivityCustInfo getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivityCustInfo }
     *     
     */
    public void setArg0(ActivityCustInfo value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link ActivityLotteryResult }
     *     
     */
    public ActivityLotteryResult getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivityLotteryResult }
     *     
     */
    public void setArg1(ActivityLotteryResult value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg2(String value) {
        this.arg2 = value;
    }

    /**
     * Gets the value of the arg3 property.
     * 
     * @return
     *     possible object is
     *     {@link Merchant }
     *     
     */
    public Merchant getArg3() {
        return arg3;
    }

    /**
     * Sets the value of the arg3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Merchant }
     *     
     */
    public void setArg3(Merchant value) {
        this.arg3 = value;
    }

}
