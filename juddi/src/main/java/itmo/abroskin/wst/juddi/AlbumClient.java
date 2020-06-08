package itmo.abroskin.wst.juddi;

import wst.abroskin.itmo.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class AlbumClient extends WebServiceGatewaySupport {
    private EndpointSelector endpointSelector;

    public AlbumClient(EndpointSelector endpointSelector) {
        this.endpointSelector = endpointSelector;
    }

    public GetAlbumsResponse getBeers(GetAlbumsRequest request) {
        GetAlbumsResponse response = (GetAlbumsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(endpointSelector.getEndpoint("album"), request);

        return response;
    }
}

