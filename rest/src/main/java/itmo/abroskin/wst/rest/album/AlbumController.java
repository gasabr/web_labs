package itmo.abroskin.wst.rest.album;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.AlbumCRUDService;
import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumDeleteDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumUpdateDto;
import itmo.abroskin.wst.rest.album.exceptions.AlbumCreationFailure;
import itmo.abroskin.wst.rest.album.exceptions.AlbumDeletionFailure;
import itmo.abroskin.wst.rest.album.exceptions.AlbumUpdateFailure;
import itmo.abroskin.wst.rest.models.AlbumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import itmo.abroskin.wst.rest.album.models.*;

import javax.xml.ws.http.HTTPException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AlbumController {
    private AlbumCRUDService crudService;
    private ConversionService conversionService;
    private final Bucket bucket;

    @Autowired
    public AlbumController(AlbumCRUDService crudService, ConversionService conversionService) {
        this.crudService = crudService;
        this.conversionService = conversionService;
        Bandwidth limit = Bandwidth.classic(200, Refill.greedy(200, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @PostMapping("/album/")
    public CreateAlbumResponse createAlbum(@RequestBody CreateAlbumRequest request) {
        final CreateAlbumResponse response = new CreateAlbumResponse();
        if (bucket.tryConsume(1)) {
            try {
                final AlbumCreateDto dto = conversionService.convert(request, AlbumCreateDto.class);
                final long id = crudService.createAlbum(dto);
                response.setId(id);
            } catch (Exception e) {
                // I know that catching everything is bad practice, but that's not production-ready code
                throw new AlbumCreationFailure(400, e.getMessage());
            }

            return response;
        }

        throw new AlbumCreationFailure(429, "above rate limit, sry");
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

        if (bucket.tryConsume(1)) {
            try {
                crudService.updateAlbum(dto);
                response.setName(dto.getName() != null ? dto.getName() : String.valueOf(id));
            } catch (Exception e) {
                throw new AlbumUpdateFailure(400, e.getMessage());
            }

            return response;
        } else {
            throw new AlbumUpdateFailure(429, "above rate limit");
        }
    }

    @DeleteMapping("/album/{id}/")
    public DeleteAlbumResponse deleteAlbum(@PathVariable long id, DeleteAlbumRequest request) {
        final AlbumDeleteDto dto = conversionService.convert(request, AlbumDeleteDto.class);
        final DeleteAlbumResponse response = new DeleteAlbumResponse();

        if (bucket.tryConsume(1)) {
            try {
                crudService.deleteAlbum(dto);
                response.setError("");
            } catch (Exception e) {
                throw new AlbumDeletionFailure(400, e.getMessage());
            }

            return response;
        } else {
            throw new AlbumDeletionFailure(429, "above rate limit");
        }
    }

}
