package com.hoxify.ws.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoxify.ws.shared.CurrentUser;
import com.hoxify.ws.shared.Views;
import com.hoxify.ws.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@CurrentUser User user) {
        return ResponseEntity.ok(user);

    }
}
