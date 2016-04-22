
/*
 * 
 */

package com.froad.client.activityLotteryResult;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:39:39.344+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "ActivityLotteryResultServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/ActivityLotteryResult?wsdl",
                  targetNamespace = "http://impl.activity.service.CB.froad.com/") 
public class ActivityLotteryResultServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.activity.service.CB.froad.com/", "ActivityLotteryResultServiceImplService");
    public final static QName ActivityLotteryResultServiceImplPort = new QName("http://impl.activity.service.CB.froad.com/", "ActivityLotteryResultServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/ActivityLotteryResult?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/ActivityLotteryResult?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public ActivityLotteryResultServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ActivityLotteryResultServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ActivityLotteryResultServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns ActivityLotteryResultService
     */
    @WebEndpoint(name = "ActivityLotteryResultServiceImplPort")
    public ActivityLotteryResultService getActivityLotteryResultServiceImplPort() {
        return super.getPort(ActivityLotteryResultServiceImplPort, ActivityLotteryResultService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ActivityLotteryResultService
     */
    @WebEndpoint(name = "ActivityLotteryResultServiceImplPort")
    public ActivityLotteryResultService getActivityLotteryResultServiceImplPort(WebServiceFeature... features) {
        return super.getPort(ActivityLotteryResultServiceImplPort, ActivityLotteryResultService.class, features);
    }

}
