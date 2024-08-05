package com.example.newsproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private static final int ID_NEWSPAPER = R.id.newspaper;
    private static final int ID_REELNEWS = R.id.reelnews;
    private static final int ID_PROFILE = R.id.profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            loadFragment(new PaperFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Fragment selectedFragment = null;
            int id = menuItem.getItemId();
            if (id == ID_NEWSPAPER) {
                selectedFragment = new PaperFragment();
            } else if (id == ID_REELNEWS) {
                selectedFragment = new ReelFragment();
            } else if (id == ID_PROFILE) {
                selectedFragment = new ProfileFragment();
            }
            return loadFragment(selectedFragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
