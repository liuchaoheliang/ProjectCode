
package com.froad.client.merchant;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addMerchantByMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addMerchantByMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="arg1" type="{http://service.CB.froad.com/}user" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://service.CB.froad.com/}merchant" minOccurs="0"/>
 *         &lt;element name="arg3" type="{http://service.CB.froad.com/}merchantPresent" minOccurs="0"/>
 *         &lt;element name="arg4" type="{http://service.CB.froad.com/}merchantTrain" minOccurs="0"/>
 *         &lt;element name="arg5" type="{http://service.CB.froad.com/}tagMAP" minOccurs="0"/>
 *         &lt;element name="arg6" type="{http://service.CB.froad.com/}merchantUserSet" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addMerchantByMap", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3",
    "arg4",
    "arg5",
    "arg6"
})
public class AddMerchantByMap {

    protected boolean arg0;
    protected User arg1;
    protected Merchant arg2;
    protected MerchantPresent arg3;
    protected MerchantTrain arg4;
    protected TagMAP arg5;
    protected MerchantUserSet arg6;

    /**
     * Gets the value of the arg0 property.
     * 
     */
    public boolean isArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     */
    public void setArg0(boolean value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setArg1(User value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     * @return
     *     possible object is
     *     {@link Merchant }
     *     
     */
    public Merchant getArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Merchant }
     *     
     */
    public void setArg2(Merchant value) {
        this.arg2 = value;
    }

    /**
     * Gets the value of the arg3 property.
     * 
     * @return
     *     possible object is
     *     {@link MerchantPresent }
     *     
     */
    public MerchantPresent getArg3() {
        return arg3;
    }

    /**
     * Sets the value of the arg3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MerchantPresent }
     *     
     */
    public void setArg3(MerchantPresent value) {
        this.arg3 = value;
    }

    /**
     * Gets the value of the arg4 property.
     * 
     * @return
     *     possible object is
     *     {@link MerchantTrain }
     *     
     */
    public MerchantTrain getArg4() {
        return arg4;
    }

    /**
     * Sets the value of the arg4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MerchantTrain }
     *     
     */
    public void setArg4(MerchantTrain value) {
        this.arg4 = value;
    }

    /**
     * Gets the value of the arg5 property.
     * 
     * @return
     *     possible object is
     *     {@link TagMAP }
     *     
     */
    public TagMAP getArg5() {
        return arg5;
    }

    /**
     * Sets the value of the arg5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TagMAP }
     *     
     */
    public void setArg5(TagMAP value) {
        this.arg5 = value;
    }

    /**
     * Gets the value of the arg6 property.
     * 
     * @return
     *     possible object is
     *     {@link MerchantUserSet }
     *     
     */
    public MerchantUserSet getArg6() {
        return arg6;
    }

    /**
     * Sets the value of the arg6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MerchantUserSet }
     *     
     */
    public void setArg6(MerchantUserSet value) {
        this.arg6 = value;
    }

}
