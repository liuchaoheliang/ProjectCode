
/*
 * 
 */

package com.froad.client.merchantTraffic;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:09.870+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "MerchantTrafficServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/MerchantTraffic?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class MerchantTrafficServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "MerchantTrafficServiceImplService");
    public final static QName MerchantTrafficServiceImplPort = new QName("http://impl.service.CB.froad.com/", "MerchantTrafficServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/MerchantTraffic?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/MerchantTraffic?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public MerchantTrafficServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MerchantTrafficServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MerchantTrafficServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns MerchantTrafficService
     */
    @WebEndpoint(name = "MerchantTrafficServiceImplPort")
    public MerchantTrafficService getMerchantTrafficServiceImplPort() {
        return super.getPort(MerchantTrafficServiceImplPort, MerchantTrafficService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MerchantTrafficService
     */
    @WebEndpoint(name = "MerchantTrafficServiceImplPort")
    public MerchantTrafficService getMerchantTrafficServiceImplPort(WebServiceFeature... features) {
        return super.getPort(MerchantTrafficServiceImplPort, MerchantTrafficService.class, features);
    }

}