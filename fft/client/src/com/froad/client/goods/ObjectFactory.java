
package com.froad.client.goods;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.goods package. 
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

    private final static QName _AddGoods_QNAME = new QName("http://service.CB.froad.com/", "addGoods");
    private final static QName _GetGoodsById_QNAME = new QName("http://service.CB.froad.com/", "getGoodsById");
    private final static QName _GetGoodsByPager_QNAME = new QName("http://service.CB.froad.com/", "getGoodsByPager");
    private final static QName _GetGoodsByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsByMerchantIdResponse");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _GetGoodsByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsByIdResponse");
    private final static QName _GetGoodsByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getGoodsByMerchantId");
    private final static QName _GetGoodsByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsByPagerResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _UpdateStateById_QNAME = new QName("http://service.CB.froad.com/", "updateStateById");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _GetStoreByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getStoreByIdResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _UpdateStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateStateByIdResponse");
    private final static QName _AddGoodsResponse_QNAME = new QName("http://service.CB.froad.com/", "addGoodsResponse");
    private final static QName _GetStoreById_QNAME = new QName("http://service.CB.froad.com/", "getStoreById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.goods
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetGoodsByPagerResponse }
     * 
     */
    public GetGoodsByPagerResponse createGetGoodsByPagerResponse() {
        return new GetGoodsByPagerResponse();
    }

    /**
     * Create an instance of {@link GetGoodsByMerchantId }
     * 
     */
    public GetGoodsByMerchantId createGetGoodsByMerchantId() {
        return new GetGoodsByMerchantId();
    }

    /**
     * Create an instance of {@link GetGoodsByIdResponse }
     * 
     */
    public GetGoodsByIdResponse createGetGoodsByIdResponse() {
        return new GetGoodsByIdResponse();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link GetGoodsByMerchantIdResponse }
     * 
     */
    public GetGoodsByMerchantIdResponse createGetGoodsByMerchantIdResponse() {
        return new GetGoodsByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link GetGoodsById }
     * 
     */
    public GetGoodsById createGetGoodsById() {
        return new GetGoodsById();
    }

    /**
     * Create an instance of {@link GetGoodsByPager }
     * 
     */
    public GetGoodsByPager createGetGoodsByPager() {
        return new GetGoodsByPager();
    }

    /**
     * Create an instance of {@link AddGoods }
     * 
     */
    public AddGoods createAddGoods() {
        return new AddGoods();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link UpdateStateById }
     * 
     */
    public UpdateStateById createUpdateStateById() {
        return new UpdateStateById();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link GetStoreByIdResponse }
     * 
     */
    public GetStoreByIdResponse createGetStoreByIdResponse() {
        return new GetStoreByIdResponse();
    }

    /**
     * Create an instance of {@link GetStoreById }
     * 
     */
    public GetStoreById createGetStoreById() {
        return new GetStoreById();
    }

    /**
     * Create an instance of {@link AddGoodsResponse }
     * 
     */
    public AddGoodsResponse createAddGoodsResponse() {
        return new AddGoodsResponse();
    }

    /**
     * Create an instance of {@link UpdateStateByIdResponse }
     * 
     */
    public UpdateStateByIdResponse createUpdateStateByIdResponse() {
        return new UpdateStateByIdResponse();
    }

    /**
     * Create an instance of {@link BuyerChannel }
     * 
     */
    public BuyerChannel createBuyerChannel() {
        return new BuyerChannel();
    }

    /**
     * Create an instance of {@link Store }
     * 
     */
    public Store createStore() {
        return new Store();
    }

    /**
     * Create an instance of {@link MerchantProduct }
     * 
     */
    public MerchantProduct createMerchantProduct() {
        return new MerchantProduct();
    }

    /**
     * Create an instance of {@link GoodsCategory }
     * 
     */
    public GoodsCategory createGoodsCategory() {
        return new GoodsCategory();
    }

    /**
     * Create an instance of {@link Goods }
     * 
     */
    public Goods createGoods() {
        return new Goods();
    }

    /**
     * Create an instance of {@link MerchantPreferential }
     * 
     */
    public MerchantPreferential createMerchantPreferential() {
        return new MerchantPreferential();
    }

    /**
     * Create an instance of {@link FundsChannel }
     * 
     */
    public FundsChannel createFundsChannel() {
        return new FundsChannel();
    }

    /**
     * Create an instance of {@link MerchantTrain }
     * 
     */
    public MerchantTrain createMerchantTrain() {
        return new MerchantTrain();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link TagDistrictA }
     * 
     */
    public TagDistrictA createTagDistrictA() {
        return new TagDistrictA();
    }

    /**
     * Create an instance of {@link TagMAP }
     * 
     */
    public TagMAP createTagMAP() {
        return new TagMAP();
    }

    /**
     * Create an instance of {@link UserCertification }
     * 
     */
    public UserCertification createUserCertification() {
        return new UserCertification();
    }

    /**
     * Create an instance of {@link TagDistrictB }
     * 
     */
    public TagDistrictB createTagDistrictB() {
        return new TagDistrictB();
    }

    /**
     * Create an instance of {@link Merchant }
     * 
     */
    public Merchant createMerchant() {
        return new Merchant();
    }

    /**
     * Create an instance of {@link Buyers }
     * 
     */
    public Buyers createBuyers() {
        return new Buyers();
    }

    /**
     * Create an instance of {@link GoodsRackAttribute }
     * 
     */
    public GoodsRackAttribute createGoodsRackAttribute() {
        return new GoodsRackAttribute();
    }

    /**
     * Create an instance of {@link MerchantPresent }
     * 
     */
    public MerchantPresent createMerchantPresent() {
        return new MerchantPresent();
    }

    /**
     * Create an instance of {@link TagClassifyB }
     * 
     */
    public TagClassifyB createTagClassifyB() {
        return new TagClassifyB();
    }

    /**
     * Create an instance of {@link MerchantPhoto }
     * 
     */
    public MerchantPhoto createMerchantPhoto() {
        return new MerchantPhoto();
    }

    /**
     * Create an instance of {@link TagClassifyA }
     * 
     */
    public TagClassifyA createTagClassifyA() {
        return new TagClassifyA();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoods }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoods")
    public JAXBElement<AddGoods> createAddGoods(AddGoods value) {
        return new JAXBElement<AddGoods>(_AddGoods_QNAME, AddGoods.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsById")
    public JAXBElement<GetGoodsById> createGetGoodsById(GetGoodsById value) {
        return new JAXBElement<GetGoodsById>(_GetGoodsById_QNAME, GetGoodsById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsByPager")
    public JAXBElement<GetGoodsByPager> createGetGoodsByPager(GetGoodsByPager value) {
        return new JAXBElement<GetGoodsByPager>(_GetGoodsByPager_QNAME, GetGoodsByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsByMerchantIdResponse")
    public JAXBElement<GetGoodsByMerchantIdResponse> createGetGoodsByMerchantIdResponse(GetGoodsByMerchantIdResponse value) {
        return new JAXBElement<GetGoodsByMerchantIdResponse>(_GetGoodsByMerchantIdResponse_QNAME, GetGoodsByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteById")
    public JAXBElement<DeleteById> createDeleteById(DeleteById value) {
        return new JAXBElement<DeleteById>(_DeleteById_QNAME, DeleteById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsByIdResponse")
    public JAXBElement<GetGoodsByIdResponse> createGetGoodsByIdResponse(GetGoodsByIdResponse value) {
        return new JAXBElement<GetGoodsByIdResponse>(_GetGoodsByIdResponse_QNAME, GetGoodsByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsByMerchantId")
    public JAXBElement<GetGoodsByMerchantId> createGetGoodsByMerchantId(GetGoodsByMerchantId value) {
        return new JAXBElement<GetGoodsByMerchantId>(_GetGoodsByMerchantId_QNAME, GetGoodsByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsByPagerResponse")
    public JAXBElement<GetGoodsByPagerResponse> createGetGoodsByPagerResponse(GetGoodsByPagerResponse value) {
        return new JAXBElement<GetGoodsByPagerResponse>(_GetGoodsByPagerResponse_QNAME, GetGoodsByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateById")
    public JAXBElement<UpdateById> createUpdateById(UpdateById value) {
        return new JAXBElement<UpdateById>(_UpdateById_QNAME, UpdateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateStateById")
    public JAXBElement<UpdateStateById> createUpdateStateById(UpdateStateById value) {
        return new JAXBElement<UpdateStateById>(_UpdateStateById_QNAME, UpdateStateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteByIdResponse")
    public JAXBElement<DeleteByIdResponse> createDeleteByIdResponse(DeleteByIdResponse value) {
        return new JAXBElement<DeleteByIdResponse>(_DeleteByIdResponse_QNAME, DeleteByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoreByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getStoreByIdResponse")
    public JAXBElement<GetStoreByIdResponse> createGetStoreByIdResponse(GetStoreByIdResponse value) {
        return new JAXBElement<GetStoreByIdResponse>(_GetStoreByIdResponse_QNAME, GetStoreByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByIdResponse")
    public JAXBElement<UpdateByIdResponse> createUpdateByIdResponse(UpdateByIdResponse value) {
        return new JAXBElement<UpdateByIdResponse>(_UpdateByIdResponse_QNAME, UpdateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateStateByIdResponse")
    public JAXBElement<UpdateStateByIdResponse> createUpdateStateByIdResponse(UpdateStateByIdResponse value) {
        return new JAXBElement<UpdateStateByIdResponse>(_UpdateStateByIdResponse_QNAME, UpdateStateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsResponse")
    public JAXBElement<AddGoodsResponse> createAddGoodsResponse(AddGoodsResponse value) {
        return new JAXBElement<AddGoodsResponse>(_AddGoodsResponse_QNAME, AddGoodsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoreById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getStoreById")
    public JAXBElement<GetStoreById> createGetStoreById(GetStoreById value) {
        return new JAXBElement<GetStoreById>(_GetStoreById_QNAME, GetStoreById.class, null, value);
    }

}
