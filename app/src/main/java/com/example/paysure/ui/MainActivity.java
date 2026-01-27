package com.example.paysure.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.paysure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        // 1. Gérer le bouton retour (Predictive Back)
        setupBackNavigation();

        // 2. Charger le fragment par défaut de manière optimisée
        if (savedInstanceState == null) {
            // On utilise un petit délai (Handler) pour laisser l'UI respirer après la connexion
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                loadFragment(new AccueilFragment());
            }, 100);
        }

        // 3. Listener de navigation
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.accuiel) selectedFragment = new AccueilFragment();
            else if (id == R.id.client) selectedFragment = new ClientFragment();
            else if (id == R.id.dette) selectedFragment = new DetteFragment();
            else if (id == R.id.paiement) selectedFragment = new PaiementFragment();
            else if (id == R.id.historique) selectedFragment = new HistoriqueFragment();

            return loadFragment(selectedFragment);
        });

        Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();
    }

    private void setupBackNavigation() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Si on n'est pas sur l'accueil, on y retourne
                if (bottomNav.getSelectedItemId() != R.id.accuiel) {
                    bottomNav.setSelectedItemId(R.id.accuiel);
                } else {
                    // Sinon on ferme l'appli
                    finish();
                }
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Animation fluide
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}