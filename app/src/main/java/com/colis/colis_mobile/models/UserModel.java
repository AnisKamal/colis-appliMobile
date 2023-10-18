package com.colis.colis_mobile.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String email;

    private String name;

    private String urlPhoto;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                '}';
    }
}
