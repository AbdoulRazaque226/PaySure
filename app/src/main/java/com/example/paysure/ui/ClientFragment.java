package com.example.paysure.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paysure.Adapter.ClientAdapter;
import com.example.paysure.R;
import com.example.paysure.api.Api;
import com.example.paysure.api.RetrofitClient;
import com.example.paysure.model.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientFragment extends Fragment {

    private String token;
    private RecyclerView recyclerView;
    private ClientAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_client, container, false);

        ImageView ajoutClient = view.findViewById(R.id.ajout_client);
        recyclerView = view.findViewById(R.id.AfficherClient);
        if (recyclerView == null) {
            Log.e("DEBUG", "Le RecyclerView est null ! Vérifiez l'ID.");
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
       

        token = requireContext()
                .getSharedPreferences("session", Context.MODE_PRIVATE)
                .getString("access_token", "");

        ajoutClient.setOnClickListener(v -> {
            com.example.paysure.AjouterClientBottomSheet bottomSheet = new com.example.paysure.AjouterClientBottomSheet();
            bottomSheet.show(getParentFragmentManager(), "AjouterClient");
        });

        AfficherClients();
        return view;
    }

    private void AfficherClients() {
        Api api = RetrofitClient.getClient(token).create(Api.class);
        Call<List<Client>> call = api.getClients();


        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(@NonNull Call<List<Client>> call,
                                   @NonNull Response<List<Client>> response) {

                if (response.isSuccessful()) {
                    List<Client> liste = response.body();
                    if (liste != null && liste.size() > 0) {
                        Log.d("API_SUCCES", "Nombre de clients : " + liste.size());
                        adapter = new ClientAdapter(liste);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d("API_VIDE", "La base de données est vide ou JSON mal structuré");
                    }
                } else {
                    Log.e("API_ERREUR", "Code erreur : " + response.code());
                }
            }
            
            

            @Override
            public void onFailure(@NonNull Call<List<Client>> call,
                                  @NonNull Throwable t) {
                showError("Erreur de connexion: " + t.getMessage());
            }
        });
    }

    private void showError(String msg) {
        if (getContext() != null) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
