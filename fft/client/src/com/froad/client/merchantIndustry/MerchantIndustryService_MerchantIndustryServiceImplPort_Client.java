
package com.froad.client.merchantIndustry;

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
 * 2014-04-15T16:38:18.763+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class MerchantIndustryService_MerchantIndustryServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "MerchantIndustryServiceImplService");

    private MerchantIndustryService_MerchantIndustryServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = MerchantIndustryServiceImplService.WSDL_LOCATION;
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
      
        MerchantIndustryServiceImplService ss = new MerchantIndustryServiceImplService(wsdlURL, SERVICE_NAME);
        MerchantIndustryService port = ss.getMerchantIndustryServiceImplPort();  
        
        {
        System.out.println("Invoking add...");
        com.froad.client.merchantIndustry.MerchantIndustry _add_arg0 = null;
        java.lang.Integer _add__return = port.add(_add_arg0);
        System.out.println("add.result=" + _add__return);


        }
        {
        System.out.println("Invoking getAll...");
        java.util.List<com.froad.client.merchantIndustry.MerchantIndustry> _getAll__return = port.getAll();
        System.out.println("getAll.result=" + _getAll__return);


        }
        {
        System.out.println("Invoking updateById...");
        com.froad.client.merchantIndustry.MerchantIndustry _updateById_arg0 = null;
        port.updateById(_updateById_arg0);


        }
        {
        System.out.println("Invoking getById...");
        java.lang.Integer _getById_arg0 = null;
        com.froad.client.merchantIndustry.MerchantIndustry _getById__return = port.getById(_getById_arg0);
        System.out.println("getById.result=" + _getById__return);


        }
        {
        System.out.println("Invoking deleteById...");
        java.lang.Integer _deleteById_arg0 = null;
        port.deleteById(_deleteById_arg0);


        }

        System.exit(0);
    }

}
