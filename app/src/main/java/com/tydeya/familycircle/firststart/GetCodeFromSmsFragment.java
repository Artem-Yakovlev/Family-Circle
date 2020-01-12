package com.tydeya.familycircle.firststart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
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
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_get_code_from_sms, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        navController = NavHostFragment.findNavController(this);

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
                        navController.navigate(R.id.createNewAccount);
                    } else {

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Snackbar.make(root, R.string.get_code_page_invalid_code, Snackbar.LENGTH_LONG)
                                    .show();

                        } else {
                            Snackbar.make(root, R.string.get_code_page_invalid_code, Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
}
