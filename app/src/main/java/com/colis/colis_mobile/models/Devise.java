package com.colis.colis_mobile.models;

public enum Devise {
    EUR("EUR"),
    USD("USD"),
    FC("FC");

    String devise;

    public String getDevise() {
        return devise;
    }

    Devise(String devise) {
        this.devise = devise;
    }
}
