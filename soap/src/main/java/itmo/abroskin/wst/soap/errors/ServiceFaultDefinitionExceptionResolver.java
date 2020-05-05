package itmo.abroskin.wst.soap.errors;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class ServiceFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {
    private static final QName MESSAGE = new QName("message");
    private static final QName CODE = new QName("code");

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Caught exception: ", ex);

        SoapFaultDetail detail = fault.addFaultDetail();
        detail.addFaultDetailElement(CODE).addText("Internal error");
        detail.addFaultDetailElement(MESSAGE).addText("For now there is nothing more to say");

    }

}
