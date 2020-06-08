package itmo.abroskin.wst.juddi;

import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlbumConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("itmo.abroskin.wst.juddi.wst");
        return marshaller;
    }

    @Bean
    public EndpointSelector endpointSelector() {
        return new EndpointSelector();
    }

    @Bean
    public BeerClient beerClient(Jaxb2Marshaller marshaller) {
        BeerClient client = new BeerClient(endpointSelector());
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}

