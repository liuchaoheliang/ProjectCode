
package com.froad.client.agreement;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.agreement package. 
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

    private final static QName _AddAgreement_QNAME = new QName("http://service.CB.froad.com/", "addAgreement");
    private final static QName _UpdateAgreement_QNAME = new QName("http://service.CB.froad.com/", "updateAgreement");
    private final static QName _GetAgreementByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getAgreementByIdResponse");
    private final static QName _GetAgreementResponse_QNAME = new QName("http://service.CB.froad.com/", "getAgreementResponse");
    private final static QName _GetAgreementById_QNAME = new QName("http://service.CB.froad.com/", "getAgreementById");
    private final static QName _UpdateAgreementResponse_QNAME = new QName("http://service.CB.froad.com/", "updateAgreementResponse");
    private final static QName _AddAgreementResponse_QNAME = new QName("http://service.CB.froad.com/", "addAgreementResponse");
    private final static QName _GetAgreement_QNAME = new QName("http://service.CB.froad.com/", "getAgreement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.agreement
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAgreementResponse }
     * 
     */
    public GetAgreementResponse createGetAgreementResponse() {
        return new GetAgreementResponse();
    }

    /**
     * Create an instance of {@link GetAgreementByIdResponse }
     * 
     */
    public GetAgreementByIdResponse createGetAgreementByIdResponse() {
        return new GetAgreementByIdResponse();
    }

    /**
     * Create an instance of {@link UpdateAgreement }
     * 
     */
    public UpdateAgreement createUpdateAgreement() {
        return new UpdateAgreement();
    }

    /**
     * Create an instance of {@link AddAgreement }
     * 
     */
    public AddAgreement createAddAgreement() {
        return new AddAgreement();
    }

    /**
     * Create an instance of {@link GetAgreement }
     * 
     */
    public GetAgreement createGetAgreement() {
        return new GetAgreement();
    }

    /**
     * Create an instance of {@link AddAgreementResponse }
     * 
     */
    public AddAgreementResponse createAddAgreementResponse() {
        return new AddAgreementResponse();
    }

    /**
     * Create an instance of {@link UpdateAgreementResponse }
     * 
     */
    public UpdateAgreementResponse createUpdateAgreementResponse() {
        return new UpdateAgreementResponse();
    }

    /**
     * Create an instance of {@link GetAgreementById }
     * 
     */
    public GetAgreementById createGetAgreementById() {
        return new GetAgreementById();
    }

    /**
     * Create an instance of {@link Agreement }
     * 
     */
    public Agreement createAgreement() {
        return new Agreement();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAgreement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addAgreement")
    public JAXBElement<AddAgreement> createAddAgreement(AddAgreement value) {
        return new JAXBElement<AddAgreement>(_AddAgreement_QNAME, AddAgreement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAgreement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateAgreement")
    public JAXBElement<UpdateAgreement> createUpdateAgreement(UpdateAgreement value) {
        return new JAXBElement<UpdateAgreement>(_UpdateAgreement_QNAME, UpdateAgreement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgreementByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAgreementByIdResponse")
    public JAXBElement<GetAgreementByIdResponse> createGetAgreementByIdResponse(GetAgreementByIdResponse value) {
        return new JAXBElement<GetAgreementByIdResponse>(_GetAgreementByIdResponse_QNAME, GetAgreementByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgreementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAgreementResponse")
    public JAXBElement<GetAgreementResponse> createGetAgreementResponse(GetAgreementResponse value) {
        return new JAXBElement<GetAgreementResponse>(_GetAgreementResponse_QNAME, GetAgreementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgreementById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAgreementById")
    public JAXBElement<GetAgreementById> createGetAgreementById(GetAgreementById value) {
        return new JAXBElement<GetAgreementById>(_GetAgreementById_QNAME, GetAgreementById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAgreementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateAgreementResponse")
    public JAXBElement<UpdateAgreementResponse> createUpdateAgreementResponse(UpdateAgreementResponse value) {
        return new JAXBElement<UpdateAgreementResponse>(_UpdateAgreementResponse_QNAME, UpdateAgreementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAgreementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addAgreementResponse")
    public JAXBElement<AddAgreementResponse> createAddAgreementResponse(AddAgreementResponse value) {
        return new JAXBElement<AddAgreementResponse>(_AddAgreementResponse_QNAME, AddAgreementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgreement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAgreement")
    public JAXBElement<GetAgreement> createGetAgreement(GetAgreement value) {
        return new JAXBElement<GetAgreement>(_GetAgreement_QNAME, GetAgreement.class, null, value);
    }

}
