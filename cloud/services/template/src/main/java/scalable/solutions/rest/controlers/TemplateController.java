package scalable.solutions.rest.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class TemplateController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${integration.reqres.unknown}")
    String unknownUri;

    @ResponseBody
    @GetMapping("/unknown")
    public ResponseEntity<String> message() {
        return restTemplate.getForEntity(unknownUri, String.class);
    }
}
