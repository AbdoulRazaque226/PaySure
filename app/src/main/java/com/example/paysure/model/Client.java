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

    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getTelephone() { return telephone; }
    public String getAdresse() { return adresse; }
}
