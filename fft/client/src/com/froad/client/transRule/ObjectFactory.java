
package com.froad.client.transRule;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.transRule package. 
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

    private final static QName _UpdateTransRuleResponse_QNAME = new QName("http://service.CB.froad.com/", "updateTransRuleResponse");
    private final static QName _UpdateTranRuleDetailResponse_QNAME = new QName("http://service.CB.froad.com/", "updateTranRuleDetailResponse");
    private final static QName _AddTranRuleDetailResponse_QNAME = new QName("http://service.CB.froad.com/", "addTranRuleDetailResponse");
    private final static QName _GetTransRulesResponse_QNAME = new QName("http://service.CB.froad.com/", "getTransRulesResponse");
    private final static QName _UpdateTranRuleDetail_QNAME = new QName("http://service.CB.froad.com/", "updateTranRuleDetail");
    private final static QName _AddTranRuleDetail_QNAME = new QName("http://service.CB.froad.com/", "addTranRuleDetail");
    private final static QName _AddTransRuleResponse_QNAME = new QName("http://service.CB.froad.com/", "addTransRuleResponse");
    private final static QName _UpdateTransRule_QNAME = new QName("http://service.CB.froad.com/", "updateTransRule");
    private final static QName _AddTransRule_QNAME = new QName("http://service.CB.froad.com/", "addTransRule");
    private final static QName _GetTransRules_QNAME = new QName("http://service.CB.froad.com/", "getTransRules");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.transRule
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateTranRuleDetail }
     * 
     */
    public UpdateTranRuleDetail createUpdateTranRuleDetail() {
        return new UpdateTranRuleDetail();
    }

    /**
     * Create an instance of {@link GetTransRulesResponse }
     * 
     */
    public GetTransRulesResponse createGetTransRulesResponse() {
        return new GetTransRulesResponse();
    }

    /**
     * Create an instance of {@link AddTranRuleDetailResponse }
     * 
     */
    public AddTranRuleDetailResponse createAddTranRuleDetailResponse() {
        return new AddTranRuleDetailResponse();
    }

    /**
     * Create an instance of {@link UpdateTranRuleDetailResponse }
     * 
     */
    public UpdateTranRuleDetailResponse createUpdateTranRuleDetailResponse() {
        return new UpdateTranRuleDetailResponse();
    }

    /**
     * Create an instance of {@link UpdateTransRuleResponse }
     * 
     */
    public UpdateTransRuleResponse createUpdateTransRuleResponse() {
        return new UpdateTransRuleResponse();
    }

    /**
     * Create an instance of {@link GetTransRules }
     * 
     */
    public GetTransRules createGetTransRules() {
        return new GetTransRules();
    }

    /**
     * Create an instance of {@link AddTransRule }
     * 
     */
    public AddTransRule createAddTransRule() {
        return new AddTransRule();
    }

    /**
     * Create an instance of {@link UpdateTransRule }
     * 
     */
    public UpdateTransRule createUpdateTransRule() {
        return new UpdateTransRule();
    }

    /**
     * Create an instance of {@link AddTransRuleResponse }
     * 
     */
    public AddTransRuleResponse createAddTransRuleResponse() {
        return new AddTransRuleResponse();
    }

    /**
     * Create an instance of {@link AddTranRuleDetail }
     * 
     */
    public AddTranRuleDetail createAddTranRuleDetail() {
        return new AddTranRuleDetail();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link RuleDetail }
     * 
     */
    public RuleDetail createRuleDetail() {
        return new RuleDetail();
    }

    /**
     * Create an instance of {@link TransRule }
     * 
     */
    public TransRule createTransRule() {
        return new TransRule();
    }

    /**
     * Create an instance of {@link RuleDetailTemplet }
     * 
     */
    public RuleDetailTemplet createRuleDetailTemplet() {
        return new RuleDetailTemplet();
    }

    /**
     * Create an instance of {@link Rule }
     * 
     */
    public Rule createRule() {
        return new Rule();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTransRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateTransRuleResponse")
    public JAXBElement<UpdateTransRuleResponse> createUpdateTransRuleResponse(UpdateTransRuleResponse value) {
        return new JAXBElement<UpdateTransRuleResponse>(_UpdateTransRuleResponse_QNAME, UpdateTransRuleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTranRuleDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateTranRuleDetailResponse")
    public JAXBElement<UpdateTranRuleDetailResponse> createUpdateTranRuleDetailResponse(UpdateTranRuleDetailResponse value) {
        return new JAXBElement<UpdateTranRuleDetailResponse>(_UpdateTranRuleDetailResponse_QNAME, UpdateTranRuleDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTranRuleDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addTranRuleDetailResponse")
    public JAXBElement<AddTranRuleDetailResponse> createAddTranRuleDetailResponse(AddTranRuleDetailResponse value) {
        return new JAXBElement<AddTranRuleDetailResponse>(_AddTranRuleDetailResponse_QNAME, AddTranRuleDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransRulesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTransRulesResponse")
    public JAXBElement<GetTransRulesResponse> createGetTransRulesResponse(GetTransRulesResponse value) {
        return new JAXBElement<GetTransRulesResponse>(_GetTransRulesResponse_QNAME, GetTransRulesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTranRuleDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateTranRuleDetail")
    public JAXBElement<UpdateTranRuleDetail> createUpdateTranRuleDetail(UpdateTranRuleDetail value) {
        return new JAXBElement<UpdateTranRuleDetail>(_UpdateTranRuleDetail_QNAME, UpdateTranRuleDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTranRuleDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addTranRuleDetail")
    public JAXBElement<AddTranRuleDetail> createAddTranRuleDetail(AddTranRuleDetail value) {
        return new JAXBElement<AddTranRuleDetail>(_AddTranRuleDetail_QNAME, AddTranRuleDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTransRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addTransRuleResponse")
    public JAXBElement<AddTransRuleResponse> createAddTransRuleResponse(AddTransRuleResponse value) {
        return new JAXBElement<AddTransRuleResponse>(_AddTransRuleResponse_QNAME, AddTransRuleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTransRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateTransRule")
    public JAXBElement<UpdateTransRule> createUpdateTransRule(UpdateTransRule value) {
        return new JAXBElement<UpdateTransRule>(_UpdateTransRule_QNAME, UpdateTransRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddTransRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addTransRule")
    public JAXBElement<AddTransRule> createAddTransRule(AddTransRule value) {
        return new JAXBElement<AddTransRule>(_AddTransRule_QNAME, AddTransRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransRules }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTransRules")
    public JAXBElement<GetTransRules> createGetTransRules(GetTransRules value) {
        return new JAXBElement<GetTransRules>(_GetTransRules_QNAME, GetTransRules.class, null, value);
    }

}
