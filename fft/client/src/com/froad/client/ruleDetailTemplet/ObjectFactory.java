
package com.froad.client.ruleDetailTemplet;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.ruleDetailTemplet package. 
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

    private final static QName _GetRuleDetailTempletsResponse_QNAME = new QName("http://service.CB.froad.com/", "getRuleDetailTempletsResponse");
    private final static QName _UpdateRuleDetailTemplet_QNAME = new QName("http://service.CB.froad.com/", "updateRuleDetailTemplet");
    private final static QName _AddRuleDetailTempletResponse_QNAME = new QName("http://service.CB.froad.com/", "addRuleDetailTempletResponse");
    private final static QName _GetRuleDetailTemplets_QNAME = new QName("http://service.CB.froad.com/", "getRuleDetailTemplets");
    private final static QName _AddRuleDetailTemplet_QNAME = new QName("http://service.CB.froad.com/", "addRuleDetailTemplet");
    private final static QName _UpdateRuleDetailTempletResponse_QNAME = new QName("http://service.CB.froad.com/", "updateRuleDetailTempletResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.ruleDetailTemplet
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRuleDetailTemplets }
     * 
     */
    public GetRuleDetailTemplets createGetRuleDetailTemplets() {
        return new GetRuleDetailTemplets();
    }

    /**
     * Create an instance of {@link AddRuleDetailTempletResponse }
     * 
     */
    public AddRuleDetailTempletResponse createAddRuleDetailTempletResponse() {
        return new AddRuleDetailTempletResponse();
    }

    /**
     * Create an instance of {@link UpdateRuleDetailTemplet }
     * 
     */
    public UpdateRuleDetailTemplet createUpdateRuleDetailTemplet() {
        return new UpdateRuleDetailTemplet();
    }

    /**
     * Create an instance of {@link GetRuleDetailTempletsResponse }
     * 
     */
    public GetRuleDetailTempletsResponse createGetRuleDetailTempletsResponse() {
        return new GetRuleDetailTempletsResponse();
    }

    /**
     * Create an instance of {@link UpdateRuleDetailTempletResponse }
     * 
     */
    public UpdateRuleDetailTempletResponse createUpdateRuleDetailTempletResponse() {
        return new UpdateRuleDetailTempletResponse();
    }

    /**
     * Create an instance of {@link AddRuleDetailTemplet }
     * 
     */
    public AddRuleDetailTemplet createAddRuleDetailTemplet() {
        return new AddRuleDetailTemplet();
    }

    /**
     * Create an instance of {@link RuleDetailTemplet }
     * 
     */
    public RuleDetailTemplet createRuleDetailTemplet() {
        return new RuleDetailTemplet();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRuleDetailTempletsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getRuleDetailTempletsResponse")
    public JAXBElement<GetRuleDetailTempletsResponse> createGetRuleDetailTempletsResponse(GetRuleDetailTempletsResponse value) {
        return new JAXBElement<GetRuleDetailTempletsResponse>(_GetRuleDetailTempletsResponse_QNAME, GetRuleDetailTempletsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRuleDetailTemplet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateRuleDetailTemplet")
    public JAXBElement<UpdateRuleDetailTemplet> createUpdateRuleDetailTemplet(UpdateRuleDetailTemplet value) {
        return new JAXBElement<UpdateRuleDetailTemplet>(_UpdateRuleDetailTemplet_QNAME, UpdateRuleDetailTemplet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRuleDetailTempletResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addRuleDetailTempletResponse")
    public JAXBElement<AddRuleDetailTempletResponse> createAddRuleDetailTempletResponse(AddRuleDetailTempletResponse value) {
        return new JAXBElement<AddRuleDetailTempletResponse>(_AddRuleDetailTempletResponse_QNAME, AddRuleDetailTempletResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRuleDetailTemplets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getRuleDetailTemplets")
    public JAXBElement<GetRuleDetailTemplets> createGetRuleDetailTemplets(GetRuleDetailTemplets value) {
        return new JAXBElement<GetRuleDetailTemplets>(_GetRuleDetailTemplets_QNAME, GetRuleDetailTemplets.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRuleDetailTemplet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addRuleDetailTemplet")
    public JAXBElement<AddRuleDetailTemplet> createAddRuleDetailTemplet(AddRuleDetailTemplet value) {
        return new JAXBElement<AddRuleDetailTemplet>(_AddRuleDetailTemplet_QNAME, AddRuleDetailTemplet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRuleDetailTempletResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateRuleDetailTempletResponse")
    public JAXBElement<UpdateRuleDetailTempletResponse> createUpdateRuleDetailTempletResponse(UpdateRuleDetailTempletResponse value) {
        return new JAXBElement<UpdateRuleDetailTempletResponse>(_UpdateRuleDetailTempletResponse_QNAME, UpdateRuleDetailTempletResponse.class, null, value);
    }

}
