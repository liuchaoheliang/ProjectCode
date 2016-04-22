
package com.froad.client.brand;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.brand package. 
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

    private final static QName _UpdateBrand_QNAME = new QName("http://service.CB.froad.com/", "updateBrand");
    private final static QName _GetAllBrand_QNAME = new QName("http://service.CB.froad.com/", "getAllBrand");
    private final static QName _GetBrandList_QNAME = new QName("http://service.CB.froad.com/", "getBrandList");
    private final static QName _GetBrandByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getBrandByIdResponse");
    private final static QName _GetAllBrandResponse_QNAME = new QName("http://service.CB.froad.com/", "getAllBrandResponse");
    private final static QName _GetBrandListResponse_QNAME = new QName("http://service.CB.froad.com/", "getBrandListResponse");
    private final static QName _AddBrand_QNAME = new QName("http://service.CB.froad.com/", "addBrand");
    private final static QName _AddBrandResponse_QNAME = new QName("http://service.CB.froad.com/", "addBrandResponse");
    private final static QName _UpdateBrandResponse_QNAME = new QName("http://service.CB.froad.com/", "updateBrandResponse");
    private final static QName _GetBrandById_QNAME = new QName("http://service.CB.froad.com/", "getBrandById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.brand
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetBrandList }
     * 
     */
    public GetBrandList createGetBrandList() {
        return new GetBrandList();
    }

    /**
     * Create an instance of {@link GetAllBrand }
     * 
     */
    public GetAllBrand createGetAllBrand() {
        return new GetAllBrand();
    }

    /**
     * Create an instance of {@link UpdateBrand }
     * 
     */
    public UpdateBrand createUpdateBrand() {
        return new UpdateBrand();
    }

    /**
     * Create an instance of {@link GetBrandById }
     * 
     */
    public GetBrandById createGetBrandById() {
        return new GetBrandById();
    }

    /**
     * Create an instance of {@link UpdateBrandResponse }
     * 
     */
    public UpdateBrandResponse createUpdateBrandResponse() {
        return new UpdateBrandResponse();
    }

    /**
     * Create an instance of {@link AddBrandResponse }
     * 
     */
    public AddBrandResponse createAddBrandResponse() {
        return new AddBrandResponse();
    }

    /**
     * Create an instance of {@link AddBrand }
     * 
     */
    public AddBrand createAddBrand() {
        return new AddBrand();
    }

    /**
     * Create an instance of {@link GetBrandListResponse }
     * 
     */
    public GetBrandListResponse createGetBrandListResponse() {
        return new GetBrandListResponse();
    }

    /**
     * Create an instance of {@link GetAllBrandResponse }
     * 
     */
    public GetAllBrandResponse createGetAllBrandResponse() {
        return new GetAllBrandResponse();
    }

    /**
     * Create an instance of {@link GetBrandByIdResponse }
     * 
     */
    public GetBrandByIdResponse createGetBrandByIdResponse() {
        return new GetBrandByIdResponse();
    }

    /**
     * Create an instance of {@link Brand }
     * 
     */
    public Brand createBrand() {
        return new Brand();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBrand }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateBrand")
    public JAXBElement<UpdateBrand> createUpdateBrand(UpdateBrand value) {
        return new JAXBElement<UpdateBrand>(_UpdateBrand_QNAME, UpdateBrand.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllBrand }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllBrand")
    public JAXBElement<GetAllBrand> createGetAllBrand(GetAllBrand value) {
        return new JAXBElement<GetAllBrand>(_GetAllBrand_QNAME, GetAllBrand.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBrandList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getBrandList")
    public JAXBElement<GetBrandList> createGetBrandList(GetBrandList value) {
        return new JAXBElement<GetBrandList>(_GetBrandList_QNAME, GetBrandList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBrandByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getBrandByIdResponse")
    public JAXBElement<GetBrandByIdResponse> createGetBrandByIdResponse(GetBrandByIdResponse value) {
        return new JAXBElement<GetBrandByIdResponse>(_GetBrandByIdResponse_QNAME, GetBrandByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllBrandResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllBrandResponse")
    public JAXBElement<GetAllBrandResponse> createGetAllBrandResponse(GetAllBrandResponse value) {
        return new JAXBElement<GetAllBrandResponse>(_GetAllBrandResponse_QNAME, GetAllBrandResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBrandListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getBrandListResponse")
    public JAXBElement<GetBrandListResponse> createGetBrandListResponse(GetBrandListResponse value) {
        return new JAXBElement<GetBrandListResponse>(_GetBrandListResponse_QNAME, GetBrandListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBrand }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addBrand")
    public JAXBElement<AddBrand> createAddBrand(AddBrand value) {
        return new JAXBElement<AddBrand>(_AddBrand_QNAME, AddBrand.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBrandResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addBrandResponse")
    public JAXBElement<AddBrandResponse> createAddBrandResponse(AddBrandResponse value) {
        return new JAXBElement<AddBrandResponse>(_AddBrandResponse_QNAME, AddBrandResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBrandResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateBrandResponse")
    public JAXBElement<UpdateBrandResponse> createUpdateBrandResponse(UpdateBrandResponse value) {
        return new JAXBElement<UpdateBrandResponse>(_UpdateBrandResponse_QNAME, UpdateBrandResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBrandById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getBrandById")
    public JAXBElement<GetBrandById> createGetBrandById(GetBrandById value) {
        return new JAXBElement<GetBrandById>(_GetBrandById_QNAME, GetBrandById.class, null, value);
    }

}
