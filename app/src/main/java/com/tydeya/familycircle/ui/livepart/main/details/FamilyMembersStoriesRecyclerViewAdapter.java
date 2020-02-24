package com.tydeya.familycircle.ui.livepart.main.details;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.domain.familymember.FamilyMember;

import java.util.ArrayList;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

public class FamilyMembersStoriesRecyclerViewAdapter
        extends RecyclerView.Adapter<FamilyMembersStoriesRecyclerViewAdapter.FamilyMemberViewHolder> {

    private Context context;
    private ArrayList<FamilyMember> members;
    private OnClickMemberStoryListener onClickMemberStoryListener;

    FamilyMembersStoriesRecyclerViewAdapter(Context context, ArrayList<FamilyMember> members,
                                            OnClickMemberStoryListener onClickMemberStoryListener) {
        this.onClickMemberStoryListener = onClickMemberStoryListener;
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public FamilyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new FamilyMemberViewHolder(layoutInflater.inflate(R.layout.cardview_family_member_live_page,
                parent, false), onClickMemberStoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberViewHolder holder, int position) {
        holder.setNameText(members.get(position).getDescription().getName());
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    static class FamilyMemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

    public interface OnClickMemberStoryListener {

        void onClickMemberStory(int position);
    }

    void refreshData(ArrayList<FamilyMember> oldFamilyMembers) {
        this.members.clear();
        this.members = oldFamilyMembers;
        notifyDataSetChanged();
    }

}
