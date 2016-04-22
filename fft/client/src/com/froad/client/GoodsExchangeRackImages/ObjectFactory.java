
package com.froad.client.GoodsExchangeRackImages;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.GoodsExchangeRackImages package. 
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

    private final static QName _DeleteGoodsExchangeRackImages_QNAME = new QName("http://service.CB.froad.com/", "deleteGoodsExchangeRackImages");
    private final static QName _UpdateGoodsExchangeRackImages_QNAME = new QName("http://service.CB.froad.com/", "updateGoodsExchangeRackImages");
    private final static QName _GetByPrimaryKeyResponse_QNAME = new QName("http://service.CB.froad.com/", "getByPrimaryKeyResponse");
    private final static QName _DeleteGoodsExchangeRackImagesResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteGoodsExchangeRackImagesResponse");
    private final static QName _AddGoodsExchangeRackImagesResponse_QNAME = new QName("http://service.CB.froad.com/", "addGoodsExchangeRackImagesResponse");
    private final static QName _AddGoodsExchangeRackImages_QNAME = new QName("http://service.CB.froad.com/", "addGoodsExchangeRackImages");
    private final static QName _GetAllGoodsExchangeRackImages_QNAME = new QName("http://service.CB.froad.com/", "getAllGoodsExchangeRackImages");
    private final static QName _UpdateGoodsExchangeRackImagesResponse_QNAME = new QName("http://service.CB.froad.com/", "updateGoodsExchangeRackImagesResponse");
    private final static QName _GetByPrimaryKey_QNAME = new QName("http://service.CB.froad.com/", "getByPrimaryKey");
    private final static QName _GetAllGoodsExchangeRackImagesResponse_QNAME = new QName("http://service.CB.froad.com/", "getAllGoodsExchangeRackImagesResponse");
    private final static QName _GetByGoodsExchangeRackIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getByGoodsExchangeRackIdResponse");
    private final static QName _GetByGoodsExchangeRackId_QNAME = new QName("http://service.CB.froad.com/", "getByGoodsExchangeRackId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.GoodsExchangeRackImages
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddGoodsExchangeRackImagesResponse }
     * 
     */
    public AddGoodsExchangeRackImagesResponse createAddGoodsExchangeRackImagesResponse() {
        return new AddGoodsExchangeRackImagesResponse();
    }

    /**
     * Create an instance of {@link DeleteGoodsExchangeRackImagesResponse }
     * 
     */
    public DeleteGoodsExchangeRackImagesResponse createDeleteGoodsExchangeRackImagesResponse() {
        return new DeleteGoodsExchangeRackImagesResponse();
    }

    /**
     * Create an instance of {@link GetByPrimaryKeyResponse }
     * 
     */
    public GetByPrimaryKeyResponse createGetByPrimaryKeyResponse() {
        return new GetByPrimaryKeyResponse();
    }

    /**
     * Create an instance of {@link UpdateGoodsExchangeRackImages }
     * 
     */
    public UpdateGoodsExchangeRackImages createUpdateGoodsExchangeRackImages() {
        return new UpdateGoodsExchangeRackImages();
    }

    /**
     * Create an instance of {@link DeleteGoodsExchangeRackImages }
     * 
     */
    public DeleteGoodsExchangeRackImages createDeleteGoodsExchangeRackImages() {
        return new DeleteGoodsExchangeRackImages();
    }

    /**
     * Create an instance of {@link GetByGoodsExchangeRackId }
     * 
     */
    public GetByGoodsExchangeRackId createGetByGoodsExchangeRackId() {
        return new GetByGoodsExchangeRackId();
    }

    /**
     * Create an instance of {@link GetByGoodsExchangeRackIdResponse }
     * 
     */
    public GetByGoodsExchangeRackIdResponse createGetByGoodsExchangeRackIdResponse() {
        return new GetByGoodsExchangeRackIdResponse();
    }

    /**
     * Create an instance of {@link GetAllGoodsExchangeRackImagesResponse }
     * 
     */
    public GetAllGoodsExchangeRackImagesResponse createGetAllGoodsExchangeRackImagesResponse() {
        return new GetAllGoodsExchangeRackImagesResponse();
    }

    /**
     * Create an instance of {@link GetByPrimaryKey }
     * 
     */
    public GetByPrimaryKey createGetByPrimaryKey() {
        return new GetByPrimaryKey();
    }

    /**
     * Create an instance of {@link UpdateGoodsExchangeRackImagesResponse }
     * 
     */
    public UpdateGoodsExchangeRackImagesResponse createUpdateGoodsExchangeRackImagesResponse() {
        return new UpdateGoodsExchangeRackImagesResponse();
    }

    /**
     * Create an instance of {@link GetAllGoodsExchangeRackImages }
     * 
     */
    public GetAllGoodsExchangeRackImages createGetAllGoodsExchangeRackImages() {
        return new GetAllGoodsExchangeRackImages();
    }

    /**
     * Create an instance of {@link AddGoodsExchangeRackImages }
     * 
     */
    public AddGoodsExchangeRackImages createAddGoodsExchangeRackImages() {
        return new AddGoodsExchangeRackImages();
    }

    /**
     * Create an instance of {@link GoodsExchangeRackImages }
     * 
     */
    public GoodsExchangeRackImages createGoodsExchangeRackImages() {
        return new GoodsExchangeRackImages();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteGoodsExchangeRackImages }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteGoodsExchangeRackImages")
    public JAXBElement<DeleteGoodsExchangeRackImages> createDeleteGoodsExchangeRackImages(DeleteGoodsExchangeRackImages value) {
        return new JAXBElement<DeleteGoodsExchangeRackImages>(_DeleteGoodsExchangeRackImages_QNAME, DeleteGoodsExchangeRackImages.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGoodsExchangeRackImages }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateGoodsExchangeRackImages")
    public JAXBElement<UpdateGoodsExchangeRackImages> createUpdateGoodsExchangeRackImages(UpdateGoodsExchangeRackImages value) {
        return new JAXBElement<UpdateGoodsExchangeRackImages>(_UpdateGoodsExchangeRackImages_QNAME, UpdateGoodsExchangeRackImages.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByPrimaryKeyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByPrimaryKeyResponse")
    public JAXBElement<GetByPrimaryKeyResponse> createGetByPrimaryKeyResponse(GetByPrimaryKeyResponse value) {
        return new JAXBElement<GetByPrimaryKeyResponse>(_GetByPrimaryKeyResponse_QNAME, GetByPrimaryKeyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteGoodsExchangeRackImagesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteGoodsExchangeRackImagesResponse")
    public JAXBElement<DeleteGoodsExchangeRackImagesResponse> createDeleteGoodsExchangeRackImagesResponse(DeleteGoodsExchangeRackImagesResponse value) {
        return new JAXBElement<DeleteGoodsExchangeRackImagesResponse>(_DeleteGoodsExchangeRackImagesResponse_QNAME, DeleteGoodsExchangeRackImagesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsExchangeRackImagesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsExchangeRackImagesResponse")
    public JAXBElement<AddGoodsExchangeRackImagesResponse> createAddGoodsExchangeRackImagesResponse(AddGoodsExchangeRackImagesResponse value) {
        return new JAXBElement<AddGoodsExchangeRackImagesResponse>(_AddGoodsExchangeRackImagesResponse_QNAME, AddGoodsExchangeRackImagesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsExchangeRackImages }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsExchangeRackImages")
    public JAXBElement<AddGoodsExchangeRackImages> createAddGoodsExchangeRackImages(AddGoodsExchangeRackImages value) {
        return new JAXBElement<AddGoodsExchangeRackImages>(_AddGoodsExchangeRackImages_QNAME, AddGoodsExchangeRackImages.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllGoodsExchangeRackImages }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllGoodsExchangeRackImages")
    public JAXBElement<GetAllGoodsExchangeRackImages> createGetAllGoodsExchangeRackImages(GetAllGoodsExchangeRackImages value) {
        return new JAXBElement<GetAllGoodsExchangeRackImages>(_GetAllGoodsExchangeRackImages_QNAME, GetAllGoodsExchangeRackImages.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGoodsExchangeRackImagesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateGoodsExchangeRackImagesResponse")
    public JAXBElement<UpdateGoodsExchangeRackImagesResponse> createUpdateGoodsExchangeRackImagesResponse(UpdateGoodsExchangeRackImagesResponse value) {
        return new JAXBElement<UpdateGoodsExchangeRackImagesResponse>(_UpdateGoodsExchangeRackImagesResponse_QNAME, UpdateGoodsExchangeRackImagesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByPrimaryKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByPrimaryKey")
    public JAXBElement<GetByPrimaryKey> createGetByPrimaryKey(GetByPrimaryKey value) {
        return new JAXBElement<GetByPrimaryKey>(_GetByPrimaryKey_QNAME, GetByPrimaryKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllGoodsExchangeRackImagesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllGoodsExchangeRackImagesResponse")
    public JAXBElement<GetAllGoodsExchangeRackImagesResponse> createGetAllGoodsExchangeRackImagesResponse(GetAllGoodsExchangeRackImagesResponse value) {
        return new JAXBElement<GetAllGoodsExchangeRackImagesResponse>(_GetAllGoodsExchangeRackImagesResponse_QNAME, GetAllGoodsExchangeRackImagesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByGoodsExchangeRackIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByGoodsExchangeRackIdResponse")
    public JAXBElement<GetByGoodsExchangeRackIdResponse> createGetByGoodsExchangeRackIdResponse(GetByGoodsExchangeRackIdResponse value) {
        return new JAXBElement<GetByGoodsExchangeRackIdResponse>(_GetByGoodsExchangeRackIdResponse_QNAME, GetByGoodsExchangeRackIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByGoodsExchangeRackId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByGoodsExchangeRackId")
    public JAXBElement<GetByGoodsExchangeRackId> createGetByGoodsExchangeRackId(GetByGoodsExchangeRackId value) {
        return new JAXBElement<GetByGoodsExchangeRackId>(_GetByGoodsExchangeRackId_QNAME, GetByGoodsExchangeRackId.class, null, value);
    }

}
