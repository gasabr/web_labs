package itmo.abroskin.wst.rest.album.converts;

import itmo.abroskin.wst.core.services.album.dto.AlbumUpdateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import itmo.abroskin.wst.rest.album.models.UpdateAlbumRequest;

@Component
public class AlbumUpdateDtoFromRequest implements Converter<UpdateAlbumRequest, AlbumUpdateDto> {
    @Override
    public AlbumUpdateDto convert(UpdateAlbumRequest updateAlbumRequest) {
        AlbumUpdateDto dto = new AlbumUpdateDto();

        dto.setAuthor(updateAlbumRequest.getAuthor());
        dto.setBillboardDebut(updateAlbumRequest.getBillboardDebut());
        dto.setDate(updateAlbumRequest.getReleaseDate());
        dto.setName(updateAlbumRequest.getName());
        dto.setPublisher(updateAlbumRequest.getPublisher());

        return dto;
    }
}
