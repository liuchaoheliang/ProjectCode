
package com.froad.client.dailyTaskRule;

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
 * 2014-04-15T16:39:14.053+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class DailyTaskRuleService_DailyTaskRuleServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "DailyTaskRuleServiceImplService");

    private DailyTaskRuleService_DailyTaskRuleServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = DailyTaskRuleServiceImplService.WSDL_LOCATION;
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
      
        DailyTaskRuleServiceImplService ss = new DailyTaskRuleServiceImplService(wsdlURL, SERVICE_NAME);
        DailyTaskRuleService port = ss.getDailyTaskRuleServiceImplPort();  
        
        {
        System.out.println("Invoking getDailyTaskRuleByPrimaryId...");
        java.lang.Integer _getDailyTaskRuleByPrimaryId_arg0 = null;
        com.froad.client.dailyTaskRule.DailyTaskRule _getDailyTaskRuleByPrimaryId__return = port.getDailyTaskRuleByPrimaryId(_getDailyTaskRuleByPrimaryId_arg0);
        System.out.println("getDailyTaskRuleByPrimaryId.result=" + _getDailyTaskRuleByPrimaryId__return);


        }

        System.exit(0);
    }

}
