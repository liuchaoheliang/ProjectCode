package com.froad.client.tempPoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:19.382+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "TempPointService")
@XmlSeeAlso({ObjectFactory.class})
public interface TempPointService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.Add")
    @WebMethod
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.AddResponse")
    public java.lang.Integer add(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.tempPoint.TempPoint arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTempPointBySelective", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.GetTempPointBySelective")
    @WebMethod
    @ResponseWrapper(localName = "getTempPointBySelectiveResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.GetTempPointBySelectiveResponse")
    public java.util.List<com.froad.client.tempPoint.TempPoint> getTempPointBySelective(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.tempPoint.TempPoint arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.UpdateById")
    @WebMethod
    @ResponseWrapper(localName = "updateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.UpdateByIdResponse")
    public boolean updateById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.tempPoint.TempPoint arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateTempPointAndFillPoint", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.UpdateTempPointAndFillPoint")
    @WebMethod
    @ResponseWrapper(localName = "updateTempPointAndFillPointResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.UpdateTempPointAndFillPointResponse")
    public boolean updateTempPointAndFillPoint(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.util.List<com.froad.client.tempPoint.TempPoint> arg3
    ) throws AppException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTempPointById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.GetTempPointById")
    @WebMethod
    @ResponseWrapper(localName = "getTempPointByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.GetTempPointByIdResponse")
    public com.froad.client.tempPoint.TempPoint getTempPointById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.DeleteById")
    @WebMethod
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.DeleteByIdResponse")
    public boolean deleteById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getTempPointByPager", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.GetTempPointByPager")
    @WebMethod
    @ResponseWrapper(localName = "getTempPointByPagerResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.tempPoint.GetTempPointByPagerResponse")
    public com.froad.client.tempPoint.TempPoint getTempPointByPager(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.tempPoint.TempPoint arg0
    );
}