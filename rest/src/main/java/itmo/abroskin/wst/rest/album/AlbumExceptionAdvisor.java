package itmo.abroskin.wst.rest.album;

import itmo.abroskin.wst.rest.album.exceptions.AlbumException;
import itmo.abroskin.wst.rest.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AlbumExceptionAdvisor {
    @ExceptionHandler(AlbumException.class)
    public ResponseEntity<ErrorResponse> operationFailure(AlbumException ex) {
        final ErrorResponse response = new ErrorResponse();
        response.setErrorCode(ex.getStatusCode());
        response.setMessage(ex.getMessage());

        HttpStatus status = HttpStatus.resolve(ex.getStatusCode());

        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }

}
