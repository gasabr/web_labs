package itmo.abroskin.wst.core.services.album;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;

import java.util.List;

public interface AlbumCRUDService {
//    Long createAlbum(AlbumCreateDto createDto);
    List<Album> search(AlbumSearchQueryDto queryDto);
//    void updateAlbum(AlbumUpdateDto updateDto);
//    void deleteAlbum(AlbumDeleteDto deleteDto);
}
