package scalable.solutions.ui.web.auth;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = {"scalable.solutions.ui.web.auth"})
public class AuthorizationExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(WebClientResponseException.class)
    public ModelAndView handleWebClientResponseException(HttpServletRequest request,
                                                         WebClientResponseException exception) {
        return handle(request, exception, exception.getStatusCode());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleHttpClientErrorException(HttpServletRequest request,
                                                       HttpClientErrorException exception) {
        return handle(request, exception, exception.getStatusCode());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request,
                                        Exception exception) {
        return handle(request, exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ModelAndView handle(HttpServletRequest request,
                                Exception exception,
                                HttpStatus status) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("url", request.getRequestURL().toString());
        data.put("method", request.getMethod());
        data.put("status", status.value());
        data.put("message", exception.getLocalizedMessage());
        data.put("cause", exception.getClass().getName());
        Gson gson = new Gson();
        return new ModelAndView("index", "error", gson.toJson(data));
    }
}