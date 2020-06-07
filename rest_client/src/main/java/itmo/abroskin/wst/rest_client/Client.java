package itmo.abroskin.wst.rest_client;


import picocli.CommandLine;
import picocli.CommandLine.Option;


import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.concurrent.Callable;


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
        String host = "http://localhost:8080/";
        HttpClient client = HttpClient.newHttpClient();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost").setPort(8080);

        switch (command) {
            case searchAlbum: {
                builder.setPath("/album/");
                if (id != null) {
                    builder.setParameter("id", String.valueOf(id));
                }

                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(builder.build())
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response);

                break;
            }
            case createAlbum: {
                builder.setPath("/album/");
                HttpRequest request = HttpRequest.newBuilder()
                        .setHeader("content-type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString("{}"))
                        .uri(builder.build())
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response);
                break;
            }
            case deleteAlbum: {
                String url = "";

                if (id != null) {
                    builder.setPath("/album/" + id + "/");
                } else {
                    System.out.println("Please enter the `id` to delete album.");
                    break;
                }

                HttpRequest request = HttpRequest.newBuilder()
                        .DELETE()
                        .uri(URI.create(url))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response);
            }
            case updateAlbum: {
                String url = "";

                if (id != null) {
                    builder.setPath("/album/" + id + "/");
                } else {
                    System.out.println("Please enter the `id` to update album.");
                    break;
                }
                HttpRequest request = HttpRequest.newBuilder()
                        .PUT(HttpRequest.BodyPublishers.ofString("{}"))
                        .uri(URI.create(url))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response);
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
