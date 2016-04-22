
/*
 * 
 */

package com.froad.client.goodsRebateRack;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:59.093+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "GoodsRebateRackServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/GoodsRebateRack?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class GoodsRebateRackServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "GoodsRebateRackServiceImplService");
    public final static QName GoodsRebateRackServiceImplPort = new QName("http://impl.service.CB.froad.com/", "GoodsRebateRackServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/GoodsRebateRack?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/GoodsRebateRack?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public GoodsRebateRackServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GoodsRebateRackServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GoodsRebateRackServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns GoodsRebateRackService
     */
    @WebEndpoint(name = "GoodsRebateRackServiceImplPort")
    public GoodsRebateRackService getGoodsRebateRackServiceImplPort() {
        return super.getPort(GoodsRebateRackServiceImplPort, GoodsRebateRackService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GoodsRebateRackService
     */
    @WebEndpoint(name = "GoodsRebateRackServiceImplPort")
    public GoodsRebateRackService getGoodsRebateRackServiceImplPort(WebServiceFeature... features) {
        return super.getPort(GoodsRebateRackServiceImplPort, GoodsRebateRackService.class, features);
    }

}
