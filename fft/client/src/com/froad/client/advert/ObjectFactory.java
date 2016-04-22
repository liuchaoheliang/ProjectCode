
package com.froad.client.advert;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.advert package. 
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

    private final static QName _AddAdvert_QNAME = new QName("http://service.CB.froad.com/", "addAdvert");
    private final static QName _UpdateAdvertResponse_QNAME = new QName("http://service.CB.froad.com/", "updateAdvertResponse");
    private final static QName _StopAdvertResponse_QNAME = new QName("http://service.CB.froad.com/", "stopAdvertResponse");
    private final static QName _GetAdverts_QNAME = new QName("http://service.CB.froad.com/", "getAdverts");
    private final static QName _AddAdvertResponse_QNAME = new QName("http://service.CB.froad.com/", "addAdvertResponse");
    private final static QName _GetAdvertByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getAdvertByPagerResponse");
    private final static QName _GetAdvertsResponse_QNAME = new QName("http://service.CB.froad.com/", "getAdvertsResponse");
    private final static QName _UpdateAdvert_QNAME = new QName("http://service.CB.froad.com/", "updateAdvert");
    private final static QName _GetAdvertByPager_QNAME = new QName("http://service.CB.froad.com/", "getAdvertByPager");
    private final static QName _StopAdvert_QNAME = new QName("http://service.CB.froad.com/", "stopAdvert");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.advert
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAdverts }
     * 
     */
    public GetAdverts createGetAdverts() {
        return new GetAdverts();
    }

    /**
     * Create an instance of {@link StopAdvertResponse }
     * 
     */
    public StopAdvertResponse createStopAdvertResponse() {
        return new StopAdvertResponse();
    }

    /**
     * Create an instance of {@link UpdateAdvertResponse }
     * 
     */
    public UpdateAdvertResponse createUpdateAdvertResponse() {
        return new UpdateAdvertResponse();
    }

    /**
     * Create an instance of {@link AddAdvert }
     * 
     */
    public AddAdvert createAddAdvert() {
        return new AddAdvert();
    }

    /**
     * Create an instance of {@link StopAdvert }
     * 
     */
    public StopAdvert createStopAdvert() {
        return new StopAdvert();
    }

    /**
     * Create an instance of {@link GetAdvertByPager }
     * 
     */
    public GetAdvertByPager createGetAdvertByPager() {
        return new GetAdvertByPager();
    }

    /**
     * Create an instance of {@link UpdateAdvert }
     * 
     */
    public UpdateAdvert createUpdateAdvert() {
        return new UpdateAdvert();
    }

    /**
     * Create an instance of {@link GetAdvertsResponse }
     * 
     */
    public GetAdvertsResponse createGetAdvertsResponse() {
        return new GetAdvertsResponse();
    }

    /**
     * Create an instance of {@link GetAdvertByPagerResponse }
     * 
     */
    public GetAdvertByPagerResponse createGetAdvertByPagerResponse() {
        return new GetAdvertByPagerResponse();
    }

    /**
     * Create an instance of {@link AddAdvertResponse }
     * 
     */
    public AddAdvertResponse createAddAdvertResponse() {
        return new AddAdvertResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link Advert }
     * 
     */
    public Advert createAdvert() {
        return new Advert();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAdvert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addAdvert")
    public JAXBElement<AddAdvert> createAddAdvert(AddAdvert value) {
        return new JAXBElement<AddAdvert>(_AddAdvert_QNAME, AddAdvert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAdvertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateAdvertResponse")
    public JAXBElement<UpdateAdvertResponse> createUpdateAdvertResponse(UpdateAdvertResponse value) {
        return new JAXBElement<UpdateAdvertResponse>(_UpdateAdvertResponse_QNAME, UpdateAdvertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopAdvertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "stopAdvertResponse")
    public JAXBElement<StopAdvertResponse> createStopAdvertResponse(StopAdvertResponse value) {
        return new JAXBElement<StopAdvertResponse>(_StopAdvertResponse_QNAME, StopAdvertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAdverts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAdverts")
    public JAXBElement<GetAdverts> createGetAdverts(GetAdverts value) {
        return new JAXBElement<GetAdverts>(_GetAdverts_QNAME, GetAdverts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAdvertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addAdvertResponse")
    public JAXBElement<AddAdvertResponse> createAddAdvertResponse(AddAdvertResponse value) {
        return new JAXBElement<AddAdvertResponse>(_AddAdvertResponse_QNAME, AddAdvertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAdvertByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAdvertByPagerResponse")
    public JAXBElement<GetAdvertByPagerResponse> createGetAdvertByPagerResponse(GetAdvertByPagerResponse value) {
        return new JAXBElement<GetAdvertByPagerResponse>(_GetAdvertByPagerResponse_QNAME, GetAdvertByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAdvertsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAdvertsResponse")
    public JAXBElement<GetAdvertsResponse> createGetAdvertsResponse(GetAdvertsResponse value) {
        return new JAXBElement<GetAdvertsResponse>(_GetAdvertsResponse_QNAME, GetAdvertsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAdvert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateAdvert")
    public JAXBElement<UpdateAdvert> createUpdateAdvert(UpdateAdvert value) {
        return new JAXBElement<UpdateAdvert>(_UpdateAdvert_QNAME, UpdateAdvert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAdvertByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAdvertByPager")
    public JAXBElement<GetAdvertByPager> createGetAdvertByPager(GetAdvertByPager value) {
        return new JAXBElement<GetAdvertByPager>(_GetAdvertByPager_QNAME, GetAdvertByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopAdvert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "stopAdvert")
    public JAXBElement<StopAdvert> createStopAdvert(StopAdvert value) {
        return new JAXBElement<StopAdvert>(_StopAdvert_QNAME, StopAdvert.class, null, value);
    }

}
