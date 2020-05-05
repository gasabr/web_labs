package itmo.abroskin.wst.core.services.album;

import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;

public interface AlbumCreateService {
    Long createAlbum(AlbumCreateDto createDto);
}
