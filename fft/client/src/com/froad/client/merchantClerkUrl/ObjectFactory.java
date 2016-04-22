
package com.froad.client.merchantClerkUrl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.merchantClerkUrl package. 
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

    private final static QName _GetMerchantClerkUrl_QNAME = new QName("http://service.CB.froad.com/", "getMerchantClerkUrl");
    private final static QName _AddMerchantClerkUrl_QNAME = new QName("http://service.CB.froad.com/", "addMerchantClerkUrl");
    private final static QName _GetMerchantClerkUrlResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantClerkUrlResponse");
    private final static QName _AddMerchantClerkUrlResponse_QNAME = new QName("http://service.CB.froad.com/", "addMerchantClerkUrlResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.merchantClerkUrl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMerchantClerkUrl }
     * 
     */
    public GetMerchantClerkUrl createGetMerchantClerkUrl() {
        return new GetMerchantClerkUrl();
    }

    /**
     * Create an instance of {@link AddMerchantClerkUrlResponse }
     * 
     */
    public AddMerchantClerkUrlResponse createAddMerchantClerkUrlResponse() {
        return new AddMerchantClerkUrlResponse();
    }

    /**
     * Create an instance of {@link GetMerchantClerkUrlResponse }
     * 
     */
    public GetMerchantClerkUrlResponse createGetMerchantClerkUrlResponse() {
        return new GetMerchantClerkUrlResponse();
    }

    /**
     * Create an instance of {@link AddMerchantClerkUrl }
     * 
     */
    public AddMerchantClerkUrl createAddMerchantClerkUrl() {
        return new AddMerchantClerkUrl();
    }

    /**
     * Create an instance of {@link MerchantClerkUrl }
     * 
     */
    public MerchantClerkUrl createMerchantClerkUrl() {
        return new MerchantClerkUrl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantClerkUrl }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantClerkUrl")
    public JAXBElement<GetMerchantClerkUrl> createGetMerchantClerkUrl(GetMerchantClerkUrl value) {
        return new JAXBElement<GetMerchantClerkUrl>(_GetMerchantClerkUrl_QNAME, GetMerchantClerkUrl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantClerkUrl }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantClerkUrl")
    public JAXBElement<AddMerchantClerkUrl> createAddMerchantClerkUrl(AddMerchantClerkUrl value) {
        return new JAXBElement<AddMerchantClerkUrl>(_AddMerchantClerkUrl_QNAME, AddMerchantClerkUrl.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantClerkUrlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantClerkUrlResponse")
    public JAXBElement<GetMerchantClerkUrlResponse> createGetMerchantClerkUrlResponse(GetMerchantClerkUrlResponse value) {
        return new JAXBElement<GetMerchantClerkUrlResponse>(_GetMerchantClerkUrlResponse_QNAME, GetMerchantClerkUrlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantClerkUrlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantClerkUrlResponse")
    public JAXBElement<AddMerchantClerkUrlResponse> createAddMerchantClerkUrlResponse(AddMerchantClerkUrlResponse value) {
        return new JAXBElement<AddMerchantClerkUrlResponse>(_AddMerchantClerkUrlResponse_QNAME, AddMerchantClerkUrlResponse.class, null, value);
    }

}
