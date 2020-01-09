package com.tydeya.familycircle.registrationlogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tydeya.familycircle.R;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    private FirebaseAuth auth;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Button signInButton;
    private Button signUpButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = auth.getCurrentUser();
        //Toast.makeText(this, "Уже вошел", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        auth = FirebaseAuth.getInstance();

        emailInput = rootView.findViewById(R.id.login_page_email_input);
        passwordInput = rootView.findViewById(R.id.login_page_password_input);

        signInButton = rootView.findViewById(R.id.login_page_sign_in_button);
        signInButton.setOnClickListener(this);

        signUpButton = rootView.findViewById(R.id.login_page_sign_up_button);
        signUpButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(final View view) {
        if (view.equals(signInButton)) {

            if (!DataConfirming.isEmptyCheck(emailInput) & !DataConfirming.isEmptyCheck(passwordInput)) {

                String userEmail = String.valueOf(emailInput.getText());
                String userPass = String.valueOf(passwordInput.getText());

                auth.signInWithEmailAndPassword(userEmail, userPass)
                        .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(view.getContext(),
                                            getResources().getText(R.string.login_page_sign_in_button)
                                                    + " success", Toast.LENGTH_LONG).show();
                                } else {
                                    Snackbar
                                            .make(view.getRootView().findViewById(R.id.fragment_login_main_layout),
                                                    R.string.wrong_login_password,
                                                    Snackbar.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });

            }
        } else if (view.equals(signUpButton)) {

        }

    }

}
