package itmo.abroskin.wst.core.services.album.impl;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.AlbumCRUDService;
import itmo.abroskin.wst.core.services.album.AlbumSearchService;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumCRUDServiceImpl implements AlbumCRUDService {
    private AlbumSearchService searchService;

    @Autowired
    public AlbumCRUDServiceImpl(AlbumSearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<Album> search(AlbumSearchQueryDto queryDto) {
        return searchService.search(queryDto);
    }
}
