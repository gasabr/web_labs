package itmo.abroskin.wst.rest.album.exceptions;

public class AlbumUpdateFailure extends AlbumException {
    public AlbumUpdateFailure(int statusCode, String message) {
        super(statusCode, message);
    }
}
