package com.hoxify.ws.auth;

import com.hoxify.ws.user.User;
import com.hoxify.ws.user.UserService;
import com.hoxify.ws.user.vm.UserVM;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UserService userService;
    PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticate(Credentials credentials) {
        User user = userService.getByUsername(credentials.getUsername());
        boolean matches = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        if (matches) {
            UserVM userVM = new UserVM(user);
            String token = Jwts.builder().setSubject(String.valueOf(user.getId()))
                    .signWith(SignatureAlgorithm.HS512, "my-app-secret").compact();
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUser(userVM);
            authResponse.setToken(token);
            return authResponse;
        }
        return null;
    }
}
