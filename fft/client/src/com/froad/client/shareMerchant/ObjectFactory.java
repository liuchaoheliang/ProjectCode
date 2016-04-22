
package com.froad.client.shareMerchant;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.shareMerchant package. 
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

    private final static QName _AddShareMerchant_QNAME = new QName("http://service.CB.froad.com/", "addShareMerchant");
    private final static QName _GetShareMerchantByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getShareMerchantByIdResponse");
    private final static QName _GetShareMerchantByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getShareMerchantByPagerResponse");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetShareMerchantByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getShareMerchantByUserIdResponse");
    private final static QName _UpdateStateById_QNAME = new QName("http://service.CB.froad.com/", "updateStateById");
    private final static QName _GetShareMerchantById_QNAME = new QName("http://service.CB.froad.com/", "getShareMerchantById");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _GetShareMerchantByPager_QNAME = new QName("http://service.CB.froad.com/", "getShareMerchantByPager");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _UpdateStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateStateByIdResponse");
    private final static QName _AddShareMerchantResponse_QNAME = new QName("http://service.CB.froad.com/", "addShareMerchantResponse");
    private final static QName _GetShareMerchantByUserId_QNAME = new QName("http://service.CB.froad.com/", "getShareMerchantByUserId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.shareMerchant
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link GetShareMerchantByPagerResponse }
     * 
     */
    public GetShareMerchantByPagerResponse createGetShareMerchantByPagerResponse() {
        return new GetShareMerchantByPagerResponse();
    }

    /**
     * Create an instance of {@link GetShareMerchantByIdResponse }
     * 
     */
    public GetShareMerchantByIdResponse createGetShareMerchantByIdResponse() {
        return new GetShareMerchantByIdResponse();
    }

    /**
     * Create an instance of {@link AddShareMerchant }
     * 
     */
    public AddShareMerchant createAddShareMerchant() {
        return new AddShareMerchant();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link GetShareMerchantById }
     * 
     */
    public GetShareMerchantById createGetShareMerchantById() {
        return new GetShareMerchantById();
    }

    /**
     * Create an instance of {@link GetShareMerchantByUserIdResponse }
     * 
     */
    public GetShareMerchantByUserIdResponse createGetShareMerchantByUserIdResponse() {
        return new GetShareMerchantByUserIdResponse();
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
     * Create an instance of {@link GetShareMerchantByPager }
     * 
     */
    public GetShareMerchantByPager createGetShareMerchantByPager() {
        return new GetShareMerchantByPager();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link GetShareMerchantByUserId }
     * 
     */
    public GetShareMerchantByUserId createGetShareMerchantByUserId() {
        return new GetShareMerchantByUserId();
    }

    /**
     * Create an instance of {@link AddShareMerchantResponse }
     * 
     */
    public AddShareMerchantResponse createAddShareMerchantResponse() {
        return new AddShareMerchantResponse();
    }

    /**
     * Create an instance of {@link UpdateStateByIdResponse }
     * 
     */
    public UpdateStateByIdResponse createUpdateStateByIdResponse() {
        return new UpdateStateByIdResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link ShareMerchant }
     * 
     */
    public ShareMerchant createShareMerchant() {
        return new ShareMerchant();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddShareMerchant }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addShareMerchant")
    public JAXBElement<AddShareMerchant> createAddShareMerchant(AddShareMerchant value) {
        return new JAXBElement<AddShareMerchant>(_AddShareMerchant_QNAME, AddShareMerchant.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShareMerchantByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getShareMerchantByIdResponse")
    public JAXBElement<GetShareMerchantByIdResponse> createGetShareMerchantByIdResponse(GetShareMerchantByIdResponse value) {
        return new JAXBElement<GetShareMerchantByIdResponse>(_GetShareMerchantByIdResponse_QNAME, GetShareMerchantByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShareMerchantByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getShareMerchantByPagerResponse")
    public JAXBElement<GetShareMerchantByPagerResponse> createGetShareMerchantByPagerResponse(GetShareMerchantByPagerResponse value) {
        return new JAXBElement<GetShareMerchantByPagerResponse>(_GetShareMerchantByPagerResponse_QNAME, GetShareMerchantByPagerResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateById")
    public JAXBElement<UpdateById> createUpdateById(UpdateById value) {
        return new JAXBElement<UpdateById>(_UpdateById_QNAME, UpdateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShareMerchantByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getShareMerchantByUserIdResponse")
    public JAXBElement<GetShareMerchantByUserIdResponse> createGetShareMerchantByUserIdResponse(GetShareMerchantByUserIdResponse value) {
        return new JAXBElement<GetShareMerchantByUserIdResponse>(_GetShareMerchantByUserIdResponse_QNAME, GetShareMerchantByUserIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShareMerchantById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getShareMerchantById")
    public JAXBElement<GetShareMerchantById> createGetShareMerchantById(GetShareMerchantById value) {
        return new JAXBElement<GetShareMerchantById>(_GetShareMerchantById_QNAME, GetShareMerchantById.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShareMerchantByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getShareMerchantByPager")
    public JAXBElement<GetShareMerchantByPager> createGetShareMerchantByPager(GetShareMerchantByPager value) {
        return new JAXBElement<GetShareMerchantByPager>(_GetShareMerchantByPager_QNAME, GetShareMerchantByPager.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddShareMerchantResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addShareMerchantResponse")
    public JAXBElement<AddShareMerchantResponse> createAddShareMerchantResponse(AddShareMerchantResponse value) {
        return new JAXBElement<AddShareMerchantResponse>(_AddShareMerchantResponse_QNAME, AddShareMerchantResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetShareMerchantByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getShareMerchantByUserId")
    public JAXBElement<GetShareMerchantByUserId> createGetShareMerchantByUserId(GetShareMerchantByUserId value) {
        return new JAXBElement<GetShareMerchantByUserId>(_GetShareMerchantByUserId_QNAME, GetShareMerchantByUserId.class, null, value);
    }

}
