package com.tydeya.familycircle.firststart.authorization;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.simplehelpers.DataConfirming;
import com.tydeya.familycircle.simplehelpers.KeyboardHelper;

import java.util.concurrent.TimeUnit;


public class GetCodeFromSmsFragment extends Fragment {

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
                    Toast.makeText(getContext(), "Authentication success", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(@NonNull String s,
                                       @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    Toast.makeText(getContext(), "Code sent", Toast.LENGTH_LONG).show();
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
                timerStringBuffer.append((l/60000)/1000).append(":");

                if ((l%60000)/1000 < 10){
                    timerStringBuffer.append("0");
                }

                timerStringBuffer.append((l%60000)/1000);
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

    private void resendSms(){
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

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {

                    if (loadingDialog.isShowing()){
                        loadingDialog.cancel();
                    }

                    if (task.isSuccessful()) {
                        navController.navigate(R.id.createNewAccountFragment);
                    } else {
                        Snackbar.make(root, R.string.get_code_page_invalid_code, Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
    }
}
