
package com.froad.client.SpringFestivalCoupon;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.SpringFestivalCoupon package. 
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

    private final static QName _AddSpringFestivalCouponResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "addSpringFestivalCouponResponse");
    private final static QName _GetSpringFestivalCouponByPagerResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "getSpringFestivalCouponByPagerResponse");
    private final static QName _UpdateSpringFestivalCouponByIdResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "updateSpringFestivalCouponByIdResponse");
    private final static QName _GetSpringFestivalCouponByPager_QNAME = new QName("http://activity.service.CB.froad.com/", "getSpringFestivalCouponByPager");
    private final static QName _GetSpringFestivalCouponByIdResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "getSpringFestivalCouponByIdResponse");
    private final static QName _AddSpringFestivalCoupon_QNAME = new QName("http://activity.service.CB.froad.com/", "addSpringFestivalCoupon");
    private final static QName _UpdateSpringFestivalCouponById_QNAME = new QName("http://activity.service.CB.froad.com/", "updateSpringFestivalCouponById");
    private final static QName _GetSpringFestivalCouponByCndition_QNAME = new QName("http://activity.service.CB.froad.com/", "getSpringFestivalCouponByCndition");
    private final static QName _GetSpringFestivalCouponById_QNAME = new QName("http://activity.service.CB.froad.com/", "getSpringFestivalCouponById");
    private final static QName _GetSpringFestivalCouponByCnditionResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "getSpringFestivalCouponByCnditionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.SpringFestivalCoupon
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateSpringFestivalCouponByIdResponse }
     * 
     */
    public UpdateSpringFestivalCouponByIdResponse createUpdateSpringFestivalCouponByIdResponse() {
        return new UpdateSpringFestivalCouponByIdResponse();
    }

    /**
     * Create an instance of {@link GetSpringFestivalCouponByPagerResponse }
     * 
     */
    public GetSpringFestivalCouponByPagerResponse createGetSpringFestivalCouponByPagerResponse() {
        return new GetSpringFestivalCouponByPagerResponse();
    }

    /**
     * Create an instance of {@link AddSpringFestivalCouponResponse }
     * 
     */
    public AddSpringFestivalCouponResponse createAddSpringFestivalCouponResponse() {
        return new AddSpringFestivalCouponResponse();
    }

    /**
     * Create an instance of {@link GetSpringFestivalCouponByIdResponse }
     * 
     */
    public GetSpringFestivalCouponByIdResponse createGetSpringFestivalCouponByIdResponse() {
        return new GetSpringFestivalCouponByIdResponse();
    }

    /**
     * Create an instance of {@link GetSpringFestivalCouponByPager }
     * 
     */
    public GetSpringFestivalCouponByPager createGetSpringFestivalCouponByPager() {
        return new GetSpringFestivalCouponByPager();
    }

    /**
     * Create an instance of {@link GetSpringFestivalCouponById }
     * 
     */
    public GetSpringFestivalCouponById createGetSpringFestivalCouponById() {
        return new GetSpringFestivalCouponById();
    }

    /**
     * Create an instance of {@link GetSpringFestivalCouponByCndition }
     * 
     */
    public GetSpringFestivalCouponByCndition createGetSpringFestivalCouponByCndition() {
        return new GetSpringFestivalCouponByCndition();
    }

    /**
     * Create an instance of {@link UpdateSpringFestivalCouponById }
     * 
     */
    public UpdateSpringFestivalCouponById createUpdateSpringFestivalCouponById() {
        return new UpdateSpringFestivalCouponById();
    }

    /**
     * Create an instance of {@link AddSpringFestivalCoupon }
     * 
     */
    public AddSpringFestivalCoupon createAddSpringFestivalCoupon() {
        return new AddSpringFestivalCoupon();
    }

    /**
     * Create an instance of {@link GetSpringFestivalCouponByCnditionResponse }
     * 
     */
    public GetSpringFestivalCouponByCnditionResponse createGetSpringFestivalCouponByCnditionResponse() {
        return new GetSpringFestivalCouponByCnditionResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link SpringFestivalCoupon }
     * 
     */
    public SpringFestivalCoupon createSpringFestivalCoupon() {
        return new SpringFestivalCoupon();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSpringFestivalCouponResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "addSpringFestivalCouponResponse")
    public JAXBElement<AddSpringFestivalCouponResponse> createAddSpringFestivalCouponResponse(AddSpringFestivalCouponResponse value) {
        return new JAXBElement<AddSpringFestivalCouponResponse>(_AddSpringFestivalCouponResponse_QNAME, AddSpringFestivalCouponResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpringFestivalCouponByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getSpringFestivalCouponByPagerResponse")
    public JAXBElement<GetSpringFestivalCouponByPagerResponse> createGetSpringFestivalCouponByPagerResponse(GetSpringFestivalCouponByPagerResponse value) {
        return new JAXBElement<GetSpringFestivalCouponByPagerResponse>(_GetSpringFestivalCouponByPagerResponse_QNAME, GetSpringFestivalCouponByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSpringFestivalCouponByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "updateSpringFestivalCouponByIdResponse")
    public JAXBElement<UpdateSpringFestivalCouponByIdResponse> createUpdateSpringFestivalCouponByIdResponse(UpdateSpringFestivalCouponByIdResponse value) {
        return new JAXBElement<UpdateSpringFestivalCouponByIdResponse>(_UpdateSpringFestivalCouponByIdResponse_QNAME, UpdateSpringFestivalCouponByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpringFestivalCouponByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getSpringFestivalCouponByPager")
    public JAXBElement<GetSpringFestivalCouponByPager> createGetSpringFestivalCouponByPager(GetSpringFestivalCouponByPager value) {
        return new JAXBElement<GetSpringFestivalCouponByPager>(_GetSpringFestivalCouponByPager_QNAME, GetSpringFestivalCouponByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpringFestivalCouponByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getSpringFestivalCouponByIdResponse")
    public JAXBElement<GetSpringFestivalCouponByIdResponse> createGetSpringFestivalCouponByIdResponse(GetSpringFestivalCouponByIdResponse value) {
        return new JAXBElement<GetSpringFestivalCouponByIdResponse>(_GetSpringFestivalCouponByIdResponse_QNAME, GetSpringFestivalCouponByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSpringFestivalCoupon }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "addSpringFestivalCoupon")
    public JAXBElement<AddSpringFestivalCoupon> createAddSpringFestivalCoupon(AddSpringFestivalCoupon value) {
        return new JAXBElement<AddSpringFestivalCoupon>(_AddSpringFestivalCoupon_QNAME, AddSpringFestivalCoupon.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSpringFestivalCouponById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "updateSpringFestivalCouponById")
    public JAXBElement<UpdateSpringFestivalCouponById> createUpdateSpringFestivalCouponById(UpdateSpringFestivalCouponById value) {
        return new JAXBElement<UpdateSpringFestivalCouponById>(_UpdateSpringFestivalCouponById_QNAME, UpdateSpringFestivalCouponById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpringFestivalCouponByCndition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getSpringFestivalCouponByCndition")
    public JAXBElement<GetSpringFestivalCouponByCndition> createGetSpringFestivalCouponByCndition(GetSpringFestivalCouponByCndition value) {
        return new JAXBElement<GetSpringFestivalCouponByCndition>(_GetSpringFestivalCouponByCndition_QNAME, GetSpringFestivalCouponByCndition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpringFestivalCouponById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getSpringFestivalCouponById")
    public JAXBElement<GetSpringFestivalCouponById> createGetSpringFestivalCouponById(GetSpringFestivalCouponById value) {
        return new JAXBElement<GetSpringFestivalCouponById>(_GetSpringFestivalCouponById_QNAME, GetSpringFestivalCouponById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpringFestivalCouponByCnditionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getSpringFestivalCouponByCnditionResponse")
    public JAXBElement<GetSpringFestivalCouponByCnditionResponse> createGetSpringFestivalCouponByCnditionResponse(GetSpringFestivalCouponByCnditionResponse value) {
        return new JAXBElement<GetSpringFestivalCouponByCnditionResponse>(_GetSpringFestivalCouponByCnditionResponse_QNAME, GetSpringFestivalCouponByCnditionResponse.class, null, value);
    }

}
