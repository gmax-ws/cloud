package scalable.solutions.orders.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageData> handle(HttpServletRequest request,
                                                   Exception exception) {
        return common(request, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageData> handleBadRequest(HttpServletRequest request,
                                                             HttpMessageNotReadableException exception) {
        return common(request, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageData> handleEntityNotFound(HttpServletRequest request,
                                                                 EntityNotFoundException exception) {
        return common(request, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessageData> handleAccessDenied(HttpServletRequest request,
                                                               AccessDeniedException exception) {
        return common(request, exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessageData> handleMethodNotSupported(HttpServletRequest request,
                                                                     HttpRequestMethodNotSupportedException exception) {
        return common(request, exception, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<ErrorMessageData> common(HttpServletRequest request,
                                                    Exception exception,
                                                    HttpStatus status) {
        ErrorMessageData data = new ErrorMessageData();
        data.url = request.getRequestURL().toString();
        data.method = request.getMethod();
        data.status = status.value();
        data.message = exception.getLocalizedMessage();
        data.cause = exception.getClass().getName();
        return new ResponseEntity<>(data, status);
    }

    static class ErrorMessageData {
        public String url;
        public String method;
        public int status;
        public String message;
        public String cause;
    }
}