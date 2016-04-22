
package com.froad.client.advert;

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
 * 2014-04-15T16:38:02.511+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class AdvertService_AdvertServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "AdvertServiceImplService");

    private AdvertService_AdvertServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = AdvertServiceImplService.WSDL_LOCATION;
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
      
        AdvertServiceImplService ss = new AdvertServiceImplService(wsdlURL, SERVICE_NAME);
        AdvertService port = ss.getAdvertServiceImplPort();  
        
        {
        System.out.println("Invoking updateAdvert...");
        com.froad.client.advert.Advert _updateAdvert_arg0 = null;
        boolean _updateAdvert__return = port.updateAdvert(_updateAdvert_arg0);
        System.out.println("updateAdvert.result=" + _updateAdvert__return);


        }
        {
        System.out.println("Invoking getAdverts...");
        com.froad.client.advert.Advert _getAdverts_arg0 = null;
        java.util.List<com.froad.client.advert.Advert> _getAdverts__return = port.getAdverts(_getAdverts_arg0);
        System.out.println("getAdverts.result=" + _getAdverts__return);


        }
        {
        System.out.println("Invoking addAdvert...");
        com.froad.client.advert.Advert _addAdvert_arg0 = null;
        java.lang.Integer _addAdvert__return = port.addAdvert(_addAdvert_arg0);
        System.out.println("addAdvert.result=" + _addAdvert__return);


        }
        {
        System.out.println("Invoking stopAdvert...");
        com.froad.client.advert.Advert _stopAdvert_arg0 = null;
        boolean _stopAdvert__return = port.stopAdvert(_stopAdvert_arg0);
        System.out.println("stopAdvert.result=" + _stopAdvert__return);


        }
        {
        System.out.println("Invoking getAdvertByPager...");
        com.froad.client.advert.Advert _getAdvertByPager_arg0 = null;
        com.froad.client.advert.Advert _getAdvertByPager__return = port.getAdvertByPager(_getAdvertByPager_arg0);
        System.out.println("getAdvertByPager.result=" + _getAdvertByPager__return);


        }

        System.exit(0);
    }

}