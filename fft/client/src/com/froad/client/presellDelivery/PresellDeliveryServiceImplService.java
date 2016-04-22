
/*
 * 
 */

package com.froad.client.presellDelivery;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:52.465+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "PresellDeliveryServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/PresellDelivery?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class PresellDeliveryServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "PresellDeliveryServiceImplService");
    public final static QName PresellDeliveryServiceImplPort = new QName("http://impl.service.CB.froad.com/", "PresellDeliveryServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/PresellDelivery?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/PresellDelivery?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public PresellDeliveryServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PresellDeliveryServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PresellDeliveryServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns PresellDeliveryService
     */
    @WebEndpoint(name = "PresellDeliveryServiceImplPort")
    public PresellDeliveryService getPresellDeliveryServiceImplPort() {
        return super.getPort(PresellDeliveryServiceImplPort, PresellDeliveryService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PresellDeliveryService
     */
    @WebEndpoint(name = "PresellDeliveryServiceImplPort")
    public PresellDeliveryService getPresellDeliveryServiceImplPort(WebServiceFeature... features) {
        return super.getPort(PresellDeliveryServiceImplPort, PresellDeliveryService.class, features);
    }

}
