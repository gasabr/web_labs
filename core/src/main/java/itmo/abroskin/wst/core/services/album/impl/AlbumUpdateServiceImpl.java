package itmo.abroskin.wst.core.services.album.impl;

import itmo.abroskin.wst.core.errors.AlbumNotFoundException;
import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.repos.AlbumRepository;
import itmo.abroskin.wst.core.services.album.AlbumUpdateService;
import itmo.abroskin.wst.core.services.album.dto.AlbumUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumUpdateServiceImpl implements AlbumUpdateService {
    @Autowired
    private final AlbumRepository albumRepository;

    public AlbumUpdateServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public void updateAlbum(AlbumUpdateDto updateDto) {
        final Album album = albumRepository
                .findById(updateDto.getId())
                .orElseThrow(() -> new AlbumNotFoundException(
                        "Can not get album with id=" + updateDto.getId())
                );
        album.setPublisher(updateDto.getPublisher());
        album.setAuthor(updateDto.getAuthor());
        album.setName(updateDto.getName());
        album.setBillboardDebut(updateDto.getBillboardDebut());

        albumRepository.save(album);
    }
}
