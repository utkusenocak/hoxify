package com.hoxify.ws.user;

import com.hoxify.ws.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Page<User> getUsers(Pageable pageable, User user) {
        if (user != null) {
            return userRepository.findByUsernameNot(user.getUsername(), pageable);
        }
        return userRepository.findAll(pageable);
    }

    public User getByUsername(String username) {
       User inDb =  userRepository.findByUsername(username);
       if (inDb == null) {
           throw new NotFoundException();
       }
       return inDb;
    }
}
