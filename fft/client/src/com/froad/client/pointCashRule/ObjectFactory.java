
package com.froad.client.pointCashRule;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.pointCashRule package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAllPointCashRule_QNAME = new QName("http://service.CB.froad.com/", "getAllPointCashRule");
    private final static QName _GetAllPointCashRuleResponse_QNAME = new QName("http://service.CB.froad.com/", "getAllPointCashRuleResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.pointCashRule
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllPointCashRule }
     * 
     */
    public GetAllPointCashRule createGetAllPointCashRule() {
        return new GetAllPointCashRule();
    }

    /**
     * Create an instance of {@link GetAllPointCashRuleResponse }
     * 
     */
    public GetAllPointCashRuleResponse createGetAllPointCashRuleResponse() {
        return new GetAllPointCashRuleResponse();
    }

    /**
     * Create an instance of {@link PointCashRule }
     * 
     */
    public PointCashRule createPointCashRule() {
        return new PointCashRule();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPointCashRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllPointCashRule")
    public JAXBElement<GetAllPointCashRule> createGetAllPointCashRule(GetAllPointCashRule value) {
        return new JAXBElement<GetAllPointCashRule>(_GetAllPointCashRule_QNAME, GetAllPointCashRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPointCashRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllPointCashRuleResponse")
    public JAXBElement<GetAllPointCashRuleResponse> createGetAllPointCashRuleResponse(GetAllPointCashRuleResponse value) {
        return new JAXBElement<GetAllPointCashRuleResponse>(_GetAllPointCashRuleResponse_QNAME, GetAllPointCashRuleResponse.class, null, value);
    }

}
