
package com.froad.client.hfcz;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.hfcz package. 
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

    private final static QName _AppException_QNAME = new QName("http://service.CB.froad.com/", "AppException");
    private final static QName _OnLine_QNAME = new QName("http://service.CB.froad.com/", "onLine");
    private final static QName _OnLineResponse_QNAME = new QName("http://service.CB.froad.com/", "onLineResponse");
    private final static QName _CheckParaCZNo_QNAME = new QName("http://service.CB.froad.com/", "checkParaCZNo");
    private final static QName _CheckParaCZNoResponse_QNAME = new QName("http://service.CB.froad.com/", "checkParaCZNoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.hfcz
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
     * Create an instance of {@link CheckParaCZNoResponse }
     * 
     */
    public CheckParaCZNoResponse createCheckParaCZNoResponse() {
        return new CheckParaCZNoResponse();
    }

    /**
     * Create an instance of {@link CheckParaCZNo }
     * 
     */
    public CheckParaCZNo createCheckParaCZNo() {
        return new CheckParaCZNo();
    }

    /**
     * Create an instance of {@link OnLineResponse }
     * 
     */
    public OnLineResponse createOnLineResponse() {
        return new OnLineResponse();
    }

    /**
     * Create an instance of {@link OnLine }
     * 
     */
    public OnLine createOnLine() {
        return new OnLine();
    }

    /**
     * Create an instance of {@link Hfcz }
     * 
     */
    public Hfcz createHfcz() {
        return new Hfcz();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link OnLine }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "onLine")
    public JAXBElement<OnLine> createOnLine(OnLine value) {
        return new JAXBElement<OnLine>(_OnLine_QNAME, OnLine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OnLineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "onLineResponse")
    public JAXBElement<OnLineResponse> createOnLineResponse(OnLineResponse value) {
        return new JAXBElement<OnLineResponse>(_OnLineResponse_QNAME, OnLineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckParaCZNo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "checkParaCZNo")
    public JAXBElement<CheckParaCZNo> createCheckParaCZNo(CheckParaCZNo value) {
        return new JAXBElement<CheckParaCZNo>(_CheckParaCZNo_QNAME, CheckParaCZNo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckParaCZNoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "checkParaCZNoResponse")
    public JAXBElement<CheckParaCZNoResponse> createCheckParaCZNoResponse(CheckParaCZNoResponse value) {
        return new JAXBElement<CheckParaCZNoResponse>(_CheckParaCZNoResponse_QNAME, CheckParaCZNoResponse.class, null, value);
    }

}
