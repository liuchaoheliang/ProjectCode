
/*
 * 
 */

package com.froad.client.goodsPresellRack;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:47.162+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "GoodsPresellRackServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/GoodsPresellRack?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class GoodsPresellRackServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "GoodsPresellRackServiceImplService");
    public final static QName GoodsPresellRackServiceImplPort = new QName("http://impl.service.CB.froad.com/", "GoodsPresellRackServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/GoodsPresellRack?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/GoodsPresellRack?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public GoodsPresellRackServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GoodsPresellRackServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GoodsPresellRackServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns GoodsPresellRackService
     */
    @WebEndpoint(name = "GoodsPresellRackServiceImplPort")
    public GoodsPresellRackService getGoodsPresellRackServiceImplPort() {
        return super.getPort(GoodsPresellRackServiceImplPort, GoodsPresellRackService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GoodsPresellRackService
     */
    @WebEndpoint(name = "GoodsPresellRackServiceImplPort")
    public GoodsPresellRackService getGoodsPresellRackServiceImplPort(WebServiceFeature... features) {
        return super.getPort(GoodsPresellRackServiceImplPort, GoodsPresellRackService.class, features);
    }

}