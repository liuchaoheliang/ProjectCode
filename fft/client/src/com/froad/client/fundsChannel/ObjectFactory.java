
package com.froad.client.fundsChannel;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.fundsChannel package. 
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

    private final static QName _GetFundsChannelById_QNAME = new QName("http://service.CB.froad.com/", "getFundsChannelById");
    private final static QName _GetFundsChannels_QNAME = new QName("http://service.CB.froad.com/", "getFundsChannels");
    private final static QName _IsExist_QNAME = new QName("http://service.CB.froad.com/", "isExist");
    private final static QName _GetAllResponse_QNAME = new QName("http://service.CB.froad.com/", "getAllResponse");
    private final static QName _GetFundsChannelByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getFundsChannelByIdResponse");
    private final static QName _IsExistResponse_QNAME = new QName("http://service.CB.froad.com/", "isExistResponse");
    private final static QName _GetAll_QNAME = new QName("http://service.CB.froad.com/", "getAll");
    private final static QName _GetFundsChannelsResponse_QNAME = new QName("http://service.CB.froad.com/", "getFundsChannelsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.fundsChannel
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IsExistResponse }
     * 
     */
    public IsExistResponse createIsExistResponse() {
        return new IsExistResponse();
    }

    /**
     * Create an instance of {@link GetFundsChannelByIdResponse }
     * 
     */
    public GetFundsChannelByIdResponse createGetFundsChannelByIdResponse() {
        return new GetFundsChannelByIdResponse();
    }

    /**
     * Create an instance of {@link GetAllResponse }
     * 
     */
    public GetAllResponse createGetAllResponse() {
        return new GetAllResponse();
    }

    /**
     * Create an instance of {@link IsExist }
     * 
     */
    public IsExist createIsExist() {
        return new IsExist();
    }

    /**
     * Create an instance of {@link GetFundsChannels }
     * 
     */
    public GetFundsChannels createGetFundsChannels() {
        return new GetFundsChannels();
    }

    /**
     * Create an instance of {@link GetFundsChannelById }
     * 
     */
    public GetFundsChannelById createGetFundsChannelById() {
        return new GetFundsChannelById();
    }

    /**
     * Create an instance of {@link GetFundsChannelsResponse }
     * 
     */
    public GetFundsChannelsResponse createGetFundsChannelsResponse() {
        return new GetFundsChannelsResponse();
    }

    /**
     * Create an instance of {@link GetAll }
     * 
     */
    public GetAll createGetAll() {
        return new GetAll();
    }

    /**
     * Create an instance of {@link FundsChannel }
     * 
     */
    public FundsChannel createFundsChannel() {
        return new FundsChannel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundsChannelById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getFundsChannelById")
    public JAXBElement<GetFundsChannelById> createGetFundsChannelById(GetFundsChannelById value) {
        return new JAXBElement<GetFundsChannelById>(_GetFundsChannelById_QNAME, GetFundsChannelById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundsChannels }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getFundsChannels")
    public JAXBElement<GetFundsChannels> createGetFundsChannels(GetFundsChannels value) {
        return new JAXBElement<GetFundsChannels>(_GetFundsChannels_QNAME, GetFundsChannels.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsExist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "isExist")
    public JAXBElement<IsExist> createIsExist(IsExist value) {
        return new JAXBElement<IsExist>(_IsExist_QNAME, IsExist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAllResponse")
    public JAXBElement<GetAllResponse> createGetAllResponse(GetAllResponse value) {
        return new JAXBElement<GetAllResponse>(_GetAllResponse_QNAME, GetAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundsChannelByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getFundsChannelByIdResponse")
    public JAXBElement<GetFundsChannelByIdResponse> createGetFundsChannelByIdResponse(GetFundsChannelByIdResponse value) {
        return new JAXBElement<GetFundsChannelByIdResponse>(_GetFundsChannelByIdResponse_QNAME, GetFundsChannelByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsExistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "isExistResponse")
    public JAXBElement<IsExistResponse> createIsExistResponse(IsExistResponse value) {
        return new JAXBElement<IsExistResponse>(_IsExistResponse_QNAME, IsExistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAll")
    public JAXBElement<GetAll> createGetAll(GetAll value) {
        return new JAXBElement<GetAll>(_GetAll_QNAME, GetAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFundsChannelsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getFundsChannelsResponse")
    public JAXBElement<GetFundsChannelsResponse> createGetFundsChannelsResponse(GetFundsChannelsResponse value) {
        return new JAXBElement<GetFundsChannelsResponse>(_GetFundsChannelsResponse_QNAME, GetFundsChannelsResponse.class, null, value);
    }

}
