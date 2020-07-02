package itmo.abroskin.wst.soap_client;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import wst.abroskin.itmo.*;

import javax.xml.soap.MessageFactory;
import java.util.Date;
import java.util.concurrent.Callable;

import static itmo.abroskin.wst.core.utils.DateConverter.dateToGregorian;

enum Command {
    searchAlbum,
    updateAlbum,
    createAlbum,
    deleteAlbum,
}

@CommandLine.Command(name = "soap_client", mixinStandardHelpOptions = true, version = "0.1",
        description = "soap client for wst labs.")
public class Client implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Valid values: ${COMPLETION-CANDIDATES}")
    private Command command;

    @Option(names = {"--name"}, description = "The file whose checksum to calculate.")
    private String name;

    @Option(names = {"--author"}, description = "Author of the album.")
    private String author;

    @Option(names = {"--releaseDate"}, description = "album release date")
    private Date releaseDate;

    @Option(names = {"--billboardTop"}, description = "minimal suitable place in chart.")
    private Integer billboardTop;

    @Option(names = {"--publisher"}, description = "Company that has Copyright.")
    private String publisher;

    @Option(names = {"--id"})
    private Long id;

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

        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setCredentials(new UsernamePasswordCredentials("user", "password"));

        switch (command) {
            case searchAlbum: {
                GetAlbumsRequest request = new GetAlbumsRequest();
                request.setName(name);
                request.setAuthor(author);
                request.setBillboardDebut(billboardTop);
                request.setPublisher(publisher);
                if (releaseDate != null) {
                    request.setReleaseDate(dateToGregorian(releaseDate));
                }

                GetAlbumsResponse response = (GetAlbumsResponse) webServiceTemplate.marshalSendAndReceive(
                        "http://localhost:8080/ws",
                        request
                );

                System.out.println(response.getAlbums().toString());
                break;
            }
            case createAlbum: {
                CreateAlbumRequest request = new CreateAlbumRequest();
                request.setAuthor(author);
                request.setName(name);
                request.setPublisher(publisher);
                request.setBillboardDebut(billboardTop);
                if (releaseDate != null) {
                    request.setReleaseDate(dateToGregorian(releaseDate));
                }

                webServiceTemplate.setMessageSender(messageSender);
                CreateAlbumResponse response = (CreateAlbumResponse) webServiceTemplate.marshalSendAndReceive(
                        "http://localhost:8080/ws",
                        request
                );
                System.out.println("Created album with id: " + response.getId());
                break;
            }
            case deleteAlbum: {
                DeleteAlbumRequest request = new DeleteAlbumRequest();
                request.setId(id);

                webServiceTemplate.setMessageSender(messageSender);
                DeleteAlbumResponse response = (DeleteAlbumResponse) webServiceTemplate.marshalSendAndReceive(
                        "http://localhost:8080/ws",
                        request
                );
                if (!response.getError().isEmpty()) {
                    System.out.println("Can not delete album with error: " + response.getError());
                } else {
                    System.out.println("Successfully deleted album: " + request.getId());
                }
                break;
            }
            case updateAlbum: {
                UpdateAlbumRequest request = new UpdateAlbumRequest();
                request.setId(id);
                request.setName(name);
                request.setAuthor(author);
                request.setPublisher(publisher);
                request.setBillboardDebut(billboardTop);
                request.setReleaseDate(dateToGregorian(releaseDate));

                webServiceTemplate.setMessageSender(messageSender);
                UpdateAlbumResponse response = (UpdateAlbumResponse) webServiceTemplate.marshalSendAndReceive(
                        "http://localhost:8080/ws",
                        request
                );
                if (!response.getName().isEmpty()) {
                    System.out.println(response.getName());
                } else {
                    System.out.println("Can not update album with error: " + response.getName());
                }
                break;
            }
        }

        return 0;
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new Client()).execute(args);
        System.exit(exitCode);
    }
}