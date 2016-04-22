
package com.froad.client.merchantProduct;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.merchantProduct package. 
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

    private final static QName _GetMerchantProductInfo_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductInfo");
    private final static QName _GetMerchantProductListByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductListByMerchantId");
    private final static QName _GetMerchantProductInfoResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductInfoResponse");
    private final static QName _AddMerchantProductResponse_QNAME = new QName("http://service.CB.froad.com/", "addMerchantProductResponse");
    private final static QName _GetMerchantProductListByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductListByMerchantIdResponse");
    private final static QName _GetMerchantProductById_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductById");
    private final static QName _UpdMerchantProductInfoResponse_QNAME = new QName("http://service.CB.froad.com/", "updMerchantProductInfoResponse");
    private final static QName _DeleteProductById_QNAME = new QName("http://service.CB.froad.com/", "deleteProductById");
    private final static QName _GetMerchantProductByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductByMerchantIdResponse");
    private final static QName _UpdMerchantProductInfo_QNAME = new QName("http://service.CB.froad.com/", "updMerchantProductInfo");
    private final static QName _GetMerchantProductByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductByUserIdResponse");
    private final static QName _DeleteProductByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteProductByIdResponse");
    private final static QName _UpdateByPrimaryKeySelectiveResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByPrimaryKeySelectiveResponse");
    private final static QName _GetMerchantProductByPager_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductByPager");
    private final static QName _UpdateByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "updateByMerchantId");
    private final static QName _AddMerchantProduct_QNAME = new QName("http://service.CB.froad.com/", "addMerchantProduct");
    private final static QName _GetMerchantProductByUserId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductByUserId");
    private final static QName _GetMerchantProductByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductByPagerResponse");
    private final static QName _UpdateByPrimaryKeySelective_QNAME = new QName("http://service.CB.froad.com/", "updateByPrimaryKeySelective");
    private final static QName _GetMerchantProductByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductByMerchantId");
    private final static QName _GetMerchantProductByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantProductByIdResponse");
    private final static QName _UpdateByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByMerchantIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.merchantProduct
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMerchantProductById }
     * 
     */
    public GetMerchantProductById createGetMerchantProductById() {
        return new GetMerchantProductById();
    }

    /**
     * Create an instance of {@link GetMerchantProductListByMerchantIdResponse }
     * 
     */
    public GetMerchantProductListByMerchantIdResponse createGetMerchantProductListByMerchantIdResponse() {
        return new GetMerchantProductListByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link AddMerchantProductResponse }
     * 
     */
    public AddMerchantProductResponse createAddMerchantProductResponse() {
        return new AddMerchantProductResponse();
    }

    /**
     * Create an instance of {@link GetMerchantProductInfoResponse }
     * 
     */
    public GetMerchantProductInfoResponse createGetMerchantProductInfoResponse() {
        return new GetMerchantProductInfoResponse();
    }

    /**
     * Create an instance of {@link GetMerchantProductListByMerchantId }
     * 
     */
    public GetMerchantProductListByMerchantId createGetMerchantProductListByMerchantId() {
        return new GetMerchantProductListByMerchantId();
    }

    /**
     * Create an instance of {@link GetMerchantProductInfo }
     * 
     */
    public GetMerchantProductInfo createGetMerchantProductInfo() {
        return new GetMerchantProductInfo();
    }

    /**
     * Create an instance of {@link UpdMerchantProductInfo }
     * 
     */
    public UpdMerchantProductInfo createUpdMerchantProductInfo() {
        return new UpdMerchantProductInfo();
    }

    /**
     * Create an instance of {@link GetMerchantProductByMerchantIdResponse }
     * 
     */
    public GetMerchantProductByMerchantIdResponse createGetMerchantProductByMerchantIdResponse() {
        return new GetMerchantProductByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link DeleteProductById }
     * 
     */
    public DeleteProductById createDeleteProductById() {
        return new DeleteProductById();
    }

    /**
     * Create an instance of {@link UpdMerchantProductInfoResponse }
     * 
     */
    public UpdMerchantProductInfoResponse createUpdMerchantProductInfoResponse() {
        return new UpdMerchantProductInfoResponse();
    }

    /**
     * Create an instance of {@link GetMerchantProductByPager }
     * 
     */
    public GetMerchantProductByPager createGetMerchantProductByPager() {
        return new GetMerchantProductByPager();
    }

    /**
     * Create an instance of {@link UpdateByPrimaryKeySelectiveResponse }
     * 
     */
    public UpdateByPrimaryKeySelectiveResponse createUpdateByPrimaryKeySelectiveResponse() {
        return new UpdateByPrimaryKeySelectiveResponse();
    }

    /**
     * Create an instance of {@link DeleteProductByIdResponse }
     * 
     */
    public DeleteProductByIdResponse createDeleteProductByIdResponse() {
        return new DeleteProductByIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantProductByUserIdResponse }
     * 
     */
    public GetMerchantProductByUserIdResponse createGetMerchantProductByUserIdResponse() {
        return new GetMerchantProductByUserIdResponse();
    }

    /**
     * Create an instance of {@link UpdateByMerchantIdResponse }
     * 
     */
    public UpdateByMerchantIdResponse createUpdateByMerchantIdResponse() {
        return new UpdateByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantProductByIdResponse }
     * 
     */
    public GetMerchantProductByIdResponse createGetMerchantProductByIdResponse() {
        return new GetMerchantProductByIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantProductByMerchantId }
     * 
     */
    public GetMerchantProductByMerchantId createGetMerchantProductByMerchantId() {
        return new GetMerchantProductByMerchantId();
    }

    /**
     * Create an instance of {@link UpdateByPrimaryKeySelective }
     * 
     */
    public UpdateByPrimaryKeySelective createUpdateByPrimaryKeySelective() {
        return new UpdateByPrimaryKeySelective();
    }

    /**
     * Create an instance of {@link GetMerchantProductByPagerResponse }
     * 
     */
    public GetMerchantProductByPagerResponse createGetMerchantProductByPagerResponse() {
        return new GetMerchantProductByPagerResponse();
    }

    /**
     * Create an instance of {@link GetMerchantProductByUserId }
     * 
     */
    public GetMerchantProductByUserId createGetMerchantProductByUserId() {
        return new GetMerchantProductByUserId();
    }

    /**
     * Create an instance of {@link AddMerchantProduct }
     * 
     */
    public AddMerchantProduct createAddMerchantProduct() {
        return new AddMerchantProduct();
    }

    /**
     * Create an instance of {@link UpdateByMerchantId }
     * 
     */
    public UpdateByMerchantId createUpdateByMerchantId() {
        return new UpdateByMerchantId();
    }

    /**
     * Create an instance of {@link MerchantProduct }
     * 
     */
    public MerchantProduct createMerchantProduct() {
        return new MerchantProduct();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductInfo")
    public JAXBElement<GetMerchantProductInfo> createGetMerchantProductInfo(GetMerchantProductInfo value) {
        return new JAXBElement<GetMerchantProductInfo>(_GetMerchantProductInfo_QNAME, GetMerchantProductInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductListByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductListByMerchantId")
    public JAXBElement<GetMerchantProductListByMerchantId> createGetMerchantProductListByMerchantId(GetMerchantProductListByMerchantId value) {
        return new JAXBElement<GetMerchantProductListByMerchantId>(_GetMerchantProductListByMerchantId_QNAME, GetMerchantProductListByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductInfoResponse")
    public JAXBElement<GetMerchantProductInfoResponse> createGetMerchantProductInfoResponse(GetMerchantProductInfoResponse value) {
        return new JAXBElement<GetMerchantProductInfoResponse>(_GetMerchantProductInfoResponse_QNAME, GetMerchantProductInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantProductResponse")
    public JAXBElement<AddMerchantProductResponse> createAddMerchantProductResponse(AddMerchantProductResponse value) {
        return new JAXBElement<AddMerchantProductResponse>(_AddMerchantProductResponse_QNAME, AddMerchantProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductListByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductListByMerchantIdResponse")
    public JAXBElement<GetMerchantProductListByMerchantIdResponse> createGetMerchantProductListByMerchantIdResponse(GetMerchantProductListByMerchantIdResponse value) {
        return new JAXBElement<GetMerchantProductListByMerchantIdResponse>(_GetMerchantProductListByMerchantIdResponse_QNAME, GetMerchantProductListByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductById")
    public JAXBElement<GetMerchantProductById> createGetMerchantProductById(GetMerchantProductById value) {
        return new JAXBElement<GetMerchantProductById>(_GetMerchantProductById_QNAME, GetMerchantProductById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdMerchantProductInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updMerchantProductInfoResponse")
    public JAXBElement<UpdMerchantProductInfoResponse> createUpdMerchantProductInfoResponse(UpdMerchantProductInfoResponse value) {
        return new JAXBElement<UpdMerchantProductInfoResponse>(_UpdMerchantProductInfoResponse_QNAME, UpdMerchantProductInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProductById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteProductById")
    public JAXBElement<DeleteProductById> createDeleteProductById(DeleteProductById value) {
        return new JAXBElement<DeleteProductById>(_DeleteProductById_QNAME, DeleteProductById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductByMerchantIdResponse")
    public JAXBElement<GetMerchantProductByMerchantIdResponse> createGetMerchantProductByMerchantIdResponse(GetMerchantProductByMerchantIdResponse value) {
        return new JAXBElement<GetMerchantProductByMerchantIdResponse>(_GetMerchantProductByMerchantIdResponse_QNAME, GetMerchantProductByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdMerchantProductInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updMerchantProductInfo")
    public JAXBElement<UpdMerchantProductInfo> createUpdMerchantProductInfo(UpdMerchantProductInfo value) {
        return new JAXBElement<UpdMerchantProductInfo>(_UpdMerchantProductInfo_QNAME, UpdMerchantProductInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductByUserIdResponse")
    public JAXBElement<GetMerchantProductByUserIdResponse> createGetMerchantProductByUserIdResponse(GetMerchantProductByUserIdResponse value) {
        return new JAXBElement<GetMerchantProductByUserIdResponse>(_GetMerchantProductByUserIdResponse_QNAME, GetMerchantProductByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProductByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteProductByIdResponse")
    public JAXBElement<DeleteProductByIdResponse> createDeleteProductByIdResponse(DeleteProductByIdResponse value) {
        return new JAXBElement<DeleteProductByIdResponse>(_DeleteProductByIdResponse_QNAME, DeleteProductByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByPrimaryKeySelectiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByPrimaryKeySelectiveResponse")
    public JAXBElement<UpdateByPrimaryKeySelectiveResponse> createUpdateByPrimaryKeySelectiveResponse(UpdateByPrimaryKeySelectiveResponse value) {
        return new JAXBElement<UpdateByPrimaryKeySelectiveResponse>(_UpdateByPrimaryKeySelectiveResponse_QNAME, UpdateByPrimaryKeySelectiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductByPager")
    public JAXBElement<GetMerchantProductByPager> createGetMerchantProductByPager(GetMerchantProductByPager value) {
        return new JAXBElement<GetMerchantProductByPager>(_GetMerchantProductByPager_QNAME, GetMerchantProductByPager.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantProduct")
    public JAXBElement<AddMerchantProduct> createAddMerchantProduct(AddMerchantProduct value) {
        return new JAXBElement<AddMerchantProduct>(_AddMerchantProduct_QNAME, AddMerchantProduct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductByUserId")
    public JAXBElement<GetMerchantProductByUserId> createGetMerchantProductByUserId(GetMerchantProductByUserId value) {
        return new JAXBElement<GetMerchantProductByUserId>(_GetMerchantProductByUserId_QNAME, GetMerchantProductByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductByPagerResponse")
    public JAXBElement<GetMerchantProductByPagerResponse> createGetMerchantProductByPagerResponse(GetMerchantProductByPagerResponse value) {
        return new JAXBElement<GetMerchantProductByPagerResponse>(_GetMerchantProductByPagerResponse_QNAME, GetMerchantProductByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByPrimaryKeySelective }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByPrimaryKeySelective")
    public JAXBElement<UpdateByPrimaryKeySelective> createUpdateByPrimaryKeySelective(UpdateByPrimaryKeySelective value) {
        return new JAXBElement<UpdateByPrimaryKeySelective>(_UpdateByPrimaryKeySelective_QNAME, UpdateByPrimaryKeySelective.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductByMerchantId")
    public JAXBElement<GetMerchantProductByMerchantId> createGetMerchantProductByMerchantId(GetMerchantProductByMerchantId value) {
        return new JAXBElement<GetMerchantProductByMerchantId>(_GetMerchantProductByMerchantId_QNAME, GetMerchantProductByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantProductByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantProductByIdResponse")
    public JAXBElement<GetMerchantProductByIdResponse> createGetMerchantProductByIdResponse(GetMerchantProductByIdResponse value) {
        return new JAXBElement<GetMerchantProductByIdResponse>(_GetMerchantProductByIdResponse_QNAME, GetMerchantProductByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByMerchantIdResponse")
    public JAXBElement<UpdateByMerchantIdResponse> createUpdateByMerchantIdResponse(UpdateByMerchantIdResponse value) {
        return new JAXBElement<UpdateByMerchantIdResponse>(_UpdateByMerchantIdResponse_QNAME, UpdateByMerchantIdResponse.class, null, value);
    }

}
