package com.example.tbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new Bluetooth();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Optional: Add the transaction to the back stack
                transaction.addToBackStack(null);

                // Define your enter and exit animations here
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                transaction.replace(R.id.fragment_container, newFragment);
                transaction.commit();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        // Set the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LiveFragment()).commit();
    }


    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    if (item.getItemId() == R.id.navigation_live) {
                        selectedFragment = new LiveFragment();
                    }

                    if (item.getItemId() == R.id.navigation_user) {
                        selectedFragment = new UserFragment();
                    }

                    if (item.getItemId() == R.id.navigation_settings) {
                        selectedFragment = new SettingsFragment();
                    }

                    if (item.getItemId() == R.id.navigation_statistics) {
                        selectedFragment = new StatsFragment();
                    }



                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }

                    return true;
                }
            };

}