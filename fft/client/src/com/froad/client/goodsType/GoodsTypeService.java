package com.froad.client.goodsType;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:36:54.261+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "GoodsTypeService")
@XmlSeeAlso({ObjectFactory.class})
public interface GoodsTypeService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addGoodsType", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.AddGoodsType")
    @WebMethod
    @ResponseWrapper(localName = "addGoodsTypeResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.AddGoodsTypeResponse")
    public java.lang.Integer addGoodsType(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.goodsType.GoodsType arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getGoodsTypeById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.GetGoodsTypeById")
    @WebMethod
    @ResponseWrapper(localName = "getGoodsTypeByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.GetGoodsTypeByIdResponse")
    public com.froad.client.goodsType.GoodsType getGoodsTypeById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAllGoodsType", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.GetAllGoodsType")
    @WebMethod
    @ResponseWrapper(localName = "getAllGoodsTypeResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.GetAllGoodsTypeResponse")
    public java.util.List<com.froad.client.goodsType.GoodsType> getAllGoodsType();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getGoodsTypeList", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.GetGoodsTypeList")
    @WebMethod
    @ResponseWrapper(localName = "getGoodsTypeListResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.GetGoodsTypeListResponse")
    public java.util.List<com.froad.client.goodsType.GoodsType> getGoodsTypeList(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.goodsType.GoodsType arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateGoodsType", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.UpdateGoodsType")
    @WebMethod
    @ResponseWrapper(localName = "updateGoodsTypeResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.goodsType.UpdateGoodsTypeResponse")
    public boolean updateGoodsType(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.goodsType.GoodsType arg0
    );
}