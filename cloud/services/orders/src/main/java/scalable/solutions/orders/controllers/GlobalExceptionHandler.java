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
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handle(HttpServletRequest request,
                                                      Exception exception) {
        return common(request, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(HttpServletRequest request,
                                                                HttpMessageNotReadableException exception) {
        return common(request, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(HttpServletRequest request,
                                                                    EntityNotFoundException exception) {
        return common(request, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(HttpServletRequest request,
                                                                  AccessDeniedException exception) {
        return common(request, exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpServletRequest request,
                                                                        HttpRequestMethodNotSupportedException exception) {
        return common(request, exception, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<Map<String, Object>> common(HttpServletRequest request,
                                                       Exception exception,
                                                       HttpStatus status) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("url", request.getRequestURL().toString());
        data.put("method", request.getMethod());
        data.put("status", status.value());
        data.put("message", exception.getLocalizedMessage());
        data.put("cause", exception.getClass().getName());
        return new ResponseEntity<>(data, status);
    }
}