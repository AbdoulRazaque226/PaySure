package com.example.paysure.api;

import com.example.paysure.model.Client;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Api {

    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @GET("rest/v1/clients") 
    Call<List<Client>> getClients();

    @POST("/rest/v1/clients")
    Call<List<Client>> addClient(
            @Header("Authorization") String bearerToken,
            @Body Client client);

    @PATCH("/rest/v1/clients")
    Call<Void> updateClient(
            @Query("id") String id,
            @Header("Authorization") String bearer,
            @Body Map<String, Object> body
    );

    @DELETE("clients")
    Call<Void> deleteClient(
            @Query("id") String id,
            @Header("Authorization") String bearer
    );

}


