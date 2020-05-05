package itmo.abroskin.wst.soap.endpoints;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import itmo.abroskin.wst.core.services.album.AlbumCRUDService;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import wst.abroskin.itmo.AlbumDto;
import wst.abroskin.itmo.GetAlbumsRequest;
import wst.abroskin.itmo.GetAlbumsResponse;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class AlbumEndpoint {
    private static final String namespace = "itmo.abroskin.wst";

    private AlbumCRUDService albumService;
    private ConversionService conversionService;

    @Autowired
    public AlbumEndpoint(AlbumCRUDService albumCRUDService, ConversionService conversionService) {
        this.albumService = albumCRUDService;
        this.conversionService = conversionService;
    }

    @PayloadRoot(namespace = namespace, localPart = "getAlbumsRequest")
    @ResponsePayload
    public GetAlbumsResponse getAlbums(@RequestPayload GetAlbumsRequest request) {
        final AlbumSearchQueryDto query = conversionService.convert(request, AlbumSearchQueryDto.class);
        final List<Album> albums = albumService.search(query);
        final List<AlbumDto> albumDtos = albums.stream()
                .map(album -> conversionService.convert(album, AlbumDto.class))
                .collect(Collectors.toList());
        final GetAlbumsResponse response = new GetAlbumsResponse();
        response.getAlbums().addAll(albumDtos);

        return response;
    }
}
