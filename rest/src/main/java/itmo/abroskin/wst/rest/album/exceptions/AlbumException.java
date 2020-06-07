package itmo.abroskin.wst.rest.album.exceptions;

public class AlbumException extends RuntimeException {
    private final int statusCode;
    private final String message;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public AlbumException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
