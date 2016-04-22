package com.froad.client.merchantPreferential;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:33.567+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "MerchantPreferentialService")
@XmlSeeAlso({ObjectFactory.class})
public interface MerchantPreferentialService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPreferentialById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialById")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPreferentialByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialByIdResponse")
    public com.froad.client.merchantPreferential.MerchantPreferential getMerchantPreferentialById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPreferentialByPager", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialByPager")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPreferentialByPagerResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialByPagerResponse")
    public com.froad.client.merchantPreferential.MerchantPreferential getMerchantPreferentialByPager(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPreferential.MerchantPreferential arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updMerchantPreferential", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.UpdMerchantPreferential")
    @WebMethod
    @ResponseWrapper(localName = "updMerchantPreferentialResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.UpdMerchantPreferentialResponse")
    public boolean updMerchantPreferential(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPreferential.MerchantPreferential arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPreferentialInfoByMerchantId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialInfoByMerchantId")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPreferentialInfoByMerchantIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialInfoByMerchantIdResponse")
    public java.util.List<com.froad.client.merchantPreferential.MerchantPreferential> getMerchantPreferentialInfoByMerchantId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updMerchantPreferentialInfo", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.UpdMerchantPreferentialInfo")
    @WebMethod
    @ResponseWrapper(localName = "updMerchantPreferentialInfoResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.UpdMerchantPreferentialInfoResponse")
    public com.froad.client.merchantPreferential.MerchantPreferential updMerchantPreferentialInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPreferential.MerchantPreferential arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPreferentialInfoByUserId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialInfoByUserId")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPreferentialInfoByUserIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialInfoByUserIdResponse")
    public java.util.List<com.froad.client.merchantPreferential.MerchantPreferential> getMerchantPreferentialInfoByUserId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPreferentialListByMerchantId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialListByMerchantId")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPreferentialListByMerchantIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.GetMerchantPreferentialListByMerchantIdResponse")
    public java.util.List<com.froad.client.merchantPreferential.MerchantPreferential> getMerchantPreferentialListByMerchantId(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPreferential.MerchantPreferential arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deletePreferentialById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.DeletePreferentialById")
    @WebMethod
    @ResponseWrapper(localName = "deletePreferentialByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.DeletePreferentialByIdResponse")
    public java.lang.Integer deletePreferentialById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPreferential.MerchantPreferential arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addMerchantPreferential", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.AddMerchantPreferential")
    @WebMethod
    @ResponseWrapper(localName = "addMerchantPreferentialResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPreferential.AddMerchantPreferentialResponse")
    public java.lang.Integer addMerchantPreferential(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPreferential.MerchantPreferential arg0
    );
}
