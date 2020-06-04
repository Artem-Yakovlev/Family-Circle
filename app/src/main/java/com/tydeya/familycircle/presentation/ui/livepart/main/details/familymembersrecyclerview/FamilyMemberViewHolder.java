package com.tydeya.familycircle.presentation.ui.livepart.main.details.familymembersrecyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.familymember.FamilyMember;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

import static com.tydeya.familycircle.utils.DipKt.getDp;

public class FamilyMemberViewHolder extends RecyclerView.ViewHolder {

    private TextView nameText;
    private ShapedImageView userShapedImage;
    private OnClickMemberStoryListener clickListener;

    FamilyMemberViewHolder(@NonNull View itemView, OnClickMemberStoryListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
    }

    /**
     * Data binding
     */

    void bindData(FamilyMember familyMember) {
        findAllViews();
        nameText.setText(familyMember.getDescription().getName());

        if (familyMember.isOnline()) {
            userShapedImage.setStrokeColor(itemView.getContext()
                    .getResources().getColor(R.color.colorOnlineGreen));
        } else {
            userShapedImage.setStrokeColor(itemView.getContext()
                    .getResources().getColor(R.color.colorTransparentGray));
        }

        setProfileImage(familyMember.getDescription().getImageAddress());
        itemView.setOnClickListener(v -> {
            clickListener.onClickFamilyMember(familyMember.getFullPhoneNumber());
        });
    }

    private void setProfileImage(String imageAddress) {
        if (imageAddress.equals("")) {
            int dipForPadding = getDp(nameText.getContext(), 10);
            userShapedImage.setPadding(dipForPadding, dipForPadding, dipForPadding, dipForPadding);
            Glide.with(nameText.getContext())
                    .load(R.drawable.ic_photo_camera_blue_36dp)
                    .into(userShapedImage);
        } else {
            userShapedImage.setPadding(0, 0, 0, 0);
            Glide.with(nameText.getContext())
                    .load(imageAddress)
                    .into(userShapedImage);
        }
    }

    private void findAllViews() {
        userShapedImage = itemView.findViewById(R.id.family_member_live_page_image);
        nameText = itemView.findViewById(R.id.family_member_live_page_text);
    }
}
