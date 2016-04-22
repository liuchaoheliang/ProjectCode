
package com.froad.client.Store;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.Store package. 
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

    private final static QName _GetStoreByPager_QNAME = new QName("http://service.CB.froad.com/", "getStoreByPager");
    private final static QName _GetStoreByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getStoreByIdResponse");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _GetStoreByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getStoreByMerchantIdResponse");
    private final static QName _GetStoreByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getStoreByMerchantId");
    private final static QName _AddStoreResponse_QNAME = new QName("http://service.CB.froad.com/", "addStoreResponse");
    private final static QName _GetStoreByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getStoreByPagerResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetStoreById_QNAME = new QName("http://service.CB.froad.com/", "getStoreById");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _AddStore_QNAME = new QName("http://service.CB.froad.com/", "addStore");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.Store
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStoreByMerchantId }
     * 
     */
    public GetStoreByMerchantId createGetStoreByMerchantId() {
        return new GetStoreByMerchantId();
    }

    /**
     * Create an instance of {@link GetStoreByMerchantIdResponse }
     * 
     */
    public GetStoreByMerchantIdResponse createGetStoreByMerchantIdResponse() {
        return new GetStoreByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link GetStoreByIdResponse }
     * 
     */
    public GetStoreByIdResponse createGetStoreByIdResponse() {
        return new GetStoreByIdResponse();
    }

    /**
     * Create an instance of {@link GetStoreByPager }
     * 
     */
    public GetStoreByPager createGetStoreByPager() {
        return new GetStoreByPager();
    }

    /**
     * Create an instance of {@link AddStore }
     * 
     */
    public AddStore createAddStore() {
        return new AddStore();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link GetStoreById }
     * 
     */
    public GetStoreById createGetStoreById() {
        return new GetStoreById();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link GetStoreByPagerResponse }
     * 
     */
    public GetStoreByPagerResponse createGetStoreByPagerResponse() {
        return new GetStoreByPagerResponse();
    }

    /**
     * Create an instance of {@link AddStoreResponse }
     * 
     */
    public AddStoreResponse createAddStoreResponse() {
        return new AddStoreResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link Store }
     * 
     */
    public Store createStore() {
        return new Store();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoreByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getStoreByPager")
    public JAXBElement<GetStoreByPager> createGetStoreByPager(GetStoreByPager value) {
        return new JAXBElement<GetStoreByPager>(_GetStoreByPager_QNAME, GetStoreByPager.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteById")
    public JAXBElement<DeleteById> createDeleteById(DeleteById value) {
        return new JAXBElement<DeleteById>(_DeleteById_QNAME, DeleteById.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoreByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getStoreByMerchantIdResponse")
    public JAXBElement<GetStoreByMerchantIdResponse> createGetStoreByMerchantIdResponse(GetStoreByMerchantIdResponse value) {
        return new JAXBElement<GetStoreByMerchantIdResponse>(_GetStoreByMerchantIdResponse_QNAME, GetStoreByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoreByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getStoreByMerchantId")
    public JAXBElement<GetStoreByMerchantId> createGetStoreByMerchantId(GetStoreByMerchantId value) {
        return new JAXBElement<GetStoreByMerchantId>(_GetStoreByMerchantId_QNAME, GetStoreByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddStoreResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addStoreResponse")
    public JAXBElement<AddStoreResponse> createAddStoreResponse(AddStoreResponse value) {
        return new JAXBElement<AddStoreResponse>(_AddStoreResponse_QNAME, AddStoreResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoreByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getStoreByPagerResponse")
    public JAXBElement<GetStoreByPagerResponse> createGetStoreByPagerResponse(GetStoreByPagerResponse value) {
        return new JAXBElement<GetStoreByPagerResponse>(_GetStoreByPagerResponse_QNAME, GetStoreByPagerResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStoreById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getStoreById")
    public JAXBElement<GetStoreById> createGetStoreById(GetStoreById value) {
        return new JAXBElement<GetStoreById>(_GetStoreById_QNAME, GetStoreById.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddStore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addStore")
    public JAXBElement<AddStore> createAddStore(AddStore value) {
        return new JAXBElement<AddStore>(_AddStore_QNAME, AddStore.class, null, value);
    }

}
