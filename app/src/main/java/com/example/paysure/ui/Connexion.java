package com.example.paysure.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paysure.R;
import com.example.paysure.api.SupabaseClient;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connexion extends AppCompatActivity {

    private EditText email;
    private EditText motDePasse;
    private Button connexionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        email = findViewById(R.id.email);
        motDePasse = findViewById(R.id.motDePasse);
        connexionBtn = findViewById(R.id.connexionBtn);

        connexionBtn.setOnClickListener(v -> {
            String Email = email.getText().toString().trim();
            String password = motDePasse.getText().toString().trim();

            if (Email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            login(Email, password);
        });
    }

    private void login(String email, String password) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();

                JSONObject json = new JSONObject();
                json.put("email", email);
                json.put("password", password);

                RequestBody body = RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json")
                );

                Request request = new Request.Builder()
                        .url(SupabaseClient.BASE_URL + "auth/v1/token?grant_type=password")
                        .addHeader("apikey", SupabaseClient.API_KEY)
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                String res = response.body().string();
                Log.d("LOGIN_RESPONSE", res);

                JSONObject obj = new JSONObject(res);

                if (response.isSuccessful()) {
                    String accessToken = obj.getString("access_token");

                    // enregistrer le token
                    getSharedPreferences("session", MODE_PRIVATE)
                            .edit()
                            .putString("access_token", accessToken)
                            .apply();

                    // afficher main activity
                    runOnUiThread(() -> {
                        startActivity(new Intent(Connexion  .this, MainActivity.class));
                        finish();
                    });

                } else {
                    String err = obj.optString("msg",
                            obj.optString("message",
                                    obj.optString("error_description", "Erreur de connexion")));



                    runOnUiThread(() -> Toast.makeText(this, err, Toast.LENGTH_SHORT).show());
                }

            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
   

