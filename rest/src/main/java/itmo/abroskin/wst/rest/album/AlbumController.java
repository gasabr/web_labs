package itmo.abroskin.wst.rest.album;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.AlbumCRUDService;
import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumDeleteDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumUpdateDto;
import itmo.abroskin.wst.rest.album.exceptions.AlbumCreationFailure;
import itmo.abroskin.wst.rest.models.AlbumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import itmo.abroskin.wst.rest.album.models.*;

import javax.xml.ws.http.HTTPException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AlbumController {
    private AlbumCRUDService crudService;
    private ConversionService conversionService;

    @Autowired
    public AlbumController(AlbumCRUDService crudService, ConversionService conversionService) {
        this.crudService = crudService;
        this.conversionService = conversionService;
    }

    @PostMapping("/album/")
    public CreateAlbumResponse createAlbum(@RequestBody CreateAlbumRequest request) {
        final CreateAlbumResponse response = new CreateAlbumResponse();
        try {
            final AlbumCreateDto dto = conversionService.convert(request, AlbumCreateDto.class);
            final long id = crudService.createAlbum(dto);
            response.setId(id);
        } catch (Exception e) {
            throw new AlbumCreationFailure(400, e.getMessage());
        }

        return response;
    }

    @GetMapping("/album/{id}/")
    public GetAlbumsResponse getAlbums(GetAlbumsRequest request) {
        final AlbumSearchQueryDto dto = conversionService.convert(request, AlbumSearchQueryDto.class);
        final List<Album> albumList = crudService.search(dto);
        final List<AlbumDto> albums = albumList.stream()
                .map(album -> conversionService.convert(album, AlbumDto.class))
                .collect(Collectors.toList());
        final GetAlbumsResponse response = new GetAlbumsResponse();
        response.setAlbums(albums);

        return response;
    }

    @PutMapping("/album/{id}/")
    public UpdateAlbumResponse updateAlbum(@PathVariable long id, @RequestBody UpdateAlbumRequest request) {
        request.setId(id);
        final AlbumUpdateDto dto = conversionService.convert(request, AlbumUpdateDto.class);
        final UpdateAlbumResponse response = new UpdateAlbumResponse();

        try {
            crudService.updateAlbum(dto);
            response.setName(dto.getName() != null ? dto.getName() : String.valueOf(id));
        } catch (Exception e) {
            response.setError(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/album/{id}/")
    public DeleteAlbumResponse deleteAlbum(@PathVariable long id, DeleteAlbumRequest request) {
        final AlbumDeleteDto dto = conversionService.convert(request, AlbumDeleteDto.class);
        final DeleteAlbumResponse response = new DeleteAlbumResponse();

        try {
            crudService.deleteAlbum(dto);
            response.setError("");
        } catch (Exception e) {
            response.setError(e.getMessage());
        }

        return response;
    }

}
