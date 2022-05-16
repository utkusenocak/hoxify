package com.hoxify.ws.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoxify.ws.shared.GenericResponse;
import com.hoxify.ws.shared.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new GenericResponse("user created");
    }

    @GetMapping("/api/1.0/users")
    @JsonView(Views.Base.class)
    Page<User> getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }
}
