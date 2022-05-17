package com.hoxify.ws.user;

import com.hoxify.ws.error.NotFoundException;
import com.hoxify.ws.file.FileService;
import com.hoxify.ws.user.vm.UserUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {


    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    FileService fileService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
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

    public User updateUser(String username, UserUpdateVM userUpdateVM) {
        User inDb = getByUsername(username);
        inDb.setDisplayName(userUpdateVM.getDisplayName());
        if (userUpdateVM.getImage() != null) {
            String oldImageName = inDb.getImage();
            try {
                String storedFileName = fileService.writeBase64EncodedStringToFile(userUpdateVM.getImage());
                inDb.setImage(storedFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileService.deleteFile(oldImageName);
        }
        return userRepository.save(inDb);
    }
}
