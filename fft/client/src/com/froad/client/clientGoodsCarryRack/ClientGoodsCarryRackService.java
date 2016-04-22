package com.froad.client.clientGoodsCarryRack;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:43.750+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "ClientGoodsCarryRackService")
@XmlSeeAlso({ObjectFactory.class})
public interface ClientGoodsCarryRackService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getClientGoodsCarryRackByGoodsId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.GetClientGoodsCarryRackByGoodsId")
    @WebMethod
    @ResponseWrapper(localName = "getClientGoodsCarryRackByGoodsIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.GetClientGoodsCarryRackByGoodsIdResponse")
    public java.util.List<com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack> getClientGoodsCarryRackByGoodsId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteStateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.DeleteStateById")
    @WebMethod
    @ResponseWrapper(localName = "deleteStateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.DeleteStateByIdResponse")
    public java.lang.Integer deleteStateById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.SelectById")
    @WebMethod
    @ResponseWrapper(localName = "selectByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.SelectByIdResponse")
    public com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack selectById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getClientGoodsCarryRack", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.GetClientGoodsCarryRack")
    @WebMethod
    @ResponseWrapper(localName = "getClientGoodsCarryRackResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.GetClientGoodsCarryRackResponse")
    public java.util.List<com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack> getClientGoodsCarryRack(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.UpdateById")
    @WebMethod
    @ResponseWrapper(localName = "updateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.UpdateByIdResponse")
    public java.lang.Integer updateById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getClientGoodsCarryRackListByPager", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.GetClientGoodsCarryRackListByPager")
    @WebMethod
    @ResponseWrapper(localName = "getClientGoodsCarryRackListByPagerResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.GetClientGoodsCarryRackListByPagerResponse")
    public com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack getClientGoodsCarryRackListByPager(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addClientGoodsCarryRack", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.AddClientGoodsCarryRack")
    @WebMethod
    @ResponseWrapper(localName = "addClientGoodsCarryRackResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.AddClientGoodsCarryRackResponse")
    public java.lang.Integer addClientGoodsCarryRack(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.DeleteById")
    @WebMethod
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.clientGoodsCarryRack.DeleteByIdResponse")
    public java.lang.Integer deleteById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}
