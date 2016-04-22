
package com.froad.client.point;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for withdrawPoints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="withdrawPoints">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cashPointsNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="points" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "withdrawPoints", propOrder = {
    "cashPointsNo",
    "objectDes",
    "objectNo",
    "objectType",
    "points"
})
public class WithdrawPoints {

    protected String cashPointsNo;
    protected String objectDes;
    protected String objectNo;
    protected String objectType;
    protected String points;

    /**
     * Gets the value of the cashPointsNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashPointsNo() {
        return cashPointsNo;
    }

    /**
     * Sets the value of the cashPointsNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashPointsNo(String value) {
        this.cashPointsNo = value;
    }

    /**
     * Gets the value of the objectDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectDes() {
        return objectDes;
    }

    /**
     * Sets the value of the objectDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectDes(String value) {
        this.objectDes = value;
    }

    /**
     * Gets the value of the objectNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectNo() {
        return objectNo;
    }

    /**
     * Sets the value of the objectNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectNo(String value) {
        this.objectNo = value;
    }

    /**
     * Gets the value of the objectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Sets the value of the objectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectType(String value) {
        this.objectType = value;
    }

    /**
     * Gets the value of the points property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoints() {
        return points;
    }

    /**
     * Sets the value of the points property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoints(String value) {
        this.points = value;
    }

}
