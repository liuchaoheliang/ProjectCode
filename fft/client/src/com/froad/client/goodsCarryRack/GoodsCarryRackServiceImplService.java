
/*
 * 
 */

package com.froad.client.goodsCarryRack;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:37.742+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "GoodsCarryRackServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/GoodsCarryRack?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class GoodsCarryRackServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "GoodsCarryRackServiceImplService");
    public final static QName GoodsCarryRackServiceImplPort = new QName("http://impl.service.CB.froad.com/", "GoodsCarryRackServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/GoodsCarryRack?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/GoodsCarryRack?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public GoodsCarryRackServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GoodsCarryRackServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GoodsCarryRackServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns GoodsCarryRackService
     */
    @WebEndpoint(name = "GoodsCarryRackServiceImplPort")
    public GoodsCarryRackService getGoodsCarryRackServiceImplPort() {
        return super.getPort(GoodsCarryRackServiceImplPort, GoodsCarryRackService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GoodsCarryRackService
     */
    @WebEndpoint(name = "GoodsCarryRackServiceImplPort")
    public GoodsCarryRackService getGoodsCarryRackServiceImplPort(WebServiceFeature... features) {
        return super.getPort(GoodsCarryRackServiceImplPort, GoodsCarryRackService.class, features);
    }

}
