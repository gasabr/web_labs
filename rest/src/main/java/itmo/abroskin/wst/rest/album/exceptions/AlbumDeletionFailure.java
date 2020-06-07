package itmo.abroskin.wst.rest.album.exceptions;

public class AlbumDeletionFailure extends AlbumException {
    public AlbumDeletionFailure(int statusCode, String message) {
        super(statusCode, message);
    }
}
