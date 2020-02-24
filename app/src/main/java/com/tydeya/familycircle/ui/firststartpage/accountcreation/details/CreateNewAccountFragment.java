package com.tydeya.familycircle.ui.firststartpage.accountcreation.details;

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
import com.tydeya.familycircle.family.member.ActiveMemberOld;
import com.tydeya.familycircle.simplehelpers.DataConfirming;
import com.tydeya.familycircle.ui.firststartpage.accountcreation.abstraction.CreateNewAccountPresenter;
import com.tydeya.familycircle.ui.firststartpage.accountcreation.abstraction.CreateNewAccountView;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;


public class CreateNewAccountFragment extends Fragment implements DatePickerUsable,
        ImageCropperUsable, CreateNewAccountView {

    private CardView dateCard;
    private ShapedImageView userPhotoImage;
    private TextView birthDateText;
    private TextInputEditText nameText;
    private Button createAccountButton;
    private NavController navController;
    private ActiveMemberOld.Builder activeMemberBuilder;
    private CreateNewAccountPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create_new_account, container, false);
        navController = NavHostFragment.findNavController(this);

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
        assert getActivity() != null && nameText.getText() != null;

        presenter = new CreateNewAccountPresenterImpl(this, getArguments().getString("phone_number"));

        dateCard.setOnClickListener(new DatePickerPresenter(new WeakReference<>(this), Calendar.getInstance()));

        //TODO set max and min size for cropping!
        userPhotoImage.setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .start(getActivity()));

        createAccountButton.setOnClickListener(v -> presenter.onClickCreateAccount(nameText.getText().toString()));

    }

    @Override
    public void dateChanged(int selectedDateYear, int selectedDateMonth, int selectedDateDay) {

        assert getContext() != null;
        Calendar calendar = new GregorianCalendar(selectedDateYear, selectedDateMonth, selectedDateDay);

        presenter.birthDateChanged(DateRefactoring.getDateLocaleText(calendar));
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
        activeMemberBuilder.setImageUri(imageUri);
    }

    @Override
    public void imageCroppedWithError(CropImage.ActivityResult activityResult) {
        Log.d("ASMR", activityResult.getError().toString());
    }

    @Override
    public void invalidName() {
        DataConfirming.isEmptyNecessaryCheck(nameText, true);
    }

    @Override
    public void accountCreated() {
        navController.navigate(R.id.selectFamilyFragment);
    }

    @Override
    public void accountCreationFailure() {

    }
}
