package com.example.paysure;

import static com.example.paysure.api.SupabaseClient.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.paysure.R;
import com.example.paysure.api.Api;
import com.example.paysure.api.RetrofitClient;
import com.example.paysure.api.SupabaseClient;
import com.example.paysure.model.Client;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;
import java.util.prefs.BackingStoreException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AjouterClientBottomSheet extends BottomSheetDialogFragment {
    
    private EditText editTextNom;
    private EditText editTextTelephone;
    private EditText editTextAdresse;
    
    private Button buttonAjouter;
    private Button buttonAnnuler;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajout_client, container, false);


        Button btnAjouter = view.findViewById(R.id.buttonAjouter);
        EditText editTextNom = view.findViewById(R.id.editTextNom);
        EditText editTextTelephone = view.findViewById(R.id.editTextTelephone);
        EditText editTextAdresse = view.findViewById(R.id.editTextAdresse);

        btnAjouter.setOnClickListener(v -> {

            String nom = editTextNom.getText().toString().trim();
            String telephone = editTextTelephone.getText().toString().trim();
            String adresse = editTextAdresse.getText().toString().trim();

            if (nom.isEmpty() || telephone.isEmpty()) {
                Toast.makeText(getContext(), "Champs obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            envoyer(nom, telephone, adresse);
        });
        return view;

    }
    

    private void envoyer(String nom, String telephone, String adresse) {

        String token = requireActivity()
                .getSharedPreferences("session", Context.MODE_PRIVATE)
                .getString("access_token", null);

        if (token == null) {
            Toast.makeText(getContext(), "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        Client client = new Client(nom, telephone, adresse);

        Api api = RetrofitClient.getClient(BASE_URL).create(Api.class);

        api.addClient("Bearer " + token, client).enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Client ajouté ✅", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),
                            "Erreur Supabase : " + response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
