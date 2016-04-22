
package com.froad.client.lottery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="k" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kssj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="z" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ct", propOrder = {
    "cc",
    "k",
    "kd",
    "kssj",
    "ss",
    "z",
    "zd"
})
public class Ct {

    protected String cc;
    protected String k;
    protected String kd;
    protected String kssj;
    protected String ss;
    protected String z;
    protected String zd;

    /**
     * Gets the value of the cc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCc() {
        return cc;
    }

    /**
     * Sets the value of the cc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCc(String value) {
        this.cc = value;
    }

    /**
     * Gets the value of the k property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getK() {
        return k;
    }

    /**
     * Sets the value of the k property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setK(String value) {
        this.k = value;
    }

    /**
     * Gets the value of the kd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKd() {
        return kd;
    }

    /**
     * Sets the value of the kd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKd(String value) {
        this.kd = value;
    }

    /**
     * Gets the value of the kssj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKssj() {
        return kssj;
    }

    /**
     * Sets the value of the kssj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKssj(String value) {
        this.kssj = value;
    }

    /**
     * Gets the value of the ss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSs() {
        return ss;
    }

    /**
     * Sets the value of the ss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSs(String value) {
        this.ss = value;
    }

    /**
     * Gets the value of the z property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZ() {
        return z;
    }

    /**
     * Sets the value of the z property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZ(String value) {
        this.z = value;
    }

    /**
     * Gets the value of the zd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZd() {
        return zd;
    }

    /**
     * Sets the value of the zd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZd(String value) {
        this.zd = value;
    }

}
