package com.froad.client.announcement;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:46.760+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "AnnouncementService")
@XmlSeeAlso({ObjectFactory.class})
public interface AnnouncementService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAnnouncementByPager", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.GetAnnouncementByPager")
    @WebMethod
    @ResponseWrapper(localName = "getAnnouncementByPagerResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.GetAnnouncementByPagerResponse")
    public com.froad.client.announcement.Announcement getAnnouncementByPager(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.announcement.Announcement arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "insert", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.Insert")
    @WebMethod
    @ResponseWrapper(localName = "insertResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.InsertResponse")
    public java.lang.Integer insert(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.announcement.Announcement arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAnnouncementById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.GetAnnouncementById")
    @WebMethod
    @ResponseWrapper(localName = "getAnnouncementByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.GetAnnouncementByIdResponse")
    public com.froad.client.announcement.Announcement getAnnouncementById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.UpdateById")
    @WebMethod
    @ResponseWrapper(localName = "updateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.UpdateByIdResponse")
    public boolean updateById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.announcement.Announcement arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getAnnouncementOrderByImportantList", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.GetAnnouncementOrderByImportantList")
    @WebMethod
    @ResponseWrapper(localName = "getAnnouncementOrderByImportantListResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.announcement.GetAnnouncementOrderByImportantListResponse")
    public java.util.List<com.froad.client.announcement.Announcement> getAnnouncementOrderByImportantList();
}
