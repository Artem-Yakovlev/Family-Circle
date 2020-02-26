package com.tydeya.familycircle.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.ui.firststartpage.FirstStartActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificationCheck();

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificationCheck();
    }

    private void verificationCheck() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, FirstStartActivity.class);
            startActivity(intent);
            this.finish();
        }
    }


}
