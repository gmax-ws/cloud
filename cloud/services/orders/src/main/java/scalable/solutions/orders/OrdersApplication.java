package scalable.solutions.orders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;
import scalable.solutions.keycloak.KeycloakSecurityConfig;

import java.time.Duration;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Orders API", version = "1.0", description = "Orders CRUD operations"))
@SecurityScheme(name = "integration", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }

    @EnableWebSecurity
    static class OAuth2SecurityConfig extends KeycloakSecurityConfig {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            http
                    .csrf().disable()
                    .antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/actuator/**").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/v3/api-docs/**").permitAll()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .anyRequest().authenticated();
            // h2 console
            http.headers().frameOptions().disable();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
            web.ignoring().antMatchers(
                    "/v3/api-docs.*",
                    "/swagger-ui.html");
        }
    }
}