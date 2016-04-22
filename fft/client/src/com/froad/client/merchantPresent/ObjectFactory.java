
package com.froad.client.merchantPresent;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.merchantPresent package. 
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

    private final static QName _AddMerchantPresent_QNAME = new QName("http://service.CB.froad.com/", "addMerchantPresent");
    private final static QName _AddMerchantPresentResponse_QNAME = new QName("http://service.CB.froad.com/", "addMerchantPresentResponse");
    private final static QName _GetMerchantPresentByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPresentByMerchantIdResponse");
    private final static QName _GetMerchantPresentByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPresentByUserIdResponse");
    private final static QName _UpdateByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "updateByMerchantId");
    private final static QName _UpdMerchantPresent_QNAME = new QName("http://service.CB.froad.com/", "updMerchantPresent");
    private final static QName _UpdMerchantPresentResponse_QNAME = new QName("http://service.CB.froad.com/", "updMerchantPresentResponse");
    private final static QName _GetMerchantPresentByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPresentByMerchantId");
    private final static QName _UpdateByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByMerchantIdResponse");
    private final static QName _GetMerchantPresentByUserId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPresentByUserId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.merchantPresent
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMerchantPresentByUserIdResponse }
     * 
     */
    public GetMerchantPresentByUserIdResponse createGetMerchantPresentByUserIdResponse() {
        return new GetMerchantPresentByUserIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPresentByMerchantIdResponse }
     * 
     */
    public GetMerchantPresentByMerchantIdResponse createGetMerchantPresentByMerchantIdResponse() {
        return new GetMerchantPresentByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link AddMerchantPresentResponse }
     * 
     */
    public AddMerchantPresentResponse createAddMerchantPresentResponse() {
        return new AddMerchantPresentResponse();
    }

    /**
     * Create an instance of {@link AddMerchantPresent }
     * 
     */
    public AddMerchantPresent createAddMerchantPresent() {
        return new AddMerchantPresent();
    }

    /**
     * Create an instance of {@link GetMerchantPresentByUserId }
     * 
     */
    public GetMerchantPresentByUserId createGetMerchantPresentByUserId() {
        return new GetMerchantPresentByUserId();
    }

    /**
     * Create an instance of {@link UpdateByMerchantIdResponse }
     * 
     */
    public UpdateByMerchantIdResponse createUpdateByMerchantIdResponse() {
        return new UpdateByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPresentByMerchantId }
     * 
     */
    public GetMerchantPresentByMerchantId createGetMerchantPresentByMerchantId() {
        return new GetMerchantPresentByMerchantId();
    }

    /**
     * Create an instance of {@link UpdMerchantPresentResponse }
     * 
     */
    public UpdMerchantPresentResponse createUpdMerchantPresentResponse() {
        return new UpdMerchantPresentResponse();
    }

    /**
     * Create an instance of {@link UpdMerchantPresent }
     * 
     */
    public UpdMerchantPresent createUpdMerchantPresent() {
        return new UpdMerchantPresent();
    }

    /**
     * Create an instance of {@link UpdateByMerchantId }
     * 
     */
    public UpdateByMerchantId createUpdateByMerchantId() {
        return new UpdateByMerchantId();
    }

    /**
     * Create an instance of {@link MerchantPresent }
     * 
     */
    public MerchantPresent createMerchantPresent() {
        return new MerchantPresent();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantPresent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantPresent")
    public JAXBElement<AddMerchantPresent> createAddMerchantPresent(AddMerchantPresent value) {
        return new JAXBElement<AddMerchantPresent>(_AddMerchantPresent_QNAME, AddMerchantPresent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantPresentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantPresentResponse")
    public JAXBElement<AddMerchantPresentResponse> createAddMerchantPresentResponse(AddMerchantPresentResponse value) {
        return new JAXBElement<AddMerchantPresentResponse>(_AddMerchantPresentResponse_QNAME, AddMerchantPresentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPresentByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPresentByMerchantIdResponse")
    public JAXBElement<GetMerchantPresentByMerchantIdResponse> createGetMerchantPresentByMerchantIdResponse(GetMerchantPresentByMerchantIdResponse value) {
        return new JAXBElement<GetMerchantPresentByMerchantIdResponse>(_GetMerchantPresentByMerchantIdResponse_QNAME, GetMerchantPresentByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPresentByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPresentByUserIdResponse")
    public JAXBElement<GetMerchantPresentByUserIdResponse> createGetMerchantPresentByUserIdResponse(GetMerchantPresentByUserIdResponse value) {
        return new JAXBElement<GetMerchantPresentByUserIdResponse>(_GetMerchantPresentByUserIdResponse_QNAME, GetMerchantPresentByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByMerchantId")
    public JAXBElement<UpdateByMerchantId> createUpdateByMerchantId(UpdateByMerchantId value) {
        return new JAXBElement<UpdateByMerchantId>(_UpdateByMerchantId_QNAME, UpdateByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdMerchantPresent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updMerchantPresent")
    public JAXBElement<UpdMerchantPresent> createUpdMerchantPresent(UpdMerchantPresent value) {
        return new JAXBElement<UpdMerchantPresent>(_UpdMerchantPresent_QNAME, UpdMerchantPresent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdMerchantPresentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updMerchantPresentResponse")
    public JAXBElement<UpdMerchantPresentResponse> createUpdMerchantPresentResponse(UpdMerchantPresentResponse value) {
        return new JAXBElement<UpdMerchantPresentResponse>(_UpdMerchantPresentResponse_QNAME, UpdMerchantPresentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPresentByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPresentByMerchantId")
    public JAXBElement<GetMerchantPresentByMerchantId> createGetMerchantPresentByMerchantId(GetMerchantPresentByMerchantId value) {
        return new JAXBElement<GetMerchantPresentByMerchantId>(_GetMerchantPresentByMerchantId_QNAME, GetMerchantPresentByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByMerchantIdResponse")
    public JAXBElement<UpdateByMerchantIdResponse> createUpdateByMerchantIdResponse(UpdateByMerchantIdResponse value) {
        return new JAXBElement<UpdateByMerchantIdResponse>(_UpdateByMerchantIdResponse_QNAME, UpdateByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPresentByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPresentByUserId")
    public JAXBElement<GetMerchantPresentByUserId> createGetMerchantPresentByUserId(GetMerchantPresentByUserId value) {
        return new JAXBElement<GetMerchantPresentByUserId>(_GetMerchantPresentByUserId_QNAME, GetMerchantPresentByUserId.class, null, value);
    }

}
