
package com.froad.client.pay;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.pay package. 
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

    private final static QName _AliPayResultCheckResponse_QNAME = new QName("http://service.CB.froad.com/", "aliPayResultCheckResponse");
    private final static QName _GetPayByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getPayByIdResponse");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _AddPay_QNAME = new QName("http://service.CB.froad.com/", "addPay");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _UpdateStateById_QNAME = new QName("http://service.CB.froad.com/", "updateStateById");
    private final static QName _UpdatePayResponse_QNAME = new QName("http://service.CB.froad.com/", "updatePayResponse");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _UpdatePay_QNAME = new QName("http://service.CB.froad.com/", "updatePay");
    private final static QName _GetPayByTransIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getPayByTransIdResponse");
    private final static QName _AliPayResultCheck_QNAME = new QName("http://service.CB.froad.com/", "aliPayResultCheck");
    private final static QName _GetPayByTransId_QNAME = new QName("http://service.CB.froad.com/", "getPayByTransId");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _AddPayResponse_QNAME = new QName("http://service.CB.froad.com/", "addPayResponse");
    private final static QName _GetPayById_QNAME = new QName("http://service.CB.froad.com/", "getPayById");
    private final static QName _UpdateStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateStateByIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.pay
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddPay }
     * 
     */
    public AddPay createAddPay() {
        return new AddPay();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link GetPayByIdResponse }
     * 
     */
    public GetPayByIdResponse createGetPayByIdResponse() {
        return new GetPayByIdResponse();
    }

    /**
     * Create an instance of {@link AliPayResultCheckResponse }
     * 
     */
    public AliPayResultCheckResponse createAliPayResultCheckResponse() {
        return new AliPayResultCheckResponse();
    }

    /**
     * Create an instance of {@link GetPayByTransIdResponse }
     * 
     */
    public GetPayByTransIdResponse createGetPayByTransIdResponse() {
        return new GetPayByTransIdResponse();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link UpdatePay }
     * 
     */
    public UpdatePay createUpdatePay() {
        return new UpdatePay();
    }

    /**
     * Create an instance of {@link UpdatePayResponse }
     * 
     */
    public UpdatePayResponse createUpdatePayResponse() {
        return new UpdatePayResponse();
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
     * Create an instance of {@link AddPayResponse }
     * 
     */
    public AddPayResponse createAddPayResponse() {
        return new AddPayResponse();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link GetPayByTransId }
     * 
     */
    public GetPayByTransId createGetPayByTransId() {
        return new GetPayByTransId();
    }

    /**
     * Create an instance of {@link AliPayResultCheck }
     * 
     */
    public AliPayResultCheck createAliPayResultCheck() {
        return new AliPayResultCheck();
    }

    /**
     * Create an instance of {@link UpdateStateByIdResponse }
     * 
     */
    public UpdateStateByIdResponse createUpdateStateByIdResponse() {
        return new UpdateStateByIdResponse();
    }

    /**
     * Create an instance of {@link GetPayById }
     * 
     */
    public GetPayById createGetPayById() {
        return new GetPayById();
    }

    /**
     * Create an instance of {@link FundsChannel }
     * 
     */
    public FundsChannel createFundsChannel() {
        return new FundsChannel();
    }

    /**
     * Create an instance of {@link Pay }
     * 
     */
    public Pay createPay() {
        return new Pay();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AliPayResultCheckResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "aliPayResultCheckResponse")
    public JAXBElement<AliPayResultCheckResponse> createAliPayResultCheckResponse(AliPayResultCheckResponse value) {
        return new JAXBElement<AliPayResultCheckResponse>(_AliPayResultCheckResponse_QNAME, AliPayResultCheckResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPayByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPayByIdResponse")
    public JAXBElement<GetPayByIdResponse> createGetPayByIdResponse(GetPayByIdResponse value) {
        return new JAXBElement<GetPayByIdResponse>(_GetPayByIdResponse_QNAME, GetPayByIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPay }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addPay")
    public JAXBElement<AddPay> createAddPay(AddPay value) {
        return new JAXBElement<AddPay>(_AddPay_QNAME, AddPay.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePayResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updatePayResponse")
    public JAXBElement<UpdatePayResponse> createUpdatePayResponse(UpdatePayResponse value) {
        return new JAXBElement<UpdatePayResponse>(_UpdatePayResponse_QNAME, UpdatePayResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePay }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updatePay")
    public JAXBElement<UpdatePay> createUpdatePay(UpdatePay value) {
        return new JAXBElement<UpdatePay>(_UpdatePay_QNAME, UpdatePay.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPayByTransIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPayByTransIdResponse")
    public JAXBElement<GetPayByTransIdResponse> createGetPayByTransIdResponse(GetPayByTransIdResponse value) {
        return new JAXBElement<GetPayByTransIdResponse>(_GetPayByTransIdResponse_QNAME, GetPayByTransIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AliPayResultCheck }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "aliPayResultCheck")
    public JAXBElement<AliPayResultCheck> createAliPayResultCheck(AliPayResultCheck value) {
        return new JAXBElement<AliPayResultCheck>(_AliPayResultCheck_QNAME, AliPayResultCheck.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPayByTransId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPayByTransId")
    public JAXBElement<GetPayByTransId> createGetPayByTransId(GetPayByTransId value) {
        return new JAXBElement<GetPayByTransId>(_GetPayByTransId_QNAME, GetPayByTransId.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPayResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addPayResponse")
    public JAXBElement<AddPayResponse> createAddPayResponse(AddPayResponse value) {
        return new JAXBElement<AddPayResponse>(_AddPayResponse_QNAME, AddPayResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPayById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPayById")
    public JAXBElement<GetPayById> createGetPayById(GetPayById value) {
        return new JAXBElement<GetPayById>(_GetPayById_QNAME, GetPayById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateStateByIdResponse")
    public JAXBElement<UpdateStateByIdResponse> createUpdateStateByIdResponse(UpdateStateByIdResponse value) {
        return new JAXBElement<UpdateStateByIdResponse>(_UpdateStateByIdResponse_QNAME, UpdateStateByIdResponse.class, null, value);
    }

}
