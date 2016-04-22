package com.froad.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-20  
 * @version 1.0
 */
public class StringResult extends StrutsResultSupport{
	/**
	 * UID
	 */
	private static final long serialVersionUID = 2423169545250326093L;
	private String contentTypeName; 
    private String stringName = ""; 

    public StringResult() { 
        super(); 
    } 

    public StringResult(String location) { 
        super(location); 
    } 

    protected void doExecute(String finalLocation, ActionInvocation invocation) 
            throws Exception { 
        HttpServletResponse response = (HttpServletResponse) invocation 
                .getInvocationContext().get(HTTP_RESPONSE); 
        // String contentType = (String) 
        // invocation.getStack().findValue(conditionalParse(contentTypeName, 
        // invocation)); 
        String contentType = conditionalParse(contentTypeName, invocation); 
        if (contentType == null) { 
            contentType = "text/plain; charset=UTF-8"; 
        } 
        response.setContentType(contentType); 
        PrintWriter out = response.getWriter(); 
        // String result = conditionalParse(stringName, invocation); 
        String result = (String) invocation.getStack().findValue(stringName); 
        out.println(result); 
        out.flush(); 
        out.close(); 

    } 

    public String getContentTypeName() { 
        return contentTypeName; 
    } 

    public void setContentTypeName(String contentTypeName) { 
        this.contentTypeName = contentTypeName; 
    } 

    public String getStringName() { 
        return stringName; 
    } 

    public void setStringName(String stringName) { 
        this.stringName = stringName; 
    } 
}
