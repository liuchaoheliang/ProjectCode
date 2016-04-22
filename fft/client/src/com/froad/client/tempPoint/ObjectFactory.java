
package com.froad.client.tempPoint;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.tempPoint package. 
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

    private final static QName _UpdateTempPointAndFillPoint_QNAME = new QName("http://service.CB.froad.com/", "updateTempPointAndFillPoint");
    private final static QName _GetTempPointById_QNAME = new QName("http://service.CB.froad.com/", "getTempPointById");
    private final static QName _AddResponse_QNAME = new QName("http://service.CB.froad.com/", "addResponse");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _GetTempPointByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getTempPointByIdResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _UpdateTempPointAndFillPointResponse_QNAME = new QName("http://service.CB.froad.com/", "updateTempPointAndFillPointResponse");
    private final static QName _Add_QNAME = new QName("http://service.CB.froad.com/", "add");
    private final static QName _GetTempPointBySelective_QNAME = new QName("http://service.CB.froad.com/", "getTempPointBySelective");
    private final static QName _GetTempPointBySelectiveResponse_QNAME = new QName("http://service.CB.froad.com/", "getTempPointBySelectiveResponse");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _AppException_QNAME = new QName("http://service.CB.froad.com/", "AppException");
    private final static QName _GetTempPointByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getTempPointByPagerResponse");
    private final static QName _GetTempPointByPager_QNAME = new QName("http://service.CB.froad.com/", "getTempPointByPager");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.tempPoint
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTempPointByIdResponse }
     * 
     */
    public GetTempPointByIdResponse createGetTempPointByIdResponse() {
        return new GetTempPointByIdResponse();
    }

    /**
     * Create an instance of {@link AddResponse }
     * 
     */
    public AddResponse createAddResponse() {
        return new AddResponse();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link GetTempPointById }
     * 
     */
    public GetTempPointById createGetTempPointById() {
        return new GetTempPointById();
    }

    /**
     * Create an instance of {@link UpdateTempPointAndFillPoint }
     * 
     */
    public UpdateTempPointAndFillPoint createUpdateTempPointAndFillPoint() {
        return new UpdateTempPointAndFillPoint();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link GetTempPointBySelectiveResponse }
     * 
     */
    public GetTempPointBySelectiveResponse createGetTempPointBySelectiveResponse() {
        return new GetTempPointBySelectiveResponse();
    }

    /**
     * Create an instance of {@link UpdateTempPointAndFillPointResponse }
     * 
     */
    public UpdateTempPointAndFillPointResponse createUpdateTempPointAndFillPointResponse() {
        return new UpdateTempPointAndFillPointResponse();
    }

    /**
     * Create an instance of {@link Add }
     * 
     */
    public Add createAdd() {
        return new Add();
    }

    /**
     * Create an instance of {@link GetTempPointBySelective }
     * 
     */
    public GetTempPointBySelective createGetTempPointBySelective() {
        return new GetTempPointBySelective();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link AppException }
     * 
     */
    public AppException createAppException() {
        return new AppException();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link GetTempPointByPager }
     * 
     */
    public GetTempPointByPager createGetTempPointByPager() {
        return new GetTempPointByPager();
    }

    /**
     * Create an instance of {@link GetTempPointByPagerResponse }
     * 
     */
    public GetTempPointByPagerResponse createGetTempPointByPagerResponse() {
        return new GetTempPointByPagerResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link TempPoint }
     * 
     */
    public TempPoint createTempPoint() {
        return new TempPoint();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTempPointAndFillPoint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateTempPointAndFillPoint")
    public JAXBElement<UpdateTempPointAndFillPoint> createUpdateTempPointAndFillPoint(UpdateTempPointAndFillPoint value) {
        return new JAXBElement<UpdateTempPointAndFillPoint>(_UpdateTempPointAndFillPoint_QNAME, UpdateTempPointAndFillPoint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTempPointById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTempPointById")
    public JAXBElement<GetTempPointById> createGetTempPointById(GetTempPointById value) {
        return new JAXBElement<GetTempPointById>(_GetTempPointById_QNAME, GetTempPointById.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteById")
    public JAXBElement<DeleteById> createDeleteById(DeleteById value) {
        return new JAXBElement<DeleteById>(_DeleteById_QNAME, DeleteById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTempPointByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTempPointByIdResponse")
    public JAXBElement<GetTempPointByIdResponse> createGetTempPointByIdResponse(GetTempPointByIdResponse value) {
        return new JAXBElement<GetTempPointByIdResponse>(_GetTempPointByIdResponse_QNAME, GetTempPointByIdResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTempPointAndFillPointResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateTempPointAndFillPointResponse")
    public JAXBElement<UpdateTempPointAndFillPointResponse> createUpdateTempPointAndFillPointResponse(UpdateTempPointAndFillPointResponse value) {
        return new JAXBElement<UpdateTempPointAndFillPointResponse>(_UpdateTempPointAndFillPointResponse_QNAME, UpdateTempPointAndFillPointResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTempPointBySelective }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTempPointBySelective")
    public JAXBElement<GetTempPointBySelective> createGetTempPointBySelective(GetTempPointBySelective value) {
        return new JAXBElement<GetTempPointBySelective>(_GetTempPointBySelective_QNAME, GetTempPointBySelective.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTempPointBySelectiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTempPointBySelectiveResponse")
    public JAXBElement<GetTempPointBySelectiveResponse> createGetTempPointBySelectiveResponse(GetTempPointBySelectiveResponse value) {
        return new JAXBElement<GetTempPointBySelectiveResponse>(_GetTempPointBySelectiveResponse_QNAME, GetTempPointBySelectiveResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AppException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "AppException")
    public JAXBElement<AppException> createAppException(AppException value) {
        return new JAXBElement<AppException>(_AppException_QNAME, AppException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTempPointByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTempPointByPagerResponse")
    public JAXBElement<GetTempPointByPagerResponse> createGetTempPointByPagerResponse(GetTempPointByPagerResponse value) {
        return new JAXBElement<GetTempPointByPagerResponse>(_GetTempPointByPagerResponse_QNAME, GetTempPointByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTempPointByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getTempPointByPager")
    public JAXBElement<GetTempPointByPager> createGetTempPointByPager(GetTempPointByPager value) {
        return new JAXBElement<GetTempPointByPager>(_GetTempPointByPager_QNAME, GetTempPointByPager.class, null, value);
    }

}
