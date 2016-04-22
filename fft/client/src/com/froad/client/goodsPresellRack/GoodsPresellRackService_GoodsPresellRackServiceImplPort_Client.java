
package com.froad.client.goodsPresellRack;

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
 * 2014-04-15T16:39:47.095+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class GoodsPresellRackService_GoodsPresellRackServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "GoodsPresellRackServiceImplService");

    private GoodsPresellRackService_GoodsPresellRackServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = GoodsPresellRackServiceImplService.WSDL_LOCATION;
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
      
        GoodsPresellRackServiceImplService ss = new GoodsPresellRackServiceImplService(wsdlURL, SERVICE_NAME);
        GoodsPresellRackService port = ss.getGoodsPresellRackServiceImplPort();  
        
        {
        System.out.println("Invoking add...");
        com.froad.client.goodsPresellRack.GoodsPresellRack _add_arg0 = null;
        java.lang.Integer _add__return = port.add(_add_arg0);
        System.out.println("add.result=" + _add__return);


        }
        {
        System.out.println("Invoking getServerNowTime...");
        javax.xml.datatype.XMLGregorianCalendar _getServerNowTime__return = port.getServerNowTime();
        System.out.println("getServerNowTime.result=" + _getServerNowTime__return);


        }
        {
        System.out.println("Invoking getByConditions...");
        com.froad.client.goodsPresellRack.GoodsPresellRack _getByConditions_arg0 = null;
        java.util.List<com.froad.client.goodsPresellRack.GoodsPresellRack> _getByConditions__return = port.getByConditions(_getByConditions_arg0);
        System.out.println("getByConditions.result=" + _getByConditions__return);


        }
        {
        System.out.println("Invoking getHistory...");
        com.froad.client.goodsPresellRack.GoodsPresellRack _getHistory_arg0 = null;
        java.util.List<com.froad.client.goodsPresellRack.GoodsPresellRack> _getHistory__return = port.getHistory(_getHistory_arg0);
        System.out.println("getHistory.result=" + _getHistory__return);


        }
        {
        System.out.println("Invoking updateById...");
        com.froad.client.goodsPresellRack.GoodsPresellRack _updateById_arg0 = null;
        boolean _updateById__return = port.updateById(_updateById_arg0);
        System.out.println("updateById.result=" + _updateById__return);


        }
        {
        System.out.println("Invoking getByPager...");
        com.froad.client.goodsPresellRack.GoodsPresellRack _getByPager_arg0 = null;
        com.froad.client.goodsPresellRack.GoodsPresellRack _getByPager__return = port.getByPager(_getByPager_arg0);
        System.out.println("getByPager.result=" + _getByPager__return);


        }
        {
        System.out.println("Invoking getById...");
        java.lang.Integer _getById_arg0 = null;
        com.froad.client.goodsPresellRack.GoodsPresellRack _getById__return = port.getById(_getById_arg0);
        System.out.println("getById.result=" + _getById__return);


        }

        System.exit(0);
    }

}