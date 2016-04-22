
package com.froad.client.goodsCategory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.goodsCategory package. 
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

    private final static QName _GetAllGoodsCategoryResponse_QNAME = new QName("http://service.CB.froad.com/", "getAllGoodsCategoryResponse");
    private final static QName _GetChildrenGoodsCategoryList_QNAME = new QName("http://service.CB.froad.com/", "getChildrenGoodsCategoryList");
    private final static QName _GetGoodsCategoryById_QNAME = new QName("http://service.CB.froad.com/", "getGoodsCategoryById");
    private final static QName _GetAllGoodsCategory_QNAME = new QName("http://service.CB.froad.com/", "getAllGoodsCategory");
    private final static QName _UpdateGoodsCategory_QNAME = new QName("http://service.CB.froad.com/", "updateGoodsCategory");
    private final static QName _AddGoodsCategoryResponse_QNAME = new QName("http://service.CB.froad.com/", "addGoodsCategoryResponse");
    private final static QName _GetGoodsCategoryByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsCategoryByIdResponse");
    private final static QName _UpdateGoodsCategoryResponse_QNAME = new QName("http://service.CB.froad.com/", "updateGoodsCategoryResponse");
    private final static QName _AddGoodsCategory_QNAME = new QName("http://service.CB.froad.com/", "addGoodsCategory");
    private final static QName _GetChildrenGoodsCategoryListResponse_QNAME = new QName("http://service.CB.froad.com/", "getChildrenGoodsCategoryListResponse");
    private final static QName _GetRootGoodsCategoryList_QNAME = new QName("http://service.CB.froad.com/", "getRootGoodsCategoryList");
    private final static QName _GetRootGoodsCategoryListResponse_QNAME = new QName("http://service.CB.froad.com/", "getRootGoodsCategoryListResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.goodsCategory
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddGoodsCategoryResponse }
     * 
     */
    public AddGoodsCategoryResponse createAddGoodsCategoryResponse() {
        return new AddGoodsCategoryResponse();
    }

    /**
     * Create an instance of {@link UpdateGoodsCategory }
     * 
     */
    public UpdateGoodsCategory createUpdateGoodsCategory() {
        return new UpdateGoodsCategory();
    }

    /**
     * Create an instance of {@link GetAllGoodsCategory }
     * 
     */
    public GetAllGoodsCategory createGetAllGoodsCategory() {
        return new GetAllGoodsCategory();
    }

    /**
     * Create an instance of {@link GetGoodsCategoryById }
     * 
     */
    public GetGoodsCategoryById createGetGoodsCategoryById() {
        return new GetGoodsCategoryById();
    }

    /**
     * Create an instance of {@link GetChildrenGoodsCategoryList }
     * 
     */
    public GetChildrenGoodsCategoryList createGetChildrenGoodsCategoryList() {
        return new GetChildrenGoodsCategoryList();
    }

    /**
     * Create an instance of {@link GetAllGoodsCategoryResponse }
     * 
     */
    public GetAllGoodsCategoryResponse createGetAllGoodsCategoryResponse() {
        return new GetAllGoodsCategoryResponse();
    }

    /**
     * Create an instance of {@link GetRootGoodsCategoryListResponse }
     * 
     */
    public GetRootGoodsCategoryListResponse createGetRootGoodsCategoryListResponse() {
        return new GetRootGoodsCategoryListResponse();
    }

    /**
     * Create an instance of {@link GetRootGoodsCategoryList }
     * 
     */
    public GetRootGoodsCategoryList createGetRootGoodsCategoryList() {
        return new GetRootGoodsCategoryList();
    }

    /**
     * Create an instance of {@link GetChildrenGoodsCategoryListResponse }
     * 
     */
    public GetChildrenGoodsCategoryListResponse createGetChildrenGoodsCategoryListResponse() {
        return new GetChildrenGoodsCategoryListResponse();
    }

    /**
     * Create an instance of {@link AddGoodsCategory }
     * 
     */
    public AddGoodsCategory createAddGoodsCategory() {
        return new AddGoodsCategory();
    }

    /**
     * Create an instance of {@link UpdateGoodsCategoryResponse }
     * 
     */
    public UpdateGoodsCategoryResponse createUpdateGoodsCategoryResponse() {
        return new UpdateGoodsCategoryResponse();
    }

    /**
     * Create an instance of {@link GetGoodsCategoryByIdResponse }
     * 
     */
    public GetGoodsCategoryByIdResponse createGetGoodsCategoryByIdResponse() {
        return new GetGoodsCategoryByIdResponse();
    }

    /**
     * Create an instance of {@link GoodsCategory }
     * 
     */
    public GoodsCategory createGoodsCategory() {
        return new GoodsCategory();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllGoodsCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllGoodsCategoryResponse")
    public JAXBElement<GetAllGoodsCategoryResponse> createGetAllGoodsCategoryResponse(GetAllGoodsCategoryResponse value) {
        return new JAXBElement<GetAllGoodsCategoryResponse>(_GetAllGoodsCategoryResponse_QNAME, GetAllGoodsCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildrenGoodsCategoryList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getChildrenGoodsCategoryList")
    public JAXBElement<GetChildrenGoodsCategoryList> createGetChildrenGoodsCategoryList(GetChildrenGoodsCategoryList value) {
        return new JAXBElement<GetChildrenGoodsCategoryList>(_GetChildrenGoodsCategoryList_QNAME, GetChildrenGoodsCategoryList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsCategoryById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsCategoryById")
    public JAXBElement<GetGoodsCategoryById> createGetGoodsCategoryById(GetGoodsCategoryById value) {
        return new JAXBElement<GetGoodsCategoryById>(_GetGoodsCategoryById_QNAME, GetGoodsCategoryById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllGoodsCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllGoodsCategory")
    public JAXBElement<GetAllGoodsCategory> createGetAllGoodsCategory(GetAllGoodsCategory value) {
        return new JAXBElement<GetAllGoodsCategory>(_GetAllGoodsCategory_QNAME, GetAllGoodsCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGoodsCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateGoodsCategory")
    public JAXBElement<UpdateGoodsCategory> createUpdateGoodsCategory(UpdateGoodsCategory value) {
        return new JAXBElement<UpdateGoodsCategory>(_UpdateGoodsCategory_QNAME, UpdateGoodsCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsCategoryResponse")
    public JAXBElement<AddGoodsCategoryResponse> createAddGoodsCategoryResponse(AddGoodsCategoryResponse value) {
        return new JAXBElement<AddGoodsCategoryResponse>(_AddGoodsCategoryResponse_QNAME, AddGoodsCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsCategoryByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsCategoryByIdResponse")
    public JAXBElement<GetGoodsCategoryByIdResponse> createGetGoodsCategoryByIdResponse(GetGoodsCategoryByIdResponse value) {
        return new JAXBElement<GetGoodsCategoryByIdResponse>(_GetGoodsCategoryByIdResponse_QNAME, GetGoodsCategoryByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGoodsCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateGoodsCategoryResponse")
    public JAXBElement<UpdateGoodsCategoryResponse> createUpdateGoodsCategoryResponse(UpdateGoodsCategoryResponse value) {
        return new JAXBElement<UpdateGoodsCategoryResponse>(_UpdateGoodsCategoryResponse_QNAME, UpdateGoodsCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsCategory")
    public JAXBElement<AddGoodsCategory> createAddGoodsCategory(AddGoodsCategory value) {
        return new JAXBElement<AddGoodsCategory>(_AddGoodsCategory_QNAME, AddGoodsCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildrenGoodsCategoryListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getChildrenGoodsCategoryListResponse")
    public JAXBElement<GetChildrenGoodsCategoryListResponse> createGetChildrenGoodsCategoryListResponse(GetChildrenGoodsCategoryListResponse value) {
        return new JAXBElement<GetChildrenGoodsCategoryListResponse>(_GetChildrenGoodsCategoryListResponse_QNAME, GetChildrenGoodsCategoryListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRootGoodsCategoryList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getRootGoodsCategoryList")
    public JAXBElement<GetRootGoodsCategoryList> createGetRootGoodsCategoryList(GetRootGoodsCategoryList value) {
        return new JAXBElement<GetRootGoodsCategoryList>(_GetRootGoodsCategoryList_QNAME, GetRootGoodsCategoryList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRootGoodsCategoryListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getRootGoodsCategoryListResponse")
    public JAXBElement<GetRootGoodsCategoryListResponse> createGetRootGoodsCategoryListResponse(GetRootGoodsCategoryListResponse value) {
        return new JAXBElement<GetRootGoodsCategoryListResponse>(_GetRootGoodsCategoryListResponse_QNAME, GetRootGoodsCategoryListResponse.class, null, value);
    }

}
