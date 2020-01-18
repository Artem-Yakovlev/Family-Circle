package com.tydeya.familycircle.firststart;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.tydeya.familycircle.R;

import static android.app.Activity.RESULT_OK;


public class CreateNewAccountFragment extends Fragment {

    private CardView dateCard;
    private CardView photoCard;
    private ImageView userPhoto;
    private TextView birthDate;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_create_new_account, container, false);

        dateCard = root.findViewById(R.id.create_account_page_date_card);
        photoCard = root.findViewById(R.id.create_account_page_photo_card);
        userPhoto = root.findViewById(R.id.create_account_page_add_photo);
        birthDate = root.findViewById(R.id.create_account_page_date_text);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateCard.setOnClickListener(new DatePickerOnclickListener(birthDate));

        photoCard.setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1,1)
                .start((Activity) v.getContext()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ASMR", requestCode + " | " + resultCode);
        // Processing of cropped photo
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                Glide.with(this)
                        .load(imageUri)
                        .into(userPhoto);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
