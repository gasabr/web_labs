package itmo.abroskin.wst.core.services.album;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import org.springframework.data.jpa.domain.Specification;

public interface AlbumJpaSpecificationFactory {
    Specification<Album> search(AlbumSearchQueryDto queryDto);
}
