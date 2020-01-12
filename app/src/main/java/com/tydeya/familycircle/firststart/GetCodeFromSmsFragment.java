package com.tydeya.familycircle.firststart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.registrationlogin.DataConfirming;


public class GetCodeFromSmsFragment extends Fragment {

    private View root;
    private FirebaseAuth firebaseAuth;
    private MaterialButton acceptCodeButton;
    private TextInputEditText codeInput;
    private String userCodeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_get_code_from_sms, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        assert getArguments() != null;
        userCodeId = getArguments().getString("userCodeId", "");

        acceptCodeButton = root.findViewById(R.id.get_code_page_button);
        codeInput = root.findViewById(R.id.get_code_page_input);

        acceptCodeButton.setOnClickListener(view -> {
            if (!DataConfirming.isEmptyNecessaryCheck(codeInput, true)) {
                verifyCode();
            }
        });

        return root;
    }

    private void verifyCode() {
        assert  codeInput.getText() != null;
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(userCodeId, codeInput.getText().toString());
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        assert getActivity() != null;

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(root.getContext(), "It's ok sign in", Toast.LENGTH_LONG).show();
                    } else {
                        // Sign in failed, display a message and update the UI

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(root.getContext(), "It's not ok", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(root.getContext(), "It's ok sign up", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
