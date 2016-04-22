
package com.froad.client.merchantTraffic;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.merchantTraffic package. 
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

    private final static QName _GetMerchantTrafficInfo_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficInfo");
    private final static QName _GetMerchantTrafficInfoByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficInfoByMerchantIdResponse");
    private final static QName _GetMerchantTrafficInfoByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficInfoByUserIdResponse");
    private final static QName _GetMerchantTrafficInfoByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficInfoByMerchantId");
    private final static QName _UpdateMerchantTraffic_QNAME = new QName("http://service.CB.froad.com/", "updateMerchantTraffic");
    private final static QName _UpdateMerchantTrafficResponse_QNAME = new QName("http://service.CB.froad.com/", "updateMerchantTrafficResponse");
    private final static QName _GetMerchantTrafficInfoByUserId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficInfoByUserId");
    private final static QName _AddMerchantTrafficResponse_QNAME = new QName("http://service.CB.froad.com/", "addMerchantTrafficResponse");
    private final static QName _GetMerchantTrafficInfoResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrafficInfoResponse");
    private final static QName _AddMerchantTraffic_QNAME = new QName("http://service.CB.froad.com/", "addMerchantTraffic");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.merchantTraffic
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateMerchantTrafficResponse }
     * 
     */
    public UpdateMerchantTrafficResponse createUpdateMerchantTrafficResponse() {
        return new UpdateMerchantTrafficResponse();
    }

    /**
     * Create an instance of {@link UpdateMerchantTraffic }
     * 
     */
    public UpdateMerchantTraffic createUpdateMerchantTraffic() {
        return new UpdateMerchantTraffic();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficInfoByMerchantId }
     * 
     */
    public GetMerchantTrafficInfoByMerchantId createGetMerchantTrafficInfoByMerchantId() {
        return new GetMerchantTrafficInfoByMerchantId();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficInfoByUserIdResponse }
     * 
     */
    public GetMerchantTrafficInfoByUserIdResponse createGetMerchantTrafficInfoByUserIdResponse() {
        return new GetMerchantTrafficInfoByUserIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficInfoByMerchantIdResponse }
     * 
     */
    public GetMerchantTrafficInfoByMerchantIdResponse createGetMerchantTrafficInfoByMerchantIdResponse() {
        return new GetMerchantTrafficInfoByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficInfo }
     * 
     */
    public GetMerchantTrafficInfo createGetMerchantTrafficInfo() {
        return new GetMerchantTrafficInfo();
    }

    /**
     * Create an instance of {@link AddMerchantTraffic }
     * 
     */
    public AddMerchantTraffic createAddMerchantTraffic() {
        return new AddMerchantTraffic();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficInfoResponse }
     * 
     */
    public GetMerchantTrafficInfoResponse createGetMerchantTrafficInfoResponse() {
        return new GetMerchantTrafficInfoResponse();
    }

    /**
     * Create an instance of {@link AddMerchantTrafficResponse }
     * 
     */
    public AddMerchantTrafficResponse createAddMerchantTrafficResponse() {
        return new AddMerchantTrafficResponse();
    }

    /**
     * Create an instance of {@link GetMerchantTrafficInfoByUserId }
     * 
     */
    public GetMerchantTrafficInfoByUserId createGetMerchantTrafficInfoByUserId() {
        return new GetMerchantTrafficInfoByUserId();
    }

    /**
     * Create an instance of {@link MerchantTraffic }
     * 
     */
    public MerchantTraffic createMerchantTraffic() {
        return new MerchantTraffic();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficInfo")
    public JAXBElement<GetMerchantTrafficInfo> createGetMerchantTrafficInfo(GetMerchantTrafficInfo value) {
        return new JAXBElement<GetMerchantTrafficInfo>(_GetMerchantTrafficInfo_QNAME, GetMerchantTrafficInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficInfoByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficInfoByMerchantIdResponse")
    public JAXBElement<GetMerchantTrafficInfoByMerchantIdResponse> createGetMerchantTrafficInfoByMerchantIdResponse(GetMerchantTrafficInfoByMerchantIdResponse value) {
        return new JAXBElement<GetMerchantTrafficInfoByMerchantIdResponse>(_GetMerchantTrafficInfoByMerchantIdResponse_QNAME, GetMerchantTrafficInfoByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficInfoByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficInfoByUserIdResponse")
    public JAXBElement<GetMerchantTrafficInfoByUserIdResponse> createGetMerchantTrafficInfoByUserIdResponse(GetMerchantTrafficInfoByUserIdResponse value) {
        return new JAXBElement<GetMerchantTrafficInfoByUserIdResponse>(_GetMerchantTrafficInfoByUserIdResponse_QNAME, GetMerchantTrafficInfoByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficInfoByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficInfoByMerchantId")
    public JAXBElement<GetMerchantTrafficInfoByMerchantId> createGetMerchantTrafficInfoByMerchantId(GetMerchantTrafficInfoByMerchantId value) {
        return new JAXBElement<GetMerchantTrafficInfoByMerchantId>(_GetMerchantTrafficInfoByMerchantId_QNAME, GetMerchantTrafficInfoByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMerchantTraffic }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateMerchantTraffic")
    public JAXBElement<UpdateMerchantTraffic> createUpdateMerchantTraffic(UpdateMerchantTraffic value) {
        return new JAXBElement<UpdateMerchantTraffic>(_UpdateMerchantTraffic_QNAME, UpdateMerchantTraffic.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateMerchantTrafficResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateMerchantTrafficResponse")
    public JAXBElement<UpdateMerchantTrafficResponse> createUpdateMerchantTrafficResponse(UpdateMerchantTrafficResponse value) {
        return new JAXBElement<UpdateMerchantTrafficResponse>(_UpdateMerchantTrafficResponse_QNAME, UpdateMerchantTrafficResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficInfoByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficInfoByUserId")
    public JAXBElement<GetMerchantTrafficInfoByUserId> createGetMerchantTrafficInfoByUserId(GetMerchantTrafficInfoByUserId value) {
        return new JAXBElement<GetMerchantTrafficInfoByUserId>(_GetMerchantTrafficInfoByUserId_QNAME, GetMerchantTrafficInfoByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantTrafficResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantTrafficResponse")
    public JAXBElement<AddMerchantTrafficResponse> createAddMerchantTrafficResponse(AddMerchantTrafficResponse value) {
        return new JAXBElement<AddMerchantTrafficResponse>(_AddMerchantTrafficResponse_QNAME, AddMerchantTrafficResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrafficInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrafficInfoResponse")
    public JAXBElement<GetMerchantTrafficInfoResponse> createGetMerchantTrafficInfoResponse(GetMerchantTrafficInfoResponse value) {
        return new JAXBElement<GetMerchantTrafficInfoResponse>(_GetMerchantTrafficInfoResponse_QNAME, GetMerchantTrafficInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantTraffic }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantTraffic")
    public JAXBElement<AddMerchantTraffic> createAddMerchantTraffic(AddMerchantTraffic value) {
        return new JAXBElement<AddMerchantTraffic>(_AddMerchantTraffic_QNAME, AddMerchantTraffic.class, null, value);
    }

}
