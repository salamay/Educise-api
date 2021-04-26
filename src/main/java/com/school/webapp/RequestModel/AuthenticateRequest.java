package com.school.webapp.RequestModel;

import org.springframework.security.authentication.AuthenticationManager;

public class AuthenticateRequest {

    private String staffid;
    private String password;

    public AuthenticateRequest() {

    }


    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}