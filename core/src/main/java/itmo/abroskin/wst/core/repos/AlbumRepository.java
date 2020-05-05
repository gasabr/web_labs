package itmo.abroskin.wst.core.repos;

import itmo.abroskin.wst.core.models.Album;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long>, JpaSpecificationExecutor<Album> {
}
