
package com.froad.client.clientGoodsCarryRack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addClientGoodsCarryRack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addClientGoodsCarryRack">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://service.CB.froad.com/}clientGoodsCarryRack" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addClientGoodsCarryRack", propOrder = {
    "arg0"
})
public class AddClientGoodsCarryRack {

    protected ClientGoodsCarryRack arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link ClientGoodsCarryRack }
     *     
     */
    public ClientGoodsCarryRack getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientGoodsCarryRack }
     *     
     */
    public void setArg0(ClientGoodsCarryRack value) {
        this.arg0 = value;
    }

}
