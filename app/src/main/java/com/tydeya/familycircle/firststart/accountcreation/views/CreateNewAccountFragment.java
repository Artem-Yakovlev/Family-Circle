package com.tydeya.familycircle.firststart.accountcreation.views;

import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.DatePickerPresenter;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.DatePickerUsable;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.ImageCropperUsable;
import com.tydeya.familycircle.family.member.ActiveMember;
import com.tydeya.familycircle.family.member.ActiveMemberBuilder;
import com.tydeya.familycircle.simplehelpers.DataConfirming;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CreateNewAccountFragment extends Fragment implements DatePickerUsable,
        ImageCropperUsable {

    private CardView dateCard;
    private CardView photoCard;
    private ImageView userPhotoImage;
    private TextView birthDateText;
    private TextInputEditText nameText;
    private Button createAccountButton;
    private View root;


    private ActiveMemberBuilder activeMemberBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_create_new_account, container, false);

        dateCard = root.findViewById(R.id.create_account_page_date_card);
        photoCard = root.findViewById(R.id.create_account_page_photo_card);
        userPhotoImage = root.findViewById(R.id.create_account_page_add_photo);
        birthDateText = root.findViewById(R.id.create_account_page_date_text);
        createAccountButton = root.findViewById(R.id.create_account_main_button);
        nameText = root.findViewById(R.id.create_account_page_name_input);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getActivity() != null;

        dateCard.setOnClickListener(new DatePickerPresenter(new WeakReference<>(this),
                Calendar.getInstance()));

        photoCard.setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .start(getActivity()));

        createAccountButton.setOnClickListener(v -> {
            if (!DataConfirming.isEmptyNecessaryCheck(nameText, true)) {
                createAccount();
            }
        });

        activeMemberBuilder = new ActiveMemberBuilder();
    }

    private void createAccount() {
        assert nameText.getText() != null;
        activeMemberBuilder.setName(nameText.getText().toString());

        ActiveMember activeMember = activeMemberBuilder.getResult();

    }

    @Override
    public void dateChanged(int selectedDateYear, int selectedDateMonth, int selectedDateDay) {

        assert getContext() != null;

        Calendar calendar = new GregorianCalendar(selectedDateYear, selectedDateMonth,
                selectedDateDay);

        activeMemberBuilder.setBirthDate(calendar);

        String DATE_TEXT_PATTERN_SKELETON = "yyyy-MM-dd";
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(),
                DATE_TEXT_PATTERN_SKELETON);

        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        String output = format.format(calendar.getTime());

        birthDateText.setText(output);
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

    }
}
