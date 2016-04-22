
package com.froad.client.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authorities complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="authorities">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authorities_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorities_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="p_authorities_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="p_authorities_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authorities", propOrder = {
    "authoritiesId",
    "authoritiesName",
    "pAuthoritiesId",
    "pAuthoritiesName"
})
public class Authorities {

    @XmlElement(name = "authorities_id")
    protected String authoritiesId;
    @XmlElement(name = "authorities_name")
    protected String authoritiesName;
    @XmlElement(name = "p_authorities_id")
    protected String pAuthoritiesId;
    @XmlElement(name = "p_authorities_name")
    protected String pAuthoritiesName;

    /**
     * Gets the value of the authoritiesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthoritiesId() {
        return authoritiesId;
    }

    /**
     * Sets the value of the authoritiesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthoritiesId(String value) {
        this.authoritiesId = value;
    }

    /**
     * Gets the value of the authoritiesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthoritiesName() {
        return authoritiesName;
    }

    /**
     * Sets the value of the authoritiesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthoritiesName(String value) {
        this.authoritiesName = value;
    }

    /**
     * Gets the value of the pAuthoritiesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAuthoritiesId() {
        return pAuthoritiesId;
    }

    /**
     * Sets the value of the pAuthoritiesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAuthoritiesId(String value) {
        this.pAuthoritiesId = value;
    }

    /**
     * Gets the value of the pAuthoritiesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAuthoritiesName() {
        return pAuthoritiesName;
    }

    /**
     * Sets the value of the pAuthoritiesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAuthoritiesName(String value) {
        this.pAuthoritiesName = value;
    }

}
