
package com.froad.client.complaintReply;

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
 * 2014-04-15T16:38:57.076+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class ComplaintReplyService_ComplaintReplyServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "ComplaintReplyServiceImplService");

    private ComplaintReplyService_ComplaintReplyServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = ComplaintReplyServiceImplService.WSDL_LOCATION;
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
      
        ComplaintReplyServiceImplService ss = new ComplaintReplyServiceImplService(wsdlURL, SERVICE_NAME);
        ComplaintReplyService port = ss.getComplaintReplyServiceImplPort();  
        
        {
        System.out.println("Invoking updateStateById...");
        java.lang.Integer _updateStateById_arg0 = null;
        java.lang.String _updateStateById_arg1 = "";
        boolean _updateStateById__return = port.updateStateById(_updateStateById_arg0, _updateStateById_arg1);
        System.out.println("updateStateById.result=" + _updateStateById__return);


        }
        {
        System.out.println("Invoking addComplaintReply...");
        com.froad.client.complaintReply.ComplaintReply _addComplaintReply_arg0 = null;
        com.froad.client.complaintReply.ComplaintReply _addComplaintReply__return = port.addComplaintReply(_addComplaintReply_arg0);
        System.out.println("addComplaintReply.result=" + _addComplaintReply__return);


        }

        System.exit(0);
    }

}