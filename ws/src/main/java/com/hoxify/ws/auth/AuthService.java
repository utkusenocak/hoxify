package com.hoxify.ws.auth;

import com.hoxify.ws.user.User;
import com.hoxify.ws.user.UserRepository;
import com.hoxify.ws.user.vm.UserVM;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    TokenRepository tokenRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
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
        String token = generateRandomToken();
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUser(user);
        tokenRepository.save(tokenEntity);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(userVM);
        authResponse.setToken(token);
        return authResponse;
    }

    @Transactional
    public UserDetails getUserDetails(String token) {
        Optional<Token> optionalToken = tokenRepository.findById(token);
        if (!optionalToken.isPresent()) {
            return null;
        }
        return optionalToken.get().getUser();
    }

    public String generateRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
