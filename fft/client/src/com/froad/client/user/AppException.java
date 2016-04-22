
package com.froad.client.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contextMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cusMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppException", propOrder = {
    "contextMsg",
    "cusMsg",
    "errMsg",
    "errType"
})
public class AppException {

    @XmlElement(required = true, nillable = true)
    protected String contextMsg;
    @XmlElement(required = true, nillable = true)
    protected String cusMsg;
    @XmlElement(required = true, nillable = true)
    protected String errMsg;
    @XmlElement(required = true, nillable = true)
    protected String errType;

    /**
     * Gets the value of the contextMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContextMsg() {
        return contextMsg;
    }

    /**
     * Sets the value of the contextMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContextMsg(String value) {
        this.contextMsg = value;
    }

    /**
     * Gets the value of the cusMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCusMsg() {
        return cusMsg;
    }

    /**
     * Sets the value of the cusMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCusMsg(String value) {
        this.cusMsg = value;
    }

    /**
     * Gets the value of the errMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * Sets the value of the errMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrMsg(String value) {
        this.errMsg = value;
    }

    /**
     * Gets the value of the errType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrType() {
        return errType;
    }

    /**
     * Sets the value of the errType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrType(String value) {
        this.errType = value;
    }

}
