package scalable.solutions.ui.web.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import scalable.solutions.ui.utils.RestUtils;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Value("${base-uri.orders}")
    private String ordersBaseUri;

    @Autowired
    HttpSession session;

    @Autowired
    private RestTemplate restClient;

    @GetMapping(value = "/orders")
    public ResponseEntity<String> getOrders() {
        String token = RestUtils.getAccessTokenFromSession(session);
        // headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        // call
        String uri = ordersBaseUri + "/orders";
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        return restClient.exchange(uri, HttpMethod.GET, httpEntity, String.class);
    }

    @GetMapping(value = "/order/{orderId}")
    public ResponseEntity<String> getOrder(@PathVariable String orderId) {
        String token = RestUtils.getAccessTokenFromSession(session);
        // headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        // call
        String uri = ordersBaseUri + "/order/" + orderId;
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        return restClient.exchange(uri, HttpMethod.GET, httpEntity, String.class);
    }

    @PostMapping(value = "/order")
    public ResponseEntity<String> createOrder(@RequestBody String order) {
        String token = RestUtils.getAccessTokenFromSession(session);
        // headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        // call
        String uri = ordersBaseUri + "/order";
        HttpEntity<String> httpEntity = new HttpEntity<>(order, headers);
        return restClient.exchange(uri, HttpMethod.POST, httpEntity, String.class);
    }

    @PutMapping(value = "/order")
    public ResponseEntity<String> updateOrder(@RequestBody String order) {
        String token = RestUtils.getAccessTokenFromSession(session);
        // headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        // call
        String uri = ordersBaseUri + "/order";
        HttpEntity<String> httpEntity = new HttpEntity<>(order, headers);
        return restClient.exchange(uri, HttpMethod.PUT, httpEntity, String.class);
    }

    @DeleteMapping(value = "/order/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        String token = RestUtils.getAccessTokenFromSession(session);
        // headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        // call
        String uri = ordersBaseUri + "/order/" + orderId;
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        return restClient.exchange(uri, HttpMethod.DELETE, httpEntity, String.class);
    }
}
