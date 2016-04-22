
package com.froad.client.activityCustInfo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.activityCustInfo package. 
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

    private final static QName _GetActivityCustInfoBySelective_QNAME = new QName("http://activity.service.CB.froad.com/", "getActivityCustInfoBySelective");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "updateByIdResponse");
    private final static QName _AddResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "addResponse");
    private final static QName _GetActivityCustInfoBySelectiveResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "getActivityCustInfoBySelectiveResponse");
    private final static QName _GetActivityCustInfoByPagerResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "getActivityCustInfoByPagerResponse");
    private final static QName _GetActivityCustInfoById_QNAME = new QName("http://activity.service.CB.froad.com/", "getActivityCustInfoById");
    private final static QName _GetActivityCustInfoByPager_QNAME = new QName("http://activity.service.CB.froad.com/", "getActivityCustInfoByPager");
    private final static QName _UpdateById_QNAME = new QName("http://activity.service.CB.froad.com/", "updateById");
    private final static QName _Add_QNAME = new QName("http://activity.service.CB.froad.com/", "add");
    private final static QName _GetActivityCustInfoByIdResponse_QNAME = new QName("http://activity.service.CB.froad.com/", "getActivityCustInfoByIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.activityCustInfo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetActivityCustInfoBySelectiveResponse }
     * 
     */
    public GetActivityCustInfoBySelectiveResponse createGetActivityCustInfoBySelectiveResponse() {
        return new GetActivityCustInfoBySelectiveResponse();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link GetActivityCustInfoBySelective }
     * 
     */
    public GetActivityCustInfoBySelective createGetActivityCustInfoBySelective() {
        return new GetActivityCustInfoBySelective();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link GetActivityCustInfoByPagerResponse }
     * 
     */
    public GetActivityCustInfoByPagerResponse createGetActivityCustInfoByPagerResponse() {
        return new GetActivityCustInfoByPagerResponse();
    }

    /**
     * Create an instance of {@link GetActivityCustInfoById }
     * 
     */
    public GetActivityCustInfoById createGetActivityCustInfoById() {
        return new GetActivityCustInfoById();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link GetActivityCustInfoByIdResponse }
     * 
     */
    public GetActivityCustInfoByIdResponse createGetActivityCustInfoByIdResponse() {
        return new GetActivityCustInfoByIdResponse();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link GetActivityCustInfoByPager }
     * 
     */
    public GetActivityCustInfoByPager createGetActivityCustInfoByPager() {
        return new GetActivityCustInfoByPager();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link ActivityCustInfo }
     * 
     */
    public ActivityCustInfo createActivityCustInfo() {
        return new ActivityCustInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityCustInfoBySelective }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getActivityCustInfoBySelective")
    public JAXBElement<GetActivityCustInfoBySelective> createGetActivityCustInfoBySelective(GetActivityCustInfoBySelective value) {
        return new JAXBElement<GetActivityCustInfoBySelective>(_GetActivityCustInfoBySelective_QNAME, GetActivityCustInfoBySelective.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "updateByIdResponse")
    public JAXBElement<UpdateByIdResponse> createUpdateByIdResponse(UpdateByIdResponse value) {
        return new JAXBElement<UpdateByIdResponse>(_UpdateByIdResponse_QNAME, UpdateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "addResponse")
    public JAXBElement<AddResponse> createAddResponse(AddResponse value) {
        return new JAXBElement<AddResponse>(_AddResponse_QNAME, AddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityCustInfoBySelectiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getActivityCustInfoBySelectiveResponse")
    public JAXBElement<GetActivityCustInfoBySelectiveResponse> createGetActivityCustInfoBySelectiveResponse(GetActivityCustInfoBySelectiveResponse value) {
        return new JAXBElement<GetActivityCustInfoBySelectiveResponse>(_GetActivityCustInfoBySelectiveResponse_QNAME, GetActivityCustInfoBySelectiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityCustInfoByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getActivityCustInfoByPagerResponse")
    public JAXBElement<GetActivityCustInfoByPagerResponse> createGetActivityCustInfoByPagerResponse(GetActivityCustInfoByPagerResponse value) {
        return new JAXBElement<GetActivityCustInfoByPagerResponse>(_GetActivityCustInfoByPagerResponse_QNAME, GetActivityCustInfoByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityCustInfoById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getActivityCustInfoById")
    public JAXBElement<GetActivityCustInfoById> createGetActivityCustInfoById(GetActivityCustInfoById value) {
        return new JAXBElement<GetActivityCustInfoById>(_GetActivityCustInfoById_QNAME, GetActivityCustInfoById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityCustInfoByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getActivityCustInfoByPager")
    public JAXBElement<GetActivityCustInfoByPager> createGetActivityCustInfoByPager(GetActivityCustInfoByPager value) {
        return new JAXBElement<GetActivityCustInfoByPager>(_GetActivityCustInfoByPager_QNAME, GetActivityCustInfoByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "updateById")
    public JAXBElement<UpdateById> createUpdateById(UpdateById value) {
        return new JAXBElement<UpdateById>(_UpdateById_QNAME, UpdateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Add }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "add")
    public JAXBElement<Add> createAdd(Add value) {
        return new JAXBElement<Add>(_Add_QNAME, Add.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityCustInfoByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://activity.service.CB.froad.com/", name = "getActivityCustInfoByIdResponse")
    public JAXBElement<GetActivityCustInfoByIdResponse> createGetActivityCustInfoByIdResponse(GetActivityCustInfoByIdResponse value) {
        return new JAXBElement<GetActivityCustInfoByIdResponse>(_GetActivityCustInfoByIdResponse_QNAME, GetActivityCustInfoByIdResponse.class, null, value);
    }

}
