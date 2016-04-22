
/*
 * 
 */

package com.froad.client.tempPoint;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:19.390+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "TempPointServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/TempPoint?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class TempPointServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "TempPointServiceImplService");
    public final static QName TempPointServiceImplPort = new QName("http://impl.service.CB.froad.com/", "TempPointServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/TempPoint?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/TempPoint?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public TempPointServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TempPointServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TempPointServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns TempPointService
     */
    @WebEndpoint(name = "TempPointServiceImplPort")
    public TempPointService getTempPointServiceImplPort() {
        return super.getPort(TempPointServiceImplPort, TempPointService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TempPointService
     */
    @WebEndpoint(name = "TempPointServiceImplPort")
    public TempPointService getTempPointServiceImplPort(WebServiceFeature... features) {
        return super.getPort(TempPointServiceImplPort, TempPointService.class, features);
    }

}
