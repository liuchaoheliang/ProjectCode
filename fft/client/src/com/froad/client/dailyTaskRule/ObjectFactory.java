
package com.froad.client.dailyTaskRule;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.dailyTaskRule package. 
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

    private final static QName _GetDailyTaskRuleByPrimaryId_QNAME = new QName("http://service.CB.froad.com/", "getDailyTaskRuleByPrimaryId");
    private final static QName _GetDailyTaskRuleByPrimaryIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getDailyTaskRuleByPrimaryIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.dailyTaskRule
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDailyTaskRuleByPrimaryId }
     * 
     */
    public GetDailyTaskRuleByPrimaryId createGetDailyTaskRuleByPrimaryId() {
        return new GetDailyTaskRuleByPrimaryId();
    }

    /**
     * Create an instance of {@link GetDailyTaskRuleByPrimaryIdResponse }
     * 
     */
    public GetDailyTaskRuleByPrimaryIdResponse createGetDailyTaskRuleByPrimaryIdResponse() {
        return new GetDailyTaskRuleByPrimaryIdResponse();
    }

    /**
     * Create an instance of {@link DailyTaskRule }
     * 
     */
    public DailyTaskRule createDailyTaskRule() {
        return new DailyTaskRule();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDailyTaskRuleByPrimaryId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getDailyTaskRuleByPrimaryId")
    public JAXBElement<GetDailyTaskRuleByPrimaryId> createGetDailyTaskRuleByPrimaryId(GetDailyTaskRuleByPrimaryId value) {
        return new JAXBElement<GetDailyTaskRuleByPrimaryId>(_GetDailyTaskRuleByPrimaryId_QNAME, GetDailyTaskRuleByPrimaryId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDailyTaskRuleByPrimaryIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getDailyTaskRuleByPrimaryIdResponse")
    public JAXBElement<GetDailyTaskRuleByPrimaryIdResponse> createGetDailyTaskRuleByPrimaryIdResponse(GetDailyTaskRuleByPrimaryIdResponse value) {
        return new JAXBElement<GetDailyTaskRuleByPrimaryIdResponse>(_GetDailyTaskRuleByPrimaryIdResponse_QNAME, GetDailyTaskRuleByPrimaryIdResponse.class, null, value);
    }

}
