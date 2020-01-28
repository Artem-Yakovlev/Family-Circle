package com.tydeya.familycircle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.firststart.FirstStartActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(this, FirstStartActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Toast.makeText(this, getString(R.string.main_activity_registred_user),
                    Toast.LENGTH_LONG).show();
        }
    }
}
