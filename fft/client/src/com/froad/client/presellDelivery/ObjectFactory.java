
package com.froad.client.presellDelivery;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.presellDelivery package. 
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

    private final static QName _GetByRackId_QNAME = new QName("http://service.CB.froad.com/", "getByRackId");
    private final static QName _GetByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getByPagerResponse");
    private final static QName _AddResponse_QNAME = new QName("http://service.CB.froad.com/", "addResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _GetByConditionsResponse_QNAME = new QName("http://service.CB.froad.com/", "getByConditionsResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _Add_QNAME = new QName("http://service.CB.froad.com/", "add");
    private final static QName _GetByRackIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getByRackIdResponse");
    private final static QName _GetByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getByIdResponse");
    private final static QName _GetByPager_QNAME = new QName("http://service.CB.froad.com/", "getByPager");
    private final static QName _GetByConditions_QNAME = new QName("http://service.CB.froad.com/", "getByConditions");
    private final static QName _GetById_QNAME = new QName("http://service.CB.froad.com/", "getById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.presellDelivery
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetByConditionsResponse }
     * 
     */
    public GetByConditionsResponse createGetByConditionsResponse() {
        return new GetByConditionsResponse();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link GetByPagerResponse }
     * 
     */
    public GetByPagerResponse createGetByPagerResponse() {
        return new GetByPagerResponse();
    }

    /**
     * Create an instance of {@link GetByRackId }
     * 
     */
    public GetByRackId createGetByRackId() {
        return new GetByRackId();
    }

    /**
     * Create an instance of {@link GetById }
     * 
     */
    public GetById createGetById() {
        return new GetById();
    }

    /**
     * Create an instance of {@link GetByConditions }
     * 
     */
    public GetByConditions createGetByConditions() {
        return new GetByConditions();
    }

    /**
     * Create an instance of {@link GetByPager }
     * 
     */
    public GetByPager createGetByPager() {
        return new GetByPager();
    }

    /**
     * Create an instance of {@link GetByIdResponse }
     * 
     */
    public GetByIdResponse createGetByIdResponse() {
        return new GetByIdResponse();
    }

    /**
     * Create an instance of {@link GetByRackIdResponse }
     * 
     */
    public GetByRackIdResponse createGetByRackIdResponse() {
        return new GetByRackIdResponse();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link PresellDelivery }
     * 
     */
    public PresellDelivery createPresellDelivery() {
        return new PresellDelivery();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByRackId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByRackId")
    public JAXBElement<GetByRackId> createGetByRackId(GetByRackId value) {
        return new JAXBElement<GetByRackId>(_GetByRackId_QNAME, GetByRackId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByPagerResponse")
    public JAXBElement<GetByPagerResponse> createGetByPagerResponse(GetByPagerResponse value) {
        return new JAXBElement<GetByPagerResponse>(_GetByPagerResponse_QNAME, GetByPagerResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByIdResponse")
    public JAXBElement<UpdateByIdResponse> createUpdateByIdResponse(UpdateByIdResponse value) {
        return new JAXBElement<UpdateByIdResponse>(_UpdateByIdResponse_QNAME, UpdateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByConditionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByConditionsResponse")
    public JAXBElement<GetByConditionsResponse> createGetByConditionsResponse(GetByConditionsResponse value) {
        return new JAXBElement<GetByConditionsResponse>(_GetByConditionsResponse_QNAME, GetByConditionsResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByRackIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByRackIdResponse")
    public JAXBElement<GetByRackIdResponse> createGetByRackIdResponse(GetByRackIdResponse value) {
        return new JAXBElement<GetByRackIdResponse>(_GetByRackIdResponse_QNAME, GetByRackIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByPager")
    public JAXBElement<GetByPager> createGetByPager(GetByPager value) {
        return new JAXBElement<GetByPager>(_GetByPager_QNAME, GetByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByConditions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByConditions")
    public JAXBElement<GetByConditions> createGetByConditions(GetByConditions value) {
        return new JAXBElement<GetByConditions>(_GetByConditions_QNAME, GetByConditions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getById")
    public JAXBElement<GetById> createGetById(GetById value) {
        return new JAXBElement<GetById>(_GetById_QNAME, GetById.class, null, value);
    }

}
