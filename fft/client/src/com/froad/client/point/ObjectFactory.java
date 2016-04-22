
package com.froad.client.point;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.point package. 
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

    private final static QName _QueryPoints_QNAME = new QName("http://service.CB.froad.com/", "queryPoints");
    private final static QName _FillPoints_QNAME = new QName("http://service.CB.froad.com/", "fillPoints");
    private final static QName _GetPointsCurrencyFormuleBySellerId_QNAME = new QName("http://service.CB.froad.com/", "getPointsCurrencyFormuleBySellerId");
    private final static QName _GetPointsCurrencyFormuleByMerchantId_QNAME = new QName("http://service.CB.froad.com/", "getPointsCurrencyFormuleByMerchantId");
    private final static QName _AppException_QNAME = new QName("http://service.CB.froad.com/", "AppException");
    private final static QName _QueryPointsResponse_QNAME = new QName("http://service.CB.froad.com/", "queryPointsResponse");
    private final static QName _FillPointsResponse_QNAME = new QName("http://service.CB.froad.com/", "fillPointsResponse");
    private final static QName _GetPointsCurrencyFormuleBySellerIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getPointsCurrencyFormuleBySellerIdResponse");
    private final static QName _PresentPointsResponse_QNAME = new QName("http://service.CB.froad.com/", "presentPointsResponse");
    private final static QName _PresentPoints_QNAME = new QName("http://service.CB.froad.com/", "presentPoints");
    private final static QName _GetPointsCurrencyFormuleByMerchantIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getPointsCurrencyFormuleByMerchantIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.point
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AppException }
     * 
     */
    public AppException createAppException() {
        return new AppException();
    }

    /**
     * Create an instance of {@link GetPointsCurrencyFormuleByMerchantId }
     * 
     */
    public GetPointsCurrencyFormuleByMerchantId createGetPointsCurrencyFormuleByMerchantId() {
        return new GetPointsCurrencyFormuleByMerchantId();
    }

    /**
     * Create an instance of {@link GetPointsCurrencyFormuleBySellerId }
     * 
     */
    public GetPointsCurrencyFormuleBySellerId createGetPointsCurrencyFormuleBySellerId() {
        return new GetPointsCurrencyFormuleBySellerId();
    }

    /**
     * Create an instance of {@link FillPoints }
     * 
     */
    public FillPoints createFillPoints() {
        return new FillPoints();
    }

    /**
     * Create an instance of {@link QueryPoints }
     * 
     */
    public QueryPoints createQueryPoints() {
        return new QueryPoints();
    }

    /**
     * Create an instance of {@link GetPointsCurrencyFormuleByMerchantIdResponse }
     * 
     */
    public GetPointsCurrencyFormuleByMerchantIdResponse createGetPointsCurrencyFormuleByMerchantIdResponse() {
        return new GetPointsCurrencyFormuleByMerchantIdResponse();
    }

    /**
     * Create an instance of {@link PresentPoints }
     * 
     */
    public PresentPoints createPresentPoints() {
        return new PresentPoints();
    }

    /**
     * Create an instance of {@link PresentPointsResponse }
     * 
     */
    public PresentPointsResponse createPresentPointsResponse() {
        return new PresentPointsResponse();
    }

    /**
     * Create an instance of {@link GetPointsCurrencyFormuleBySellerIdResponse }
     * 
     */
    public GetPointsCurrencyFormuleBySellerIdResponse createGetPointsCurrencyFormuleBySellerIdResponse() {
        return new GetPointsCurrencyFormuleBySellerIdResponse();
    }

    /**
     * Create an instance of {@link FillPointsResponse }
     * 
     */
    public FillPointsResponse createFillPointsResponse() {
        return new FillPointsResponse();
    }

    /**
     * Create an instance of {@link QueryPointsResponse }
     * 
     */
    public QueryPointsResponse createQueryPointsResponse() {
        return new QueryPointsResponse();
    }

    /**
     * Create an instance of {@link Pager }
     * 
     */
    public Pager createPager() {
        return new Pager();
    }

    /**
     * Create an instance of {@link PointsCurrencyFormula }
     * 
     */
    public PointsCurrencyFormula createPointsCurrencyFormula() {
        return new PointsCurrencyFormula();
    }

    /**
     * Create an instance of {@link Rule }
     * 
     */
    public Rule createRule() {
        return new Rule();
    }

    /**
     * Create an instance of {@link WithdrawPoints }
     * 
     */
    public WithdrawPoints createWithdrawPoints() {
        return new WithdrawPoints();
    }

    /**
     * Create an instance of {@link PointsAccount }
     * 
     */
    public PointsAccount createPointsAccount() {
        return new PointsAccount();
    }

    /**
     * Create an instance of {@link Points }
     * 
     */
    public Points createPoints() {
        return new Points();
    }

    /**
     * Create an instance of {@link SellerRuleDetail }
     * 
     */
    public SellerRuleDetail createSellerRuleDetail() {
        return new SellerRuleDetail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPoints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryPoints")
    public JAXBElement<QueryPoints> createQueryPoints(QueryPoints value) {
        return new JAXBElement<QueryPoints>(_QueryPoints_QNAME, QueryPoints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FillPoints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "fillPoints")
    public JAXBElement<FillPoints> createFillPoints(FillPoints value) {
        return new JAXBElement<FillPoints>(_FillPoints_QNAME, FillPoints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPointsCurrencyFormuleBySellerId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPointsCurrencyFormuleBySellerId")
    public JAXBElement<GetPointsCurrencyFormuleBySellerId> createGetPointsCurrencyFormuleBySellerId(GetPointsCurrencyFormuleBySellerId value) {
        return new JAXBElement<GetPointsCurrencyFormuleBySellerId>(_GetPointsCurrencyFormuleBySellerId_QNAME, GetPointsCurrencyFormuleBySellerId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPointsCurrencyFormuleByMerchantId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPointsCurrencyFormuleByMerchantId")
    public JAXBElement<GetPointsCurrencyFormuleByMerchantId> createGetPointsCurrencyFormuleByMerchantId(GetPointsCurrencyFormuleByMerchantId value) {
        return new JAXBElement<GetPointsCurrencyFormuleByMerchantId>(_GetPointsCurrencyFormuleByMerchantId_QNAME, GetPointsCurrencyFormuleByMerchantId.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPointsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryPointsResponse")
    public JAXBElement<QueryPointsResponse> createQueryPointsResponse(QueryPointsResponse value) {
        return new JAXBElement<QueryPointsResponse>(_QueryPointsResponse_QNAME, QueryPointsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FillPointsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "fillPointsResponse")
    public JAXBElement<FillPointsResponse> createFillPointsResponse(FillPointsResponse value) {
        return new JAXBElement<FillPointsResponse>(_FillPointsResponse_QNAME, FillPointsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPointsCurrencyFormuleBySellerIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPointsCurrencyFormuleBySellerIdResponse")
    public JAXBElement<GetPointsCurrencyFormuleBySellerIdResponse> createGetPointsCurrencyFormuleBySellerIdResponse(GetPointsCurrencyFormuleBySellerIdResponse value) {
        return new JAXBElement<GetPointsCurrencyFormuleBySellerIdResponse>(_GetPointsCurrencyFormuleBySellerIdResponse_QNAME, GetPointsCurrencyFormuleBySellerIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PresentPointsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "presentPointsResponse")
    public JAXBElement<PresentPointsResponse> createPresentPointsResponse(PresentPointsResponse value) {
        return new JAXBElement<PresentPointsResponse>(_PresentPointsResponse_QNAME, PresentPointsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PresentPoints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "presentPoints")
    public JAXBElement<PresentPoints> createPresentPoints(PresentPoints value) {
        return new JAXBElement<PresentPoints>(_PresentPoints_QNAME, PresentPoints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPointsCurrencyFormuleByMerchantIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getPointsCurrencyFormuleByMerchantIdResponse")
    public JAXBElement<GetPointsCurrencyFormuleByMerchantIdResponse> createGetPointsCurrencyFormuleByMerchantIdResponse(GetPointsCurrencyFormuleByMerchantIdResponse value) {
        return new JAXBElement<GetPointsCurrencyFormuleByMerchantIdResponse>(_GetPointsCurrencyFormuleByMerchantIdResponse_QNAME, GetPointsCurrencyFormuleByMerchantIdResponse.class, null, value);
    }

}
