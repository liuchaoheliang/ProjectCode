package com.froad.client.suggestion;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:48.316+08:00
 * Generated source version: 2.3.3
 * 
 */
 
@WebService(targetNamespace = "http://service.CB.froad.com/", name = "SuggestionService")
@XmlSeeAlso({ObjectFactory.class})
public interface SuggestionService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSuggestions", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.GetSuggestions")
    @WebMethod
    @ResponseWrapper(localName = "getSuggestionsResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.GetSuggestionsResponse")
    public java.util.List<com.froad.client.suggestion.Suggestion> getSuggestions(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.suggestion.Suggestion arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteStateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.DeleteStateById")
    @WebMethod
    @ResponseWrapper(localName = "deleteStateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.DeleteStateByIdResponse")
    public java.lang.Integer deleteStateById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSuggestionListByPager", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.GetSuggestionListByPager")
    @WebMethod
    @ResponseWrapper(localName = "getSuggestionListByPagerResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.GetSuggestionListByPagerResponse")
    public com.froad.client.suggestion.Suggestion getSuggestionListByPager(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.suggestion.Suggestion arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "selectById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.SelectById")
    @WebMethod
    @ResponseWrapper(localName = "selectByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.SelectByIdResponse")
    public com.froad.client.suggestion.Suggestion selectById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "updateById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.UpdateById")
    @WebMethod
    @ResponseWrapper(localName = "updateByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.UpdateByIdResponse")
    public java.lang.Integer updateById(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.suggestion.Suggestion arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "addSuggestion", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.AddSuggestion")
    @WebMethod
    @ResponseWrapper(localName = "addSuggestionResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.AddSuggestionResponse")
    public java.lang.Integer addSuggestion(
        @WebParam(name = "arg0", targetNamespace = "")
        com.froad.client.suggestion.Suggestion arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSuggestionByUserId", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.GetSuggestionByUserId")
    @WebMethod
    @ResponseWrapper(localName = "getSuggestionByUserIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.GetSuggestionByUserIdResponse")
    public java.util.List<com.froad.client.suggestion.Suggestion> getSuggestionByUserId(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "deleteById", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.DeleteById")
    @WebMethod
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://service.CB.froad.com/", className = "com.froad.client.suggestion.DeleteByIdResponse")
    public java.lang.Integer deleteById(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}
