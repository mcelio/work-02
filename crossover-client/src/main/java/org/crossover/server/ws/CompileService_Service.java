package org.crossover.server.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.1
 * 2015-07-29T17:39:28.934-03:00
 * Generated source version: 3.1.1
 * 
 */
@WebServiceClient(name = "compileService", 
                  wsdlLocation = "http://localhost:8080/crossover-server/services/compile?wsdl",
                  targetNamespace = "http://ws.server.crossover.org/") 
public class CompileService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ws.server.crossover.org/", "compileService");
    public final static QName CompileServiceImplPort = new QName("http://ws.server.crossover.org/", "CompileServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/crossover-server/services/compile?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(CompileService_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/crossover-server/services/compile?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public CompileService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public CompileService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CompileService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public CompileService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public CompileService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public CompileService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns CompileService
     */
    @WebEndpoint(name = "CompileServiceImplPort")
    public CompileService getCompileServiceImplPort() {
        return super.getPort(CompileServiceImplPort, CompileService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CompileService
     */
    @WebEndpoint(name = "CompileServiceImplPort")
    public CompileService getCompileServiceImplPort(WebServiceFeature... features) {
        return super.getPort(CompileServiceImplPort, CompileService.class, features);
    }

}