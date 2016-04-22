
package com.froad.client.lottery;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.lottery package. 
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

    private final static QName _QueryZCinfos_QNAME = new QName("http://service.CB.froad.com/", "queryZCinfos");
    private final static QName _QueryLastPeridReward_QNAME = new QName("http://service.CB.froad.com/", "queryLastPeridReward");
    private final static QName _QueryPeridListNow_QNAME = new QName("http://service.CB.froad.com/", "queryPeridListNow");
    private final static QName _CalOrderResponse_QNAME = new QName("http://service.CB.froad.com/", "calOrderResponse");
    private final static QName _QueryRewardByTranIDResponse_QNAME = new QName("http://service.CB.froad.com/", "queryRewardByTranIDResponse");
    private final static QName _CreateOrder_QNAME = new QName("http://service.CB.froad.com/", "createOrder");
    private final static QName _QueryPeridListNowResponse_QNAME = new QName("http://service.CB.froad.com/", "queryPeridListNowResponse");
    private final static QName _QueryRewardByTranID_QNAME = new QName("http://service.CB.froad.com/", "queryRewardByTranID");
    private final static QName _QueryZCinfosResponse_QNAME = new QName("http://service.CB.froad.com/", "queryZCinfosResponse");
    private final static QName _QueryLastPeridRewardResponse_QNAME = new QName("http://service.CB.froad.com/", "queryLastPeridRewardResponse");
    private final static QName _AppException_QNAME = new QName("http://service.CB.froad.com/", "AppException");
    private final static QName _CreateOrderResponse_QNAME = new QName("http://service.CB.froad.com/", "createOrderResponse");
    private final static QName _CalOrder_QNAME = new QName("http://service.CB.froad.com/", "calOrder");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.lottery
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CalOrderResponse }
     * 
     */
    public CalOrderResponse createCalOrderResponse() {
        return new CalOrderResponse();
    }

    /**
     * Create an instance of {@link QueryRewardByTranIDResponse }
     * 
     */
    public QueryRewardByTranIDResponse createQueryRewardByTranIDResponse() {
        return new QueryRewardByTranIDResponse();
    }

    /**
     * Create an instance of {@link QueryLastPeridReward }
     * 
     */
    public QueryLastPeridReward createQueryLastPeridReward() {
        return new QueryLastPeridReward();
    }

    /**
     * Create an instance of {@link QueryPeridListNow }
     * 
     */
    public QueryPeridListNow createQueryPeridListNow() {
        return new QueryPeridListNow();
    }

    /**
     * Create an instance of {@link QueryZCinfos }
     * 
     */
    public QueryZCinfos createQueryZCinfos() {
        return new QueryZCinfos();
    }

    /**
     * Create an instance of {@link QueryRewardByTranID }
     * 
     */
    public QueryRewardByTranID createQueryRewardByTranID() {
        return new QueryRewardByTranID();
    }

    /**
     * Create an instance of {@link QueryPeridListNowResponse }
     * 
     */
    public QueryPeridListNowResponse createQueryPeridListNowResponse() {
        return new QueryPeridListNowResponse();
    }

    /**
     * Create an instance of {@link CreateOrder }
     * 
     */
    public CreateOrder createCreateOrder() {
        return new CreateOrder();
    }

    /**
     * Create an instance of {@link CreateOrderResponse }
     * 
     */
    public CreateOrderResponse createCreateOrderResponse() {
        return new CreateOrderResponse();
    }

    /**
     * Create an instance of {@link QueryLastPeridRewardResponse }
     * 
     */
    public QueryLastPeridRewardResponse createQueryLastPeridRewardResponse() {
        return new QueryLastPeridRewardResponse();
    }

    /**
     * Create an instance of {@link AppException }
     * 
     */
    public AppException createAppException() {
        return new AppException();
    }

    /**
     * Create an instance of {@link QueryZCinfosResponse }
     * 
     */
    public QueryZCinfosResponse createQueryZCinfosResponse() {
        return new QueryZCinfosResponse();
    }

    /**
     * Create an instance of {@link CalOrder }
     * 
     */
    public CalOrder createCalOrder() {
        return new CalOrder();
    }

    /**
     * Create an instance of {@link Lottery }
     * 
     */
    public Lottery createLottery() {
        return new Lottery();
    }

    /**
     * Create an instance of {@link Period }
     * 
     */
    public Period createPeriod() {
        return new Period();
    }

    /**
     * Create an instance of {@link Ct }
     * 
     */
    public Ct createCt() {
        return new Ct();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryZCinfos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryZCinfos")
    public JAXBElement<QueryZCinfos> createQueryZCinfos(QueryZCinfos value) {
        return new JAXBElement<QueryZCinfos>(_QueryZCinfos_QNAME, QueryZCinfos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLastPeridReward }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryLastPeridReward")
    public JAXBElement<QueryLastPeridReward> createQueryLastPeridReward(QueryLastPeridReward value) {
        return new JAXBElement<QueryLastPeridReward>(_QueryLastPeridReward_QNAME, QueryLastPeridReward.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPeridListNow }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryPeridListNow")
    public JAXBElement<QueryPeridListNow> createQueryPeridListNow(QueryPeridListNow value) {
        return new JAXBElement<QueryPeridListNow>(_QueryPeridListNow_QNAME, QueryPeridListNow.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "calOrderResponse")
    public JAXBElement<CalOrderResponse> createCalOrderResponse(CalOrderResponse value) {
        return new JAXBElement<CalOrderResponse>(_CalOrderResponse_QNAME, CalOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRewardByTranIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryRewardByTranIDResponse")
    public JAXBElement<QueryRewardByTranIDResponse> createQueryRewardByTranIDResponse(QueryRewardByTranIDResponse value) {
        return new JAXBElement<QueryRewardByTranIDResponse>(_QueryRewardByTranIDResponse_QNAME, QueryRewardByTranIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "createOrder")
    public JAXBElement<CreateOrder> createCreateOrder(CreateOrder value) {
        return new JAXBElement<CreateOrder>(_CreateOrder_QNAME, CreateOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryPeridListNowResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryPeridListNowResponse")
    public JAXBElement<QueryPeridListNowResponse> createQueryPeridListNowResponse(QueryPeridListNowResponse value) {
        return new JAXBElement<QueryPeridListNowResponse>(_QueryPeridListNowResponse_QNAME, QueryPeridListNowResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRewardByTranID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryRewardByTranID")
    public JAXBElement<QueryRewardByTranID> createQueryRewardByTranID(QueryRewardByTranID value) {
        return new JAXBElement<QueryRewardByTranID>(_QueryRewardByTranID_QNAME, QueryRewardByTranID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryZCinfosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryZCinfosResponse")
    public JAXBElement<QueryZCinfosResponse> createQueryZCinfosResponse(QueryZCinfosResponse value) {
        return new JAXBElement<QueryZCinfosResponse>(_QueryZCinfosResponse_QNAME, QueryZCinfosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryLastPeridRewardResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "queryLastPeridRewardResponse")
    public JAXBElement<QueryLastPeridRewardResponse> createQueryLastPeridRewardResponse(QueryLastPeridRewardResponse value) {
        return new JAXBElement<QueryLastPeridRewardResponse>(_QueryLastPeridRewardResponse_QNAME, QueryLastPeridRewardResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "createOrderResponse")
    public JAXBElement<CreateOrderResponse> createCreateOrderResponse(CreateOrderResponse value) {
        return new JAXBElement<CreateOrderResponse>(_CreateOrderResponse_QNAME, CreateOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "calOrder")
    public JAXBElement<CalOrder> createCalOrder(CalOrder value) {
        return new JAXBElement<CalOrder>(_CalOrder_QNAME, CalOrder.class, null, value);
    }

}
