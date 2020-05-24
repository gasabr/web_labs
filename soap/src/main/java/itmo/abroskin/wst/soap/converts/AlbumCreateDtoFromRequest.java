package itmo.abroskin.wst.soap.converts;

import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import wst.abroskin.itmo.CreateAlbumRequest;

@Component
public class AlbumCreateDtoFromRequest implements Converter<CreateAlbumRequest, AlbumCreateDto> {
    @Override
    public AlbumCreateDto convert(CreateAlbumRequest createAlbumRequest) {
        final AlbumCreateDto dto = new AlbumCreateDto();

//        System.out.println(createAlbumRequest);
        dto.setAuthor(createAlbumRequest.getAuthor());
        dto.setBillboardDebut(createAlbumRequest.getBillboardDebut());
//        dto.setDate(new java.util.Date(createAlbumRequest.getReleaseDate().getMillisecond()));
        dto.setName(createAlbumRequest.getName());
        dto.setPublisher(createAlbumRequest.getPublisher());

        return dto;
    }
}
