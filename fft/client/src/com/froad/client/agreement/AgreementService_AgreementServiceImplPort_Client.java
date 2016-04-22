
package com.froad.client.agreement;

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
 * 2014-04-15T16:37:04.376+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class AgreementService_AgreementServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "AgreementServiceImplService");

    private AgreementService_AgreementServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = AgreementServiceImplService.WSDL_LOCATION;
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
      
        AgreementServiceImplService ss = new AgreementServiceImplService(wsdlURL, SERVICE_NAME);
        AgreementService port = ss.getAgreementServiceImplPort();  
        
        {
        System.out.println("Invoking getAgreementById...");
        java.lang.Integer _getAgreementById_arg0 = null;
        com.froad.client.agreement.Agreement _getAgreementById__return = port.getAgreementById(_getAgreementById_arg0);
        System.out.println("getAgreementById.result=" + _getAgreementById__return);


        }
        {
        System.out.println("Invoking addAgreement...");
        com.froad.client.agreement.Agreement _addAgreement_arg0 = null;
        java.lang.Integer _addAgreement__return = port.addAgreement(_addAgreement_arg0);
        System.out.println("addAgreement.result=" + _addAgreement__return);


        }
        {
        System.out.println("Invoking updateAgreement...");
        com.froad.client.agreement.Agreement _updateAgreement_arg0 = null;
        boolean _updateAgreement__return = port.updateAgreement(_updateAgreement_arg0);
        System.out.println("updateAgreement.result=" + _updateAgreement__return);


        }
        {
        System.out.println("Invoking getAgreement...");
        com.froad.client.agreement.Agreement _getAgreement_arg0 = null;
        java.util.List<com.froad.client.agreement.Agreement> _getAgreement__return = port.getAgreement(_getAgreement_arg0);
        System.out.println("getAgreement.result=" + _getAgreement__return);


        }

        System.exit(0);
    }

}
