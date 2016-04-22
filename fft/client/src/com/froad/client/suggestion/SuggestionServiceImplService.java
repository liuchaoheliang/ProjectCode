
/*
 * 
 */

package com.froad.client.suggestion;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.3.3
 * 2014-04-15T16:37:48.325+08:00
 * Generated source version: 2.3.3
 * 
 */


@WebServiceClient(name = "SuggestionServiceImplService", 
                  wsdlLocation = "http://localhost:8989/fft_server/Suggestion?wsdl",
                  targetNamespace = "http://impl.service.CB.froad.com/") 
public class SuggestionServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.CB.froad.com/", "SuggestionServiceImplService");
    public final static QName SuggestionServiceImplPort = new QName("http://impl.service.CB.froad.com/", "SuggestionServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8989/fft_server/Suggestion?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8989/fft_server/Suggestion?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public SuggestionServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SuggestionServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SuggestionServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns SuggestionService
     */
    @WebEndpoint(name = "SuggestionServiceImplPort")
    public SuggestionService getSuggestionServiceImplPort() {
        return super.getPort(SuggestionServiceImplPort, SuggestionService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SuggestionService
     */
    @WebEndpoint(name = "SuggestionServiceImplPort")
    public SuggestionService getSuggestionServiceImplPort(WebServiceFeature... features) {
        return super.getPort(SuggestionServiceImplPort, SuggestionService.class, features);
    }

}