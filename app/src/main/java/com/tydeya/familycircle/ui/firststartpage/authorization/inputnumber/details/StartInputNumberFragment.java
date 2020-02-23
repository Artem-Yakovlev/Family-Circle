package com.tydeya.familycircle.ui.firststartpage.authorization.inputnumber.details;

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
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.simplehelpers.KeyboardHelper;
import com.tydeya.familycircle.ui.firststartpage.authorization.inputnumber.abstraction.StartInputNumberPresenter;
import com.tydeya.familycircle.ui.firststartpage.authorization.inputnumber.abstraction.StartInputNumberView;

import java.util.Objects;

/**
 * In this fragment, the user enters his phone number for registration and goes to the next page.
 *
 * @author Artem Yakovlev
 **/

public class StartInputNumberFragment extends Fragment implements StartInputNumberView {

    private View root;
    private NavController navController;
    private MaterialButton nextButton;

    private CountryCodePicker countryPicker;
    private TextInputEditText phoneNumberInput;

    private ProgressDialog loadingDialog;

    private StartInputNumberPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

        presenter = new StartInputNumberPresenterImpl(this);
        nextButton.setOnClickListener(v -> presenter.onClickNextButton(countryPicker.isValidFullNumber()));
    }

    private void closeLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

    /**
     * Callbacks of valid checker
     */

    @Override
    public void validPhoneNumber() {
        VerifyPhoneNumberDialog.create(countryPicker.getContext(), countryPicker.getFullNumberWithPlus(),
                (dialog, which) -> {
                    KeyboardHelper.hideKeyboard(Objects.requireNonNull(getActivity()));

                    loadingDialog = ProgressDialog.show(getContext(), null,
                            getString(R.string.loading_text), true);

                    presenter.verifyPhoneNumber(countryPicker.getFullNumberWithPlus(), getActivity());

                }, null).show();
    }

    @Override
    public void invalidPhoneNumber() {
        phoneNumberInput.setError(root.getContext().getResources().getString(R.string.start_input_number_invalid_error));
    }

    /**
     * Callbacks of auth tool
     */

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        closeLoadingDialog();

        Bundle bundle = new Bundle();
        bundle.putString("userCodeId", s);
        bundle.putString("userPhoneNumber", countryPicker.getFullNumberWithPlus());
        navController.navigate(R.id.getCodeFromSmsFragment, bundle);
    }

    /**
     * Callback of account existing tool
     */

    @Override
    public void accountExist() {
        closeLoadingDialog();
        navController.navigate(R.id.selectFamilyFragment);
    }

    @Override
    public void accountNotExistButAuthIsSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString("phone_number", countryPicker.getFullNumberWithPlus());
        closeLoadingDialog();
        navController.navigate(R.id.createNewAccountFragment, bundle);
    }
}

