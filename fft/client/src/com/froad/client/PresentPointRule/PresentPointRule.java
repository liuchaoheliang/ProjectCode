
package com.froad.client.PresentPointRule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for presentPointRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="presentPointRule">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="activeTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expireTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodExchangeRackId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="pointValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "presentPointRule", propOrder = {
    "activeTime",
    "createTime",
    "expireTime",
    "goodExchangeRackId",
    "goodName",
    "id",
    "pointValue",
    "state",
    "updateTime"
})
public class PresentPointRule
    extends Pager
{

    protected String activeTime;
    protected String createTime;
    protected String expireTime;
    protected String goodExchangeRackId;
    protected String goodName;
    protected Integer id;
    protected String pointValue;
    protected Integer state;
    protected String updateTime;

    /**
     * Gets the value of the activeTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveTime() {
        return activeTime;
    }

    /**
     * Sets the value of the activeTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveTime(String value) {
        this.activeTime = value;
    }

    /**
     * Gets the value of the createTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * Sets the value of the createTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateTime(String value) {
        this.createTime = value;
    }

    /**
     * Gets the value of the expireTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * Sets the value of the expireTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpireTime(String value) {
        this.expireTime = value;
    }

    /**
     * Gets the value of the goodExchangeRackId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodExchangeRackId() {
        return goodExchangeRackId;
    }

    /**
     * Sets the value of the goodExchangeRackId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodExchangeRackId(String value) {
        this.goodExchangeRackId = value;
    }

    /**
     * Gets the value of the goodName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * Sets the value of the goodName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodName(String value) {
        this.goodName = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the pointValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointValue() {
        return pointValue;
    }

    /**
     * Sets the value of the pointValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointValue(String value) {
        this.pointValue = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setState(Integer value) {
        this.state = value;
    }

    /**
     * Gets the value of the updateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the value of the updateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateTime(String value) {
        this.updateTime = value;
    }

}
