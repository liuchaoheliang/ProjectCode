
package com.froad.client.goodsCarryRack;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.goodsCarryRack package. 
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

    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _SelectByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "selectByIdResponse");
    private final static QName _GetGoodsCarryRack_QNAME = new QName("http://service.CB.froad.com/", "getGoodsCarryRack");
    private final static QName _GetGoodsCarryRackByGoodsId_QNAME = new QName("http://service.CB.froad.com/", "getGoodsCarryRackByGoodsId");
    private final static QName _GetGoodsCarryRackListByPager_QNAME = new QName("http://service.CB.froad.com/", "getGoodsCarryRackListByPager");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetGoodsCarryRackByGoodsIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsCarryRackByGoodsIdResponse");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _AddGoodsCarryRackResponse_QNAME = new QName("http://service.CB.froad.com/", "addGoodsCarryRackResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _AddGoodsCarryRack_QNAME = new QName("http://service.CB.froad.com/", "addGoodsCarryRack");
    private final static QName _GetGoodsCarryRackListByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsCarryRackListByPagerResponse");
    private final static QName _GetGoodsCarryRackResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsCarryRackResponse");
    private final static QName _DeleteStateById_QNAME = new QName("http://service.CB.froad.com/", "deleteStateById");
    private final static QName _DeleteStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteStateByIdResponse");
    private final static QName _SelectById_QNAME = new QName("http://service.CB.froad.com/", "selectById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.goodsCarryRack
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
     * Create an instance of {@link GetGoodsCarryRack }
     * 
     */
    public GetGoodsCarryRack createGetGoodsCarryRack() {
        return new GetGoodsCarryRack();
    }

    /**
     * Create an instance of {@link GetGoodsCarryRackByGoodsId }
     * 
     */
    public GetGoodsCarryRackByGoodsId createGetGoodsCarryRackByGoodsId() {
        return new GetGoodsCarryRackByGoodsId();
    }

    /**
     * Create an instance of {@link GetGoodsCarryRackListByPager }
     * 
     */
    public GetGoodsCarryRackListByPager createGetGoodsCarryRackListByPager() {
        return new GetGoodsCarryRackListByPager();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link GetGoodsCarryRackByGoodsIdResponse }
     * 
     */
    public GetGoodsCarryRackByGoodsIdResponse createGetGoodsCarryRackByGoodsIdResponse() {
        return new GetGoodsCarryRackByGoodsIdResponse();
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
     * Create an instance of {@link AddGoodsCarryRack }
     * 
     */
    public AddGoodsCarryRack createAddGoodsCarryRack() {
        return new AddGoodsCarryRack();
    }

    /**
     * Create an instance of {@link GetGoodsCarryRackListByPagerResponse }
     * 
     */
    public GetGoodsCarryRackListByPagerResponse createGetGoodsCarryRackListByPagerResponse() {
        return new GetGoodsCarryRackListByPagerResponse();
    }

    /**
     * Create an instance of {@link AddGoodsCarryRackResponse }
     * 
     */
    public AddGoodsCarryRackResponse createAddGoodsCarryRackResponse() {
        return new AddGoodsCarryRackResponse();
    }

    /**
     * Create an instance of {@link SelectById }
     * 
     */
    public SelectById createSelectById() {
        return new SelectById();
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
     * Create an instance of {@link GetGoodsCarryRackResponse }
     * 
     */
    public GetGoodsCarryRackResponse createGetGoodsCarryRackResponse() {
        return new GetGoodsCarryRackResponse();
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
     * Create an instance of {@link GoodsCarryRack }
     * 
     */
    public GoodsCarryRack createGoodsCarryRack() {
        return new GoodsCarryRack();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteById")
    public JAXBElement<DeleteById> createDeleteById(DeleteById value) {
        return new JAXBElement<DeleteById>(_DeleteById_QNAME, DeleteById.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsCarryRack }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsCarryRack")
    public JAXBElement<GetGoodsCarryRack> createGetGoodsCarryRack(GetGoodsCarryRack value) {
        return new JAXBElement<GetGoodsCarryRack>(_GetGoodsCarryRack_QNAME, GetGoodsCarryRack.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsCarryRackByGoodsId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsCarryRackByGoodsId")
    public JAXBElement<GetGoodsCarryRackByGoodsId> createGetGoodsCarryRackByGoodsId(GetGoodsCarryRackByGoodsId value) {
        return new JAXBElement<GetGoodsCarryRackByGoodsId>(_GetGoodsCarryRackByGoodsId_QNAME, GetGoodsCarryRackByGoodsId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsCarryRackListByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsCarryRackListByPager")
    public JAXBElement<GetGoodsCarryRackListByPager> createGetGoodsCarryRackListByPager(GetGoodsCarryRackListByPager value) {
        return new JAXBElement<GetGoodsCarryRackListByPager>(_GetGoodsCarryRackListByPager_QNAME, GetGoodsCarryRackListByPager.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsCarryRackByGoodsIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsCarryRackByGoodsIdResponse")
    public JAXBElement<GetGoodsCarryRackByGoodsIdResponse> createGetGoodsCarryRackByGoodsIdResponse(GetGoodsCarryRackByGoodsIdResponse value) {
        return new JAXBElement<GetGoodsCarryRackByGoodsIdResponse>(_GetGoodsCarryRackByGoodsIdResponse_QNAME, GetGoodsCarryRackByGoodsIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsCarryRackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsCarryRackResponse")
    public JAXBElement<AddGoodsCarryRackResponse> createAddGoodsCarryRackResponse(AddGoodsCarryRackResponse value) {
        return new JAXBElement<AddGoodsCarryRackResponse>(_AddGoodsCarryRackResponse_QNAME, AddGoodsCarryRackResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsCarryRack }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsCarryRack")
    public JAXBElement<AddGoodsCarryRack> createAddGoodsCarryRack(AddGoodsCarryRack value) {
        return new JAXBElement<AddGoodsCarryRack>(_AddGoodsCarryRack_QNAME, AddGoodsCarryRack.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsCarryRackListByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsCarryRackListByPagerResponse")
    public JAXBElement<GetGoodsCarryRackListByPagerResponse> createGetGoodsCarryRackListByPagerResponse(GetGoodsCarryRackListByPagerResponse value) {
        return new JAXBElement<GetGoodsCarryRackListByPagerResponse>(_GetGoodsCarryRackListByPagerResponse_QNAME, GetGoodsCarryRackListByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsCarryRackResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsCarryRackResponse")
    public JAXBElement<GetGoodsCarryRackResponse> createGetGoodsCarryRackResponse(GetGoodsCarryRackResponse value) {
        return new JAXBElement<GetGoodsCarryRackResponse>(_GetGoodsCarryRackResponse_QNAME, GetGoodsCarryRackResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "selectById")
    public JAXBElement<SelectById> createSelectById(SelectById value) {
        return new JAXBElement<SelectById>(_SelectById_QNAME, SelectById.class, null, value);
    }

}
