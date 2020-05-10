package itmo.abroskin.wst.soap;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import wst.abroskin.itmo.GetAlbumsRequest;
import wst.abroskin.itmo.GetAlbumsResponse;

import javax.xml.soap.MessageFactory;
import java.time.Month;
import java.util.concurrent.Callable;

@Command(name = "soap_client", mixinStandardHelpOptions = true, version = "0.1",
        description = "soap client for wst labs.")
class SoapClient implements Callable<Integer> {

    @Option(names = {"--name"}, description = "The file whose checksum to calculate.")
    private String name;

    @Option(names = {"--author"}, description = "Author of the album.")
    private String author;

    @Option(names = {"--release-month"}, description = "album release date")
    private Month releaseMonth;

    @Option(names = {"--billboardTop"}, description = "minimal suitable place in chart.")
    private Integer billboardTop;

    @Option(names = {"--publisher"}, description = "Company that has Copyright.")
    private String publisher;

    @Option(names = {"--id"})
    private Long id;

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new SoapClient()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
        messageFactory.afterPropertiesSet();

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("wst.abroskin.itmo");
        marshaller.afterPropertiesSet();

        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        webServiceTemplate.afterPropertiesSet();
        GetAlbumsRequest request = new GetAlbumsRequest();
        request.setName(name);
        request.setAuthor(author);
        request.setBillboardDebut(billboardTop);
        request.setPublisher(publisher);

        GetAlbumsResponse response = (GetAlbumsResponse) webServiceTemplate.marshalSendAndReceive(
                "http://localhost:8080/ws",
                request
        );

        return 0;
    }
}