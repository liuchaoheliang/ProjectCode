
/*
 * 
 */

package com.froad.client.buyers;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:12.143+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "BuyersServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/Buyers?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class BuyersServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "BuyersServiceImplService");
    public final static QName BuyersServiceImplPort = new QName("http://impl.service.CB.froad.com/", "BuyersServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/Buyers?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/Buyers?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public BuyersServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public BuyersServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BuyersServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns BuyersService
     */
    @WebEndpoint(name = "BuyersServiceImplPort")
    public BuyersService getBuyersServiceImplPort() {
        return super.getPort(BuyersServiceImplPort, BuyersService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BuyersService
     */
    @WebEndpoint(name = "BuyersServiceImplPort")
    public BuyersService getBuyersServiceImplPort(WebServiceFeature... features) {
        return super.getPort(BuyersServiceImplPort, BuyersService.class, features);
    }

}