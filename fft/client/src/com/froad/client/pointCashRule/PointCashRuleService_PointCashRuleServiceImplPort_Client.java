
package com.froad.client.pointCashRule;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:01.923+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class PointCashRuleService_PointCashRuleServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "PointCashRuleServiceImplService");

    private PointCashRuleService_PointCashRuleServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = PointCashRuleServiceImplService.WSDL_LOCATION;
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
      
        PointCashRuleServiceImplService ss = new PointCashRuleServiceImplService(wsdlURL, SERVICE_NAME);
        PointCashRuleService port = ss.getPointCashRuleServiceImplPort();  
        
        {
        System.out.println("Invoking getAllPointCashRule...");
        java.util.List<com.froad.client.pointCashRule.PointCashRule> _getAllPointCashRule__return = port.getAllPointCashRule();
        System.out.println("getAllPointCashRule.result=" + _getAllPointCashRule__return);


        }

        System.exit(0);
    }

}
