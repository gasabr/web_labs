package itmo.abroskin.wst.core.services.album.impl;

import itmo.abroskin.wst.core.errors.AlbumNotFoundException;
import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.repos.AlbumRepository;
import itmo.abroskin.wst.core.services.album.AlbumDeleteService;
import itmo.abroskin.wst.core.services.album.dto.AlbumDeleteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumDeleteServiceImpl implements AlbumDeleteService {
    @Autowired
    private final AlbumRepository albumRepository;

    public AlbumDeleteServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public void deleteAlbum(AlbumDeleteDto deleteDto) {
        final Album album = albumRepository
                .findById(deleteDto.getId())
                .orElseThrow(() -> new AlbumNotFoundException(
                        "Can not get album with id=" + deleteDto.getId())
                );
        albumRepository.delete(album);
    }
}
