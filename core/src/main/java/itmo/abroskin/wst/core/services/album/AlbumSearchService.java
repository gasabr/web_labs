package itmo.abroskin.wst.core.services.album;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;

import java.util.List;

public interface AlbumSearchService {
    List<Album> search(AlbumSearchQueryDto queryDto);
}
