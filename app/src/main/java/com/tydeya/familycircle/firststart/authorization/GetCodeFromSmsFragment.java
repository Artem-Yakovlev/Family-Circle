package com.tydeya.familycircle.firststart.authorization;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.synchronization.accountexisting.AccountIsExistResultRecipient;
import com.tydeya.familycircle.synchronization.accountexisting.AccountPhoneSynchronizationTool;
import com.tydeya.familycircle.simplehelpers.DataConfirming;
import com.tydeya.familycircle.simplehelpers.KeyboardHelper;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;


public class GetCodeFromSmsFragment extends Fragment implements AccountIsExistResultRecipient {

    private View root;
    private FirebaseAuth firebaseAuth;
    private MaterialButton acceptCodeButton;
    private TextInputEditText codeInput;
    private NavController navController;
    private ProgressDialog loadingDialog;
    private CountDownTimer resendSmsTimer;
    private Button resendButton;
    private TextView resendTimerTextView;

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
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_get_code_from_sms, container, false);

        acceptCodeButton = root.findViewById(R.id.get_code_page_button);
        codeInput = root.findViewById(R.id.get_code_page_input);
        resendButton = root.findViewById(R.id.get_code_page_sms_button);
        resendTimerTextView = root.findViewById(R.id.get_code_page_resend_timer);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        navController = NavHostFragment.findNavController(this);

        resendSmsTimer = new CountDownTimer(60000, 1000) {

            private StringBuffer timerStringBuffer;

            @Override
            public void onTick(long l) {

                timerStringBuffer = new StringBuffer();
                timerStringBuffer.append((l / 60000) / 1000).append(":");

                if ((l % 60000) / 1000 < 10) {
                    timerStringBuffer.append("0");
                }

                timerStringBuffer.append((l % 60000) / 1000);
                resendTimerTextView.setText(timerStringBuffer);

            }

            @Override
            public void onFinish() {
                resendButton.setEnabled(true);
            }
        };

        acceptCodeButton.setOnClickListener(v -> {
            if (!DataConfirming.isEmptyNecessaryCheck(codeInput, true)) {
                verifyCode();
            }
        });

        resendButton.setOnClickListener(v -> {
            resendButton.setEnabled(false);
            resendSms();
            resendSmsTimer.start();
        });

        resendButton.callOnClick();

    }

    private void resendSms() {
        assert getArguments() != null && getActivity() != null;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                getArguments().getString("userPhoneNumber"),
                60,
                TimeUnit.SECONDS,
                getActivity(),
                authCallbacks);
    }

    private void verifyCode() {
        assert codeInput.getText() != null && getArguments() != null && getActivity() != null;

        KeyboardHelper.hideKeyboard(getActivity());

        loadingDialog = ProgressDialog.show(getContext(), null,
                getString(R.string.loading_text), true);

        String userCodeId = getArguments().getString("userCodeId", "");

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(userCodeId,
                codeInput.getText().toString());
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        assert getActivity() != null;
        assert getArguments() != null;

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {

                    if (task.isSuccessful()) {
                        AccountPhoneSynchronizationTool accountPhoneSynchronizationTool =
                                new AccountPhoneSynchronizationTool(new WeakReference<>(this));
                        accountPhoneSynchronizationTool.isAccountWithPhoneExist(getArguments().getString("userPhoneNumber"));
                    } else {
                        closeLoadingDialog();
                        Snackbar.make(root, R.string.get_code_page_invalid_code, Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
    }

    @Override
    public void isExist(QuerySnapshot queryDocumentSnapshots) {
        closeLoadingDialog();
        navController.navigate(R.id.selectFamilyFragment);
    }

    @Override
    public void isNotExist() {
        Bundle bundle = new Bundle();
        bundle.putString("phone_number", getArguments().getString("userPhoneNumber"));
        closeLoadingDialog();
        navController.navigate(R.id.createNewAccountFragment, bundle);
    }

    @Override
    public void isError(Exception e) {
        closeLoadingDialog();
        Log.d("ASMR", e.toString());
    }

    private void closeLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }
}
