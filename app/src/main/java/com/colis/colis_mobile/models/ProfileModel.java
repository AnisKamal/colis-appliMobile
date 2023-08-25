package com.colis.colis_mobile.models;

import java.io.Serializable;

public class ProfileModel implements Serializable {

    private String fullName ;

    private String photoProfile ;

    public ProfileModel(String fullName, String photoProfile) {
        this.fullName = fullName;
        this.photoProfile = photoProfile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }
}
