
package com.froad.client.shareMerchant;

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
 * 2014-04-15T16:38:59.536+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class ShareMerchantService_ShareMerchantServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "ShareMerchantServiceImplService");

    private ShareMerchantService_ShareMerchantServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = ShareMerchantServiceImplService.WSDL_LOCATION;
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
      
        ShareMerchantServiceImplService ss = new ShareMerchantServiceImplService(wsdlURL, SERVICE_NAME);
        ShareMerchantService port = ss.getShareMerchantServiceImplPort();  
        
        {
        System.out.println("Invoking getShareMerchantByPager...");
        com.froad.client.shareMerchant.ShareMerchant _getShareMerchantByPager_arg0 = null;
        com.froad.client.shareMerchant.ShareMerchant _getShareMerchantByPager__return = port.getShareMerchantByPager(_getShareMerchantByPager_arg0);
        System.out.println("getShareMerchantByPager.result=" + _getShareMerchantByPager__return);


        }
        {
        System.out.println("Invoking getShareMerchantByUserId...");
        java.lang.String _getShareMerchantByUserId_arg0 = "";
        java.util.List<com.froad.client.shareMerchant.ShareMerchant> _getShareMerchantByUserId__return = port.getShareMerchantByUserId(_getShareMerchantByUserId_arg0);
        System.out.println("getShareMerchantByUserId.result=" + _getShareMerchantByUserId__return);


        }
        {
        System.out.println("Invoking getShareMerchantById...");
        java.lang.Integer _getShareMerchantById_arg0 = null;
        com.froad.client.shareMerchant.ShareMerchant _getShareMerchantById__return = port.getShareMerchantById(_getShareMerchantById_arg0);
        System.out.println("getShareMerchantById.result=" + _getShareMerchantById__return);


        }
        {
        System.out.println("Invoking updateById...");
        com.froad.client.shareMerchant.ShareMerchant _updateById_arg0 = null;
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
        System.out.println("Invoking addShareMerchant...");
        com.froad.client.shareMerchant.ShareMerchant _addShareMerchant_arg0 = null;
        java.lang.Integer _addShareMerchant__return = port.addShareMerchant(_addShareMerchant_arg0);
        System.out.println("addShareMerchant.result=" + _addShareMerchant__return);


        }
        {
        System.out.println("Invoking deleteById...");
        java.lang.Integer _deleteById_arg0 = null;
        boolean _deleteById__return = port.deleteById(_deleteById_arg0);
        System.out.println("deleteById.result=" + _deleteById__return);


        }

        System.exit(0);
    }

}
