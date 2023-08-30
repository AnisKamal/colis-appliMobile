package com.colis.colis_mobile.models;

import java.io.Serializable;

public class ProfileModel implements Serializable {

    private String fullName ;

    private String photoProfile ;

    private String NTelephone ;

    public ProfileModel(String fullName, String photoProfile, String NTelephone) {
        this.fullName = fullName;
        this.photoProfile = photoProfile;
        this.NTelephone = NTelephone;
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

    public String getNTelephone() {
        return NTelephone;
    }

    public void setNTelephone(String NTelephone) {
        this.NTelephone = NTelephone;
    }
}
