
package com.froad.client.goodsAttribute;

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
 * 2014-04-15T16:36:59.534+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class GoodsAttributeService_GoodsAttributeServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "GoodsAttributeServiceImplService");

    private GoodsAttributeService_GoodsAttributeServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = GoodsAttributeServiceImplService.WSDL_LOCATION;
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
      
        GoodsAttributeServiceImplService ss = new GoodsAttributeServiceImplService(wsdlURL, SERVICE_NAME);
        GoodsAttributeService port = ss.getGoodsAttributeServiceImplPort();  
        
        {
        System.out.println("Invoking getGoodsAttributeById...");
        java.lang.Integer _getGoodsAttributeById_arg0 = null;
        com.froad.client.goodsAttribute.GoodsAttribute _getGoodsAttributeById__return = port.getGoodsAttributeById(_getGoodsAttributeById_arg0);
        System.out.println("getGoodsAttributeById.result=" + _getGoodsAttributeById__return);


        }
        {
        System.out.println("Invoking getGoodsAttributeList...");
        com.froad.client.goodsAttribute.GoodsAttribute _getGoodsAttributeList_arg0 = null;
        java.util.List<com.froad.client.goodsAttribute.GoodsAttribute> _getGoodsAttributeList__return = port.getGoodsAttributeList(_getGoodsAttributeList_arg0);
        System.out.println("getGoodsAttributeList.result=" + _getGoodsAttributeList__return);


        }
        {
        System.out.println("Invoking addGoodsAttribute...");
        com.froad.client.goodsAttribute.GoodsAttribute _addGoodsAttribute_arg0 = null;
        java.lang.Integer _addGoodsAttribute__return = port.addGoodsAttribute(_addGoodsAttribute_arg0);
        System.out.println("addGoodsAttribute.result=" + _addGoodsAttribute__return);


        }
        {
        System.out.println("Invoking updateGoodsAttribute...");
        com.froad.client.goodsAttribute.GoodsAttribute _updateGoodsAttribute_arg0 = null;
        boolean _updateGoodsAttribute__return = port.updateGoodsAttribute(_updateGoodsAttribute_arg0);
        System.out.println("updateGoodsAttribute.result=" + _updateGoodsAttribute__return);


        }

        System.exit(0);
    }

}
