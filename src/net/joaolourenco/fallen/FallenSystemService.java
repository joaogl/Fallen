
package net.joaolourenco.fallen;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "FallenSystemService", targetNamespace = "http://fallen.joaolourenco.net/", wsdlLocation = "http://joaolourenco.net:8080/FallenWebservice/FallenSystemService?WSDL")
public class FallenSystemService
    extends Service
{

    private final static URL FALLENSYSTEMSERVICE_WSDL_LOCATION;
    private final static WebServiceException FALLENSYSTEMSERVICE_EXCEPTION;
    private final static QName FALLENSYSTEMSERVICE_QNAME = new QName("http://fallen.joaolourenco.net/", "FallenSystemService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://joaolourenco.net:8080/FallenWebservice/FallenSystemService?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        FALLENSYSTEMSERVICE_WSDL_LOCATION = url;
        FALLENSYSTEMSERVICE_EXCEPTION = e;
    }

    public FallenSystemService() {
        super(__getWsdlLocation(), FALLENSYSTEMSERVICE_QNAME);
    }

    public FallenSystemService(WebServiceFeature... features) {
        super(__getWsdlLocation(), FALLENSYSTEMSERVICE_QNAME, features);
    }

    public FallenSystemService(URL wsdlLocation) {
        super(wsdlLocation, FALLENSYSTEMSERVICE_QNAME);
    }

    public FallenSystemService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, FALLENSYSTEMSERVICE_QNAME, features);
    }

    public FallenSystemService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FallenSystemService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns FallenSystem
     */
    @WebEndpoint(name = "FallenSystemPort")
    public FallenSystem getFallenSystemPort() {
        return super.getPort(new QName("http://fallen.joaolourenco.net/", "FallenSystemPort"), FallenSystem.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FallenSystem
     */
    @WebEndpoint(name = "FallenSystemPort")
    public FallenSystem getFallenSystemPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://fallen.joaolourenco.net/", "FallenSystemPort"), FallenSystem.class, features);
    }

    private static URL __getWsdlLocation() {
        if (FALLENSYSTEMSERVICE_EXCEPTION!= null) {
            throw FALLENSYSTEMSERVICE_EXCEPTION;
        }
        return FALLENSYSTEMSERVICE_WSDL_LOCATION;
    }

}
