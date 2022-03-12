package com.hoxify.ws.user;

import com.hoxify.ws.error.ApiError;
import com.hoxify.ws.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String username = user.getUsername();
        if (username == null || username.isEmpty()) {
            ApiError error = new ApiError(400, "Validation Error", "/api/1.0/users");
            Map<String,String> validationErrors = new HashMap<>();
            validationErrors.put("username", "Username can not be null");
            error.setValidationErrors(validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        userService.save(user);
        return ResponseEntity.ok(new GenericResponse("user created"));
    }
}
