package com.example.paysure.api;

import com.example.paysure.model.Client;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    // Supabase tables are usually plural
    @GET("rest/v1/clients") 
    Call<List<Client>> getClients(
            
    );
}
