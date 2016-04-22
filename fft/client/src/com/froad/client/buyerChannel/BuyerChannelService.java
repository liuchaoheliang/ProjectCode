package com.froad.client.buyerChannel;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:17.561+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "BuyerChannelService")
@XmlSeeAlso({ObjectFactory.class})
public interface BuyerChannelService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getBuyerChannelByUserId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.GetBuyerChannelByUserId")
    @WebMethod
    @ResponseWrapper(localName = "getBuyerChannelByUserIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.GetBuyerChannelByUserIdResponse")
    public com.froad.client.buyerChannel.BuyerChannel getBuyerChannelByUserId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteStateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.DeleteStateById")
    @WebMethod
    @ResponseWrapper(localName = "deleteStateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.DeleteStateByIdResponse")
    public java.lang.Integer deleteStateById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.SelectById")
    @WebMethod
    @ResponseWrapper(localName = "selectByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.SelectByIdResponse")
    public com.froad.client.buyerChannel.BuyerChannel selectById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateChannelByBuyerId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.UpdateChannelByBuyerId")
    @WebMethod
    @ResponseWrapper(localName = "updateChannelByBuyerIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.UpdateChannelByBuyerIdResponse")
    public int updateChannelByBuyerId(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.buyerChannel.BuyerChannel arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.UpdateById")
    @WebMethod
    @ResponseWrapper(localName = "updateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.UpdateByIdResponse")
    public java.lang.Integer updateById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.buyerChannel.BuyerChannel arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getBuyerChannelByMerchantId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.GetBuyerChannelByMerchantId")
    @WebMethod
    @ResponseWrapper(localName = "getBuyerChannelByMerchantIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.GetBuyerChannelByMerchantIdResponse")
    public com.froad.client.buyerChannel.BuyerChannel getBuyerChannelByMerchantId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addBuyerChannel", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.AddBuyerChannel")
    @WebMethod
    @ResponseWrapper(localName = "addBuyerChannelResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.AddBuyerChannelResponse")
    public java.lang.Integer addBuyerChannel(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.buyerChannel.BuyerChannel arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.DeleteById")
    @WebMethod
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.DeleteByIdResponse")
    public java.lang.Integer deleteById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "setDefaultChannel", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.SetDefaultChannel")
    @WebMethod
    @ResponseWrapper(localName = "setDefaultChannelResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.buyerChannel.SetDefaultChannelResponse")
    public boolean setDefaultChannel(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );
}