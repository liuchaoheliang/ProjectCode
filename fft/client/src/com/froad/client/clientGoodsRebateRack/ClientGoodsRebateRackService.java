package com.froad.client.clientGoodsRebateRack;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:41.766+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "ClientGoodsRebateRackService")
@XmlSeeAlso({ObjectFactory.class})
public interface ClientGoodsRebateRackService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addClientGoodsRebateRack", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.AddClientGoodsRebateRack")
    @WebMethod
    @ResponseWrapper(localName = "addClientGoodsRebateRackResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.AddClientGoodsRebateRackResponse")
    public java.lang.Integer addClientGoodsRebateRack(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.clientGoodsRebateRack.ClientGoodsRebateRack arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getGoodsRebateRackByPager", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.GetGoodsRebateRackByPager")
    @WebMethod
    @ResponseWrapper(localName = "getGoodsRebateRackByPagerResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.GetGoodsRebateRackByPagerResponse")
    public com.froad.client.clientGoodsRebateRack.ClientGoodsRebateRack getGoodsRebateRackByPager(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.clientGoodsRebateRack.ClientGoodsRebateRack arg0
    );

    @RequestWrapper(localName = "updateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.UpdateById")
    @WebMethod
    @ResponseWrapper(localName = "updateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.UpdateByIdResponse")
    public void updateById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.clientGoodsRebateRack.ClientGoodsRebateRack arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getClientGoodsRebateRackById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.GetClientGoodsRebateRackById")
    @WebMethod
    @ResponseWrapper(localName = "getClientGoodsRebateRackByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.GetClientGoodsRebateRackByIdResponse")
    public com.froad.client.clientGoodsRebateRack.ClientGoodsRebateRack getClientGoodsRebateRackById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @RequestWrapper(localName = "updateStateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.UpdateStateById")
    @WebMethod
    @ResponseWrapper(localName = "updateStateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.UpdateStateByIdResponse")
    public void updateStateById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );

    @RequestWrapper(localName = "deleteById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.DeleteById")
    @WebMethod
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsRebateRack.DeleteByIdResponse")
    public void deleteById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );
}