package itmo.abroskin.wst.core.services.album;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumDeleteDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumUpdateDto;

import java.util.List;

public interface AlbumCRUDService {
    Long createAlbum(AlbumCreateDto createDto);
    List<Album> search(AlbumSearchQueryDto queryDto);
    void updateAlbum(AlbumUpdateDto updateDto);
    void deleteAlbum(AlbumDeleteDto deleteDto);
}
