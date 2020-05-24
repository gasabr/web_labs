package itmo.abroskin.wst.core.services.album.impl;
import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.repos.AlbumRepository;
import itmo.abroskin.wst.core.services.album.AlbumCreateService;
import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumCreateServiceImpl implements AlbumCreateService {
    @Autowired
    private final AlbumRepository albumRepository;

    public AlbumCreateServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Long createAlbum(AlbumCreateDto createDto) {
        final Album album = new Album();

        album.setAuthor(createDto.getAuthor());
        album.setName(createDto.getName());
        album.setBillboardDebut(createDto.getBillboardDebut());
        album.setPublisher(createDto.getPublisher());

        final Album albumInDb = albumRepository.save(album);
        return albumInDb.getId();
    }
}
