package com.tydeya.familycircle.ui.mainlivepage.details.membersstoriesadapter;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

class FamilyMemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView nameText;
    private ShapedImageView userShapedImage;
    private OnClickMemberStoryListener onClickMemberStoryListener;

    FamilyMemberViewHolder(@NonNull View itemView, OnClickMemberStoryListener onClickMemberStoryListener) {
        super(itemView);
        this.onClickMemberStoryListener = onClickMemberStoryListener;
        userShapedImage = itemView.findViewById(R.id.family_member_live_page_image);
        nameText = itemView.findViewById(R.id.family_member_live_page_text);
        itemView.setOnClickListener(this);
    }

    void setNameText(String name) {
        nameText.setText(name);
    }

    void setImage(Uri imageUri) {
        Glide.with(nameText.getContext())
                .load(imageUri)
                .into(userShapedImage);
    }

    @Override
    public void onClick(View view) {
        onClickMemberStoryListener.onClickMemberStory(getAdapterPosition());
    }
}
