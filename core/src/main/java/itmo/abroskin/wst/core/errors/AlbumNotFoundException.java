package itmo.abroskin.wst.core.errors;

import itmo.abroskin.wst.core.models.Album;

import javax.persistence.EntityNotFoundException;

public class AlbumNotFoundException extends EntityNotFoundException {
    public AlbumNotFoundException(String searchedBy) {
        super(searchedBy);
    }
}
