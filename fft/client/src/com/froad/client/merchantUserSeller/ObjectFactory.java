
package com.froad.client.merchantUserSeller;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.merchantUserSeller package. 
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

    private final static QName _DeleteByMerchantSellerResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByMerchantSellerResponse");
    private final static QName _GetMerchantUserSellersResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantUserSellersResponse");
    private final static QName _GetMerchantUserSellers_QNAME = new QName("http://service.CB.froad.com/", "getMerchantUserSellers");
    private final static QName _UpdateByMerchantSellerResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByMerchantSellerResponse");
    private final static QName _UpdateByMerchantSeller_QNAME = new QName("http://service.CB.froad.com/", "updateByMerchantSeller");
    private final static QName _AddMerchantUserSeller_QNAME = new QName("http://service.CB.froad.com/", "addMerchantUserSeller");
    private final static QName _DeleteByMerchantSeller_QNAME = new QName("http://service.CB.froad.com/", "deleteByMerchantSeller");
    private final static QName _AddMerchantUserSellerResponse_QNAME = new QName("http://service.CB.froad.com/", "addMerchantUserSellerResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.merchantUserSeller
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMerchantUserSellers }
     * 
     */
    public GetMerchantUserSellers createGetMerchantUserSellers() {
        return new GetMerchantUserSellers();
    }

    /**
     * Create an instance of {@link GetMerchantUserSellersResponse }
     * 
     */
    public GetMerchantUserSellersResponse createGetMerchantUserSellersResponse() {
        return new GetMerchantUserSellersResponse();
    }

    /**
     * Create an instance of {@link DeleteByMerchantSellerResponse }
     * 
     */
    public DeleteByMerchantSellerResponse createDeleteByMerchantSellerResponse() {
        return new DeleteByMerchantSellerResponse();
    }

    /**
     * Create an instance of {@link AddMerchantUserSellerResponse }
     * 
     */
    public AddMerchantUserSellerResponse createAddMerchantUserSellerResponse() {
        return new AddMerchantUserSellerResponse();
    }

    /**
     * Create an instance of {@link DeleteByMerchantSeller }
     * 
     */
    public DeleteByMerchantSeller createDeleteByMerchantSeller() {
        return new DeleteByMerchantSeller();
    }

    /**
     * Create an instance of {@link AddMerchantUserSeller }
     * 
     */
    public AddMerchantUserSeller createAddMerchantUserSeller() {
        return new AddMerchantUserSeller();
    }

    /**
     * Create an instance of {@link UpdateByMerchantSeller }
     * 
     */
    public UpdateByMerchantSeller createUpdateByMerchantSeller() {
        return new UpdateByMerchantSeller();
    }

    /**
     * Create an instance of {@link UpdateByMerchantSellerResponse }
     * 
     */
    public UpdateByMerchantSellerResponse createUpdateByMerchantSellerResponse() {
        return new UpdateByMerchantSellerResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link MerchantUserSeller }
     * 
     */
    public MerchantUserSeller createMerchantUserSeller() {
        return new MerchantUserSeller();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteByMerchantSellerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteByMerchantSellerResponse")
    public JAXBElement<DeleteByMerchantSellerResponse> createDeleteByMerchantSellerResponse(DeleteByMerchantSellerResponse value) {
        return new JAXBElement<DeleteByMerchantSellerResponse>(_DeleteByMerchantSellerResponse_QNAME, DeleteByMerchantSellerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantUserSellersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantUserSellersResponse")
    public JAXBElement<GetMerchantUserSellersResponse> createGetMerchantUserSellersResponse(GetMerchantUserSellersResponse value) {
        return new JAXBElement<GetMerchantUserSellersResponse>(_GetMerchantUserSellersResponse_QNAME, GetMerchantUserSellersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantUserSellers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantUserSellers")
    public JAXBElement<GetMerchantUserSellers> createGetMerchantUserSellers(GetMerchantUserSellers value) {
        return new JAXBElement<GetMerchantUserSellers>(_GetMerchantUserSellers_QNAME, GetMerchantUserSellers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByMerchantSellerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByMerchantSellerResponse")
    public JAXBElement<UpdateByMerchantSellerResponse> createUpdateByMerchantSellerResponse(UpdateByMerchantSellerResponse value) {
        return new JAXBElement<UpdateByMerchantSellerResponse>(_UpdateByMerchantSellerResponse_QNAME, UpdateByMerchantSellerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByMerchantSeller }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByMerchantSeller")
    public JAXBElement<UpdateByMerchantSeller> createUpdateByMerchantSeller(UpdateByMerchantSeller value) {
        return new JAXBElement<UpdateByMerchantSeller>(_UpdateByMerchantSeller_QNAME, UpdateByMerchantSeller.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantUserSeller }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantUserSeller")
    public JAXBElement<AddMerchantUserSeller> createAddMerchantUserSeller(AddMerchantUserSeller value) {
        return new JAXBElement<AddMerchantUserSeller>(_AddMerchantUserSeller_QNAME, AddMerchantUserSeller.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteByMerchantSeller }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteByMerchantSeller")
    public JAXBElement<DeleteByMerchantSeller> createDeleteByMerchantSeller(DeleteByMerchantSeller value) {
        return new JAXBElement<DeleteByMerchantSeller>(_DeleteByMerchantSeller_QNAME, DeleteByMerchantSeller.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantUserSellerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantUserSellerResponse")
    public JAXBElement<AddMerchantUserSellerResponse> createAddMerchantUserSellerResponse(AddMerchantUserSellerResponse value) {
        return new JAXBElement<AddMerchantUserSellerResponse>(_AddMerchantUserSellerResponse_QNAME, AddMerchantUserSellerResponse.class, null, value);
    }

}
