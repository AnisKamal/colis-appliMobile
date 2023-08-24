package com.colis.colis_mobile.models;

import java.io.Serializable;
import java.time.LocalDateTime;


public class PostModel  implements Serializable {

    private  String regionDepart ;

    private  LocalDateTime dateRegionDepart ;

    private String regionDestination ;

    private LocalDateTime dateRegionDestination ;

    private Double prix ;

    private String devise ;

    private Integer kiloInitial ;

    private int kiloRestant ;

    private String description;

    private boolean activity;

    public PostModel() {
    }

//    public PostModel(String lieuDepart, LocalDateTime dateDepart, String lieuDestination, LocalDateTime dateDestination, Double prix, String devise, int kiloInitial, int kiloRestant, String description, boolean activity) {
//        this.lieuDepart = lieuDepart;
//        this.dateDepart = dateDepart;
//        this.lieuDestination = lieuDestination;
//        this.dateDestination = dateDestination;
//        this.prix = prix;
//        this.devise = devise;
//        this.kiloInitial = kiloInitial;
//        this.kiloRestant = kiloRestant;
//        this.description = description;
//        this.activity = activity;
//
//    }


    public PostModel(String regionDepart, LocalDateTime dateRegionDepart, String regionDestination, LocalDateTime dateRegionDestination, Double prix, String devise, Integer kiloInitial, int kiloRestant, String description, boolean activity) {
        this.regionDepart = regionDepart;
        this.dateRegionDepart = dateRegionDepart;
        this.regionDestination = regionDestination;
        this.dateRegionDestination = dateRegionDestination;
        this.prix = prix;
        this.devise = devise;
        this.kiloInitial = kiloInitial;
        this.kiloRestant = kiloRestant;
        this.description = description;
        this.activity = activity;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegionDepart() {
        return regionDepart;
    }

    public LocalDateTime getDateRegionDepart() {
        return dateRegionDepart;
    }

    public String getRegionDestination() {
        return regionDestination;
    }

    public LocalDateTime getDateRegionDestination() {
        return dateRegionDestination;
    }

    public void setRegionDepart(String regionDepart) {
        this.regionDepart = regionDepart;
    }

    public void setDateRegionDepart(LocalDateTime dateRegionDepart) {
        this.dateRegionDepart = dateRegionDepart;
    }

    public void setRegionDestination(String regionDestination) {
        this.regionDestination = regionDestination;
    }

    public void setDateRegionDestination(LocalDateTime dateRegionDestination) {
        this.dateRegionDestination = dateRegionDestination;
    }

    public void setkiloInitial(Integer kiloInitial) {
        this.kiloInitial = kiloInitial;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Integer getkiloInitial() {
        return kiloInitial;
    }

    public void setkiloInitial(int kiloInitial) {
        this.kiloInitial = kiloInitial;
    }

    public int getkiloRestant() {
        return kiloRestant;
    }

    public void setkiloRestant(int kiloRestant) {
        this.kiloRestant = kiloRestant;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "regionDepart='" + regionDepart + '\'' +
                ", dateRegionDepart=" + dateRegionDepart +
                ", regionDestination='" + regionDestination + '\'' +
                ", dateRegionDestination=" + dateRegionDestination +
                ", prix=" + prix +
                ", devise='" + devise + '\'' +
                ", kiloInitial=" + kiloInitial +
                ", kiloRestant=" + kiloRestant +
                ", description='" + description + '\'' +
                ", activity=" + activity +
                '}';
    }
}
