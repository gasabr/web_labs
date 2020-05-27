package itmo.abroskin.wst.soap.converts;

import itmo.abroskin.wst.core.services.album.dto.AlbumUpdateDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import wst.abroskin.itmo.UpdateAlbumRequest;

import static itmo.abroskin.wst.core.utils.DateConverter.gregorianToDate;

@Component
public class AlbumUpdateDtoFromRequest implements Converter<UpdateAlbumRequest, AlbumUpdateDto> {
    @Override
    public AlbumUpdateDto convert(UpdateAlbumRequest updateAlbumRequest) {
        AlbumUpdateDto dto = new AlbumUpdateDto();

        dto.setAuthor(updateAlbumRequest.getAuthor());
        dto.setBillboardDebut(updateAlbumRequest.getBillboardDebut());
        dto.setDate(gregorianToDate(updateAlbumRequest.getReleaseDate()));
        dto.setName(updateAlbumRequest.getName());
        dto.setPublisher(updateAlbumRequest.getPublisher());

        return dto;
    }
}
