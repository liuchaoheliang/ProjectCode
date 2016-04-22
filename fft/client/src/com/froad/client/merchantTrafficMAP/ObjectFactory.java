
package com.froad.client.merchantTrafficMAP;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.merchantTrafficMAP package. 
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

    private final static QName _GetMerchantTrafficMapByUserId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficMapByUserId");
    private final static QName _AddMerchantTrafficMAPResponse_QNAME = new QName("http://service.CB.froad.com/", "addMerchantTrafficMAPResponse");
    private final static QName _GetMerchantTrafficMapByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficMapByUserIdResponse");
    private final static QName _AddMerchantTrafficMAP_QNAME = new QName("http://service.CB.froad.com/", "addMerchantTrafficMAP");
    private final static QName _UpdateMerchantTrafficMAPResponse_QNAME = new QName("http://service.CB.froad.com/", "updateMerchantTrafficMAPResponse");
    private final static QName _UpdateMerchantTrafficMAP_QNAME = new QName("http://service.CB.froad.com/", "updateMerchantTrafficMAP");
    private final static QName _GetMerchantTrafficMapByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficMapByMerchantIdResponse");
    private final static QName _GetMerchantTrafficMapByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficMapByMerchantId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.merchantTrafficMAP
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMerchantTrafficMapByUserIdResponse }
     * 
     */
    public GetMerchantTrafficMapByUserIdResponse createGetMerchantTrafficMapByUserIdResponse() {
        return new GetMerchantTrafficMapByUserIdResponse();
    }

    /**
     * Create an instance of {@link AddMerchantTrafficMAPResponse }
     * 
     */
    public AddMerchantTrafficMAPResponse createAddMerchantTrafficMAPResponse() {
        return new AddMerchantTrafficMAPResponse();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficMapByUserId }
     * 
     */
    public GetMerchantTrafficMapByUserId createGetMerchantTrafficMapByUserId() {
        return new GetMerchantTrafficMapByUserId();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficMapByMerchantId }
     * 
     */
    public GetMerchantTrafficMapByMerchantId createGetMerchantTrafficMapByMerchantId() {
        return new GetMerchantTrafficMapByMerchantId();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficMapByMerchantIdResponse }
     * 
     */
    public GetMerchantTrafficMapByMerchantIdResponse createGetMerchantTrafficMapByMerchantIdResponse() {
        return new GetMerchantTrafficMapByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link UpdateMerchantTrafficMAP }
     * 
     */
    public UpdateMerchantTrafficMAP createUpdateMerchantTrafficMAP() {
        return new UpdateMerchantTrafficMAP();
    }

    /**
     * Create an instance of {@link UpdateMerchantTrafficMAPResponse }
     * 
     */
    public UpdateMerchantTrafficMAPResponse createUpdateMerchantTrafficMAPResponse() {
        return new UpdateMerchantTrafficMAPResponse();
    }

    /**
     * Create an instance of {@link AddMerchantTrafficMAP }
     * 
     */
    public AddMerchantTrafficMAP createAddMerchantTrafficMAP() {
        return new AddMerchantTrafficMAP();
    }

    /**
     * Create an instance of {@link MerchantTrafficMAP }
     * 
     */
    public MerchantTrafficMAP createMerchantTrafficMAP() {
        return new MerchantTrafficMAP();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficMapByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficMapByUserId")
    public JAXBElement<GetMerchantTrafficMapByUserId> createGetMerchantTrafficMapByUserId(GetMerchantTrafficMapByUserId value) {
        return new JAXBElement<GetMerchantTrafficMapByUserId>(_GetMerchantTrafficMapByUserId_QNAME, GetMerchantTrafficMapByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantTrafficMAPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantTrafficMAPResponse")
    public JAXBElement<AddMerchantTrafficMAPResponse> createAddMerchantTrafficMAPResponse(AddMerchantTrafficMAPResponse value) {
        return new JAXBElement<AddMerchantTrafficMAPResponse>(_AddMerchantTrafficMAPResponse_QNAME, AddMerchantTrafficMAPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficMapByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficMapByUserIdResponse")
    public JAXBElement<GetMerchantTrafficMapByUserIdResponse> createGetMerchantTrafficMapByUserIdResponse(GetMerchantTrafficMapByUserIdResponse value) {
        return new JAXBElement<GetMerchantTrafficMapByUserIdResponse>(_GetMerchantTrafficMapByUserIdResponse_QNAME, GetMerchantTrafficMapByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantTrafficMAP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantTrafficMAP")
    public JAXBElement<AddMerchantTrafficMAP> createAddMerchantTrafficMAP(AddMerchantTrafficMAP value) {
        return new JAXBElement<AddMerchantTrafficMAP>(_AddMerchantTrafficMAP_QNAME, AddMerchantTrafficMAP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMerchantTrafficMAPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateMerchantTrafficMAPResponse")
    public JAXBElement<UpdateMerchantTrafficMAPResponse> createUpdateMerchantTrafficMAPResponse(UpdateMerchantTrafficMAPResponse value) {
        return new JAXBElement<UpdateMerchantTrafficMAPResponse>(_UpdateMerchantTrafficMAPResponse_QNAME, UpdateMerchantTrafficMAPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMerchantTrafficMAP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateMerchantTrafficMAP")
    public JAXBElement<UpdateMerchantTrafficMAP> createUpdateMerchantTrafficMAP(UpdateMerchantTrafficMAP value) {
        return new JAXBElement<UpdateMerchantTrafficMAP>(_UpdateMerchantTrafficMAP_QNAME, UpdateMerchantTrafficMAP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficMapByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficMapByMerchantIdResponse")
    public JAXBElement<GetMerchantTrafficMapByMerchantIdResponse> createGetMerchantTrafficMapByMerchantIdResponse(GetMerchantTrafficMapByMerchantIdResponse value) {
        return new JAXBElement<GetMerchantTrafficMapByMerchantIdResponse>(_GetMerchantTrafficMapByMerchantIdResponse_QNAME, GetMerchantTrafficMapByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficMapByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficMapByMerchantId")
    public JAXBElement<GetMerchantTrafficMapByMerchantId> createGetMerchantTrafficMapByMerchantId(GetMerchantTrafficMapByMerchantId value) {
        return new JAXBElement<GetMerchantTrafficMapByMerchantId>(_GetMerchantTrafficMapByMerchantId_QNAME, GetMerchantTrafficMapByMerchantId.class, null, value);
    }

}
