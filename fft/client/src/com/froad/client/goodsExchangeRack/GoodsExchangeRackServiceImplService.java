
/*
 * 
 */

package com.froad.client.goodsExchangeRack;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:28.969+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "GoodsExchangeRackServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/GoodsExchangeRack?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class GoodsExchangeRackServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "GoodsExchangeRackServiceImplService");
    public final static QName GoodsExchangeRackServiceImplPort = new QName("http://impl.service.CB.froad.com/", "GoodsExchangeRackServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/GoodsExchangeRack?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/GoodsExchangeRack?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public GoodsExchangeRackServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GoodsExchangeRackServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GoodsExchangeRackServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns GoodsExchangeRackService
     */
    @WebEndpoint(name = "GoodsExchangeRackServiceImplPort")
    public GoodsExchangeRackService getGoodsExchangeRackServiceImplPort() {
        return super.getPort(GoodsExchangeRackServiceImplPort, GoodsExchangeRackService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GoodsExchangeRackService
     */
    @WebEndpoint(name = "GoodsExchangeRackServiceImplPort")
    public GoodsExchangeRackService getGoodsExchangeRackServiceImplPort(WebServiceFeature... features) {
        return super.getPort(GoodsExchangeRackServiceImplPort, GoodsExchangeRackService.class, features);
    }

}