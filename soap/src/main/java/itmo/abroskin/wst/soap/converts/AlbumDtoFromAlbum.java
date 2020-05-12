package itmo.abroskin.wst.soap.converts;

import itmo.abroskin.wst.core.models.Album;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import wst.abroskin.itmo.AlbumDto;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

@Component
public class AlbumDtoFromAlbum implements Converter<Album, AlbumDto> {

    @Override
    public AlbumDto convert(Album source) {
        final AlbumDto dto = new AlbumDto();

        dto.setAuthor(source.getAuthor());
        dto.setBillboardDebut(source.getBillboardDebut());
        dto.setId(source.getId());
        dto.setPublisher(source.getPublisher());
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(source.getReleaseDate());
        try {
            XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            dto.setReleaseDate(calendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        return dto;
    }
}
