package com.hoxify.ws.auth;

import com.hoxify.ws.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Token {
    @Id
    private String token;
    @ManyToOne
    private User user;
}
