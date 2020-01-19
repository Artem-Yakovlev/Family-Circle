package com.tydeya.familycircle.firststart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.simplehelpers.DataConfirming;


public class GetCodeFromSmsFragment extends Fragment {

    private View root;
    private FirebaseAuth firebaseAuth;
    private MaterialButton acceptCodeButton;
    private TextInputEditText codeInput;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_get_code_from_sms, container, false);

        acceptCodeButton = root.findViewById(R.id.get_code_page_button);
        codeInput = root.findViewById(R.id.get_code_page_input);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        navController = NavHostFragment.findNavController(this);

        acceptCodeButton.setOnClickListener(v -> {
            if (!DataConfirming.isEmptyNecessaryCheck(codeInput, true)) {
                verifyCode();
            }
        });
    }

    private void verifyCode() {
        assert codeInput.getText() != null && getArguments() != null;

        String userCodeId = getArguments().getString("userCodeId", "");

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(userCodeId,
                codeInput.getText().toString());
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        assert getActivity() != null;

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        navController.navigate(R.id.createNewAccountFragment);
                    } else {
                        Snackbar.make(root, R.string.get_code_page_invalid_code, Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
    }
}
