package com.tydeya.familycircle.firststart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.tydeya.familycircle.R;


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

        assert getActivity() != null;

        dateCard.setOnClickListener(new DatePickerOnclickListener(birthDate));

        photoCard.setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1,1)
                .start(getActivity()));
    }
}
