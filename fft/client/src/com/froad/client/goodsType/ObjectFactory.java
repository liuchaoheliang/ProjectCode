
package com.froad.client.goodsType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.goodsType package. 
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

    private final static QName _AddGoodsTypeResponse_QNAME = new QName("http://service.CB.froad.com/", "addGoodsTypeResponse");
    private final static QName _GetAllGoodsTypeResponse_QNAME = new QName("http://service.CB.froad.com/", "getAllGoodsTypeResponse");
    private final static QName _GetGoodsTypeListResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsTypeListResponse");
    private final static QName _GetGoodsTypeList_QNAME = new QName("http://service.CB.froad.com/", "getGoodsTypeList");
    private final static QName _GetGoodsTypeByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsTypeByIdResponse");
    private final static QName _UpdateGoodsType_QNAME = new QName("http://service.CB.froad.com/", "updateGoodsType");
    private final static QName _AddGoodsType_QNAME = new QName("http://service.CB.froad.com/", "addGoodsType");
    private final static QName _UpdateGoodsTypeResponse_QNAME = new QName("http://service.CB.froad.com/", "updateGoodsTypeResponse");
    private final static QName _GetAllGoodsType_QNAME = new QName("http://service.CB.froad.com/", "getAllGoodsType");
    private final static QName _GetGoodsTypeById_QNAME = new QName("http://service.CB.froad.com/", "getGoodsTypeById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.goodsType
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateGoodsType }
     * 
     */
    public UpdateGoodsType createUpdateGoodsType() {
        return new UpdateGoodsType();
    }

    /**
     * Create an instance of {@link GetGoodsTypeByIdResponse }
     * 
     */
    public GetGoodsTypeByIdResponse createGetGoodsTypeByIdResponse() {
        return new GetGoodsTypeByIdResponse();
    }

    /**
     * Create an instance of {@link GetGoodsTypeList }
     * 
     */
    public GetGoodsTypeList createGetGoodsTypeList() {
        return new GetGoodsTypeList();
    }

    /**
     * Create an instance of {@link GetGoodsTypeListResponse }
     * 
     */
    public GetGoodsTypeListResponse createGetGoodsTypeListResponse() {
        return new GetGoodsTypeListResponse();
    }

    /**
     * Create an instance of {@link GetAllGoodsTypeResponse }
     * 
     */
    public GetAllGoodsTypeResponse createGetAllGoodsTypeResponse() {
        return new GetAllGoodsTypeResponse();
    }

    /**
     * Create an instance of {@link AddGoodsTypeResponse }
     * 
     */
    public AddGoodsTypeResponse createAddGoodsTypeResponse() {
        return new AddGoodsTypeResponse();
    }

    /**
     * Create an instance of {@link GetGoodsTypeById }
     * 
     */
    public GetGoodsTypeById createGetGoodsTypeById() {
        return new GetGoodsTypeById();
    }

    /**
     * Create an instance of {@link GetAllGoodsType }
     * 
     */
    public GetAllGoodsType createGetAllGoodsType() {
        return new GetAllGoodsType();
    }

    /**
     * Create an instance of {@link UpdateGoodsTypeResponse }
     * 
     */
    public UpdateGoodsTypeResponse createUpdateGoodsTypeResponse() {
        return new UpdateGoodsTypeResponse();
    }

    /**
     * Create an instance of {@link AddGoodsType }
     * 
     */
    public AddGoodsType createAddGoodsType() {
        return new AddGoodsType();
    }

    /**
     * Create an instance of {@link GoodsType }
     * 
     */
    public GoodsType createGoodsType() {
        return new GoodsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsTypeResponse")
    public JAXBElement<AddGoodsTypeResponse> createAddGoodsTypeResponse(AddGoodsTypeResponse value) {
        return new JAXBElement<AddGoodsTypeResponse>(_AddGoodsTypeResponse_QNAME, AddGoodsTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllGoodsTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllGoodsTypeResponse")
    public JAXBElement<GetAllGoodsTypeResponse> createGetAllGoodsTypeResponse(GetAllGoodsTypeResponse value) {
        return new JAXBElement<GetAllGoodsTypeResponse>(_GetAllGoodsTypeResponse_QNAME, GetAllGoodsTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsTypeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsTypeListResponse")
    public JAXBElement<GetGoodsTypeListResponse> createGetGoodsTypeListResponse(GetGoodsTypeListResponse value) {
        return new JAXBElement<GetGoodsTypeListResponse>(_GetGoodsTypeListResponse_QNAME, GetGoodsTypeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsTypeList")
    public JAXBElement<GetGoodsTypeList> createGetGoodsTypeList(GetGoodsTypeList value) {
        return new JAXBElement<GetGoodsTypeList>(_GetGoodsTypeList_QNAME, GetGoodsTypeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsTypeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsTypeByIdResponse")
    public JAXBElement<GetGoodsTypeByIdResponse> createGetGoodsTypeByIdResponse(GetGoodsTypeByIdResponse value) {
        return new JAXBElement<GetGoodsTypeByIdResponse>(_GetGoodsTypeByIdResponse_QNAME, GetGoodsTypeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGoodsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateGoodsType")
    public JAXBElement<UpdateGoodsType> createUpdateGoodsType(UpdateGoodsType value) {
        return new JAXBElement<UpdateGoodsType>(_UpdateGoodsType_QNAME, UpdateGoodsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsType")
    public JAXBElement<AddGoodsType> createAddGoodsType(AddGoodsType value) {
        return new JAXBElement<AddGoodsType>(_AddGoodsType_QNAME, AddGoodsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGoodsTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateGoodsTypeResponse")
    public JAXBElement<UpdateGoodsTypeResponse> createUpdateGoodsTypeResponse(UpdateGoodsTypeResponse value) {
        return new JAXBElement<UpdateGoodsTypeResponse>(_UpdateGoodsTypeResponse_QNAME, UpdateGoodsTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllGoodsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllGoodsType")
    public JAXBElement<GetAllGoodsType> createGetAllGoodsType(GetAllGoodsType value) {
        return new JAXBElement<GetAllGoodsType>(_GetAllGoodsType_QNAME, GetAllGoodsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsTypeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsTypeById")
    public JAXBElement<GetGoodsTypeById> createGetGoodsTypeById(GetGoodsTypeById value) {
        return new JAXBElement<GetGoodsTypeById>(_GetGoodsTypeById_QNAME, GetGoodsTypeById.class, null, value);
    }

}
