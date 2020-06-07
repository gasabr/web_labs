package itmo.abroskin.wst.rest.album.converts;

import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import itmo.abroskin.wst.rest.album.models.CreateAlbumRequest;

@Component
public class AlbumCreateDtoFromRequest implements Converter<CreateAlbumRequest, AlbumCreateDto> {
    @Override
    public AlbumCreateDto convert(CreateAlbumRequest createAlbumRequest) {
        final AlbumCreateDto dto = new AlbumCreateDto();

        dto.setAuthor(createAlbumRequest.getAuthor());
        dto.setBillboardDebut(createAlbumRequest.getBillboardDebut());
        dto.setDate(createAlbumRequest.getReleaseDate());
        dto.setName(createAlbumRequest.getName());
        dto.setPublisher(createAlbumRequest.getPublisher());

        return dto;
    }

}
