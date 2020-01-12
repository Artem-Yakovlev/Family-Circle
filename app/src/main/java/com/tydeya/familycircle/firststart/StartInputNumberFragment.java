package com.tydeya.familycircle.firststart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;
import com.tydeya.familycircle.R;


public class StartInputNumberFragment extends Fragment {

    private View root;
    private NavController navController;
    private MaterialButton nextButton;

    private CountryCodePicker countryPicker;
    private TextInputEditText phoneNumberInput;

    //private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_start_input_number, container, false);

        //firebaseAuth = FirebaseAuth.getInstance();

        // Processing of country code picker
        countryPicker = root.findViewById(R.id.start_input_phone_number_ccp);
        phoneNumberInput = root.findViewById(R.id.start_input_phone_number_input);
        countryPicker.registerCarrierNumberEditText(phoneNumberInput);

        // Processing of next button
        navController = NavHostFragment.findNavController(this);
        nextButton = root.findViewById(R.id.start_input_phone_number_next);
        nextButton.setOnClickListener(view -> {
            if (countryPicker.isValidFullNumber()){
                //sendVerificationCode();
                navController.navigate(R.id.getCodeFromSmsFragment);
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
    /*
    private void sendVerificationCode(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+79056644712",        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d("ASMR", true + "");
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("ASMR", false + " " + e.toString());
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("ASMR", s);
        }
    };*/

}

