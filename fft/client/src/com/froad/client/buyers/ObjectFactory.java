
package com.froad.client.buyers;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.buyers package. 
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

    private final static QName _UpdateBuyerAndBuyerChannel_QNAME = new QName("http://service.CB.froad.com/", "updateBuyerAndBuyerChannel");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _UpdateBuyerAndBuyerChannelResponse_QNAME = new QName("http://service.CB.froad.com/", "updateBuyerAndBuyerChannelResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _AddBuyer_QNAME = new QName("http://service.CB.froad.com/", "addBuyer");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _GetBuyerByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getBuyerByUserIdResponse");
    private final static QName _DeleteStateById_QNAME = new QName("http://service.CB.froad.com/", "deleteStateById");
    private final static QName _DeleteStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteStateByIdResponse");
    private final static QName _GetBuyerByUserId_QNAME = new QName("http://service.CB.froad.com/", "getBuyerByUserId");
    private final static QName _GetByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getByIdResponse");
    private final static QName _GetById_QNAME = new QName("http://service.CB.froad.com/", "getById");
    private final static QName _AddBuyerResponse_QNAME = new QName("http://service.CB.froad.com/", "addBuyerResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.buyers
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateBuyerAndBuyerChannelResponse }
     * 
     */
    public UpdateBuyerAndBuyerChannelResponse createUpdateBuyerAndBuyerChannelResponse() {
        return new UpdateBuyerAndBuyerChannelResponse();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link UpdateBuyerAndBuyerChannel }
     * 
     */
    public UpdateBuyerAndBuyerChannel createUpdateBuyerAndBuyerChannel() {
        return new UpdateBuyerAndBuyerChannel();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link AddBuyer }
     * 
     */
    public AddBuyer createAddBuyer() {
        return new AddBuyer();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link GetBuyerByUserIdResponse }
     * 
     */
    public GetBuyerByUserIdResponse createGetBuyerByUserIdResponse() {
        return new GetBuyerByUserIdResponse();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link AddBuyerResponse }
     * 
     */
    public AddBuyerResponse createAddBuyerResponse() {
        return new AddBuyerResponse();
    }

    /**
     * Create an instance of {@link GetById }
     * 
     */
    public GetById createGetById() {
        return new GetById();
    }

    /**
     * Create an instance of {@link GetBuyerByUserId }
     * 
     */
    public GetBuyerByUserId createGetBuyerByUserId() {
        return new GetBuyerByUserId();
    }

    /**
     * Create an instance of {@link GetByIdResponse }
     * 
     */
    public GetByIdResponse createGetByIdResponse() {
        return new GetByIdResponse();
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
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link BuyerChannel }
     * 
     */
    public BuyerChannel createBuyerChannel() {
        return new BuyerChannel();
    }

    /**
     * Create an instance of {@link UserCertification }
     * 
     */
    public UserCertification createUserCertification() {
        return new UserCertification();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBuyerAndBuyerChannel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateBuyerAndBuyerChannel")
    public JAXBElement<UpdateBuyerAndBuyerChannel> createUpdateBuyerAndBuyerChannel(UpdateBuyerAndBuyerChannel value) {
        return new JAXBElement<UpdateBuyerAndBuyerChannel>(_UpdateBuyerAndBuyerChannel_QNAME, UpdateBuyerAndBuyerChannel.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateBuyerAndBuyerChannelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateBuyerAndBuyerChannelResponse")
    public JAXBElement<UpdateBuyerAndBuyerChannelResponse> createUpdateBuyerAndBuyerChannelResponse(UpdateBuyerAndBuyerChannelResponse value) {
        return new JAXBElement<UpdateBuyerAndBuyerChannelResponse>(_UpdateBuyerAndBuyerChannelResponse_QNAME, UpdateBuyerAndBuyerChannelResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBuyer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addBuyer")
    public JAXBElement<AddBuyer> createAddBuyer(AddBuyer value) {
        return new JAXBElement<AddBuyer>(_AddBuyer_QNAME, AddBuyer.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByIdResponse")
    public JAXBElement<UpdateByIdResponse> createUpdateByIdResponse(UpdateByIdResponse value) {
        return new JAXBElement<UpdateByIdResponse>(_UpdateByIdResponse_QNAME, UpdateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBuyerByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getBuyerByUserIdResponse")
    public JAXBElement<GetBuyerByUserIdResponse> createGetBuyerByUserIdResponse(GetBuyerByUserIdResponse value) {
        return new JAXBElement<GetBuyerByUserIdResponse>(_GetBuyerByUserIdResponse_QNAME, GetBuyerByUserIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBuyerByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getBuyerByUserId")
    public JAXBElement<GetBuyerByUserId> createGetBuyerByUserId(GetBuyerByUserId value) {
        return new JAXBElement<GetBuyerByUserId>(_GetBuyerByUserId_QNAME, GetBuyerByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByIdResponse")
    public JAXBElement<GetByIdResponse> createGetByIdResponse(GetByIdResponse value) {
        return new JAXBElement<GetByIdResponse>(_GetByIdResponse_QNAME, GetByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getById")
    public JAXBElement<GetById> createGetById(GetById value) {
        return new JAXBElement<GetById>(_GetById_QNAME, GetById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBuyerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addBuyerResponse")
    public JAXBElement<AddBuyerResponse> createAddBuyerResponse(AddBuyerResponse value) {
        return new JAXBElement<AddBuyerResponse>(_AddBuyerResponse_QNAME, AddBuyerResponse.class, null, value);
    }

}
