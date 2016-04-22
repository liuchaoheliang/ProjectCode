
package com.froad.client.tag;

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
 * 2014-04-15T16:36:31.338+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class TagDistrictBService_TagDistrictBServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "TagDistrictBServiceImplService");

    private TagDistrictBService_TagDistrictBServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = TagDistrictBServiceImplService.WSDL_LOCATION;
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
      
        TagDistrictBServiceImplService ss = new TagDistrictBServiceImplService(wsdlURL, SERVICE_NAME);
        TagDistrictBService port = ss.getTagDistrictBServiceImplPort();  
        
        {
        System.out.println("Invoking getAllTagDistrictBByEnabled...");
        java.util.List<com.froad.client.tag.TagDistrictB> _getAllTagDistrictBByEnabled__return = port.getAllTagDistrictBByEnabled();
        System.out.println("getAllTagDistrictBByEnabled.result=" + _getAllTagDistrictBByEnabled__return);


        }
        {
        System.out.println("Invoking getTagDistrictBById...");
        java.lang.Integer _getTagDistrictBById_arg0 = null;
        com.froad.client.tag.TagDistrictB _getTagDistrictBById__return = port.getTagDistrictBById(_getTagDistrictBById_arg0);
        System.out.println("getTagDistrictBById.result=" + _getTagDistrictBById__return);


        }
        {
        System.out.println("Invoking getTagDistrictBByValue...");
        com.froad.client.tag.TagDistrictB _getTagDistrictBByValue_arg0 = null;
        java.util.List<com.froad.client.tag.TagDistrictB> _getTagDistrictBByValue__return = port.getTagDistrictBByValue(_getTagDistrictBByValue_arg0);
        System.out.println("getTagDistrictBByValue.result=" + _getTagDistrictBByValue__return);


        }
        {
        System.out.println("Invoking getAllTagDistrictB...");
        java.util.List<com.froad.client.tag.TagDistrictB> _getAllTagDistrictB__return = port.getAllTagDistrictB();
        System.out.println("getAllTagDistrictB.result=" + _getAllTagDistrictB__return);


        }

        System.exit(0);
    }

}