
package com.froad.client.merchant;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:06.652+08:00
 * Generated source version: 2.3.3
 * 
 */

@WebFault(name = "AppException", targetNamespace = "http://service.CB.froad.com/")
public class AppException_Exception extends Exception {
    public static final long serialVersionUID = 20140415163806L;
    
    private com.froad.client.merchant.AppException appException;

    public AppException_Exception() {
        super();
    }
    
    public AppException_Exception(String message) {
        super(message);
    }
    
    public AppException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException_Exception(String message, com.froad.client.merchant.AppException appException) {
        super(message);
        this.appException = appException;
    }

    public AppException_Exception(String message, com.froad.client.merchant.AppException appException, Throwable cause) {
        super(message, cause);
        this.appException = appException;
    }

    public com.froad.client.merchant.AppException getFaultInfo() {
        return this.appException;
    }
}