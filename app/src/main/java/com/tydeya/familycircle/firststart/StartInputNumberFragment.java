package com.tydeya.familycircle.firststart;

import android.app.ProgressDialog;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.simplehelpers.KeyboardHelper;

import java.util.concurrent.TimeUnit;

/**
 * In this fragment, the user enters his phone number for registration and goes to the next page.
 * @author Artem Yakovlev
 **/

public class StartInputNumberFragment extends Fragment {

    private View root;
    private NavController navController;
    private MaterialButton nextButton;

    private CountryCodePicker countryPicker;
    private TextInputEditText phoneNumberInput;

    private ProgressDialog loadingDialog;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                }

                @Override
                public void onCodeSent(@NonNull String s,
                                       @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    if (loadingDialog.isShowing()){
                        loadingDialog.cancel();
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("userCodeId", s);
                    navController.navigate(R.id.getCodeFromSmsFragment, bundle);
                }
            };

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

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextButton.setOnClickListener(v -> {
            if (countryPicker.isValidFullNumber()){
                sendVerificationCode();
            } else {
                phoneNumberInput.setError(root.getContext()
                        .getResources().getString(R.string.start_input_number_invalid_error));
            }
        });
    }

    /**
     * This method sends verification code through firebase and shows loading dialog
     **/
    private void sendVerificationCode(){
        assert getActivity() != null;

        KeyboardHelper.hideKeyboard(getActivity());

        loadingDialog = ProgressDialog.show(getContext(), null,
                getString(R.string.loading_text), true);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                countryPicker.getFullNumberWithPlus(),
                60,
                TimeUnit.SECONDS,
                getActivity(),
                authCallbacks);
    }

}

