package com.tydeya.familycircle.firststart.accountcreation.views;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.DatePickerPresenter;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.DatePickerUsable;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.DateRefactoring;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.ImageCropperUsable;
import com.tydeya.domain.member.ActiveMember;
import com.tydeya.familycircle.simplehelpers.DataConfirming;
import com.tydeya.familycircle.synchronization.accountcreate.CreateSyncAccountTool;
import com.tydeya.familycircle.synchronization.accountcreate.SyncAccountCreatedRecipient;
import com.tydeya.familycircle.user.User;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;


public class CreateNewAccountFragment extends Fragment implements DatePickerUsable,
        ImageCropperUsable, SyncAccountCreatedRecipient {

    private CardView dateCard;
    private ShapedImageView userPhotoImage;
    private TextView birthDateText;
    private TextInputEditText nameText;
    private Button createAccountButton;
    private NavController navController;
    private ActiveMember.Builder activeMemberBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create_new_account, container, false);

        dateCard = root.findViewById(R.id.create_account_page_date_card);
        userPhotoImage = root.findViewById(R.id.create_account_page_add_photo);
        birthDateText = root.findViewById(R.id.create_account_page_date_text);
        createAccountButton = root.findViewById(R.id.create_account_main_button);
        nameText = root.findViewById(R.id.create_account_page_name_input);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        assert getActivity() != null;

        dateCard.setOnClickListener(new DatePickerPresenter(new WeakReference<>(this),
                Calendar.getInstance()));
        //TODO set max and min size for cropping!
        userPhotoImage.setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .start(getActivity()));

        createAccountButton.setOnClickListener(v -> {
            if (!DataConfirming.isEmptyNecessaryCheck(nameText, true)) {
                createAccount();
            }
        });

        activeMemberBuilder = new ActiveMember.Builder();
    }

    private void createAccount() {
        assert nameText.getText() != null;
        activeMemberBuilder.setName(nameText.getText().toString());
        activeMemberBuilder.setPhoneNumber(getArguments().getString("phone_number"));

        ActiveMember activeMember = activeMemberBuilder.build();
        User user = User.getInstance();
        user.setUserFamilyMember(activeMember);



        CreateSyncAccountTool createSyncAccountTool = new CreateSyncAccountTool(new WeakReference<>(this));
        createSyncAccountTool.CreateAccount(user.getUserFamilyMember());
    }

    @Override
    public void dateChanged(int selectedDateYear, int selectedDateMonth, int selectedDateDay) {

        assert getContext() != null;

        Calendar calendar = new GregorianCalendar(selectedDateYear, selectedDateMonth,
                selectedDateDay);

        activeMemberBuilder.setBirthDate(calendar);
        birthDateText.setText(DateRefactoring.getDateLocaleText(calendar));
        birthDateText.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void imageCroppedSuccessfully(CropImage.ActivityResult activityResult) {
        Uri imageUri = activityResult.getUri();
        userPhotoImage.setPadding(0, 0, 0, 0);
        Glide.with(this)
                .load(imageUri)
                .into(userPhotoImage);
        activeMemberBuilder.setImageAddress(imageUri);
    }

    @Override
    public void imageCroppedWithError(CropImage.ActivityResult activityResult) {

    }

    @Override
    public void accountSuccessfullyCreated() {
        navController.navigate(R.id.selectFamilyFragment);
    }

    @Override
    public void accountCreationFailed(Exception e) {
        Log.d("ASMR", e.toString());
    }
}
