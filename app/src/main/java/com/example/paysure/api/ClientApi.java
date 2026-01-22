package com.example.paysure.api;

import com.example.paysure.model.Client;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ClientApi {

    @GET("clients")
    Call<List<Client>> getClients();
}

