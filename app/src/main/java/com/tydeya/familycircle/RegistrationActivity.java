package com.tydeya.familycircle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.login_page_email_input);
        passwordInput = findViewById(R.id.login_page_password_input);
        signInButton = findViewById(R.id.login_page_sign_in_button);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        //Toast.makeText(this, "Уже вошел", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(final View view) {
        if (view.equals(signInButton)){

            String userEmail = String.valueOf(emailInput.getText());
            String userPass = String.valueOf(passwordInput.getText());


            auth.signInWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                        // 1212GAga!
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("ASMR", "true");
                            } else {
                                Log.d("ASMR", "false");
                            }
                        }
                    });
        }
    }
}
