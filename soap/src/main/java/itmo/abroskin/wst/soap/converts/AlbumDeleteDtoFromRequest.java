package itmo.abroskin.wst.soap.converts;

import itmo.abroskin.wst.core.services.album.dto.AlbumDeleteDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import wst.abroskin.itmo.DeleteAlbumRequest;

@Component
public class AlbumDeleteDtoFromRequest implements Converter<DeleteAlbumRequest, AlbumDeleteDto> {
    @Override
    public AlbumDeleteDto convert(DeleteAlbumRequest deleteAlbumRequest) {
        final AlbumDeleteDto dto = new AlbumDeleteDto();

        dto.setId(deleteAlbumRequest.getId());

        return dto;
    }
}
