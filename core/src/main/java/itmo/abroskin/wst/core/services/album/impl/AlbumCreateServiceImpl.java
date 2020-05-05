package itmo.abroskin.wst.core.services.album.impl;
import itmo.abroskin.wst.core.repos.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumCreateServiceImpl {
    @Autowired
    private AlbumRepository albumRepository;

    public AlbumCreateServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }


}
