package scalable.solutions.ui.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*Here we are using dummy data, you need to load user data from database or other third party application*/
        User user = findUserByUsername(username);

        if (user != null) {
            org.springframework.security.core.userdetails.User.UserBuilder builder;
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            builder.roles(user.getRoles());
            return builder.build();
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    private User findUserByUsername(String username) {
        if (username.equalsIgnoreCase("admin")) {
            return new User(username, "admin", "ADMIN");
        } else if (username.equalsIgnoreCase("mag")) {
            return new User(username, "root", "USER");
        }
        return null;
    }
}
