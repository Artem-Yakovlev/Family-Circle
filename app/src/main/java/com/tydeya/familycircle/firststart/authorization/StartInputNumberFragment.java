package com.tydeya.familycircle.firststart.authorization;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.synchronization.accountexisting.AccountIsExistResultRecipient;
import com.tydeya.familycircle.synchronization.accountexisting.AccountPhoneSynchronizationTool;
import com.tydeya.familycircle.simplehelpers.KeyboardHelper;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * In this fragment, the user enters his phone number for registration and goes to the next page.
 *
 * @author Artem Yakovlev
 **/

public class StartInputNumberFragment extends Fragment implements AccountIsExistResultRecipient {

    private View root;
    private NavController navController;
    private MaterialButton nextButton;

    private CountryCodePicker countryPicker;
    private TextInputEditText phoneNumberInput;

    private ProgressDialog loadingDialog;

    private AccountPhoneSynchronizationTool accountPhoneSynchronizationTool;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    accountPhoneSynchronizationTool.isAccountWithPhoneExist(countryPicker.getFullNumberWithPlus());
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    closeLoadingDialog();
                    Log.d("ASMR", "Auth failed: " + e.toString());
                }

                @Override
                public void onCodeSent(@NonNull String s,
                                       @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    closeLoadingDialog();

                    Bundle bundle = new Bundle();
                    bundle.putString("userCodeId", s);
                    bundle.putString("userPhoneNumber", countryPicker.getFullNumberWithPlus());
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
            if (countryPicker.isValidFullNumber()) {
                verifyPhoneNumber();
            } else {
                phoneNumberInput.setError(root.getContext()
                        .getResources().getString(R.string.start_input_number_invalid_error));
            }
        });
        accountPhoneSynchronizationTool =
                new AccountPhoneSynchronizationTool(new WeakReference<>(this));
    }


    private void verifyPhoneNumber() {

        StringBuilder questionText = new StringBuilder(getResources().getString(R.string.first_start_input_number_correct_text));
        questionText.append(" ").append(countryPicker.getFullNumberWithPlus()).append(" ?");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setTitle(getResources().getString(R.string.first_start_input_number_correct_title));
        alertDialogBuilder.setMessage(questionText);

        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.first_start_input_number_correct_yes),
                (dialogInterface, i) -> sendVerificationCode());

        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.first_start_input_number_correct_no),
                ((dialogInterface, i) -> {
                }));

        AlertDialog isCorrectNumberDialog = alertDialogBuilder.create();
        isCorrectNumberDialog.show();
    }

    /**
     * This method sends verification code through firebase and shows loading dialog
     **/
    private void sendVerificationCode() {
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

    @Override
    public void isExist(QuerySnapshot queryDocumentSnapshots) {
        closeLoadingDialog();
        navController.navigate(R.id.selectFamilyFragment);
    }

    @Override
    public void isNotExist() {
        Bundle bundle = new Bundle();
        bundle.putString("phone_number", countryPicker.getFullNumberWithPlus());
        closeLoadingDialog();
        navController.navigate(R.id.createNewAccountFragment, bundle);
    }

    @Override
    public void isError(Exception e) {
        Log.d("ASMR", e.toString());
    }

    private void closeLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }
}

