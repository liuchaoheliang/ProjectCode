
package com.froad.client.merchant;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for preferType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="preferType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="cash"/>
 *     &lt;enumeration value="points"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "preferType")
@XmlEnum
public enum PreferType {

    @XmlEnumValue("cash")
    CASH("cash"),
    @XmlEnumValue("points")
    POINTS("points");
    private final String value;

    PreferType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PreferType fromValue(String v) {
        for (PreferType c: PreferType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
