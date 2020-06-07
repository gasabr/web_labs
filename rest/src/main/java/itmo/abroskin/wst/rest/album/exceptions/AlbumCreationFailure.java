package itmo.abroskin.wst.rest.album.exceptions;

public class AlbumCreationFailure extends AlbumException {
    public AlbumCreationFailure(int statusCode, String message) {
        super(statusCode, message);
    }
}
