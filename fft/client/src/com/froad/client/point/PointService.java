package com.froad.client.point;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:44.361+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "PointService")
@XmlSeeAlso({ObjectFactory.class})
public interface PointService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getPointsCurrencyFormuleByMerchantId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.GetPointsCurrencyFormuleByMerchantId")
    @WebMethod
    @ResponseWrapper(localName = "getPointsCurrencyFormuleByMerchantIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.GetPointsCurrencyFormuleByMerchantIdResponse")
    public com.froad.client.point.PointsCurrencyFormula getPointsCurrencyFormuleByMerchantId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "fillPoints", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.FillPoints")
    @WebMethod
    @ResponseWrapper(localName = "fillPointsResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.FillPointsResponse")
    public com.froad.client.point.Points fillPoints(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.point.Points arg0
    ) throws AppException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "queryPoints", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.QueryPoints")
    @WebMethod
    @ResponseWrapper(localName = "queryPointsResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.QueryPointsResponse")
    public com.froad.client.point.Points queryPoints(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.point.Points arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getPointsCurrencyFormuleBySellerId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.GetPointsCurrencyFormuleBySellerId")
    @WebMethod
    @ResponseWrapper(localName = "getPointsCurrencyFormuleBySellerIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.GetPointsCurrencyFormuleBySellerIdResponse")
    public com.froad.client.point.PointsCurrencyFormula getPointsCurrencyFormuleBySellerId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "presentPoints", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.PresentPoints")
    @WebMethod
    @ResponseWrapper(localName = "presentPointsResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.point.PresentPointsResponse")
    public com.froad.client.point.Points presentPoints(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.point.Points arg0
    ) throws AppException_Exception;
}