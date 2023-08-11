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

    private Integer poidInitial ;

    private int poidRestant ;

    private String description;

    private boolean activity;

    public PostModel() {
    }

//    public PostModel(String lieuDepart, LocalDateTime dateDepart, String lieuDestination, LocalDateTime dateDestination, Double prix, String devise, int poidInitial, int poidRestant, String description, boolean activity) {
//        this.lieuDepart = lieuDepart;
//        this.dateDepart = dateDepart;
//        this.lieuDestination = lieuDestination;
//        this.dateDestination = dateDestination;
//        this.prix = prix;
//        this.devise = devise;
//        this.poidInitial = poidInitial;
//        this.poidRestant = poidRestant;
//        this.description = description;
//        this.activity = activity;
//
//    }


    public PostModel(String regionDepart, LocalDateTime dateRegionDepart, String regionDestination, LocalDateTime dateRegionDestination, Double prix, String devise, Integer poidInitial, int poidRestant, String description, boolean activity) {
        this.regionDepart = regionDepart;
        this.dateRegionDepart = dateRegionDepart;
        this.regionDestination = regionDestination;
        this.dateRegionDestination = dateRegionDestination;
        this.prix = prix;
        this.devise = devise;
        this.poidInitial = poidInitial;
        this.poidRestant = poidRestant;
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

    public void setPoidInitial(Integer poidInitial) {
        this.poidInitial = poidInitial;
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

    public Integer getPoidInitial() {
        return poidInitial;
    }

    public void setPoidInitial(int poidInitial) {
        this.poidInitial = poidInitial;
    }

    public int getPoidRestant() {
        return poidRestant;
    }

    public void setPoidRestant(int poidRestant) {
        this.poidRestant = poidRestant;
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
                ", poidInitial=" + poidInitial +
                ", poidRestant=" + poidRestant +
                ", description='" + description + '\'' +
                ", activity=" + activity +
                '}';
    }
}
