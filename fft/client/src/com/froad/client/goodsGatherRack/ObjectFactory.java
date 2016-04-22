
package com.froad.client.goodsGatherRack;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.goodsGatherRack package. 
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

    private final static QName _AddGoodsGatherRack_QNAME = new QName("http://service.CB.froad.com/", "addGoodsGatherRack");
    private final static QName _GetGoodsGatherRack_QNAME = new QName("http://service.CB.froad.com/", "getGoodsGatherRack");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _GetGoodsGatherRackByGoodsIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsGatherRackByGoodsIdResponse");
    private final static QName _SelectByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "selectByIdResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetGoodsGatherRackListByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsGatherRackListByPagerResponse");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _GetGoodsGatherRackListByPager_QNAME = new QName("http://service.CB.froad.com/", "getGoodsGatherRackListByPager");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _GetGoodsGatherRackResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsGatherRackResponse");
    private final static QName _DeleteStateById_QNAME = new QName("http://service.CB.froad.com/", "deleteStateById");
    private final static QName _DeleteStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteStateByIdResponse");
    private final static QName _AddGoodsGatherRackResponse_QNAME = new QName("http://service.CB.froad.com/", "addGoodsGatherRackResponse");
    private final static QName _GetGoodsGatherRackByGoodsId_QNAME = new QName("http://service.CB.froad.com/", "getGoodsGatherRackByGoodsId");
    private final static QName _SelectById_QNAME = new QName("http://service.CB.froad.com/", "selectById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.goodsGatherRack
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectByIdResponse }
     * 
     */
    public SelectByIdResponse createSelectByIdResponse() {
        return new SelectByIdResponse();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link GetGoodsGatherRackByGoodsIdResponse }
     * 
     */
    public GetGoodsGatherRackByGoodsIdResponse createGetGoodsGatherRackByGoodsIdResponse() {
        return new GetGoodsGatherRackByGoodsIdResponse();
    }

    /**
     * Create an instance of {@link AddGoodsGatherRack }
     * 
     */
    public AddGoodsGatherRack createAddGoodsGatherRack() {
        return new AddGoodsGatherRack();
    }

    /**
     * Create an instance of {@link GetGoodsGatherRack }
     * 
     */
    public GetGoodsGatherRack createGetGoodsGatherRack() {
        return new GetGoodsGatherRack();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link GetGoodsGatherRackListByPagerResponse }
     * 
     */
    public GetGoodsGatherRackListByPagerResponse createGetGoodsGatherRackListByPagerResponse() {
        return new GetGoodsGatherRackListByPagerResponse();
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
     * Create an instance of {@link GetGoodsGatherRackListByPager }
     * 
     */
    public GetGoodsGatherRackListByPager createGetGoodsGatherRackListByPager() {
        return new GetGoodsGatherRackListByPager();
    }

    /**
     * Create an instance of {@link SelectById }
     * 
     */
    public SelectById createSelectById() {
        return new SelectById();
    }

    /**
     * Create an instance of {@link GetGoodsGatherRackByGoodsId }
     * 
     */
    public GetGoodsGatherRackByGoodsId createGetGoodsGatherRackByGoodsId() {
        return new GetGoodsGatherRackByGoodsId();
    }

    /**
     * Create an instance of {@link AddGoodsGatherRackResponse }
     * 
     */
    public AddGoodsGatherRackResponse createAddGoodsGatherRackResponse() {
        return new AddGoodsGatherRackResponse();
    }

    /**
     * Create an instance of {@link DeleteStateByIdResponse }
     * 
     */
    public DeleteStateByIdResponse createDeleteStateByIdResponse() {
        return new DeleteStateByIdResponse();
    }

    /**
     * Create an instance of {@link DeleteStateById }
     * 
     */
    public DeleteStateById createDeleteStateById() {
        return new DeleteStateById();
    }

    /**
     * Create an instance of {@link GetGoodsGatherRackResponse }
     * 
     */
    public GetGoodsGatherRackResponse createGetGoodsGatherRackResponse() {
        return new GetGoodsGatherRackResponse();
    }

    /**
     * Create an instance of {@link BuyerChannel }
     * 
     */
    public BuyerChannel createBuyerChannel() {
        return new BuyerChannel();
    }

    /**
     * Create an instance of {@link RuleDetail }
     * 
     */
    public RuleDetail createRuleDetail() {
        return new RuleDetail();
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
     * Create an instance of {@link RuleDetailTemplet }
     * 
     */
    public RuleDetailTemplet createRuleDetailTemplet() {
        return new RuleDetailTemplet();
    }

    /**
     * Create an instance of {@link GoodsGatherRack }
     * 
     */
    public GoodsGatherRack createGoodsGatherRack() {
        return new GoodsGatherRack();
    }

    /**
     * Create an instance of {@link Rule }
     * 
     */
    public Rule createRule() {
        return new Rule();
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
     * Create an instance of {@link SellerRule }
     * 
     */
    public SellerRule createSellerRule() {
        return new SellerRule();
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
     * Create an instance of {@link GoodsGatherRackImages }
     * 
     */
    public GoodsGatherRackImages createGoodsGatherRackImages() {
        return new GoodsGatherRackImages();
    }

    /**
     * Create an instance of {@link GoodsRackAttribute }
     * 
     */
    public GoodsRackAttribute createGoodsRackAttribute() {
        return new GoodsRackAttribute();
    }

    /**
     * Create an instance of {@link SellerChannel }
     * 
     */
    public SellerChannel createSellerChannel() {
        return new SellerChannel();
    }

    /**
     * Create an instance of {@link TransRule }
     * 
     */
    public TransRule createTransRule() {
        return new TransRule();
    }

    /**
     * Create an instance of {@link MerchantPresent }
     * 
     */
    public MerchantPresent createMerchantPresent() {
        return new MerchantPresent();
    }

    /**
     * Create an instance of {@link Seller }
     * 
     */
    public Seller createSeller() {
        return new Seller();
    }

    /**
     * Create an instance of {@link TagClassifyB }
     * 
     */
    public TagClassifyB createTagClassifyB() {
        return new TagClassifyB();
    }

    /**
     * Create an instance of {@link TagClassifyA }
     * 
     */
    public TagClassifyA createTagClassifyA() {
        return new TagClassifyA();
    }

    /**
     * Create an instance of {@link MerchantPhoto }
     * 
     */
    public MerchantPhoto createMerchantPhoto() {
        return new MerchantPhoto();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsGatherRack }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsGatherRack")
    public JAXBElement<AddGoodsGatherRack> createAddGoodsGatherRack(AddGoodsGatherRack value) {
        return new JAXBElement<AddGoodsGatherRack>(_AddGoodsGatherRack_QNAME, AddGoodsGatherRack.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsGatherRack }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsGatherRack")
    public JAXBElement<GetGoodsGatherRack> createGetGoodsGatherRack(GetGoodsGatherRack value) {
        return new JAXBElement<GetGoodsGatherRack>(_GetGoodsGatherRack_QNAME, GetGoodsGatherRack.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsGatherRackByGoodsIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsGatherRackByGoodsIdResponse")
    public JAXBElement<GetGoodsGatherRackByGoodsIdResponse> createGetGoodsGatherRackByGoodsIdResponse(GetGoodsGatherRackByGoodsIdResponse value) {
        return new JAXBElement<GetGoodsGatherRackByGoodsIdResponse>(_GetGoodsGatherRackByGoodsIdResponse_QNAME, GetGoodsGatherRackByGoodsIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "selectByIdResponse")
    public JAXBElement<SelectByIdResponse> createSelectByIdResponse(SelectByIdResponse value) {
        return new JAXBElement<SelectByIdResponse>(_SelectByIdResponse_QNAME, SelectByIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsGatherRackListByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsGatherRackListByPagerResponse")
    public JAXBElement<GetGoodsGatherRackListByPagerResponse> createGetGoodsGatherRackListByPagerResponse(GetGoodsGatherRackListByPagerResponse value) {
        return new JAXBElement<GetGoodsGatherRackListByPagerResponse>(_GetGoodsGatherRackListByPagerResponse_QNAME, GetGoodsGatherRackListByPagerResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsGatherRackListByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsGatherRackListByPager")
    public JAXBElement<GetGoodsGatherRackListByPager> createGetGoodsGatherRackListByPager(GetGoodsGatherRackListByPager value) {
        return new JAXBElement<GetGoodsGatherRackListByPager>(_GetGoodsGatherRackListByPager_QNAME, GetGoodsGatherRackListByPager.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsGatherRackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsGatherRackResponse")
    public JAXBElement<GetGoodsGatherRackResponse> createGetGoodsGatherRackResponse(GetGoodsGatherRackResponse value) {
        return new JAXBElement<GetGoodsGatherRackResponse>(_GetGoodsGatherRackResponse_QNAME, GetGoodsGatherRackResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteStateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteStateById")
    public JAXBElement<DeleteStateById> createDeleteStateById(DeleteStateById value) {
        return new JAXBElement<DeleteStateById>(_DeleteStateById_QNAME, DeleteStateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteStateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteStateByIdResponse")
    public JAXBElement<DeleteStateByIdResponse> createDeleteStateByIdResponse(DeleteStateByIdResponse value) {
        return new JAXBElement<DeleteStateByIdResponse>(_DeleteStateByIdResponse_QNAME, DeleteStateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsGatherRackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsGatherRackResponse")
    public JAXBElement<AddGoodsGatherRackResponse> createAddGoodsGatherRackResponse(AddGoodsGatherRackResponse value) {
        return new JAXBElement<AddGoodsGatherRackResponse>(_AddGoodsGatherRackResponse_QNAME, AddGoodsGatherRackResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsGatherRackByGoodsId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsGatherRackByGoodsId")
    public JAXBElement<GetGoodsGatherRackByGoodsId> createGetGoodsGatherRackByGoodsId(GetGoodsGatherRackByGoodsId value) {
        return new JAXBElement<GetGoodsGatherRackByGoodsId>(_GetGoodsGatherRackByGoodsId_QNAME, GetGoodsGatherRackByGoodsId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "selectById")
    public JAXBElement<SelectById> createSelectById(SelectById value) {
        return new JAXBElement<SelectById>(_SelectById_QNAME, SelectById.class, null, value);
    }

}
