package com.colis.colis_mobile.models;

import java.io.Serializable;

public class AuthenticationRequestModel implements Serializable {

    private String email ;

    private String password ;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequestModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
