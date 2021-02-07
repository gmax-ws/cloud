package scalable.solutions.ui.utils;


import org.springframework.http.ResponseEntity;
import scalable.solutions.ui.web.auth.AuthorizationController;

import javax.servlet.http.HttpSession;

public class RestUtils {

    /**
     * Clone ResponseEntity without headers
     *
     * @param response ResponseEntity source
     * @return ResponseEntity Clone
     */
    public static <T> ResponseEntity<T> clone(ResponseEntity<T> response) {
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    /**
     * Get access token from http session. Throw an exception if doesn't exists.
     *
     * @param session Http session
     * @return access token or raise exception if not found
     */
    public static String getAccessTokenFromSession(HttpSession session) {
        Object token = session.getAttribute(AuthorizationController.AUTHORIZATION);
        if (token == null) {
            throw new RuntimeException("Access token is missing");
        }
        return token.toString();
    }

    /**
     * Get refresh token from http session. Throw an exception if doesn't exists.
     *
     * @param session Http session
     * @return refresh token or raise exception if not found
     */
    public static String getRefreshTokenFromSession(HttpSession session) {
        Object token = session.getAttribute(AuthorizationController.REFRESH_TOKEN);
        if (token == null) {
            throw new RuntimeException("Refresh token is missing");
        }
        return token.toString();
    }
}