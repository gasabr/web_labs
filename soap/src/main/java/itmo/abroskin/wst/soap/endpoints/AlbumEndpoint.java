package itmo.abroskin.wst.soap.endpoints;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumDeleteDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import itmo.abroskin.wst.core.services.album.AlbumCRUDService;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import wst.abroskin.itmo.*;

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

    @PayloadRoot(namespace = namespace, localPart = "createAlbumRequest")
    @ResponsePayload
    public CreateAlbumResponse createAlbum(@RequestPayload CreateAlbumRequest request) {
        final AlbumCreateDto dto = conversionService.convert(request, AlbumCreateDto.class);
        final CreateAlbumResponse response = new CreateAlbumResponse();

        try {
            long id = albumService.createAlbum(dto);
            response.setId(id);
        } catch (Exception e) {
            response.setError(e.getMessage());
        }
        return response;
    }

    @PayloadRoot(namespace = namespace, localPart = "updateAlbumRequest")
    @ResponsePayload
    public UpdateAlbumResponse updateAlbum(@RequestPayload UpdateAlbumRequest request) {
        final AlbumUpdateDto dto = conversionService.convert(request, AlbumUpdateDto.class);
        final UpdateAlbumResponse response = new UpdateAlbumResponse();

        try {
            albumService.updateAlbum(dto);
            response.setName(dto != null ? dto.getName() : "");
        } catch (Exception e) {
            response.setError(e.getMessage());
        }


        return response;
    }

    @PayloadRoot(namespace = namespace, localPart = "deleteAlbumRequest")
    @ResponsePayload
    public DeleteAlbumResponse deleteAlbum(@RequestPayload DeleteAlbumRequest request) {
        final AlbumDeleteDto dto = conversionService.convert(request, AlbumDeleteDto.class);
        final DeleteAlbumResponse response = new DeleteAlbumResponse();

        try {
            albumService.deleteAlbum(dto);
            response.setError("");
        } catch (Exception e) {
            response.setError(e.getMessage());
        }

        return response;
    }
}
