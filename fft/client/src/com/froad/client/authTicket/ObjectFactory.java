
package com.froad.client.authTicket;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.authTicket package. 
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

    private final static QName _GetAuthTickBySelective_QNAME = new QName("http://service.CB.froad.com/", "getAuthTickBySelective");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _GetAuthTickByTransId_QNAME = new QName("http://service.CB.froad.com/", "getAuthTickByTransId");
    private final static QName _GetAuthTicketById_QNAME = new QName("http://service.CB.froad.com/", "getAuthTicketById");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _UpdateStateById_QNAME = new QName("http://service.CB.froad.com/", "updateStateById");
    private final static QName _IsNotExistResponse_QNAME = new QName("http://service.CB.froad.com/", "isNotExistResponse");
    private final static QName _GetAuthTickByAuthId_QNAME = new QName("http://service.CB.froad.com/", "getAuthTickByAuthId");
    private final static QName _GetAuthTickBySelectiveResponse_QNAME = new QName("http://service.CB.froad.com/", "getAuthTickBySelectiveResponse");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _AddAuthTicket_QNAME = new QName("http://service.CB.froad.com/", "addAuthTicket");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _AppException_QNAME = new QName("http://service.CB.froad.com/", "AppException");
    private final static QName _GetAuthTicketByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getAuthTicketByIdResponse");
    private final static QName _GetAuthTickByTransIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getAuthTickByTransIdResponse");
    private final static QName _GetAuthTickByAuthIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getAuthTickByAuthIdResponse");
    private final static QName _UpdateStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateStateByIdResponse");
    private final static QName _AddAuthTicketResponse_QNAME = new QName("http://service.CB.froad.com/", "addAuthTicketResponse");
    private final static QName _IsNotExist_QNAME = new QName("http://service.CB.froad.com/", "isNotExist");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.authTicket
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAuthTicketById }
     * 
     */
    public GetAuthTicketById createGetAuthTicketById() {
        return new GetAuthTicketById();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link GetAuthTickByTransId }
     * 
     */
    public GetAuthTickByTransId createGetAuthTickByTransId() {
        return new GetAuthTickByTransId();
    }

    /**
     * Create an instance of {@link GetAuthTickBySelective }
     * 
     */
    public GetAuthTickBySelective createGetAuthTickBySelective() {
        return new GetAuthTickBySelective();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link GetAuthTickByAuthId }
     * 
     */
    public GetAuthTickByAuthId createGetAuthTickByAuthId() {
        return new GetAuthTickByAuthId();
    }

    /**
     * Create an instance of {@link GetAuthTickBySelectiveResponse }
     * 
     */
    public GetAuthTickBySelectiveResponse createGetAuthTickBySelectiveResponse() {
        return new GetAuthTickBySelectiveResponse();
    }

    /**
     * Create an instance of {@link IsNotExistResponse }
     * 
     */
    public IsNotExistResponse createIsNotExistResponse() {
        return new IsNotExistResponse();
    }

    /**
     * Create an instance of {@link UpdateStateById }
     * 
     */
    public UpdateStateById createUpdateStateById() {
        return new UpdateStateById();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link GetAuthTickByTransIdResponse }
     * 
     */
    public GetAuthTickByTransIdResponse createGetAuthTickByTransIdResponse() {
        return new GetAuthTickByTransIdResponse();
    }

    /**
     * Create an instance of {@link AppException }
     * 
     */
    public AppException createAppException() {
        return new AppException();
    }

    /**
     * Create an instance of {@link GetAuthTicketByIdResponse }
     * 
     */
    public GetAuthTicketByIdResponse createGetAuthTicketByIdResponse() {
        return new GetAuthTicketByIdResponse();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link AddAuthTicket }
     * 
     */
    public AddAuthTicket createAddAuthTicket() {
        return new AddAuthTicket();
    }

    /**
     * Create an instance of {@link IsNotExist }
     * 
     */
    public IsNotExist createIsNotExist() {
        return new IsNotExist();
    }

    /**
     * Create an instance of {@link AddAuthTicketResponse }
     * 
     */
    public AddAuthTicketResponse createAddAuthTicketResponse() {
        return new AddAuthTicketResponse();
    }

    /**
     * Create an instance of {@link UpdateStateByIdResponse }
     * 
     */
    public UpdateStateByIdResponse createUpdateStateByIdResponse() {
        return new UpdateStateByIdResponse();
    }

    /**
     * Create an instance of {@link GetAuthTickByAuthIdResponse }
     * 
     */
    public GetAuthTickByAuthIdResponse createGetAuthTickByAuthIdResponse() {
        return new GetAuthTickByAuthIdResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link AuthTicket }
     * 
     */
    public AuthTicket createAuthTicket() {
        return new AuthTicket();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthTickBySelective }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAuthTickBySelective")
    public JAXBElement<GetAuthTickBySelective> createGetAuthTickBySelective(GetAuthTickBySelective value) {
        return new JAXBElement<GetAuthTickBySelective>(_GetAuthTickBySelective_QNAME, GetAuthTickBySelective.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthTickByTransId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAuthTickByTransId")
    public JAXBElement<GetAuthTickByTransId> createGetAuthTickByTransId(GetAuthTickByTransId value) {
        return new JAXBElement<GetAuthTickByTransId>(_GetAuthTickByTransId_QNAME, GetAuthTickByTransId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthTicketById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAuthTicketById")
    public JAXBElement<GetAuthTicketById> createGetAuthTicketById(GetAuthTicketById value) {
        return new JAXBElement<GetAuthTicketById>(_GetAuthTicketById_QNAME, GetAuthTicketById.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateStateById")
    public JAXBElement<UpdateStateById> createUpdateStateById(UpdateStateById value) {
        return new JAXBElement<UpdateStateById>(_UpdateStateById_QNAME, UpdateStateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsNotExistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "isNotExistResponse")
    public JAXBElement<IsNotExistResponse> createIsNotExistResponse(IsNotExistResponse value) {
        return new JAXBElement<IsNotExistResponse>(_IsNotExistResponse_QNAME, IsNotExistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthTickByAuthId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAuthTickByAuthId")
    public JAXBElement<GetAuthTickByAuthId> createGetAuthTickByAuthId(GetAuthTickByAuthId value) {
        return new JAXBElement<GetAuthTickByAuthId>(_GetAuthTickByAuthId_QNAME, GetAuthTickByAuthId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthTickBySelectiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAuthTickBySelectiveResponse")
    public JAXBElement<GetAuthTickBySelectiveResponse> createGetAuthTickBySelectiveResponse(GetAuthTickBySelectiveResponse value) {
        return new JAXBElement<GetAuthTickBySelectiveResponse>(_GetAuthTickBySelectiveResponse_QNAME, GetAuthTickBySelectiveResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAuthTicket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addAuthTicket")
    public JAXBElement<AddAuthTicket> createAddAuthTicket(AddAuthTicket value) {
        return new JAXBElement<AddAuthTicket>(_AddAuthTicket_QNAME, AddAuthTicket.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthTicketByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAuthTicketByIdResponse")
    public JAXBElement<GetAuthTicketByIdResponse> createGetAuthTicketByIdResponse(GetAuthTicketByIdResponse value) {
        return new JAXBElement<GetAuthTicketByIdResponse>(_GetAuthTicketByIdResponse_QNAME, GetAuthTicketByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthTickByTransIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAuthTickByTransIdResponse")
    public JAXBElement<GetAuthTickByTransIdResponse> createGetAuthTickByTransIdResponse(GetAuthTickByTransIdResponse value) {
        return new JAXBElement<GetAuthTickByTransIdResponse>(_GetAuthTickByTransIdResponse_QNAME, GetAuthTickByTransIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthTickByAuthIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getAuthTickByAuthIdResponse")
    public JAXBElement<GetAuthTickByAuthIdResponse> createGetAuthTickByAuthIdResponse(GetAuthTickByAuthIdResponse value) {
        return new JAXBElement<GetAuthTickByAuthIdResponse>(_GetAuthTickByAuthIdResponse_QNAME, GetAuthTickByAuthIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateStateByIdResponse")
    public JAXBElement<UpdateStateByIdResponse> createUpdateStateByIdResponse(UpdateStateByIdResponse value) {
        return new JAXBElement<UpdateStateByIdResponse>(_UpdateStateByIdResponse_QNAME, UpdateStateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAuthTicketResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addAuthTicketResponse")
    public JAXBElement<AddAuthTicketResponse> createAddAuthTicketResponse(AddAuthTicketResponse value) {
        return new JAXBElement<AddAuthTicketResponse>(_AddAuthTicketResponse_QNAME, AddAuthTicketResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsNotExist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "isNotExist")
    public JAXBElement<IsNotExist> createIsNotExist(IsNotExist value) {
        return new JAXBElement<IsNotExist>(_IsNotExist_QNAME, IsNotExist.class, null, value);
    }

}
