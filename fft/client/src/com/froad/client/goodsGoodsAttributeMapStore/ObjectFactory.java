
package com.froad.client.goodsGoodsAttributeMapStore;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.goodsGoodsAttributeMapStore package. 
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

    private final static QName _AddGoodsGoodsAttributeMapStoreResponse_QNAME = new QName("http://service.CB.froad.com/", "addGoodsGoodsAttributeMapStoreResponse");
    private final static QName _AddGoodsGoodsAttributeMapStore_QNAME = new QName("http://service.CB.froad.com/", "addGoodsGoodsAttributeMapStore");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.goodsGoodsAttributeMapStore
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddGoodsGoodsAttributeMapStore }
     * 
     */
    public AddGoodsGoodsAttributeMapStore createAddGoodsGoodsAttributeMapStore() {
        return new AddGoodsGoodsAttributeMapStore();
    }

    /**
     * Create an instance of {@link AddGoodsGoodsAttributeMapStoreResponse }
     * 
     */
    public AddGoodsGoodsAttributeMapStoreResponse createAddGoodsGoodsAttributeMapStoreResponse() {
        return new AddGoodsGoodsAttributeMapStoreResponse();
    }

    /**
     * Create an instance of {@link GoodsGoodsAttributeMapStore }
     * 
     */
    public GoodsGoodsAttributeMapStore createGoodsGoodsAttributeMapStore() {
        return new GoodsGoodsAttributeMapStore();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsGoodsAttributeMapStoreResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsGoodsAttributeMapStoreResponse")
    public JAXBElement<AddGoodsGoodsAttributeMapStoreResponse> createAddGoodsGoodsAttributeMapStoreResponse(AddGoodsGoodsAttributeMapStoreResponse value) {
        return new JAXBElement<AddGoodsGoodsAttributeMapStoreResponse>(_AddGoodsGoodsAttributeMapStoreResponse_QNAME, AddGoodsGoodsAttributeMapStoreResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddGoodsGoodsAttributeMapStore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addGoodsGoodsAttributeMapStore")
    public JAXBElement<AddGoodsGoodsAttributeMapStore> createAddGoodsGoodsAttributeMapStore(AddGoodsGoodsAttributeMapStore value) {
        return new JAXBElement<AddGoodsGoodsAttributeMapStore>(_AddGoodsGoodsAttributeMapStore_QNAME, AddGoodsGoodsAttributeMapStore.class, null, value);
    }

}
