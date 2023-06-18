package com.colis.colis_mobile.models;

import java.time.LocalDateTime;




public class PostModel {

    private  String lieuDepart ;

    private  LocalDateTime dateDepart ;

    private String lieuDestination ;

    private LocalDateTime dateDestination ;

    private Double prix ;

    private String devise ;

    private int poidInitial ;

    private int poidRestant ;

    public PostModel() {
    }

    public PostModel(String lieuDepart, LocalDateTime dateDepart, String lieuDestination, LocalDateTime dateDestination, Double prix, String devise, int poidInitial, int poidRestant) {
        this.lieuDepart = lieuDepart;
        this.dateDepart = dateDepart;
        this.lieuDestination = lieuDestination;
        this.dateDestination = dateDestination;
        this.prix = prix;
        this.devise = devise;
        this.poidInitial = poidInitial;
        this.poidRestant = poidRestant;
    }

    public String getLieuDepart() {
        return lieuDepart;
    }

    public void setLieuDepart(String lieuDepart) {
        this.lieuDepart = lieuDepart;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getLieuDestination() {
        return lieuDestination;
    }

    public void setLieuDestination(String lieuDestination) {
        this.lieuDestination = lieuDestination;
    }

    public LocalDateTime getDateDestination() {
        return dateDestination;
    }

    public void setDateDestination(LocalDateTime dateDestination) {
        this.dateDestination = dateDestination;
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

    public int getPoidInitial() {
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
}
