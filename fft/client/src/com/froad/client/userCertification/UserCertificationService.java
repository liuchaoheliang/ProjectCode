package com.froad.client.userCertification;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:16.739+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "UserCertificationService")
@XmlSeeAlso({ObjectFactory.class})
public interface UserCertificationService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addOrUpdateBindingNew", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.AddOrUpdateBindingNew")
    @WebMethod
    @ResponseWrapper(localName = "addOrUpdateBindingNewResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.AddOrUpdateBindingNewResponse")
    public java.lang.Integer addOrUpdateBindingNew(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.userCertification.UserCertification arg0
    ) throws AppException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.Add")
    @WebMethod
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.AddResponse")
    public java.lang.Integer add(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.userCertification.UserCertification arg0
    ) throws AppException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUserCertByUserId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.GetUserCertByUserId")
    @WebMethod
    @ResponseWrapper(localName = "getUserCertByUserIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.GetUserCertByUserIdResponse")
    public com.froad.client.userCertification.UserCertification getUserCertByUserId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "checkAccount", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.CheckAccount")
    @WebMethod
    @ResponseWrapper(localName = "checkAccountResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.CheckAccountResponse")
    public boolean checkAccount(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.userCertification.UserCertification arg0
    ) throws AppException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUserCertBySelective", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.GetUserCertBySelective")
    @WebMethod
    @ResponseWrapper(localName = "getUserCertBySelectiveResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.userCertification.GetUserCertBySelectiveResponse")
    public java.util.List<com.froad.client.userCertification.UserCertification> getUserCertBySelective(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.userCertification.UserCertification arg0
    );
}