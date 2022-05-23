package com.hoxify.ws.auth;

import com.hoxify.ws.user.vm.UserVM;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserVM user;
}
