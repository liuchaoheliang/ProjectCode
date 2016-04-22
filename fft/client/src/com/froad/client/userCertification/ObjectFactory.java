
package com.froad.client.userCertification;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.userCertification package. 
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

    private final static QName _GetUserCertBySelectiveResponse_QNAME = new QName("http://service.CB.froad.com/", "getUserCertBySelectiveResponse");
    private final static QName _AddResponse_QNAME = new QName("http://service.CB.froad.com/", "addResponse");
    private final static QName _GetUserCertBySelective_QNAME = new QName("http://service.CB.froad.com/", "getUserCertBySelective");
    private final static QName _GetUserCertByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getUserCertByUserIdResponse");
    private final static QName _AddOrUpdateBindingNewResponse_QNAME = new QName("http://service.CB.froad.com/", "addOrUpdateBindingNewResponse");
    private final static QName _AppException_QNAME = new QName("http://service.CB.froad.com/", "AppException");
    private final static QName _CheckAccount_QNAME = new QName("http://service.CB.froad.com/", "checkAccount");
    private final static QName _GetUserCertByUserId_QNAME = new QName("http://service.CB.froad.com/", "getUserCertByUserId");
    private final static QName _CheckAccountResponse_QNAME = new QName("http://service.CB.froad.com/", "checkAccountResponse");
    private final static QName _Add_QNAME = new QName("http://service.CB.froad.com/", "add");
    private final static QName _AddOrUpdateBindingNew_QNAME = new QName("http://service.CB.froad.com/", "addOrUpdateBindingNew");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.userCertification
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AppException }
     * 
     */
    public AppException createAppException() {
        return new AppException();
    }

    /**
     * Create an instance of {@link AddOrUpdateBindingNewResponse }
     * 
     */
    public AddOrUpdateBindingNewResponse createAddOrUpdateBindingNewResponse() {
        return new AddOrUpdateBindingNewResponse();
    }

    /**
     * Create an instance of {@link GetUserCertByUserIdResponse }
     * 
     */
    public GetUserCertByUserIdResponse createGetUserCertByUserIdResponse() {
        return new GetUserCertByUserIdResponse();
    }

    /**
     * Create an instance of {@link GetUserCertBySelective }
     * 
     */
    public GetUserCertBySelective createGetUserCertBySelective() {
        return new GetUserCertBySelective();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link GetUserCertBySelectiveResponse }
     * 
     */
    public GetUserCertBySelectiveResponse createGetUserCertBySelectiveResponse() {
        return new GetUserCertBySelectiveResponse();
    }

    /**
     * Create an instance of {@link AddOrUpdateBindingNew }
     * 
     */
    public AddOrUpdateBindingNew createAddOrUpdateBindingNew() {
        return new AddOrUpdateBindingNew();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link CheckAccountResponse }
     * 
     */
    public CheckAccountResponse createCheckAccountResponse() {
        return new CheckAccountResponse();
    }

    /**
     * Create an instance of {@link GetUserCertByUserId }
     * 
     */
    public GetUserCertByUserId createGetUserCertByUserId() {
        return new GetUserCertByUserId();
    }

    /**
     * Create an instance of {@link CheckAccount }
     * 
     */
    public CheckAccount createCheckAccount() {
        return new CheckAccount();
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
     * Create an instance of {@link BuyerChannel }
     * 
     */
    public BuyerChannel createBuyerChannel() {
        return new BuyerChannel();
    }

    /**
     * Create an instance of {@link Buyers }
     * 
     */
    public Buyers createBuyers() {
        return new Buyers();
    }

    /**
     * Create an instance of {@link FundsChannel }
     * 
     */
    public FundsChannel createFundsChannel() {
        return new FundsChannel();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserCertBySelectiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getUserCertBySelectiveResponse")
    public JAXBElement<GetUserCertBySelectiveResponse> createGetUserCertBySelectiveResponse(GetUserCertBySelectiveResponse value) {
        return new JAXBElement<GetUserCertBySelectiveResponse>(_GetUserCertBySelectiveResponse_QNAME, GetUserCertBySelectiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserCertBySelective }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getUserCertBySelective")
    public JAXBElement<GetUserCertBySelective> createGetUserCertBySelective(GetUserCertBySelective value) {
        return new JAXBElement<GetUserCertBySelective>(_GetUserCertBySelective_QNAME, GetUserCertBySelective.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserCertByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getUserCertByUserIdResponse")
    public JAXBElement<GetUserCertByUserIdResponse> createGetUserCertByUserIdResponse(GetUserCertByUserIdResponse value) {
        return new JAXBElement<GetUserCertByUserIdResponse>(_GetUserCertByUserIdResponse_QNAME, GetUserCertByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddOrUpdateBindingNewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addOrUpdateBindingNewResponse")
    public JAXBElement<AddOrUpdateBindingNewResponse> createAddOrUpdateBindingNewResponse(AddOrUpdateBindingNewResponse value) {
        return new JAXBElement<AddOrUpdateBindingNewResponse>(_AddOrUpdateBindingNewResponse_QNAME, AddOrUpdateBindingNewResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "checkAccount")
    public JAXBElement<CheckAccount> createCheckAccount(CheckAccount value) {
        return new JAXBElement<CheckAccount>(_CheckAccount_QNAME, CheckAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserCertByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getUserCertByUserId")
    public JAXBElement<GetUserCertByUserId> createGetUserCertByUserId(GetUserCertByUserId value) {
        return new JAXBElement<GetUserCertByUserId>(_GetUserCertByUserId_QNAME, GetUserCertByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "checkAccountResponse")
    public JAXBElement<CheckAccountResponse> createCheckAccountResponse(CheckAccountResponse value) {
        return new JAXBElement<CheckAccountResponse>(_CheckAccountResponse_QNAME, CheckAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddOrUpdateBindingNew }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addOrUpdateBindingNew")
    public JAXBElement<AddOrUpdateBindingNew> createAddOrUpdateBindingNew(AddOrUpdateBindingNew value) {
        return new JAXBElement<AddOrUpdateBindingNew>(_AddOrUpdateBindingNew_QNAME, AddOrUpdateBindingNew.class, null, value);
    }

}
