package com.froad.client.merchantTrain;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:15.812+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "MerchantTrainService")
@XmlSeeAlso({ObjectFactory.class})
public interface MerchantTrainService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addMerchantTrain", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantTrain.AddMerchantTrain")
    @WebMethod
    @ResponseWrapper(localName = "addMerchantTrainResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantTrain.AddMerchantTrainResponse")
    public java.lang.Integer addMerchantTrain(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantTrain.MerchantTrain arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantTrain.UpdateById")
    @WebMethod
    @ResponseWrapper(localName = "updateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantTrain.UpdateByIdResponse")
    public boolean updateById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantTrain.MerchantTrain arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantTrainByUserId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantTrain.GetMerchantTrainByUserId")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantTrainByUserIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantTrain.GetMerchantTrainByUserIdResponse")
    public com.froad.client.merchantTrain.MerchantTrain getMerchantTrainByUserId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantTrainByMerchantId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantTrain.GetMerchantTrainByMerchantId")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantTrainByMerchantIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantTrain.GetMerchantTrainByMerchantIdResponse")
    public com.froad.client.merchantTrain.MerchantTrain getMerchantTrainByMerchantId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}
