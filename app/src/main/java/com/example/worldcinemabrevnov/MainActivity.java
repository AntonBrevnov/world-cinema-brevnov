package com.example.worldcinemabrevnov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    HomeFragment homeFragment = new HomeFragment();
    QueryFragment queryFragment = new QueryFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        System.out.println("Test");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, homeFragment).commit();
                return true;
            case R.id.navigation_query:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, queryFragment).commit();
                return true;
            case R.id.navigation_favorite:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, favoriteFragment).commit();
                return true;
            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, profileFragment).commit();
                return true;
        }
        return false;
    }
}