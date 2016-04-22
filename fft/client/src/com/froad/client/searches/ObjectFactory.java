
package com.froad.client.searches;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.searches package. 
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

    private final static QName _QuerySearchesByPager_QNAME = new QName("http://service.CB.froad.com/", "querySearchesByPager");
    private final static QName _AddSearchesResponse_QNAME = new QName("http://service.CB.froad.com/", "addSearchesResponse");
    private final static QName _UpdSearchesByHisResponse_QNAME = new QName("http://service.CB.froad.com/", "updSearchesByHisResponse");
    private final static QName _GetSearchesByHis_QNAME = new QName("http://service.CB.froad.com/", "getSearchesByHis");
    private final static QName _GetSearchesByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getSearchesByIdResponse");
    private final static QName _GetSearchesById_QNAME = new QName("http://service.CB.froad.com/", "getSearchesById");
    private final static QName _GetSearchesByHisResponse_QNAME = new QName("http://service.CB.froad.com/", "getSearchesByHisResponse");
    private final static QName _AddSearches_QNAME = new QName("http://service.CB.froad.com/", "addSearches");
    private final static QName _QuerySearchesByPagerResponse_QNAME = new QName("http://service.CB.froad.com/", "querySearchesByPagerResponse");
    private final static QName _UpdSearchesByHis_QNAME = new QName("http://service.CB.froad.com/", "updSearchesByHis");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.searches
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSearchesByIdResponse }
     * 
     */
    public GetSearchesByIdResponse createGetSearchesByIdResponse() {
        return new GetSearchesByIdResponse();
    }

    /**
     * Create an instance of {@link GetSearchesByHis }
     * 
     */
    public GetSearchesByHis createGetSearchesByHis() {
        return new GetSearchesByHis();
    }

    /**
     * Create an instance of {@link UpdSearchesByHisResponse }
     * 
     */
    public UpdSearchesByHisResponse createUpdSearchesByHisResponse() {
        return new UpdSearchesByHisResponse();
    }

    /**
     * Create an instance of {@link AddSearchesResponse }
     * 
     */
    public AddSearchesResponse createAddSearchesResponse() {
        return new AddSearchesResponse();
    }

    /**
     * Create an instance of {@link QuerySearchesByPager }
     * 
     */
    public QuerySearchesByPager createQuerySearchesByPager() {
        return new QuerySearchesByPager();
    }

    /**
     * Create an instance of {@link UpdSearchesByHis }
     * 
     */
    public UpdSearchesByHis createUpdSearchesByHis() {
        return new UpdSearchesByHis();
    }

    /**
     * Create an instance of {@link QuerySearchesByPagerResponse }
     * 
     */
    public QuerySearchesByPagerResponse createQuerySearchesByPagerResponse() {
        return new QuerySearchesByPagerResponse();
    }

    /**
     * Create an instance of {@link AddSearches }
     * 
     */
    public AddSearches createAddSearches() {
        return new AddSearches();
    }

    /**
     * Create an instance of {@link GetSearchesByHisResponse }
     * 
     */
    public GetSearchesByHisResponse createGetSearchesByHisResponse() {
        return new GetSearchesByHisResponse();
    }

    /**
     * Create an instance of {@link GetSearchesById }
     * 
     */
    public GetSearchesById createGetSearchesById() {
        return new GetSearchesById();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link Searches }
     * 
     */
    public Searches createSearches() {
        return new Searches();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuerySearchesByPager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "querySearchesByPager")
    public JAXBElement<QuerySearchesByPager> createQuerySearchesByPager(QuerySearchesByPager value) {
        return new JAXBElement<QuerySearchesByPager>(_QuerySearchesByPager_QNAME, QuerySearchesByPager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSearchesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addSearchesResponse")
    public JAXBElement<AddSearchesResponse> createAddSearchesResponse(AddSearchesResponse value) {
        return new JAXBElement<AddSearchesResponse>(_AddSearchesResponse_QNAME, AddSearchesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdSearchesByHisResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updSearchesByHisResponse")
    public JAXBElement<UpdSearchesByHisResponse> createUpdSearchesByHisResponse(UpdSearchesByHisResponse value) {
        return new JAXBElement<UpdSearchesByHisResponse>(_UpdSearchesByHisResponse_QNAME, UpdSearchesByHisResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchesByHis }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSearchesByHis")
    public JAXBElement<GetSearchesByHis> createGetSearchesByHis(GetSearchesByHis value) {
        return new JAXBElement<GetSearchesByHis>(_GetSearchesByHis_QNAME, GetSearchesByHis.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchesByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSearchesByIdResponse")
    public JAXBElement<GetSearchesByIdResponse> createGetSearchesByIdResponse(GetSearchesByIdResponse value) {
        return new JAXBElement<GetSearchesByIdResponse>(_GetSearchesByIdResponse_QNAME, GetSearchesByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchesById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSearchesById")
    public JAXBElement<GetSearchesById> createGetSearchesById(GetSearchesById value) {
        return new JAXBElement<GetSearchesById>(_GetSearchesById_QNAME, GetSearchesById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchesByHisResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getSearchesByHisResponse")
    public JAXBElement<GetSearchesByHisResponse> createGetSearchesByHisResponse(GetSearchesByHisResponse value) {
        return new JAXBElement<GetSearchesByHisResponse>(_GetSearchesByHisResponse_QNAME, GetSearchesByHisResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSearches }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addSearches")
    public JAXBElement<AddSearches> createAddSearches(AddSearches value) {
        return new JAXBElement<AddSearches>(_AddSearches_QNAME, AddSearches.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QuerySearchesByPagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "querySearchesByPagerResponse")
    public JAXBElement<QuerySearchesByPagerResponse> createQuerySearchesByPagerResponse(QuerySearchesByPagerResponse value) {
        return new JAXBElement<QuerySearchesByPagerResponse>(_QuerySearchesByPagerResponse_QNAME, QuerySearchesByPagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdSearchesByHis }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updSearchesByHis")
    public JAXBElement<UpdSearchesByHis> createUpdSearchesByHis(UpdSearchesByHis value) {
        return new JAXBElement<UpdSearchesByHis>(_UpdSearchesByHis_QNAME, UpdSearchesByHis.class, null, value);
    }

}
