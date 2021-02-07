package scalable.solutions.zuul;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import scalable.solutions.zuul.filters.ZuulPostFilter;
import scalable.solutions.zuul.filters.ZuulPreFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouterApplication.class, args);
    }

    @Bean
    public ZuulPreFilter zuulPreFilter() {
        return new ZuulPreFilter();
    }

    @Bean
    public ZuulPostFilter zuulPostFilter() {
        return new ZuulPostFilter();
    }

    @Bean
    public FallbackProvider zuulFallbackProvider() {
        return new FallbackProvider() {
            @Override
            public String getRoute() {
                return "*";
            }

            @Override
            public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
                return new ClientHttpResponse() {
                    @Override
                    public HttpStatus getStatusCode() throws IOException {
                        return HttpStatus.OK;
                    }

                    @Override
                    public int getRawStatusCode() throws IOException {
                        return HttpStatus.OK.value();
                    }

                    @Override
                    public String getStatusText() throws IOException {
                        return HttpStatus.OK.getReasonPhrase();
                    }

                    @Override
                    public void close() {
                    }

                    @Override
                    public InputStream getBody() throws IOException {
                        FallbackMessage fallbackMessage = new FallbackMessage(route, cause.getMessage());
                        String jsonMessage = new Gson().toJson(fallbackMessage);
                        return new ByteArrayInputStream(jsonMessage.getBytes());
                    }

                    @Override
                    public HttpHeaders getHeaders() {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        return headers;
                    }
                };
            }
        };
    }

    static class FallbackMessage {
        String route;
        String cause;

        public FallbackMessage(String route, String cause) {
            this.route = route;
            this.cause = cause;
        }
    }
}