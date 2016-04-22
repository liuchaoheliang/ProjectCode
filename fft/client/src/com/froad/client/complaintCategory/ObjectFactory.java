
package com.froad.client.complaintCategory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.complaintCategory package. 
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

    private final static QName _AddComplaintCategoryResponse_QNAME = new QName("http://service.CB.froad.com/", "addComplaintCategoryResponse");
    private final static QName _GetComplaintCategoryListResponse_QNAME = new QName("http://service.CB.froad.com/", "getComplaintCategoryListResponse");
    private final static QName _AddComplaintCategory_QNAME = new QName("http://service.CB.froad.com/", "addComplaintCategory");
    private final static QName _UpdateStateById_QNAME = new QName("http://service.CB.froad.com/", "updateStateById");
    private final static QName _StopComplaintCategoryResponse_QNAME = new QName("http://service.CB.froad.com/", "stopComplaintCategoryResponse");
    private final static QName _DeleteComplaintCategoryById_QNAME = new QName("http://service.CB.froad.com/", "deleteComplaintCategoryById");
    private final static QName _GetComplaintCategoryByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getComplaintCategoryByIdResponse");
    private final static QName _GetComplaintCategoryById_QNAME = new QName("http://service.CB.froad.com/", "getComplaintCategoryById");
    private final static QName _UpdateComplaintCategoryByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateComplaintCategoryByIdResponse");
    private final static QName _UpdateComplaintCategoryById_QNAME = new QName("http://service.CB.froad.com/", "updateComplaintCategoryById");
    private final static QName _DeleteComplaintCategoryByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteComplaintCategoryByIdResponse");
    private final static QName _StopComplaintCategory_QNAME = new QName("http://service.CB.froad.com/", "stopComplaintCategory");
    private final static QName _UpdateStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateStateByIdResponse");
    private final static QName _GetComplaintCategoryList_QNAME = new QName("http://service.CB.froad.com/", "getComplaintCategoryList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.complaintCategory
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddComplaintCategory }
     * 
     */
    public AddComplaintCategory createAddComplaintCategory() {
        return new AddComplaintCategory();
    }

    /**
     * Create an instance of {@link AddComplaintCategoryResponse }
     * 
     */
    public AddComplaintCategoryResponse createAddComplaintCategoryResponse() {
        return new AddComplaintCategoryResponse();
    }

    /**
     * Create an instance of {@link GetComplaintCategoryListResponse }
     * 
     */
    public GetComplaintCategoryListResponse createGetComplaintCategoryListResponse() {
        return new GetComplaintCategoryListResponse();
    }

    /**
     * Create an instance of {@link DeleteComplaintCategoryById }
     * 
     */
    public DeleteComplaintCategoryById createDeleteComplaintCategoryById() {
        return new DeleteComplaintCategoryById();
    }

    /**
     * Create an instance of {@link StopComplaintCategoryResponse }
     * 
     */
    public StopComplaintCategoryResponse createStopComplaintCategoryResponse() {
        return new StopComplaintCategoryResponse();
    }

    /**
     * Create an instance of {@link UpdateStateById }
     * 
     */
    public UpdateStateById createUpdateStateById() {
        return new UpdateStateById();
    }

    /**
     * Create an instance of {@link DeleteComplaintCategoryByIdResponse }
     * 
     */
    public DeleteComplaintCategoryByIdResponse createDeleteComplaintCategoryByIdResponse() {
        return new DeleteComplaintCategoryByIdResponse();
    }

    /**
     * Create an instance of {@link StopComplaintCategory }
     * 
     */
    public StopComplaintCategory createStopComplaintCategory() {
        return new StopComplaintCategory();
    }

    /**
     * Create an instance of {@link UpdateComplaintCategoryById }
     * 
     */
    public UpdateComplaintCategoryById createUpdateComplaintCategoryById() {
        return new UpdateComplaintCategoryById();
    }

    /**
     * Create an instance of {@link UpdateComplaintCategoryByIdResponse }
     * 
     */
    public UpdateComplaintCategoryByIdResponse createUpdateComplaintCategoryByIdResponse() {
        return new UpdateComplaintCategoryByIdResponse();
    }

    /**
     * Create an instance of {@link GetComplaintCategoryById }
     * 
     */
    public GetComplaintCategoryById createGetComplaintCategoryById() {
        return new GetComplaintCategoryById();
    }

    /**
     * Create an instance of {@link GetComplaintCategoryByIdResponse }
     * 
     */
    public GetComplaintCategoryByIdResponse createGetComplaintCategoryByIdResponse() {
        return new GetComplaintCategoryByIdResponse();
    }

    /**
     * Create an instance of {@link GetComplaintCategoryList }
     * 
     */
    public GetComplaintCategoryList createGetComplaintCategoryList() {
        return new GetComplaintCategoryList();
    }

    /**
     * Create an instance of {@link UpdateStateByIdResponse }
     * 
     */
    public UpdateStateByIdResponse createUpdateStateByIdResponse() {
        return new UpdateStateByIdResponse();
    }

    /**
     * Create an instance of {@link ComplaintCategory }
     * 
     */
    public ComplaintCategory createComplaintCategory() {
        return new ComplaintCategory();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddComplaintCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addComplaintCategoryResponse")
    public JAXBElement<AddComplaintCategoryResponse> createAddComplaintCategoryResponse(AddComplaintCategoryResponse value) {
        return new JAXBElement<AddComplaintCategoryResponse>(_AddComplaintCategoryResponse_QNAME, AddComplaintCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComplaintCategoryListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getComplaintCategoryListResponse")
    public JAXBElement<GetComplaintCategoryListResponse> createGetComplaintCategoryListResponse(GetComplaintCategoryListResponse value) {
        return new JAXBElement<GetComplaintCategoryListResponse>(_GetComplaintCategoryListResponse_QNAME, GetComplaintCategoryListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddComplaintCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addComplaintCategory")
    public JAXBElement<AddComplaintCategory> createAddComplaintCategory(AddComplaintCategory value) {
        return new JAXBElement<AddComplaintCategory>(_AddComplaintCategory_QNAME, AddComplaintCategory.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link StopComplaintCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "stopComplaintCategoryResponse")
    public JAXBElement<StopComplaintCategoryResponse> createStopComplaintCategoryResponse(StopComplaintCategoryResponse value) {
        return new JAXBElement<StopComplaintCategoryResponse>(_StopComplaintCategoryResponse_QNAME, StopComplaintCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteComplaintCategoryById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteComplaintCategoryById")
    public JAXBElement<DeleteComplaintCategoryById> createDeleteComplaintCategoryById(DeleteComplaintCategoryById value) {
        return new JAXBElement<DeleteComplaintCategoryById>(_DeleteComplaintCategoryById_QNAME, DeleteComplaintCategoryById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComplaintCategoryByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getComplaintCategoryByIdResponse")
    public JAXBElement<GetComplaintCategoryByIdResponse> createGetComplaintCategoryByIdResponse(GetComplaintCategoryByIdResponse value) {
        return new JAXBElement<GetComplaintCategoryByIdResponse>(_GetComplaintCategoryByIdResponse_QNAME, GetComplaintCategoryByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComplaintCategoryById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getComplaintCategoryById")
    public JAXBElement<GetComplaintCategoryById> createGetComplaintCategoryById(GetComplaintCategoryById value) {
        return new JAXBElement<GetComplaintCategoryById>(_GetComplaintCategoryById_QNAME, GetComplaintCategoryById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateComplaintCategoryByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateComplaintCategoryByIdResponse")
    public JAXBElement<UpdateComplaintCategoryByIdResponse> createUpdateComplaintCategoryByIdResponse(UpdateComplaintCategoryByIdResponse value) {
        return new JAXBElement<UpdateComplaintCategoryByIdResponse>(_UpdateComplaintCategoryByIdResponse_QNAME, UpdateComplaintCategoryByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateComplaintCategoryById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateComplaintCategoryById")
    public JAXBElement<UpdateComplaintCategoryById> createUpdateComplaintCategoryById(UpdateComplaintCategoryById value) {
        return new JAXBElement<UpdateComplaintCategoryById>(_UpdateComplaintCategoryById_QNAME, UpdateComplaintCategoryById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteComplaintCategoryByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteComplaintCategoryByIdResponse")
    public JAXBElement<DeleteComplaintCategoryByIdResponse> createDeleteComplaintCategoryByIdResponse(DeleteComplaintCategoryByIdResponse value) {
        return new JAXBElement<DeleteComplaintCategoryByIdResponse>(_DeleteComplaintCategoryByIdResponse_QNAME, DeleteComplaintCategoryByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopComplaintCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "stopComplaintCategory")
    public JAXBElement<StopComplaintCategory> createStopComplaintCategory(StopComplaintCategory value) {
        return new JAXBElement<StopComplaintCategory>(_StopComplaintCategory_QNAME, StopComplaintCategory.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComplaintCategoryList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getComplaintCategoryList")
    public JAXBElement<GetComplaintCategoryList> createGetComplaintCategoryList(GetComplaintCategoryList value) {
        return new JAXBElement<GetComplaintCategoryList>(_GetComplaintCategoryList_QNAME, GetComplaintCategoryList.class, null, value);
    }

}
