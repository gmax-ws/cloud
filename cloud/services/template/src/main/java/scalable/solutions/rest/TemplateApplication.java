package scalable.solutions.rest;

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

import javax.ws.rs.HttpMethod;
import java.time.Duration;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Template API", version = "1.0", description = "External services proxy"))
@SecurityScheme(name = "integration", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
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
                    .antMatchers("/v3/api-docs/**").permitAll()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/ord/**").permitAll()
                    .mvcMatchers(HttpMethod.GET, "/api/unknown/**").hasRole("role_user")
                    .anyRequest().authenticated();
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