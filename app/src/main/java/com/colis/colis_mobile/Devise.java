package com.colis.colis_mobile;

public enum Devise {
    EUR("EUR"),
    USD("USD"),
    MAD("MAD"),
    FCFA("FCFA");

    String devise;

    public String getDevise() {
        return devise;
    }

    Devise(String devise) {
        this.devise = devise;
    }
}
