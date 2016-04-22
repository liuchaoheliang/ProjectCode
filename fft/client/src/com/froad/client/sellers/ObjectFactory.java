
package com.froad.client.sellers;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.sellers package. 
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

    private final static QName _AddSellerResponse_QNAME = new QName("http://service.CB.froad.com/", "addSellerResponse");
    private final static QName _BindingAccount_QNAME = new QName("http://service.CB.froad.com/", "bindingAccount");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _GetSellerByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getSellerByUserIdResponse");
    private final static QName _SelectByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "selectByIdResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetBySelectiveResponse_QNAME = new QName("http://service.CB.froad.com/", "getBySelectiveResponse");
    private final static QName _BindingAccountResponse_QNAME = new QName("http://service.CB.froad.com/", "bindingAccountResponse");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _GetSellerByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getSellerByMerchantId");
    private final static QName _GetBySelective_QNAME = new QName("http://service.CB.froad.com/", "getBySelective");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _AppException_QNAME = new QName("http://service.CB.froad.com/", "AppException");
    private final static QName _GetSellerByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getSellerByMerchantIdResponse");
    private final static QName _DeleteStateById_QNAME = new QName("http://service.CB.froad.com/", "deleteStateById");
    private final static QName _DeleteStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteStateByIdResponse");
    private final static QName _AddSeller_QNAME = new QName("http://service.CB.froad.com/", "addSeller");
    private final static QName _GetSellerByUserId_QNAME = new QName("http://service.CB.froad.com/", "getSellerByUserId");
    private final static QName _SelectById_QNAME = new QName("http://service.CB.froad.com/", "selectById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.sellers
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
     * Create an instance of {@link GetSellerByUserIdResponse }
     * 
     */
    public GetSellerByUserIdResponse createGetSellerByUserIdResponse() {
        return new GetSellerByUserIdResponse();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link BindingAccount }
     * 
     */
    public BindingAccount createBindingAccount() {
        return new BindingAccount();
    }

    /**
     * Create an instance of {@link AddSellerResponse }
     * 
     */
    public AddSellerResponse createAddSellerResponse() {
        return new AddSellerResponse();
    }

    /**
     * Create an instance of {@link GetSellerByMerchantId }
     * 
     */
    public GetSellerByMerchantId createGetSellerByMerchantId() {
        return new GetSellerByMerchantId();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link BindingAccountResponse }
     * 
     */
    public BindingAccountResponse createBindingAccountResponse() {
        return new BindingAccountResponse();
    }

    /**
     * Create an instance of {@link GetBySelectiveResponse }
     * 
     */
    public GetBySelectiveResponse createGetBySelectiveResponse() {
        return new GetBySelectiveResponse();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link AppException }
     * 
     */
    public AppException createAppException() {
        return new AppException();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link GetBySelective }
     * 
     */
    public GetBySelective createGetBySelective() {
        return new GetBySelective();
    }

    /**
     * Create an instance of {@link SelectById }
     * 
     */
    public SelectById createSelectById() {
        return new SelectById();
    }

    /**
     * Create an instance of {@link GetSellerByUserId }
     * 
     */
    public GetSellerByUserId createGetSellerByUserId() {
        return new GetSellerByUserId();
    }

    /**
     * Create an instance of {@link AddSeller }
     * 
     */
    public AddSeller createAddSeller() {
        return new AddSeller();
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
     * Create an instance of {@link GetSellerByMerchantIdResponse }
     * 
     */
    public GetSellerByMerchantIdResponse createGetSellerByMerchantIdResponse() {
        return new GetSellerByMerchantIdResponse();
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
     * Create an instance of {@link FundsChannel }
     * 
     */
    public FundsChannel createFundsChannel() {
        return new FundsChannel();
    }

    /**
     * Create an instance of {@link RuleDetailTemplet }
     * 
     */
    public RuleDetailTemplet createRuleDetailTemplet() {
        return new RuleDetailTemplet();
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
     * Create an instance of {@link Buyers }
     * 
     */
    public Buyers createBuyers() {
        return new Buyers();
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
     * Create an instance of {@link Seller }
     * 
     */
    public Seller createSeller() {
        return new Seller();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSellerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addSellerResponse")
    public JAXBElement<AddSellerResponse> createAddSellerResponse(AddSellerResponse value) {
        return new JAXBElement<AddSellerResponse>(_AddSellerResponse_QNAME, AddSellerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BindingAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "bindingAccount")
    public JAXBElement<BindingAccount> createBindingAccount(BindingAccount value) {
        return new JAXBElement<BindingAccount>(_BindingAccount_QNAME, BindingAccount.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSellerByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSellerByUserIdResponse")
    public JAXBElement<GetSellerByUserIdResponse> createGetSellerByUserIdResponse(GetSellerByUserIdResponse value) {
        return new JAXBElement<GetSellerByUserIdResponse>(_GetSellerByUserIdResponse_QNAME, GetSellerByUserIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBySelectiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getBySelectiveResponse")
    public JAXBElement<GetBySelectiveResponse> createGetBySelectiveResponse(GetBySelectiveResponse value) {
        return new JAXBElement<GetBySelectiveResponse>(_GetBySelectiveResponse_QNAME, GetBySelectiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BindingAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "bindingAccountResponse")
    public JAXBElement<BindingAccountResponse> createBindingAccountResponse(BindingAccountResponse value) {
        return new JAXBElement<BindingAccountResponse>(_BindingAccountResponse_QNAME, BindingAccountResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSellerByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSellerByMerchantId")
    public JAXBElement<GetSellerByMerchantId> createGetSellerByMerchantId(GetSellerByMerchantId value) {
        return new JAXBElement<GetSellerByMerchantId>(_GetSellerByMerchantId_QNAME, GetSellerByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBySelective }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getBySelective")
    public JAXBElement<GetBySelective> createGetBySelective(GetBySelective value) {
        return new JAXBElement<GetBySelective>(_GetBySelective_QNAME, GetBySelective.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AppException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "AppException")
    public JAXBElement<AppException> createAppException(AppException value) {
        return new JAXBElement<AppException>(_AppException_QNAME, AppException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSellerByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSellerByMerchantIdResponse")
    public JAXBElement<GetSellerByMerchantIdResponse> createGetSellerByMerchantIdResponse(GetSellerByMerchantIdResponse value) {
        return new JAXBElement<GetSellerByMerchantIdResponse>(_GetSellerByMerchantIdResponse_QNAME, GetSellerByMerchantIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSeller }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addSeller")
    public JAXBElement<AddSeller> createAddSeller(AddSeller value) {
        return new JAXBElement<AddSeller>(_AddSeller_QNAME, AddSeller.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSellerByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSellerByUserId")
    public JAXBElement<GetSellerByUserId> createGetSellerByUserId(GetSellerByUserId value) {
        return new JAXBElement<GetSellerByUserId>(_GetSellerByUserId_QNAME, GetSellerByUserId.class, null, value);
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
