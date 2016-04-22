package com.froad.client.merchantPhoto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:28.522+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "MerchantPhotoService")
@XmlSeeAlso({ObjectFactory.class})
public interface MerchantPhotoService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addMerchantPhoto", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.AddMerchantPhoto")
    @WebMethod
    @ResponseWrapper(localName = "addMerchantPhotoResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.AddMerchantPhotoResponse")
    public java.lang.Integer addMerchantPhoto(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPhoto.MerchantPhoto arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPhotoInfo", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoInfo")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPhotoInfoResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoInfoResponse")
    public java.util.List<com.froad.client.merchantPhoto.MerchantPhoto> getMerchantPhotoInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPhotoById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoById")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPhotoByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoByIdResponse")
    public com.froad.client.merchantPhoto.MerchantPhoto getMerchantPhotoById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPhotoByPager", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoByPager")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPhotoByPagerResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoByPagerResponse")
    public com.froad.client.merchantPhoto.MerchantPhoto getMerchantPhotoByPager(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPhoto.MerchantPhoto arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPhotoByUserId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoByUserId")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPhotoByUserIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoByUserIdResponse")
    public java.util.List<com.froad.client.merchantPhoto.MerchantPhoto> getMerchantPhotoByUserId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updMerchantPhotoInfo", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.UpdMerchantPhotoInfo")
    @WebMethod
    @ResponseWrapper(localName = "updMerchantPhotoInfoResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.UpdMerchantPhotoInfoResponse")
    public boolean updMerchantPhotoInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPhoto.MerchantPhoto arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPhotos", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotos")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPhotosResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotosResponse")
    public java.util.List<com.froad.client.merchantPhoto.MerchantPhoto> getMerchantPhotos(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPhoto.MerchantPhoto arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deletePhotoById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.DeletePhotoById")
    @WebMethod
    @ResponseWrapper(localName = "deletePhotoByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.DeletePhotoByIdResponse")
    public java.lang.Integer deletePhotoById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.merchantPhoto.MerchantPhoto arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getMerchantPhotoByMerchantId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoByMerchantId")
    @WebMethod
    @ResponseWrapper(localName = "getMerchantPhotoByMerchantIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.merchantPhoto.GetMerchantPhotoByMerchantIdResponse")
    public java.util.List<com.froad.client.merchantPhoto.MerchantPhoto> getMerchantPhotoByMerchantId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}