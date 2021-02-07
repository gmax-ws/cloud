package scalable.solutions.ui.web.auth;

import com.google.gson.Gson;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import scalable.solutions.ui.utils.RestUtils;

import javax.servlet.http.HttpSession;
import java.util.Properties;

@Controller
public class AuthorizationController {

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final String TOKEN_TYPE = "TOKEN_TYPE";
    public static final String AUTHORIZATION = "AUTHORIZATION";

    @Value("${keycloak.client-id}")
    String client;

    @Value("${keycloak.client-secret}")
    String secret;

    @Value("${keycloak.scope}")
    String scope;

    @Value("${keycloak.token-uri}")
    String uri;

    @Value("${keycloak.auth-uri}")
    String auth;

    @Value("${server.redirect-uri}")
    String redirect;

    @Autowired
    HttpSession session;

    @Autowired
    private RestTemplate restClient;

    @GetMapping(value = "/authorize")
    public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
        String state = stateBuilder();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(auth)
                .queryParam("response_type", "code")
                .queryParam("client_id", client)
                .queryParam("redirect_uri", redirect)
                .queryParam("scope", scope)
                .queryParam("state", state);

        attributes.addFlashAttribute("state", state);
        return new RedirectView(builder.toUriString());
    }

    @GetMapping(value = "/authorized")        // registered redirect_uri for authorization_code
    public String authorized(@RequestParam String code,
                             @RequestParam String state,
                             @RequestParam String session_state,
                             @ModelAttribute("state") String stateInit) {
        if (state.equals(stateInit)) {
            // params
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.set("grant_type", "authorization_code");
            params.set("client_id", client);
            params.set("client_secret", secret);
            params.set("code", code);
            params.set("scope", scope);
            params.set("redirect_uri", redirect);
            // headers
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            // call
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restClient.exchange(uri, HttpMethod.POST, httpEntity, String.class);
            // send back response
            return storeToken(response) ? "orders" : "index";
        } else {
            return "index";
        }
    }

    @PostMapping(value = "/refresh")
    public String refresh(Model model) {
        // @formatter:off
        String refreshToken = RestUtils.getRefreshTokenFromSession(session);

        // params
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "refresh_token");
        params.set("refresh_token", refreshToken);
        // headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        // call
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restClient.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        // send back response
        model.addAttribute("authorized", storeToken(response));
        return "index";
        // @formatter:on
    }

    private boolean storeToken(ResponseEntity<String> response) {
        // @formatter:off
        HttpStatus status = response.getStatusCode();

        if (status != HttpStatus.OK) {
            return false;
        }

        String entity = response.getBody();

        Gson gson = new Gson();
        Properties fieldsJson = gson.fromJson(entity, Properties.class);

        session.setAttribute(ACCESS_TOKEN, fieldsJson.get("access_token"));
        session.setAttribute(REFRESH_TOKEN, fieldsJson.get("refresh_token"));
        session.setAttribute(TOKEN_TYPE, fieldsJson.get("token_type"));
        session.setAttribute(AUTHORIZATION,
                String.join(" ",
                        fieldsJson.get("token_type").toString(),
                        fieldsJson.get("access_token").toString()));
        return true;
        // @formatter:on
    }

    private String stateBuilder() {
        return RandomStringUtils.randomAlphanumeric(16);
    }
}
