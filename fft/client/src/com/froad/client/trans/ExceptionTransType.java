
package com.froad.client.trans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for exceptionTransType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="exceptionTransType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL_REFUND_POINTS"/>
 *     &lt;enumeration value="REFUND_POINTS"/>
 *     &lt;enumeration value="REFUND_TIMEOUT_POINTS"/>
 *     &lt;enumeration value="REFUND"/>
 *     &lt;enumeration value="DEDUCT_REBATE"/>
 *     &lt;enumeration value="REBATE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "exceptionTransType")
@XmlEnum
public enum ExceptionTransType {

    ALL_REFUND_POINTS,
    REFUND_POINTS,
    REFUND_TIMEOUT_POINTS,
    REFUND,
    DEDUCT_REBATE,
    REBATE;

    public String value() {
        return name();
    }

    public static ExceptionTransType fromValue(String v) {
        return valueOf(v);
    }

}
