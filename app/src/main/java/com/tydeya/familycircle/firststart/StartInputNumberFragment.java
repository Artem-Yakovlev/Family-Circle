package com.tydeya.familycircle.firststart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.tydeya.familycircle.R;

import java.util.concurrent.TimeUnit;


public class StartInputNumberFragment extends Fragment {

    private View root;
    private NavController navController;
    private MaterialButton nextButton;

    private CountryCodePicker countryPicker;
    private TextInputEditText phoneNumberInput;

    private FirebaseAuth firebaseAuth;

    private String codeSent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_start_input_number, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        // Processing of country code picker
        countryPicker = root.findViewById(R.id.start_input_phone_number_ccp);
        phoneNumberInput = root.findViewById(R.id.start_input_phone_number_input);
        countryPicker.registerCarrierNumberEditText(phoneNumberInput);

        // Processing of next button
        navController = NavHostFragment.findNavController(this);
        nextButton = root.findViewById(R.id.start_input_phone_number_next);
        nextButton.setOnClickListener(view -> {
            if (countryPicker.isValidFullNumber()){
                sendVerificationCode();
            } else {
                phoneNumberInput.setError(root.getContext().
                        getResources().
                        getString(R.string.start_input_number_invalid_error));
            }
        });

        return root;
    }

    /*
     * This method sends verification code through firebase
     * */
    private void sendVerificationCode(){
        assert getActivity() != null;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                countryPicker.getFullNumberWithPlus(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Bundle bundle = new Bundle();
            bundle.putString("userCodeId", s);
            navController.navigate(R.id.getCodeFromSmsFragment, bundle);
        }
    };

}

