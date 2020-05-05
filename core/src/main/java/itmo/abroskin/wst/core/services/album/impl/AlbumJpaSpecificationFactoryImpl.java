package itmo.abroskin.wst.core.services.album.impl;

import itmo.abroskin.wst.core.models.Album;
import itmo.abroskin.wst.core.models.Album_;
import itmo.abroskin.wst.core.services.album.AlbumJpaSpecificationFactory;
import itmo.abroskin.wst.core.services.album.dto.AlbumSearchQueryDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AlbumJpaSpecificationFactoryImpl implements AlbumJpaSpecificationFactory {
    @Override
    public Specification<Album> search(final AlbumSearchQueryDto queryDto) {
        return (Specification<Album>) (root, criteriaQuery, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (queryDto.getAuthor() != null && !queryDto.getAuthor().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        root.get(Album_.AUTHOR), queryDto.getAuthor()
                ));
            }

            if (queryDto.getBillboardDebut() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get(Album_.BILLBOARD_DEBUT), queryDto.getBillboardDebut())
                );
            }

            if (queryDto.getDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Album_.DATE), queryDto.getDate()));
            }

            if (queryDto.getName() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Album_.NAME), queryDto.getName()));
            }

            if (queryDto.getPublisher() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Album_.PUBLISHER), queryDto.getPublisher()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
