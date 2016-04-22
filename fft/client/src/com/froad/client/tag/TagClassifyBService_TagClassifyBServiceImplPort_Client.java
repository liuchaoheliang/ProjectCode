
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
 * 2014-04-15T16:36:26.397+08:00
 * Generated source version: 2.3.3
 * 
 */
public final class TagClassifyBService_TagClassifyBServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.CB.froad.com/", "TagClassifyBServiceImplService");

    private TagClassifyBService_TagClassifyBServiceImplPort_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = TagClassifyBServiceImplService.WSDL_LOCATION;
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
      
        TagClassifyBServiceImplService ss = new TagClassifyBServiceImplService(wsdlURL, SERVICE_NAME);
        TagClassifyBService port = ss.getTagClassifyBServiceImplPort();  
        
        {
        System.out.println("Invoking getAllTagClassifyB...");
        java.util.List<com.froad.client.tag.TagClassifyB> _getAllTagClassifyB__return = port.getAllTagClassifyB();
        System.out.println("getAllTagClassifyB.result=" + _getAllTagClassifyB__return);


        }
        {
        System.out.println("Invoking getTagClassifyBById...");
        java.lang.Integer _getTagClassifyBById_arg0 = null;
        com.froad.client.tag.TagClassifyB _getTagClassifyBById__return = port.getTagClassifyBById(_getTagClassifyBById_arg0);
        System.out.println("getTagClassifyBById.result=" + _getTagClassifyBById__return);


        }
        {
        System.out.println("Invoking getTagClassifyBByValue...");
        com.froad.client.tag.TagClassifyB _getTagClassifyBByValue_arg0 = null;
        java.util.List<com.froad.client.tag.TagClassifyB> _getTagClassifyBByValue__return = port.getTagClassifyBByValue(_getTagClassifyBByValue_arg0);
        System.out.println("getTagClassifyBByValue.result=" + _getTagClassifyBByValue__return);


        }
        {
        System.out.println("Invoking getAllTagClassifyBByEnabled...");
        java.util.List<com.froad.client.tag.TagClassifyB> _getAllTagClassifyBByEnabled__return = port.getAllTagClassifyBByEnabled();
        System.out.println("getAllTagClassifyBByEnabled.result=" + _getAllTagClassifyBByEnabled__return);


        }

        System.exit(0);
    }

}