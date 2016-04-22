
package com.froad.client.suggestion;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.suggestion package. 
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

    private final static QName _AddSuggestionResponse_QNAME = new QName("http://service.CB.froad.com/", "addSuggestionResponse");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _SelectByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "selectByIdResponse");
    private final static QName _GetSuggestionsResponse_QNAME = new QName("http://service.CB.froad.com/", "getSuggestionsResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetSuggestionByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getSuggestionByUserIdResponse");
    private final static QName _GetSuggestions_QNAME = new QName("http://service.CB.froad.com/", "getSuggestions");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _GetSuggestionListByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "getSuggestionListByPagerResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _DeleteStateById_QNAME = new QName("http://service.CB.froad.com/", "deleteStateById");
    private final static QName _DeleteStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteStateByIdResponse");
    private final static QName _GetSuggestionByUserId_QNAME = new QName("http://service.CB.froad.com/", "getSuggestionByUserId");
    private final static QName _AddSuggestion_QNAME = new QName("http://service.CB.froad.com/", "addSuggestion");
    private final static QName _GetSuggestionListByPager_QNAME = new QName("http://service.CB.froad.com/", "getSuggestionListByPager");
    private final static QName _SelectById_QNAME = new QName("http://service.CB.froad.com/", "selectById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.suggestion
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectByIdResponse }
     * 
     */
    public SelectByIdResponse createSelectByIdResponse() {
        return new SelectByIdResponse();
    }

    /**
     * Create an instance of {@link GetSuggestionsResponse }
     * 
     */
    public GetSuggestionsResponse createGetSuggestionsResponse() {
        return new GetSuggestionsResponse();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link AddSuggestionResponse }
     * 
     */
    public AddSuggestionResponse createAddSuggestionResponse() {
        return new AddSuggestionResponse();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
    }

    /**
     * Create an instance of {@link GetSuggestionListByPagerResponse }
     * 
     */
    public GetSuggestionListByPagerResponse createGetSuggestionListByPagerResponse() {
        return new GetSuggestionListByPagerResponse();
    }

    /**
     * Create an instance of {@link GetSuggestions }
     * 
     */
    public GetSuggestions createGetSuggestions() {
        return new GetSuggestions();
    }

    /**
     * Create an instance of {@link GetSuggestionByUserIdResponse }
     * 
     */
    public GetSuggestionByUserIdResponse createGetSuggestionByUserIdResponse() {
        return new GetSuggestionByUserIdResponse();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link AddSuggestion }
     * 
     */
    public AddSuggestion createAddSuggestion() {
        return new AddSuggestion();
    }

    /**
     * Create an instance of {@link GetSuggestionListByPager }
     * 
     */
    public GetSuggestionListByPager createGetSuggestionListByPager() {
        return new GetSuggestionListByPager();
    }

    /**
     * Create an instance of {@link SelectById }
     * 
     */
    public SelectById createSelectById() {
        return new SelectById();
    }

    /**
     * Create an instance of {@link GetSuggestionByUserId }
     * 
     */
    public GetSuggestionByUserId createGetSuggestionByUserId() {
        return new GetSuggestionByUserId();
    }

    /**
     * Create an instance of {@link DeleteStateByIdResponse }
     * 
     */
    public DeleteStateByIdResponse createDeleteStateByIdResponse() {
        return new DeleteStateByIdResponse();
    }

    /**
     * Create an instance of {@link DeleteStateById }
     * 
     */
    public DeleteStateById createDeleteStateById() {
        return new DeleteStateById();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link Suggestion }
     * 
     */
    public Suggestion createSuggestion() {
        return new Suggestion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSuggestionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addSuggestionResponse")
    public JAXBElement<AddSuggestionResponse> createAddSuggestionResponse(AddSuggestionResponse value) {
        return new JAXBElement<AddSuggestionResponse>(_AddSuggestionResponse_QNAME, AddSuggestionResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "selectByIdResponse")
    public JAXBElement<SelectByIdResponse> createSelectByIdResponse(SelectByIdResponse value) {
        return new JAXBElement<SelectByIdResponse>(_SelectByIdResponse_QNAME, SelectByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSuggestionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSuggestionsResponse")
    public JAXBElement<GetSuggestionsResponse> createGetSuggestionsResponse(GetSuggestionsResponse value) {
        return new JAXBElement<GetSuggestionsResponse>(_GetSuggestionsResponse_QNAME, GetSuggestionsResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSuggestionByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSuggestionByUserIdResponse")
    public JAXBElement<GetSuggestionByUserIdResponse> createGetSuggestionByUserIdResponse(GetSuggestionByUserIdResponse value) {
        return new JAXBElement<GetSuggestionByUserIdResponse>(_GetSuggestionByUserIdResponse_QNAME, GetSuggestionByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSuggestions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSuggestions")
    public JAXBElement<GetSuggestions> createGetSuggestions(GetSuggestions value) {
        return new JAXBElement<GetSuggestions>(_GetSuggestions_QNAME, GetSuggestions.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSuggestionListByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSuggestionListByPagerResponse")
    public JAXBElement<GetSuggestionListByPagerResponse> createGetSuggestionListByPagerResponse(GetSuggestionListByPagerResponse value) {
        return new JAXBElement<GetSuggestionListByPagerResponse>(_GetSuggestionListByPagerResponse_QNAME, GetSuggestionListByPagerResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteStateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteStateById")
    public JAXBElement<DeleteStateById> createDeleteStateById(DeleteStateById value) {
        return new JAXBElement<DeleteStateById>(_DeleteStateById_QNAME, DeleteStateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteStateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteStateByIdResponse")
    public JAXBElement<DeleteStateByIdResponse> createDeleteStateByIdResponse(DeleteStateByIdResponse value) {
        return new JAXBElement<DeleteStateByIdResponse>(_DeleteStateByIdResponse_QNAME, DeleteStateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSuggestionByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSuggestionByUserId")
    public JAXBElement<GetSuggestionByUserId> createGetSuggestionByUserId(GetSuggestionByUserId value) {
        return new JAXBElement<GetSuggestionByUserId>(_GetSuggestionByUserId_QNAME, GetSuggestionByUserId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSuggestion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addSuggestion")
    public JAXBElement<AddSuggestion> createAddSuggestion(AddSuggestion value) {
        return new JAXBElement<AddSuggestion>(_AddSuggestion_QNAME, AddSuggestion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSuggestionListByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSuggestionListByPager")
    public JAXBElement<GetSuggestionListByPager> createGetSuggestionListByPager(GetSuggestionListByPager value) {
        return new JAXBElement<GetSuggestionListByPager>(_GetSuggestionListByPager_QNAME, GetSuggestionListByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "selectById")
    public JAXBElement<SelectById> createSelectById(SelectById value) {
        return new JAXBElement<SelectById>(_SelectById_QNAME, SelectById.class, null, value);
    }

}
