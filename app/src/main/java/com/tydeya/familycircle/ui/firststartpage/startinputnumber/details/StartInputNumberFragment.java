package com.tydeya.familycircle.ui.firststartpage.startinputnumber.details;

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
import com.hbb20.CountryCodePicker;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.framework.phoneverification.details.PhoneVerificationHandlerImpl;
import com.tydeya.familycircle.simplehelpers.KeyboardHelper;
import com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction.StartInputNumberPresenter;
import com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction.StartInputNumberView;

/**
 * In this fragment, the user enters his phone number for registration and goes to the next page.
 *
 * @author Artem Yakovlev
 **/

public class StartInputNumberFragment extends Fragment implements StartInputNumberView {

    private NavController navController;
    private MaterialButton nextButton;

    private CountryCodePicker countryPicker;
    private TextInputEditText phoneNumberInput;

    private ProgressDialog loadingDialog;

    private StartInputNumberPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_start_input_number, container, false);

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
        presenter = new StartInputNumberPresenterImpl(this, new PhoneVerificationHandlerImpl());
        nextButton.setOnClickListener(v -> presenter.onAcceptButtonClick(countryPicker.isValidFullNumber()));
    }

    @Override
    public void phoneNumberCountryCorrect() {
        assert getActivity() != null;

        VerifyPhoneNumberDialog.create(countryPicker.getContext(), countryPicker.getFullNumberWithPlus(),
                (dialogInterface, i) -> {
                    KeyboardHelper.hideKeyboard(getActivity());
                    loadingDialog = ProgressDialog.show(getContext(), null, getString(R.string.loading_text),
                            true);
                    presenter.sendVerificationCode(countryPicker.getFullNumberWithPlus());
                },
                ((dialogInterface, i) -> {
                })).show();
    }

    @Override
    public void phoneNumberCountryIncorrect() {
        phoneNumberInput.setError(countryPicker.getContext()
                .getResources().getString(R.string.start_input_number_invalid_error));
    }

    @Override
    public void verificationCodeSent(String userCodeId) {
        closeLoadingDialog();
        Bundle bundle = new Bundle();
        bundle.putString("userCodeId", userCodeId);
        bundle.putString("userPhoneNumber", countryPicker.getFullNumberWithPlus());
        navController.navigate(R.id.getCodeFromSmsFragment, bundle);
    }

    @Override
    public void verificationFailure(Exception e) {
        closeLoadingDialog();
        Log.d("ASMR", e.toString());
    }

    @Override
    public void verificationSuccess(boolean accountExist) {
        closeLoadingDialog();
        if (accountExist) {
            navController.navigate(R.id.selectFamilyFragment);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("phone_number", countryPicker.getFullNumberWithPlus());
            closeLoadingDialog();
            navController.navigate(R.id.createNewAccountFragment, bundle);
        }
    }

    private void closeLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

}

