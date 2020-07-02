package itmo.abroskin.wst.soap.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MethodEndpoint;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;
import wst.abroskin.itmo.GetAlbumsRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicAuthenticationInterceptor implements EndpointInterceptor {
    private BasicAuthenticationConverter basicAuthenticationConverter;

    public BasicAuthenticationInterceptor(BasicAuthenticationConverter basicAuthenticationConverter) {
        this.basicAuthenticationConverter = basicAuthenticationConverter;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext, Object o) throws Exception {
        if (!((MethodEndpoint)o).getMethod().isAnnotationPresent(SecuredByBasicAuth.class)) {
            return true;
        }

        TransportContext ctx = TransportContextHolder.getTransportContext();
        HttpServletRequest req = ((HttpServletConnection) ctx.getConnection()).getHttpServletRequest();

        UsernamePasswordAuthenticationToken token = basicAuthenticationConverter.convert(req);

        if (token != null && token.getPrincipal().equals("user") && token.getCredentials().equals("password")) {
            return true;
        }

        HttpServletResponse response = ((HttpServletConnection) ctx.getConnection()).getHttpServletResponse();

        response.setStatus(403);
        response.sendError(403);
        return false;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object o) throws Exception {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object o) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object o, Exception e) throws Exception {

    }
}