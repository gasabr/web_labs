package itmo.abroskin.wst.core.services.album.impl;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.AlbumCRUDService;
import itmo.abroskin.wst.core.services.album.AlbumSearchService;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;

import java.util.List;

public class AlbumCRUDServiceImpl implements AlbumCRUDService {
    private AlbumSearchService searchService;

    public AlbumCRUDServiceImpl(AlbumSearchService searchService) {
        this.searchService = searchService;
    }

    public List<Album> search(AlbumSearchQueryDto queryDto) {
        return searchService.search(queryDto);
    }
}
