
package com.froad.client.tag;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.tag package. 
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

    private final static QName _InsertBatch_QNAME = new QName("http://service.CB.froad.com/", "insertBatch");
    private final static QName _AddResponse_QNAME = new QName("http://service.CB.froad.com/", "addResponse");
    private final static QName _GetTagMapsByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getTagMapsByMerchantId");
    private final static QName _GetTagMapsByMerchantNameResponse_QNAME = new QName("http://service.CB.froad.com/", "getTagMapsByMerchantNameResponse");
    private final static QName _GetTagMapsByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getTagMapsByMerchantIdResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetTagMapsByMerchantName_QNAME = new QName("http://service.CB.froad.com/", "getTagMapsByMerchantName");
    private final static QName _Add_QNAME = new QName("http://service.CB.froad.com/", "add");
    private final static QName _GetTagsByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getTagsByMerchantId");
    private final static QName _InsertBatchResponse_QNAME = new QName("http://service.CB.froad.com/", "insertBatchResponse");
    private final static QName _GetTagsByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getTagsByMerchantIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.tag
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
     * Create an instance of {@link GetTagMapsByMerchantIdResponse }
     * 
     */
    public GetTagMapsByMerchantIdResponse createGetTagMapsByMerchantIdResponse() {
        return new GetTagMapsByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link GetTagMapsByMerchantNameResponse }
     * 
     */
    public GetTagMapsByMerchantNameResponse createGetTagMapsByMerchantNameResponse() {
        return new GetTagMapsByMerchantNameResponse();
    }

    /**
     * Create an instance of {@link GetTagMapsByMerchantId }
     * 
     */
    public GetTagMapsByMerchantId createGetTagMapsByMerchantId() {
        return new GetTagMapsByMerchantId();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link InsertBatch }
     * 
     */
    public InsertBatch createInsertBatch() {
        return new InsertBatch();
    }

    /**
     * Create an instance of {@link GetTagsByMerchantIdResponse }
     * 
     */
    public GetTagsByMerchantIdResponse createGetTagsByMerchantIdResponse() {
        return new GetTagsByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link InsertBatchResponse }
     * 
     */
    public InsertBatchResponse createInsertBatchResponse() {
        return new InsertBatchResponse();
    }

    /**
     * Create an instance of {@link GetTagsByMerchantId }
     * 
     */
    public GetTagsByMerchantId createGetTagsByMerchantId() {
        return new GetTagsByMerchantId();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link GetTagMapsByMerchantName }
     * 
     */
    public GetTagMapsByMerchantName createGetTagMapsByMerchantName() {
        return new GetTagMapsByMerchantName();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link TagMAP }
     * 
     */
    public TagMAP createTagMAP() {
        return new TagMAP();
    }

    /**
     * Create an instance of {@link TagDistrictA }
     * 
     */
    public TagDistrictA createTagDistrictA() {
        return new TagDistrictA();
    }

    /**
     * Create an instance of {@link TagDistrictB }
     * 
     */
    public TagDistrictB createTagDistrictB() {
        return new TagDistrictB();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertBatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "insertBatch")
    public JAXBElement<InsertBatch> createInsertBatch(InsertBatch value) {
        return new JAXBElement<InsertBatch>(_InsertBatch_QNAME, InsertBatch.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagMapsByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTagMapsByMerchantId")
    public JAXBElement<GetTagMapsByMerchantId> createGetTagMapsByMerchantId(GetTagMapsByMerchantId value) {
        return new JAXBElement<GetTagMapsByMerchantId>(_GetTagMapsByMerchantId_QNAME, GetTagMapsByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagMapsByMerchantNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTagMapsByMerchantNameResponse")
    public JAXBElement<GetTagMapsByMerchantNameResponse> createGetTagMapsByMerchantNameResponse(GetTagMapsByMerchantNameResponse value) {
        return new JAXBElement<GetTagMapsByMerchantNameResponse>(_GetTagMapsByMerchantNameResponse_QNAME, GetTagMapsByMerchantNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagMapsByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTagMapsByMerchantIdResponse")
    public JAXBElement<GetTagMapsByMerchantIdResponse> createGetTagMapsByMerchantIdResponse(GetTagMapsByMerchantIdResponse value) {
        return new JAXBElement<GetTagMapsByMerchantIdResponse>(_GetTagMapsByMerchantIdResponse_QNAME, GetTagMapsByMerchantIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagMapsByMerchantName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTagMapsByMerchantName")
    public JAXBElement<GetTagMapsByMerchantName> createGetTagMapsByMerchantName(GetTagMapsByMerchantName value) {
        return new JAXBElement<GetTagMapsByMerchantName>(_GetTagMapsByMerchantName_QNAME, GetTagMapsByMerchantName.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagsByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTagsByMerchantId")
    public JAXBElement<GetTagsByMerchantId> createGetTagsByMerchantId(GetTagsByMerchantId value) {
        return new JAXBElement<GetTagsByMerchantId>(_GetTagsByMerchantId_QNAME, GetTagsByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertBatchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "insertBatchResponse")
    public JAXBElement<InsertBatchResponse> createInsertBatchResponse(InsertBatchResponse value) {
        return new JAXBElement<InsertBatchResponse>(_InsertBatchResponse_QNAME, InsertBatchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTagsByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTagsByMerchantIdResponse")
    public JAXBElement<GetTagsByMerchantIdResponse> createGetTagsByMerchantIdResponse(GetTagsByMerchantIdResponse value) {
        return new JAXBElement<GetTagsByMerchantIdResponse>(_GetTagsByMerchantIdResponse_QNAME, GetTagsByMerchantIdResponse.class, null, value);
    }

}
