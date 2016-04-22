
package com.froad.client.merchantTrain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.merchantTrain package. 
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

    private final static QName _AddMerchantTrainResponse_QNAME = new QName("http://service.CB.froad.com/", "addMerchantTrainResponse");
    private final static QName _GetMerchantTrainByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrainByMerchantId");
    private final static QName _GetMerchantTrainByUserId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrainByUserId");
    private final static QName _GetMerchantTrainByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrainByUserIdResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetMerchantTrainByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantTrainByMerchantIdResponse");
    private final static QName _AddMerchantTrain_QNAME = new QName("http://service.CB.froad.com/", "addMerchantTrain");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.merchantTrain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantTrainByUserIdResponse }
     * 
     */
    public GetMerchantTrainByUserIdResponse createGetMerchantTrainByUserIdResponse() {
        return new GetMerchantTrainByUserIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantTrainByUserId }
     * 
     */
    public GetMerchantTrainByUserId createGetMerchantTrainByUserId() {
        return new GetMerchantTrainByUserId();
    }

    /**
     * Create an instance of {@link GetMerchantTrainByMerchantId }
     * 
     */
    public GetMerchantTrainByMerchantId createGetMerchantTrainByMerchantId() {
        return new GetMerchantTrainByMerchantId();
    }

    /**
     * Create an instance of {@link AddMerchantTrainResponse }
     * 
     */
    public AddMerchantTrainResponse createAddMerchantTrainResponse() {
        return new AddMerchantTrainResponse();
    }

    /**
     * Create an instance of {@link AddMerchantTrain }
     * 
     */
    public AddMerchantTrain createAddMerchantTrain() {
        return new AddMerchantTrain();
    }

    /**
     * Create an instance of {@link GetMerchantTrainByMerchantIdResponse }
     * 
     */
    public GetMerchantTrainByMerchantIdResponse createGetMerchantTrainByMerchantIdResponse() {
        return new GetMerchantTrainByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link MerchantTrain }
     * 
     */
    public MerchantTrain createMerchantTrain() {
        return new MerchantTrain();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantTrainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantTrainResponse")
    public JAXBElement<AddMerchantTrainResponse> createAddMerchantTrainResponse(AddMerchantTrainResponse value) {
        return new JAXBElement<AddMerchantTrainResponse>(_AddMerchantTrainResponse_QNAME, AddMerchantTrainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrainByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrainByMerchantId")
    public JAXBElement<GetMerchantTrainByMerchantId> createGetMerchantTrainByMerchantId(GetMerchantTrainByMerchantId value) {
        return new JAXBElement<GetMerchantTrainByMerchantId>(_GetMerchantTrainByMerchantId_QNAME, GetMerchantTrainByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrainByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrainByUserId")
    public JAXBElement<GetMerchantTrainByUserId> createGetMerchantTrainByUserId(GetMerchantTrainByUserId value) {
        return new JAXBElement<GetMerchantTrainByUserId>(_GetMerchantTrainByUserId_QNAME, GetMerchantTrainByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrainByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrainByUserIdResponse")
    public JAXBElement<GetMerchantTrainByUserIdResponse> createGetMerchantTrainByUserIdResponse(GetMerchantTrainByUserIdResponse value) {
        return new JAXBElement<GetMerchantTrainByUserIdResponse>(_GetMerchantTrainByUserIdResponse_QNAME, GetMerchantTrainByUserIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateById")
    public JAXBElement<UpdateById> createUpdateById(UpdateById value) {
        return new JAXBElement<UpdateById>(_UpdateById_QNAME, UpdateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantTrainByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantTrainByMerchantIdResponse")
    public JAXBElement<GetMerchantTrainByMerchantIdResponse> createGetMerchantTrainByMerchantIdResponse(GetMerchantTrainByMerchantIdResponse value) {
        return new JAXBElement<GetMerchantTrainByMerchantIdResponse>(_GetMerchantTrainByMerchantIdResponse_QNAME, GetMerchantTrainByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantTrain }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantTrain")
    public JAXBElement<AddMerchantTrain> createAddMerchantTrain(AddMerchantTrain value) {
        return new JAXBElement<AddMerchantTrain>(_AddMerchantTrain_QNAME, AddMerchantTrain.class, null, value);
    }

}
