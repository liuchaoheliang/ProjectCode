
package com.froad.client.merchantPhoto;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.merchantPhoto package. 
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

    private final static QName _GetMerchantPhotoByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoByMerchantId");
    private final static QName _GetMerchantPhotoByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoByMerchantIdResponse");
    private final static QName _UpdMerchantPhotoInfoResponse_QNAME = new QName("http://service.CB.froad.com/", "updMerchantPhotoInfoResponse");
    private final static QName _GetMerchantPhotoByPager_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoByPager");
    private final static QName _AddMerchantPhoto_QNAME = new QName("http://service.CB.froad.com/", "addMerchantPhoto");
    private final static QName _UpdMerchantPhotoInfo_QNAME = new QName("http://service.CB.froad.com/", "updMerchantPhotoInfo");
    private final static QName _GetMerchantPhotos_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotos");
    private final static QName _DeletePhotoByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deletePhotoByIdResponse");
    private final static QName _GetMerchantPhotoByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoByUserIdResponse");
    private final static QName _GetMerchantPhotoInfo_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoInfo");
    private final static QName _GetMerchantPhotoByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoByPagerResponse");
    private final static QName _GetMerchantPhotoByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoByIdResponse");
    private final static QName _GetMerchantPhotosResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotosResponse");
    private final static QName _DeletePhotoById_QNAME = new QName("http://service.CB.froad.com/", "deletePhotoById");
    private final static QName _GetMerchantPhotoByUserId_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoByUserId");
    private final static QName _GetMerchantPhotoInfoResponse_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoInfoResponse");
    private final static QName _GetMerchantPhotoById_QNAME = new QName("http://service.CB.froad.com/", "getMerchantPhotoById");
    private final static QName _AddMerchantPhotoResponse_QNAME = new QName("http://service.CB.froad.com/", "addMerchantPhotoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.merchantPhoto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddMerchantPhoto }
     * 
     */
    public AddMerchantPhoto createAddMerchantPhoto() {
        return new AddMerchantPhoto();
    }

    /**
     * Create an instance of {@link UpdMerchantPhotoInfoResponse }
     * 
     */
    public UpdMerchantPhotoInfoResponse createUpdMerchantPhotoInfoResponse() {
        return new UpdMerchantPhotoInfoResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoByPager }
     * 
     */
    public GetMerchantPhotoByPager createGetMerchantPhotoByPager() {
        return new GetMerchantPhotoByPager();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoByMerchantId }
     * 
     */
    public GetMerchantPhotoByMerchantId createGetMerchantPhotoByMerchantId() {
        return new GetMerchantPhotoByMerchantId();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoByMerchantIdResponse }
     * 
     */
    public GetMerchantPhotoByMerchantIdResponse createGetMerchantPhotoByMerchantIdResponse() {
        return new GetMerchantPhotoByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoByIdResponse }
     * 
     */
    public GetMerchantPhotoByIdResponse createGetMerchantPhotoByIdResponse() {
        return new GetMerchantPhotoByIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoByPagerResponse }
     * 
     */
    public GetMerchantPhotoByPagerResponse createGetMerchantPhotoByPagerResponse() {
        return new GetMerchantPhotoByPagerResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPhotos }
     * 
     */
    public GetMerchantPhotos createGetMerchantPhotos() {
        return new GetMerchantPhotos();
    }

    /**
     * Create an instance of {@link DeletePhotoByIdResponse }
     * 
     */
    public DeletePhotoByIdResponse createDeletePhotoByIdResponse() {
        return new DeletePhotoByIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoByUserIdResponse }
     * 
     */
    public GetMerchantPhotoByUserIdResponse createGetMerchantPhotoByUserIdResponse() {
        return new GetMerchantPhotoByUserIdResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoInfo }
     * 
     */
    public GetMerchantPhotoInfo createGetMerchantPhotoInfo() {
        return new GetMerchantPhotoInfo();
    }

    /**
     * Create an instance of {@link UpdMerchantPhotoInfo }
     * 
     */
    public UpdMerchantPhotoInfo createUpdMerchantPhotoInfo() {
        return new UpdMerchantPhotoInfo();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoByUserId }
     * 
     */
    public GetMerchantPhotoByUserId createGetMerchantPhotoByUserId() {
        return new GetMerchantPhotoByUserId();
    }

    /**
     * Create an instance of {@link DeletePhotoById }
     * 
     */
    public DeletePhotoById createDeletePhotoById() {
        return new DeletePhotoById();
    }

    /**
     * Create an instance of {@link GetMerchantPhotosResponse }
     * 
     */
    public GetMerchantPhotosResponse createGetMerchantPhotosResponse() {
        return new GetMerchantPhotosResponse();
    }

    /**
     * Create an instance of {@link AddMerchantPhotoResponse }
     * 
     */
    public AddMerchantPhotoResponse createAddMerchantPhotoResponse() {
        return new AddMerchantPhotoResponse();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoById }
     * 
     */
    public GetMerchantPhotoById createGetMerchantPhotoById() {
        return new GetMerchantPhotoById();
    }

    /**
     * Create an instance of {@link GetMerchantPhotoInfoResponse }
     * 
     */
    public GetMerchantPhotoInfoResponse createGetMerchantPhotoInfoResponse() {
        return new GetMerchantPhotoInfoResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link MerchantPhoto }
     * 
     */
    public MerchantPhoto createMerchantPhoto() {
        return new MerchantPhoto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoByMerchantId")
    public JAXBElement<GetMerchantPhotoByMerchantId> createGetMerchantPhotoByMerchantId(GetMerchantPhotoByMerchantId value) {
        return new JAXBElement<GetMerchantPhotoByMerchantId>(_GetMerchantPhotoByMerchantId_QNAME, GetMerchantPhotoByMerchantId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoByMerchantIdResponse")
    public JAXBElement<GetMerchantPhotoByMerchantIdResponse> createGetMerchantPhotoByMerchantIdResponse(GetMerchantPhotoByMerchantIdResponse value) {
        return new JAXBElement<GetMerchantPhotoByMerchantIdResponse>(_GetMerchantPhotoByMerchantIdResponse_QNAME, GetMerchantPhotoByMerchantIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdMerchantPhotoInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updMerchantPhotoInfoResponse")
    public JAXBElement<UpdMerchantPhotoInfoResponse> createUpdMerchantPhotoInfoResponse(UpdMerchantPhotoInfoResponse value) {
        return new JAXBElement<UpdMerchantPhotoInfoResponse>(_UpdMerchantPhotoInfoResponse_QNAME, UpdMerchantPhotoInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoByPager")
    public JAXBElement<GetMerchantPhotoByPager> createGetMerchantPhotoByPager(GetMerchantPhotoByPager value) {
        return new JAXBElement<GetMerchantPhotoByPager>(_GetMerchantPhotoByPager_QNAME, GetMerchantPhotoByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantPhoto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantPhoto")
    public JAXBElement<AddMerchantPhoto> createAddMerchantPhoto(AddMerchantPhoto value) {
        return new JAXBElement<AddMerchantPhoto>(_AddMerchantPhoto_QNAME, AddMerchantPhoto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdMerchantPhotoInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updMerchantPhotoInfo")
    public JAXBElement<UpdMerchantPhotoInfo> createUpdMerchantPhotoInfo(UpdMerchantPhotoInfo value) {
        return new JAXBElement<UpdMerchantPhotoInfo>(_UpdMerchantPhotoInfo_QNAME, UpdMerchantPhotoInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotos")
    public JAXBElement<GetMerchantPhotos> createGetMerchantPhotos(GetMerchantPhotos value) {
        return new JAXBElement<GetMerchantPhotos>(_GetMerchantPhotos_QNAME, GetMerchantPhotos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePhotoByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deletePhotoByIdResponse")
    public JAXBElement<DeletePhotoByIdResponse> createDeletePhotoByIdResponse(DeletePhotoByIdResponse value) {
        return new JAXBElement<DeletePhotoByIdResponse>(_DeletePhotoByIdResponse_QNAME, DeletePhotoByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoByUserIdResponse")
    public JAXBElement<GetMerchantPhotoByUserIdResponse> createGetMerchantPhotoByUserIdResponse(GetMerchantPhotoByUserIdResponse value) {
        return new JAXBElement<GetMerchantPhotoByUserIdResponse>(_GetMerchantPhotoByUserIdResponse_QNAME, GetMerchantPhotoByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoInfo")
    public JAXBElement<GetMerchantPhotoInfo> createGetMerchantPhotoInfo(GetMerchantPhotoInfo value) {
        return new JAXBElement<GetMerchantPhotoInfo>(_GetMerchantPhotoInfo_QNAME, GetMerchantPhotoInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoByPagerResponse")
    public JAXBElement<GetMerchantPhotoByPagerResponse> createGetMerchantPhotoByPagerResponse(GetMerchantPhotoByPagerResponse value) {
        return new JAXBElement<GetMerchantPhotoByPagerResponse>(_GetMerchantPhotoByPagerResponse_QNAME, GetMerchantPhotoByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoByIdResponse")
    public JAXBElement<GetMerchantPhotoByIdResponse> createGetMerchantPhotoByIdResponse(GetMerchantPhotoByIdResponse value) {
        return new JAXBElement<GetMerchantPhotoByIdResponse>(_GetMerchantPhotoByIdResponse_QNAME, GetMerchantPhotoByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotosResponse")
    public JAXBElement<GetMerchantPhotosResponse> createGetMerchantPhotosResponse(GetMerchantPhotosResponse value) {
        return new JAXBElement<GetMerchantPhotosResponse>(_GetMerchantPhotosResponse_QNAME, GetMerchantPhotosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePhotoById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deletePhotoById")
    public JAXBElement<DeletePhotoById> createDeletePhotoById(DeletePhotoById value) {
        return new JAXBElement<DeletePhotoById>(_DeletePhotoById_QNAME, DeletePhotoById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoByUserId")
    public JAXBElement<GetMerchantPhotoByUserId> createGetMerchantPhotoByUserId(GetMerchantPhotoByUserId value) {
        return new JAXBElement<GetMerchantPhotoByUserId>(_GetMerchantPhotoByUserId_QNAME, GetMerchantPhotoByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoInfoResponse")
    public JAXBElement<GetMerchantPhotoInfoResponse> createGetMerchantPhotoInfoResponse(GetMerchantPhotoInfoResponse value) {
        return new JAXBElement<GetMerchantPhotoInfoResponse>(_GetMerchantPhotoInfoResponse_QNAME, GetMerchantPhotoInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMerchantPhotoById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getMerchantPhotoById")
    public JAXBElement<GetMerchantPhotoById> createGetMerchantPhotoById(GetMerchantPhotoById value) {
        return new JAXBElement<GetMerchantPhotoById>(_GetMerchantPhotoById_QNAME, GetMerchantPhotoById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMerchantPhotoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addMerchantPhotoResponse")
    public JAXBElement<AddMerchantPhotoResponse> createAddMerchantPhotoResponse(AddMerchantPhotoResponse value) {
        return new JAXBElement<AddMerchantPhotoResponse>(_AddMerchantPhotoResponse_QNAME, AddMerchantPhotoResponse.class, null, value);
    }

}
