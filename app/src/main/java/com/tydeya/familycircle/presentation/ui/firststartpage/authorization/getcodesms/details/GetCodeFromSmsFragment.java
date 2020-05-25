package com.tydeya.familycircle.presentation.ui.firststartpage.authorization.getcodesms.details;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.presentation.ui.firststartpage.authorization.getcodesms.abstraction.GetCodeFromSmsPresenter;
import com.tydeya.familycircle.presentation.ui.firststartpage.authorization.getcodesms.abstraction.GetCodeFromSmsView;
import com.tydeya.familycircle.utils.KeyboardHelper;

import static com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_PHONE_NUMBER;
import static com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_USER_CODE_ID;
import static com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_USER_PHONE_NUMBER;


public class GetCodeFromSmsFragment extends Fragment implements GetCodeFromSmsView {

    private View root;
    private MaterialButton acceptCodeButton;
    private TextInputEditText codeInput;
    private NavController navController;
    private ProgressDialog loadingDialog;
    private Button resendButton;
    private TextView resendTimerTextView;

    private GetCodeFromSmsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_get_code_from_sms, container, false);
        navController = NavHostFragment.findNavController(this);

        acceptCodeButton = root.findViewById(R.id.get_code_page_button);
        codeInput = root.findViewById(R.id.get_code_page_input);
        resendButton = root.findViewById(R.id.get_code_page_sms_button);
        resendTimerTextView = root.findViewById(R.id.get_code_page_resend_timer);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert codeInput.getText() != null && getArguments() != null;

        presenter = new GetCodeFromSmsPresenterImpl(this, FirebaseAuth.getInstance(),
                getArguments().getString(BUNDLE_USER_PHONE_NUMBER));

        acceptCodeButton.setOnClickListener(v -> presenter.onClickAcceptButton(codeInput.getText().toString()));

        resendButton.setOnClickListener(v -> {
            resendButton.setEnabled(false);
            presenter.onClickResendButton(getActivity());
        });

        resendButton.callOnClick();

    }

    /**
     * Presenter check code callbacks
     */

    @Override
    public void invalidCodeFormatAlert() {
        codeInput.setError(getString(R.string.get_code_page_invalid_code_alert));
    }

    @Override
    public void codeFormatIsValid() {
        assert codeInput.getText() != null && getArguments() != null && getActivity() != null;
        KeyboardHelper.hideKeyboard(getActivity());
        loadingDialog = ProgressDialog.show(getContext(), null, getString(R.string.loading_text), true);

        presenter.signInWithCode(getArguments().getString(BUNDLE_USER_CODE_ID, ""),
                codeInput.getText().toString(), getActivity());

    }

    @Override
    public void codeIsInvalid() {
        closeLoadingDialog();
        Snackbar.make(root, R.string.get_code_page_invalid_code, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Account existing checker callbacks
     */

    @Override
    public void accountIsExist() {
        closeLoadingDialog();
        navController.navigate(R.id.selectFamilyFragment);
    }

    @Override
    public void accountIsNotExistButVerificationIsSuccess(String fullPhoneNumber) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_PHONE_NUMBER, fullPhoneNumber);
        closeLoadingDialog();
        navController.navigate(R.id.createNewAccountFragment, bundle);
    }

    /**
     * Timer callbacks
     */

    @Override
    public void timerTick(String timeDown) {
        resendTimerTextView.setText(timeDown);
    }

    @Override
    public void timerFinish() {
        resendButton.setEnabled(true);
    }


    private void closeLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }
}
