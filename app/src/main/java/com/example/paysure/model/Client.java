package com.example.paysure.model;

import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("id")
    private String id;

    @SerializedName("nom")
    private String nom;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("adresse")
    private String adresse;

    public Client(String nom, String telephone, String adresse) {
        this.nom = nom;
        this.telephone = telephone;
        this.adresse = adresse;
    }


    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getTelephone() { return telephone; }
    public String getAdresse() { return adresse; }
}
