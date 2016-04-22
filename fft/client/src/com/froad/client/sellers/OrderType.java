
package com.froad.client.sellers;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for orderType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="orderType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="asc"/>
 *     &lt;enumeration value="desc"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "orderType")
@XmlEnum
public enum OrderType {

    @XmlEnumValue("asc")
    ASC("asc"),
    @XmlEnumValue("desc")
    DESC("desc");
    private final String value;

    OrderType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrderType fromValue(String v) {
        for (OrderType c: OrderType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
