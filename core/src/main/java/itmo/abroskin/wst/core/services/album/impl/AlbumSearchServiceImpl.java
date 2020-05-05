package itmo.abroskin.wst.core.services.album.impl;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.repos.AlbumRepository;
import itmo.abroskin.wst.core.services.album.AlbumJpaSpecificationFactory;
import itmo.abroskin.wst.core.services.album.AlbumSearchService;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumSearchServiceImpl implements AlbumSearchService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumJpaSpecificationFactory specificationFactory;

    public AlbumSearchServiceImpl(AlbumRepository albumRepository, AlbumJpaSpecificationFactory specificationFactory) {
        this.albumRepository = albumRepository;
        this.specificationFactory = specificationFactory;
    }

    @Override
    public List<Album> search(AlbumSearchQueryDto queryDto) {
        return albumRepository.findAll(specificationFactory.search(queryDto));
    }
}
