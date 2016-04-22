
package com.froad.client.authTicket;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:21.893+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class AuthTicketService_AuthTicketServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "AuthTicketServiceImplService");

    private AuthTicketService_AuthTicketServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = AuthTicketServiceImplService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        AuthTicketServiceImplService ss = new AuthTicketServiceImplService(wsdlURL, SERVICE_NAME);
        AuthTicketService port = ss.getAuthTicketServiceImplPort();  
        
        {
        System.out.println("Invoking getAuthTicketById...");
        java.lang.Integer _getAuthTicketById_arg0 = null;
        com.froad.client.authTicket.AuthTicket _getAuthTicketById__return = port.getAuthTicketById(_getAuthTicketById_arg0);
        System.out.println("getAuthTicketById.result=" + _getAuthTicketById__return);


        }
        {
        System.out.println("Invoking getAuthTickByAuthId...");
        java.lang.Integer _getAuthTickByAuthId_arg0 = null;
        java.lang.Integer _getAuthTickByAuthId_arg1 = null;
        com.froad.client.authTicket.Result _getAuthTickByAuthId__return = port.getAuthTickByAuthId(_getAuthTickByAuthId_arg0, _getAuthTickByAuthId_arg1);
        System.out.println("getAuthTickByAuthId.result=" + _getAuthTickByAuthId__return);


        }
        {
        System.out.println("Invoking getAuthTickBySelective...");
        com.froad.client.authTicket.AuthTicket _getAuthTickBySelective_arg0 = null;
        java.util.List<com.froad.client.authTicket.AuthTicket> _getAuthTickBySelective__return = port.getAuthTickBySelective(_getAuthTickBySelective_arg0);
        System.out.println("getAuthTickBySelective.result=" + _getAuthTickBySelective__return);


        }
        {
        System.out.println("Invoking addAuthTicket...");
        com.froad.client.authTicket.AuthTicket _addAuthTicket_arg0 = null;
        try {
            com.froad.client.authTicket.AuthTicket _addAuthTicket__return = port.addAuthTicket(_addAuthTicket_arg0);
            System.out.println("addAuthTicket.result=" + _addAuthTicket__return);

        } catch (AppException_Exception e) { 
            System.out.println("Expected exception: AppException has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking updateById...");
        com.froad.client.authTicket.AuthTicket _updateById_arg0 = null;
        boolean _updateById__return = port.updateById(_updateById_arg0);
        System.out.println("updateById.result=" + _updateById__return);


        }
        {
        System.out.println("Invoking updateStateById...");
        java.lang.Integer _updateStateById_arg0 = null;
        java.lang.String _updateStateById_arg1 = "";
        boolean _updateStateById__return = port.updateStateById(_updateStateById_arg0, _updateStateById_arg1);
        System.out.println("updateStateById.result=" + _updateStateById__return);


        }
        {
        System.out.println("Invoking isNotExist...");
        java.lang.String _isNotExist_arg0 = "";
        boolean _isNotExist__return = port.isNotExist(_isNotExist_arg0);
        System.out.println("isNotExist.result=" + _isNotExist__return);


        }
        {
        System.out.println("Invoking deleteById...");
        java.lang.Integer _deleteById_arg0 = null;
        boolean _deleteById__return = port.deleteById(_deleteById_arg0);
        System.out.println("deleteById.result=" + _deleteById__return);


        }
        {
        System.out.println("Invoking getAuthTickByTransId...");
        java.lang.Integer _getAuthTickByTransId_arg0 = null;
        java.util.List<com.froad.client.authTicket.AuthTicket> _getAuthTickByTransId__return = port.getAuthTickByTransId(_getAuthTickByTransId_arg0);
        System.out.println("getAuthTickByTransId.result=" + _getAuthTickByTransId__return);


        }

        System.exit(0);
    }

}