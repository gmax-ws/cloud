package scalable.solutions.ui.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import scalable.solutions.ui.utils.RestUtils;

import javax.servlet.http.HttpSession;

@RestController
public class SwaggerController {
    @Autowired
    HttpSession session;

    @Value("${base-uri.swagger}")
    private String swaggerUri;

    @GetMapping(value = "/swagger")
    public ResponseEntity<String> swaggerUrl() {
        return ResponseEntity.ok(swaggerUri);
    }

    @GetMapping(value = "/token")
    public ResponseEntity<String> getToken() {
        String token = RestUtils.getAccessTokenFromSession(session);
        int pos = token.indexOf(" ");
        return ResponseEntity.ok(token.substring(pos).trim());
    }
}