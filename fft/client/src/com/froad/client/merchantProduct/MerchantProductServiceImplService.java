
/*
 * 
 */

package com.froad.client.merchantProduct;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:38:31.047+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "MerchantProductServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/MerchantProduct?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class MerchantProductServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "MerchantProductServiceImplService");
    public final static QName MerchantProductServiceImplPort = new QName("http://impl.service.CB.froad.com/", "MerchantProductServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/MerchantProduct?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/MerchantProduct?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public MerchantProductServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MerchantProductServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MerchantProductServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns MerchantProductService
     */
    @WebEndpoint(name = "MerchantProductServiceImplPort")
    public MerchantProductService getMerchantProductServiceImplPort() {
        return super.getPort(MerchantProductServiceImplPort, MerchantProductService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MerchantProductService
     */
    @WebEndpoint(name = "MerchantProductServiceImplPort")
    public MerchantProductService getMerchantProductServiceImplPort(WebServiceFeature... features) {
        return super.getPort(MerchantProductServiceImplPort, MerchantProductService.class, features);
    }

}
