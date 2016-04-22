
package com.froad.client.clientGoodsExchangeRack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getClientGoodsExchangeRack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getClientGoodsExchangeRack">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://service.CB.froad.com/}clientGoodsExchangeRack" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getClientGoodsExchangeRack", propOrder = {
    "arg0"
})
public class GetClientGoodsExchangeRack {

    protected ClientGoodsExchangeRack arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link ClientGoodsExchangeRack }
     *     
     */
    public ClientGoodsExchangeRack getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientGoodsExchangeRack }
     *     
     */
    public void setArg0(ClientGoodsExchangeRack value) {
        this.arg0 = value;
    }

}
