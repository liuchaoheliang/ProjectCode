package com.froad.client.transDetails;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:36:46.668+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "TransDetailsService")
@XmlSeeAlso({ObjectFactory.class})
public interface TransDetailsService {

    @RequestWrapper(localName = "updateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.UpdateById")
    @WebMethod
    @ResponseWrapper(localName = "updateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.UpdateByIdResponse")
    public void updateById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.transDetails.TransDetails arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addTransDetails", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.AddTransDetails")
    @WebMethod
    @ResponseWrapper(localName = "addTransDetailsResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.AddTransDetailsResponse")
    public java.lang.Integer addTransDetails(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.transDetails.TransDetails arg0
    );

    @RequestWrapper(localName = "updateStateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.UpdateStateById")
    @WebMethod
    @ResponseWrapper(localName = "updateStateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.UpdateStateByIdResponse")
    public void updateStateById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );

    @RequestWrapper(localName = "deleteById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.DeleteById")
    @WebMethod
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.DeleteByIdResponse")
    public void deleteById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTransDetailsById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.GetTransDetailsById")
    @WebMethod
    @ResponseWrapper(localName = "getTransDetailsByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.transDetails.GetTransDetailsByIdResponse")
    public com.froad.client.transDetails.TransDetails getTransDetailsById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );
}
