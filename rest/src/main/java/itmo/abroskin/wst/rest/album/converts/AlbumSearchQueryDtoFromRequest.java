package itmo.abroskin.wst.rest.album.converts;

import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import wst.abroskin.itmo.GetAlbumsRequest;

import java.util.Date;

@Component
public class AlbumSearchQueryDtoFromRequest implements Converter<GetAlbumsRequest, AlbumSearchQueryDto> {

    @Override
    public AlbumSearchQueryDto convert(GetAlbumsRequest source) {
        final AlbumSearchQueryDto query = new AlbumSearchQueryDto();

        query.setName(source.getName());
        query.setAuthor(source.getAuthor());
        if (source.getReleaseDate() != null) {
            Date releaseDate = new Date(source.getReleaseDate().getMillisecond());
            query.setDate(releaseDate);
        } else {
            query.setDate(null);
        }

        query.setBillboardDebut(source.getBillboardDebut());
        query.setPublisher(source.getPublisher());

        return query;
    }
}
