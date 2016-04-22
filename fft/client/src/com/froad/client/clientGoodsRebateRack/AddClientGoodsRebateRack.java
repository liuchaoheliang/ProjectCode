
package com.froad.client.clientGoodsRebateRack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addClientGoodsRebateRack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addClientGoodsRebateRack">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://service.CB.froad.com/}clientGoodsRebateRack" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addClientGoodsRebateRack", propOrder = {
    "arg0"
})
public class AddClientGoodsRebateRack {

    protected ClientGoodsRebateRack arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link ClientGoodsRebateRack }
     *     
     */
    public ClientGoodsRebateRack getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientGoodsRebateRack }
     *     
     */
    public void setArg0(ClientGoodsRebateRack value) {
        this.arg0 = value;
    }

}
