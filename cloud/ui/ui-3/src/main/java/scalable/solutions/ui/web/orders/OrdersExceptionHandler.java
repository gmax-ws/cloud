package scalable.solutions.ui.web.orders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = {"scalable.solutions.ui.web.orders"})
public class OrdersExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(HttpServletRequest request,
                                                                   WebClientResponseException exception) {
        return new ResponseEntity<>(exception.getResponseBodyAsString(), exception.getStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpServletRequest request,
                                                                 HttpClientErrorException exception) {
        return new ResponseEntity<>(exception.getResponseBodyAsString(), exception.getStatusCode());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleHttpServerErrorException(HttpServletRequest request,
                                                                 HttpServerErrorException exception) {
        return new ResponseEntity<>(exception.getResponseBodyAsString(), exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(HttpServletRequest request,
                                                               Exception exception) {
        return handle(request, exception);
    }

    private ResponseEntity<Map<String, String>> handle(HttpServletRequest request,
                                                       Exception exception) {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("url", request.getRequestURL().toString());
        data.put("method", request.getMethod());
        data.put("message", exception.getLocalizedMessage());
        data.put("cause", exception.getClass().getName());
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}