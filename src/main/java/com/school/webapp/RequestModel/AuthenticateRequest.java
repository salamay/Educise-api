package com.school.webapp.RequestModel;

import org.springframework.security.authentication.AuthenticationManager;

public class AuthenticateRequest {

    private String username;
    private String password;

    public AuthenticateRequest() {

    }

    public AuthenticateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}