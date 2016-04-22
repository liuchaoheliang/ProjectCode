
package com.froad.client.announcement;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.announcement package. 
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

    private final static QName _GetAnnouncementById_QNAME = new QName("http://service.CB.froad.com/", "getAnnouncementById");
    private final static QName _GetAnnouncementOrderByImportantList_QNAME = new QName("http://service.CB.froad.com/", "getAnnouncementOrderByImportantList");
    private final static QName _GetAnnouncementByPager_QNAME = new QName("http://service.CB.froad.com/", "getAnnouncementByPager");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _GetAnnouncementByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getAnnouncementByIdResponse");
    private final static QName _GetAnnouncementByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getAnnouncementByPagerResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetAnnouncementOrderByImportantListResponse_QNAME = new QName("http://service.CB.froad.com/", "getAnnouncementOrderByImportantListResponse");
    private final static QName _Insert_QNAME = new QName("http://service.CB.froad.com/", "insert");
    private final static QName _InsertResponse_QNAME = new QName("http://service.CB.froad.com/", "insertResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.announcement
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAnnouncementByPagerResponse }
     * 
     */
    public GetAnnouncementByPagerResponse createGetAnnouncementByPagerResponse() {
        return new GetAnnouncementByPagerResponse();
    }

    /**
     * Create an instance of {@link GetAnnouncementByIdResponse }
     * 
     */
    public GetAnnouncementByIdResponse createGetAnnouncementByIdResponse() {
        return new GetAnnouncementByIdResponse();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link GetAnnouncementByPager }
     * 
     */
    public GetAnnouncementByPager createGetAnnouncementByPager() {
        return new GetAnnouncementByPager();
    }

    /**
     * Create an instance of {@link GetAnnouncementOrderByImportantList }
     * 
     */
    public GetAnnouncementOrderByImportantList createGetAnnouncementOrderByImportantList() {
        return new GetAnnouncementOrderByImportantList();
    }

    /**
     * Create an instance of {@link GetAnnouncementById }
     * 
     */
    public GetAnnouncementById createGetAnnouncementById() {
        return new GetAnnouncementById();
    }

    /**
     * Create an instance of {@link InsertResponse }
     * 
     */
    public InsertResponse createInsertResponse() {
        return new InsertResponse();
    }

    /**
     * Create an instance of {@link Insert }
     * 
     */
    public Insert createInsert() {
        return new Insert();
    }

    /**
     * Create an instance of {@link GetAnnouncementOrderByImportantListResponse }
     * 
     */
    public GetAnnouncementOrderByImportantListResponse createGetAnnouncementOrderByImportantListResponse() {
        return new GetAnnouncementOrderByImportantListResponse();
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
     * Create an instance of {@link Announcement }
     * 
     */
    public Announcement createAnnouncement() {
        return new Announcement();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAnnouncementById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAnnouncementById")
    public JAXBElement<GetAnnouncementById> createGetAnnouncementById(GetAnnouncementById value) {
        return new JAXBElement<GetAnnouncementById>(_GetAnnouncementById_QNAME, GetAnnouncementById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAnnouncementOrderByImportantList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAnnouncementOrderByImportantList")
    public JAXBElement<GetAnnouncementOrderByImportantList> createGetAnnouncementOrderByImportantList(GetAnnouncementOrderByImportantList value) {
        return new JAXBElement<GetAnnouncementOrderByImportantList>(_GetAnnouncementOrderByImportantList_QNAME, GetAnnouncementOrderByImportantList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAnnouncementByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAnnouncementByPager")
    public JAXBElement<GetAnnouncementByPager> createGetAnnouncementByPager(GetAnnouncementByPager value) {
        return new JAXBElement<GetAnnouncementByPager>(_GetAnnouncementByPager_QNAME, GetAnnouncementByPager.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAnnouncementByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAnnouncementByIdResponse")
    public JAXBElement<GetAnnouncementByIdResponse> createGetAnnouncementByIdResponse(GetAnnouncementByIdResponse value) {
        return new JAXBElement<GetAnnouncementByIdResponse>(_GetAnnouncementByIdResponse_QNAME, GetAnnouncementByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAnnouncementByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAnnouncementByPagerResponse")
    public JAXBElement<GetAnnouncementByPagerResponse> createGetAnnouncementByPagerResponse(GetAnnouncementByPagerResponse value) {
        return new JAXBElement<GetAnnouncementByPagerResponse>(_GetAnnouncementByPagerResponse_QNAME, GetAnnouncementByPagerResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAnnouncementOrderByImportantListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAnnouncementOrderByImportantListResponse")
    public JAXBElement<GetAnnouncementOrderByImportantListResponse> createGetAnnouncementOrderByImportantListResponse(GetAnnouncementOrderByImportantListResponse value) {
        return new JAXBElement<GetAnnouncementOrderByImportantListResponse>(_GetAnnouncementOrderByImportantListResponse_QNAME, GetAnnouncementOrderByImportantListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Insert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "insert")
    public JAXBElement<Insert> createInsert(Insert value) {
        return new JAXBElement<Insert>(_Insert_QNAME, Insert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "insertResponse")
    public JAXBElement<InsertResponse> createInsertResponse(InsertResponse value) {
        return new JAXBElement<InsertResponse>(_InsertResponse_QNAME, InsertResponse.class, null, value);
    }

}
