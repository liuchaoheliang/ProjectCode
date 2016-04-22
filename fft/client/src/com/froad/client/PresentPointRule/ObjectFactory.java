
package com.froad.client.PresentPointRule;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.PresentPointRule package. 
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

    private final static QName _AddPresentPointRuleResponse_QNAME = new QName("http://service.CB.froad.com/", "addPresentPointRuleResponse");
    private final static QName _GetByConditionsResponse_QNAME = new QName("http://service.CB.froad.com/", "getByConditionsResponse");
    private final static QName _GetPresentPointRuleByPager_QNAME = new QName("http://service.CB.froad.com/", "getPresentPointRuleByPager");
    private final static QName _GetPresentPointRuleByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getPresentPointRuleByPagerResponse");
    private final static QName _UpdatePresentPointRuleByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updatePresentPointRuleByIdResponse");
    private final static QName _GetByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getByIdResponse");
    private final static QName _GetByConditions_QNAME = new QName("http://service.CB.froad.com/", "getByConditions");
    private final static QName _GetById_QNAME = new QName("http://service.CB.froad.com/", "getById");
    private final static QName _AddPresentPointRule_QNAME = new QName("http://service.CB.froad.com/", "addPresentPointRule");
    private final static QName _UpdatePresentPointRuleById_QNAME = new QName("http://service.CB.froad.com/", "updatePresentPointRuleById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.PresentPointRule
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPresentPointRuleByPager }
     * 
     */
    public GetPresentPointRuleByPager createGetPresentPointRuleByPager() {
        return new GetPresentPointRuleByPager();
    }

    /**
     * Create an instance of {@link GetByConditionsResponse }
     * 
     */
    public GetByConditionsResponse createGetByConditionsResponse() {
        return new GetByConditionsResponse();
    }

    /**
     * Create an instance of {@link AddPresentPointRuleResponse }
     * 
     */
    public AddPresentPointRuleResponse createAddPresentPointRuleResponse() {
        return new AddPresentPointRuleResponse();
    }

    /**
     * Create an instance of {@link UpdatePresentPointRuleById }
     * 
     */
    public UpdatePresentPointRuleById createUpdatePresentPointRuleById() {
        return new UpdatePresentPointRuleById();
    }

    /**
     * Create an instance of {@link AddPresentPointRule }
     * 
     */
    public AddPresentPointRule createAddPresentPointRule() {
        return new AddPresentPointRule();
    }

    /**
     * Create an instance of {@link GetById }
     * 
     */
    public GetById createGetById() {
        return new GetById();
    }

    /**
     * Create an instance of {@link GetByConditions }
     * 
     */
    public GetByConditions createGetByConditions() {
        return new GetByConditions();
    }

    /**
     * Create an instance of {@link GetByIdResponse }
     * 
     */
    public GetByIdResponse createGetByIdResponse() {
        return new GetByIdResponse();
    }

    /**
     * Create an instance of {@link UpdatePresentPointRuleByIdResponse }
     * 
     */
    public UpdatePresentPointRuleByIdResponse createUpdatePresentPointRuleByIdResponse() {
        return new UpdatePresentPointRuleByIdResponse();
    }

    /**
     * Create an instance of {@link GetPresentPointRuleByPagerResponse }
     * 
     */
    public GetPresentPointRuleByPagerResponse createGetPresentPointRuleByPagerResponse() {
        return new GetPresentPointRuleByPagerResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link PresentPointRule }
     * 
     */
    public PresentPointRule createPresentPointRule() {
        return new PresentPointRule();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPresentPointRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addPresentPointRuleResponse")
    public JAXBElement<AddPresentPointRuleResponse> createAddPresentPointRuleResponse(AddPresentPointRuleResponse value) {
        return new JAXBElement<AddPresentPointRuleResponse>(_AddPresentPointRuleResponse_QNAME, AddPresentPointRuleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByConditionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByConditionsResponse")
    public JAXBElement<GetByConditionsResponse> createGetByConditionsResponse(GetByConditionsResponse value) {
        return new JAXBElement<GetByConditionsResponse>(_GetByConditionsResponse_QNAME, GetByConditionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPresentPointRuleByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPresentPointRuleByPager")
    public JAXBElement<GetPresentPointRuleByPager> createGetPresentPointRuleByPager(GetPresentPointRuleByPager value) {
        return new JAXBElement<GetPresentPointRuleByPager>(_GetPresentPointRuleByPager_QNAME, GetPresentPointRuleByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPresentPointRuleByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPresentPointRuleByPagerResponse")
    public JAXBElement<GetPresentPointRuleByPagerResponse> createGetPresentPointRuleByPagerResponse(GetPresentPointRuleByPagerResponse value) {
        return new JAXBElement<GetPresentPointRuleByPagerResponse>(_GetPresentPointRuleByPagerResponse_QNAME, GetPresentPointRuleByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePresentPointRuleByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updatePresentPointRuleByIdResponse")
    public JAXBElement<UpdatePresentPointRuleByIdResponse> createUpdatePresentPointRuleByIdResponse(UpdatePresentPointRuleByIdResponse value) {
        return new JAXBElement<UpdatePresentPointRuleByIdResponse>(_UpdatePresentPointRuleByIdResponse_QNAME, UpdatePresentPointRuleByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByIdResponse")
    public JAXBElement<GetByIdResponse> createGetByIdResponse(GetByIdResponse value) {
        return new JAXBElement<GetByIdResponse>(_GetByIdResponse_QNAME, GetByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByConditions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByConditions")
    public JAXBElement<GetByConditions> createGetByConditions(GetByConditions value) {
        return new JAXBElement<GetByConditions>(_GetByConditions_QNAME, GetByConditions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getById")
    public JAXBElement<GetById> createGetById(GetById value) {
        return new JAXBElement<GetById>(_GetById_QNAME, GetById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPresentPointRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addPresentPointRule")
    public JAXBElement<AddPresentPointRule> createAddPresentPointRule(AddPresentPointRule value) {
        return new JAXBElement<AddPresentPointRule>(_AddPresentPointRule_QNAME, AddPresentPointRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePresentPointRuleById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updatePresentPointRuleById")
    public JAXBElement<UpdatePresentPointRuleById> createUpdatePresentPointRuleById(UpdatePresentPointRuleById value) {
        return new JAXBElement<UpdatePresentPointRuleById>(_UpdatePresentPointRuleById_QNAME, UpdatePresentPointRuleById.class, null, value);
    }

}
