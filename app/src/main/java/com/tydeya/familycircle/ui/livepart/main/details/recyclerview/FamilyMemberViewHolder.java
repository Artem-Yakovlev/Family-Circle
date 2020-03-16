package com.tydeya.familycircle.ui.livepart.main.details.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.familymember.FamilyMember;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

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
     * */

    void bindData(FamilyMember familyMember) {
        findAllViews();

        nameText.setText(familyMember.getDescription().getName());
        /*
        Glide.with(nameText.getContext())
                .load(familyMember.getDescription().getImageAddress())
                .into(userShapedImage);*/
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
