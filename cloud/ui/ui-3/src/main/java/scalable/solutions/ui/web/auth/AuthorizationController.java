package scalable.solutions.ui.web.auth;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
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

    @Autowired
    HttpSession session;

    @Autowired
    private RestTemplate restClient;

    @Autowired
    AuthenticationManager authenticationManager;

    public boolean login(String username, String password) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return auth.isAuthenticated();
    }

    @PostMapping(value = "/authorize")
    public String authorize(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam(value = "grant_type") String grantType,
                            Model model) {
        if (login(username, password)) {
            // params
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.set("grant_type", grantType);
            params.set("client_id", client);
            params.set("client_secret", secret);
            params.set("scope", scope);
            // headers
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

            // call
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restClient.exchange(uri, HttpMethod.POST, httpEntity, String.class);

            // send back response
            model.addAttribute("authorized", storeToken(response));
        } else {
            model.addAttribute("authorized", false);
        }
        return "index";
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
}