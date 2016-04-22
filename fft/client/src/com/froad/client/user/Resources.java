
package com.froad.client.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resources complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resources">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="p_resources_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="p_resources_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resources_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resources_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resources_value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resources", propOrder = {
    "pResourcesId",
    "pResourcesName",
    "resourcesId",
    "resourcesName",
    "resourcesValue"
})
public class Resources {

    @XmlElement(name = "p_resources_id")
    protected String pResourcesId;
    @XmlElement(name = "p_resources_name")
    protected String pResourcesName;
    @XmlElement(name = "resources_id")
    protected String resourcesId;
    @XmlElement(name = "resources_name")
    protected String resourcesName;
    @XmlElement(name = "resources_value")
    protected String resourcesValue;

    /**
     * Gets the value of the pResourcesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPResourcesId() {
        return pResourcesId;
    }

    /**
     * Sets the value of the pResourcesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPResourcesId(String value) {
        this.pResourcesId = value;
    }

    /**
     * Gets the value of the pResourcesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPResourcesName() {
        return pResourcesName;
    }

    /**
     * Sets the value of the pResourcesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPResourcesName(String value) {
        this.pResourcesName = value;
    }

    /**
     * Gets the value of the resourcesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourcesId() {
        return resourcesId;
    }

    /**
     * Sets the value of the resourcesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourcesId(String value) {
        this.resourcesId = value;
    }

    /**
     * Gets the value of the resourcesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourcesName() {
        return resourcesName;
    }

    /**
     * Sets the value of the resourcesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourcesName(String value) {
        this.resourcesName = value;
    }

    /**
     * Gets the value of the resourcesValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourcesValue() {
        return resourcesValue;
    }

    /**
     * Sets the value of the resourcesValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourcesValue(String value) {
        this.resourcesValue = value;
    }

}
