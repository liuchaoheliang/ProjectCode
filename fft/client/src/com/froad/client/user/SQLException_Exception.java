
package com.froad.client.user;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:36:13.778+08:00
 * Generated source version: 2.3.3
 * 
 */

@WebFault(name = "SQLException", targetNamespace = "http://user.service.CB.froad.com/")
public class SQLException_Exception extends Exception {
    public static final long serialVersionUID = 20140415163613L;
    
    private com.froad.client.user.SQLException sqlException;

    public SQLException_Exception() {
        super();
    }
    
    public SQLException_Exception(String message) {
        super(message);
    }
    
    public SQLException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLException_Exception(String message, com.froad.client.user.SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public SQLException_Exception(String message, com.froad.client.user.SQLException sqlException, Throwable cause) {
        super(message, cause);
        this.sqlException = sqlException;
    }

    public com.froad.client.user.SQLException getFaultInfo() {
        return this.sqlException;
    }
}