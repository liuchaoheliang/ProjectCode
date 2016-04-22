
package com.froad.client.presellDeliveryMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for presellDeliveryMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="presellDeliveryMap">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.CB.froad.com/}pager">
 *       &lt;sequence>
 *         &lt;element name="deliveryId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="goodsPresellRack" type="{http://service.CB.froad.com/}goodsPresellRack" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="presellDelivery" type="{http://service.CB.froad.com/}presellDelivery" minOccurs="0"/>
 *         &lt;element name="presellRackId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "presellDeliveryMap", propOrder = {
    "deliveryId",
    "goodsPresellRack",
    "id",
    "presellDelivery",
    "presellRackId"
})
public class PresellDeliveryMap
    extends Pager
{

    protected Integer deliveryId;
    protected GoodsPresellRack goodsPresellRack;
    protected Integer id;
    protected PresellDelivery presellDelivery;
    protected Integer presellRackId;

    /**
     * Gets the value of the deliveryId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeliveryId() {
        return deliveryId;
    }

    /**
     * Sets the value of the deliveryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeliveryId(Integer value) {
        this.deliveryId = value;
    }

    /**
     * Gets the value of the goodsPresellRack property.
     * 
     * @return
     *     possible object is
     *     {@link GoodsPresellRack }
     *     
     */
    public GoodsPresellRack getGoodsPresellRack() {
        return goodsPresellRack;
    }

    /**
     * Sets the value of the goodsPresellRack property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoodsPresellRack }
     *     
     */
    public void setGoodsPresellRack(GoodsPresellRack value) {
        this.goodsPresellRack = value;
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
     * Gets the value of the presellDelivery property.
     * 
     * @return
     *     possible object is
     *     {@link PresellDelivery }
     *     
     */
    public PresellDelivery getPresellDelivery() {
        return presellDelivery;
    }

    /**
     * Sets the value of the presellDelivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link PresellDelivery }
     *     
     */
    public void setPresellDelivery(PresellDelivery value) {
        this.presellDelivery = value;
    }

    /**
     * Gets the value of the presellRackId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPresellRackId() {
        return presellRackId;
    }

    /**
     * Sets the value of the presellRackId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPresellRackId(Integer value) {
        this.presellRackId = value;
    }

}
