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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_start_input_number, container, false);

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
    private void sendVerificationCode(){


    }

}

