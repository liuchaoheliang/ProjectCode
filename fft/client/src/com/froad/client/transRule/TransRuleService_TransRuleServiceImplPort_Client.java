
package com.froad.client.transRule;

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
 * 2014-04-15T16:37:06.983+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class TransRuleService_TransRuleServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "TransRuleServiceImplService");

    private TransRuleService_TransRuleServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = TransRuleServiceImplService.WSDL_LOCATION;
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
      
        TransRuleServiceImplService ss = new TransRuleServiceImplService(wsdlURL, SERVICE_NAME);
        TransRuleService port = ss.getTransRuleServiceImplPort();  
        
        {
        System.out.println("Invoking updateTranRuleDetail...");
        com.froad.client.transRule.RuleDetail _updateTranRuleDetail_arg0 = null;
        boolean _updateTranRuleDetail__return = port.updateTranRuleDetail(_updateTranRuleDetail_arg0);
        System.out.println("updateTranRuleDetail.result=" + _updateTranRuleDetail__return);


        }
        {
        System.out.println("Invoking addTranRuleDetail...");
        com.froad.client.transRule.RuleDetail _addTranRuleDetail_arg0 = null;
        java.lang.Integer _addTranRuleDetail__return = port.addTranRuleDetail(_addTranRuleDetail_arg0);
        System.out.println("addTranRuleDetail.result=" + _addTranRuleDetail__return);


        }
        {
        System.out.println("Invoking addTransRule...");
        com.froad.client.transRule.TransRule _addTransRule_arg0 = null;
        java.lang.Integer _addTransRule__return = port.addTransRule(_addTransRule_arg0);
        System.out.println("addTransRule.result=" + _addTransRule__return);


        }
        {
        System.out.println("Invoking updateTransRule...");
        com.froad.client.transRule.TransRule _updateTransRule_arg0 = null;
        boolean _updateTransRule__return = port.updateTransRule(_updateTransRule_arg0);
        System.out.println("updateTransRule.result=" + _updateTransRule__return);


        }
        {
        System.out.println("Invoking getTransRules...");
        com.froad.client.transRule.TransRule _getTransRules_arg0 = null;
        java.util.List<com.froad.client.transRule.TransRule> _getTransRules__return = port.getTransRules(_getTransRules_arg0);
        System.out.println("getTransRules.result=" + _getTransRules__return);


        }

        System.exit(0);
    }

}