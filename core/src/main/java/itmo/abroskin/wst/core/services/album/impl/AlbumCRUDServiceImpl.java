package itmo.abroskin.wst.core.services.album.impl;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.*;
import itmo.abroskin.wst.core.services.album.dto.AlbumCreateDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumDeleteDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import itmo.abroskin.wst.core.services.album.dto.AlbumUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumCRUDServiceImpl implements AlbumCRUDService {
    private final AlbumSearchService searchService;
    private final AlbumCreateService createService;
    private final AlbumUpdateService updateService;
    private final AlbumDeleteService deleteService;

    @Autowired
    public AlbumCRUDServiceImpl(
            AlbumSearchService searchService,
            AlbumCreateService createService,
            AlbumDeleteService deleteService,
            AlbumUpdateService updateService) {
        this.searchService = searchService;
        this.createService = createService;
        this.deleteService = deleteService;
        this.updateService = updateService;
    }

    @Override
    public Long createAlbum(AlbumCreateDto createDto) {
        return this.createService.createAlbum(createDto);
    }

    @Override
    public List<Album> search(AlbumSearchQueryDto queryDto) {
        return searchService.search(queryDto);
    }

    @Override
    public void updateAlbum(AlbumUpdateDto updateDto) {
        this.updateService.updateAlbum(updateDto);
    }

    @Override
    public void deleteAlbum(AlbumDeleteDto deleteDto) {
        this.deleteService.deleteAlbum(deleteDto);
    }
}
