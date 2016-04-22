package com.froad.client.agreement;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:04.442+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "AgreementService")
@XmlSeeAlso({ObjectFactory.class})
public interface AgreementService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAgreementById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.agreement.GetAgreementById")
    @WebMethod
    @ResponseWrapper(localName = "getAgreementByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.agreement.GetAgreementByIdResponse")
    public com.froad.client.agreement.Agreement getAgreementById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addAgreement", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.agreement.AddAgreement")
    @WebMethod
    @ResponseWrapper(localName = "addAgreementResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.agreement.AddAgreementResponse")
    public java.lang.Integer addAgreement(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.agreement.Agreement arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateAgreement", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.agreement.UpdateAgreement")
    @WebMethod
    @ResponseWrapper(localName = "updateAgreementResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.agreement.UpdateAgreementResponse")
    public boolean updateAgreement(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.agreement.Agreement arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAgreement", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.agreement.GetAgreement")
    @WebMethod
    @ResponseWrapper(localName = "getAgreementResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.agreement.GetAgreementResponse")
    public java.util.List<com.froad.client.agreement.Agreement> getAgreement(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.agreement.Agreement arg0
    );
}