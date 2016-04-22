
/*
 * 
 */

package com.froad.client.goodsGoodsAttributeMapStore;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:02.011+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "GoodsGoodsAttributeMapStoreServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/GoodsGoodsAttributeMapStore?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class GoodsGoodsAttributeMapStoreServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "GoodsGoodsAttributeMapStoreServiceImplService");
    public final static QName GoodsGoodsAttributeMapStoreServiceImplPort = new QName("http://impl.service.CB.froad.com/", "GoodsGoodsAttributeMapStoreServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/GoodsGoodsAttributeMapStore?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/GoodsGoodsAttributeMapStore?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public GoodsGoodsAttributeMapStoreServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GoodsGoodsAttributeMapStoreServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GoodsGoodsAttributeMapStoreServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns GoodsGoodsAttributeMapStoreService
     */
    @WebEndpoint(name = "GoodsGoodsAttributeMapStoreServiceImplPort")
    public GoodsGoodsAttributeMapStoreService getGoodsGoodsAttributeMapStoreServiceImplPort() {
        return super.getPort(GoodsGoodsAttributeMapStoreServiceImplPort, GoodsGoodsAttributeMapStoreService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GoodsGoodsAttributeMapStoreService
     */
    @WebEndpoint(name = "GoodsGoodsAttributeMapStoreServiceImplPort")
    public GoodsGoodsAttributeMapStoreService getGoodsGoodsAttributeMapStoreServiceImplPort(WebServiceFeature... features) {
        return super.getPort(GoodsGoodsAttributeMapStoreServiceImplPort, GoodsGoodsAttributeMapStoreService.class, features);
    }

}