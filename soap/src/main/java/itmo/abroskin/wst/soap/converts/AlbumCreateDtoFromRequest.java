package itmo.abroskin.wst.soap.converts;

import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import wst.abroskin.itmo.CreateAlbumRequest;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

import static itmo.abroskin.wst.core.utils.DateConverter.gregorianToDate;

@Component
public class AlbumCreateDtoFromRequest implements Converter<CreateAlbumRequest, AlbumCreateDto> {
    @Override
    public AlbumCreateDto convert(CreateAlbumRequest createAlbumRequest) {
        final AlbumCreateDto dto = new AlbumCreateDto();

        dto.setAuthor(createAlbumRequest.getAuthor());
        dto.setBillboardDebut(createAlbumRequest.getBillboardDebut());
        dto.setDate(gregorianToDate(createAlbumRequest.getReleaseDate()));
        dto.setName(createAlbumRequest.getName());
        dto.setPublisher(createAlbumRequest.getPublisher());

        return dto;
    }

}
