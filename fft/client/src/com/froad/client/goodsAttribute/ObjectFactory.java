
package com.froad.client.goodsAttribute;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.goodsAttribute package. 
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

    private final static QName _GetGoodsAttributeByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsAttributeByIdResponse");
    private final static QName _GetGoodsAttributeById_QNAME = new QName("http://service.CB.froad.com/", "getGoodsAttributeById");
    private final static QName _AddGoodsAttribute_QNAME = new QName("http://service.CB.froad.com/", "addGoodsAttribute");
    private final static QName _UpdateGoodsAttribute_QNAME = new QName("http://service.CB.froad.com/", "updateGoodsAttribute");
    private final static QName _UpdateGoodsAttributeResponse_QNAME = new QName("http://service.CB.froad.com/", "updateGoodsAttributeResponse");
    private final static QName _GetGoodsAttributeList_QNAME = new QName("http://service.CB.froad.com/", "getGoodsAttributeList");
    private final static QName _GetGoodsAttributeListResponse_QNAME = new QName("http://service.CB.froad.com/", "getGoodsAttributeListResponse");
    private final static QName _AddGoodsAttributeResponse_QNAME = new QName("http://service.CB.froad.com/", "addGoodsAttributeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.goodsAttribute
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateGoodsAttribute }
     * 
     */
    public UpdateGoodsAttribute createUpdateGoodsAttribute() {
        return new UpdateGoodsAttribute();
    }

    /**
     * Create an instance of {@link AddGoodsAttribute }
     * 
     */
    public AddGoodsAttribute createAddGoodsAttribute() {
        return new AddGoodsAttribute();
    }

    /**
     * Create an instance of {@link GetGoodsAttributeById }
     * 
     */
    public GetGoodsAttributeById createGetGoodsAttributeById() {
        return new GetGoodsAttributeById();
    }

    /**
     * Create an instance of {@link GetGoodsAttributeByIdResponse }
     * 
     */
    public GetGoodsAttributeByIdResponse createGetGoodsAttributeByIdResponse() {
        return new GetGoodsAttributeByIdResponse();
    }

    /**
     * Create an instance of {@link AddGoodsAttributeResponse }
     * 
     */
    public AddGoodsAttributeResponse createAddGoodsAttributeResponse() {
        return new AddGoodsAttributeResponse();
    }

    /**
     * Create an instance of {@link GetGoodsAttributeListResponse }
     * 
     */
    public GetGoodsAttributeListResponse createGetGoodsAttributeListResponse() {
        return new GetGoodsAttributeListResponse();
    }

    /**
     * Create an instance of {@link GetGoodsAttributeList }
     * 
     */
    public GetGoodsAttributeList createGetGoodsAttributeList() {
        return new GetGoodsAttributeList();
    }

    /**
     * Create an instance of {@link UpdateGoodsAttributeResponse }
     * 
     */
    public UpdateGoodsAttributeResponse createUpdateGoodsAttributeResponse() {
        return new UpdateGoodsAttributeResponse();
    }

    /**
     * Create an instance of {@link GoodsAttribute }
     * 
     */
    public GoodsAttribute createGoodsAttribute() {
        return new GoodsAttribute();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsAttributeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsAttributeByIdResponse")
    public JAXBElement<GetGoodsAttributeByIdResponse> createGetGoodsAttributeByIdResponse(GetGoodsAttributeByIdResponse value) {
        return new JAXBElement<GetGoodsAttributeByIdResponse>(_GetGoodsAttributeByIdResponse_QNAME, GetGoodsAttributeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsAttributeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsAttributeById")
    public JAXBElement<GetGoodsAttributeById> createGetGoodsAttributeById(GetGoodsAttributeById value) {
        return new JAXBElement<GetGoodsAttributeById>(_GetGoodsAttributeById_QNAME, GetGoodsAttributeById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsAttribute")
    public JAXBElement<AddGoodsAttribute> createAddGoodsAttribute(AddGoodsAttribute value) {
        return new JAXBElement<AddGoodsAttribute>(_AddGoodsAttribute_QNAME, AddGoodsAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGoodsAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateGoodsAttribute")
    public JAXBElement<UpdateGoodsAttribute> createUpdateGoodsAttribute(UpdateGoodsAttribute value) {
        return new JAXBElement<UpdateGoodsAttribute>(_UpdateGoodsAttribute_QNAME, UpdateGoodsAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGoodsAttributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateGoodsAttributeResponse")
    public JAXBElement<UpdateGoodsAttributeResponse> createUpdateGoodsAttributeResponse(UpdateGoodsAttributeResponse value) {
        return new JAXBElement<UpdateGoodsAttributeResponse>(_UpdateGoodsAttributeResponse_QNAME, UpdateGoodsAttributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsAttributeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsAttributeList")
    public JAXBElement<GetGoodsAttributeList> createGetGoodsAttributeList(GetGoodsAttributeList value) {
        return new JAXBElement<GetGoodsAttributeList>(_GetGoodsAttributeList_QNAME, GetGoodsAttributeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGoodsAttributeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getGoodsAttributeListResponse")
    public JAXBElement<GetGoodsAttributeListResponse> createGetGoodsAttributeListResponse(GetGoodsAttributeListResponse value) {
        return new JAXBElement<GetGoodsAttributeListResponse>(_GetGoodsAttributeListResponse_QNAME, GetGoodsAttributeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsAttributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsAttributeResponse")
    public JAXBElement<AddGoodsAttributeResponse> createAddGoodsAttributeResponse(AddGoodsAttributeResponse value) {
        return new JAXBElement<AddGoodsAttributeResponse>(_AddGoodsAttributeResponse_QNAME, AddGoodsAttributeResponse.class, null, value);
    }

}
