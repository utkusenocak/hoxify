package com.hoxify.ws.auth;

import com.hoxify.ws.user.User;
import com.hoxify.ws.user.UserRepository;
import com.hoxify.ws.user.UserService;
import com.hoxify.ws.user.vm.UserVM;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticate(Credentials credentials) {
        User user = userRepository.findByUsername(credentials.getUsername());
        if (user == null) {
            throw new AuthException();
        }
        boolean matches = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        if (!matches) {
            throw new AuthException();
        }
        UserVM userVM = new UserVM(user);
        String token = Jwts.builder().setSubject(String.valueOf(user.getId()))
                .signWith(SignatureAlgorithm.HS512, "my-app-secret").compact();
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(userVM);
        authResponse.setToken(token);
        return authResponse;
    }
    @Transactional
    public UserDetails getUserDetails(String token) {
        JwtParser parser = Jwts.parser().setSigningKey("my-app-secret");
        try {
            parser.parse(token);
            Claims claims = parser.parseClaimsJws(token).getBody();
            Long userId = new Long(claims.getSubject());
            User user = userRepository.getById(userId);
            return (User) ((HibernateProxy)user).getHibernateLazyInitializer().getImplementation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
