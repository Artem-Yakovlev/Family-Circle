package com.tydeya.familycircle.ui.livepart.main.details.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.familymember.FamilyMember;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

import static com.tydeya.familycircle.utils.DipKt.getDp;

public class FamilyMemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView nameText;
    private ShapedImageView userShapedImage;
    private OnClickMemberStoryListener onClickMemberStoryListener;

    FamilyMemberViewHolder(@NonNull View itemView, OnClickMemberStoryListener onClickMemberStoryListener) {
        super(itemView);
        this.onClickMemberStoryListener = onClickMemberStoryListener;
        itemView.setOnClickListener(this);
    }

    /**
     * Data binding
     */

    void bindData(FamilyMember familyMember, int onlineStatusColor) {
        findAllViews();

        nameText.setText(familyMember.getDescription().getName());
        userShapedImage.setStrokeColor(onlineStatusColor);
        setProfileImage(familyMember.getDescription().getImageAddress());
    }

    private void setProfileImage(String imageAddress) {
        if (imageAddress.equals("")) {
            int dipForPadding = getDp(nameText.getContext(), 10);
            userShapedImage.setPadding(dipForPadding,dipForPadding,dipForPadding,dipForPadding);
            Glide.with(nameText.getContext())
                    .load(R.drawable.ic_photo_camera_blue_36dp)
                    .into(userShapedImage);
        } else {
            userShapedImage.setPadding(0,0,0,0);
            Glide.with(nameText.getContext())
                    .load(imageAddress)
                    .into(userShapedImage);
        }
    }

    private void findAllViews() {
        userShapedImage = itemView.findViewById(R.id.family_member_live_page_image);
        nameText = itemView.findViewById(R.id.family_member_live_page_text);
    }

    @Override
    public void onClick(View view) {
        onClickMemberStoryListener.onClickMemberStory(getAdapterPosition());
    }
}
