
package com.froad.client.merchantClerkUrl;

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
 * 2014-04-15T16:39:29.165+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class MerchantClerkUrlService_MerchantClerkUrlServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "MerchantClerkUrlServiceImplService");

    private MerchantClerkUrlService_MerchantClerkUrlServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = MerchantClerkUrlServiceImplService.WSDL_LOCATION;
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
      
        MerchantClerkUrlServiceImplService ss = new MerchantClerkUrlServiceImplService(wsdlURL, SERVICE_NAME);
        MerchantClerkUrlService port = ss.getMerchantClerkUrlServiceImplPort();  
        
        {
        System.out.println("Invoking addMerchantClerkUrl...");
        com.froad.client.merchantClerkUrl.MerchantClerkUrl _addMerchantClerkUrl_arg0 = null;
        java.lang.Integer _addMerchantClerkUrl__return = port.addMerchantClerkUrl(_addMerchantClerkUrl_arg0);
        System.out.println("addMerchantClerkUrl.result=" + _addMerchantClerkUrl__return);


        }
        {
        System.out.println("Invoking getMerchantClerkUrl...");
        java.util.List<com.froad.client.merchantClerkUrl.MerchantClerkUrl> _getMerchantClerkUrl__return = port.getMerchantClerkUrl();
        System.out.println("getMerchantClerkUrl.result=" + _getMerchantClerkUrl__return);


        }

        System.exit(0);
    }

}
