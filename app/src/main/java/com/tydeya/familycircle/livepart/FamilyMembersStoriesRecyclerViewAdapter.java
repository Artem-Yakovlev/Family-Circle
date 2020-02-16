package com.tydeya.familycircle.livepart;

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
import com.tydeya.familycircle.family.member.FamilyMember;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

public class FamilyMembersStoriesRecyclerViewAdapter
        extends RecyclerView.Adapter<FamilyMembersStoriesRecyclerViewAdapter.FamilyMemberViewHolder> {

    private Context context;
    private ArrayList<FamilyMember> familyMembers;
    private WeakReference<OnClickMemberStoryListener> onClickMemberStoryListener;

    FamilyMembersStoriesRecyclerViewAdapter(Context context, ArrayList<FamilyMember> familyMembers,
                                            WeakReference<OnClickMemberStoryListener> onClickMemberStoryListener) {
        this.onClickMemberStoryListener = onClickMemberStoryListener;
        this.context = context;
        this.familyMembers = familyMembers;
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
        holder.setNameText(familyMembers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return familyMembers.size();
    }

    static class FamilyMemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameText;
        private ShapedImageView userShapedImage;
        private WeakReference<OnClickMemberStoryListener> onClickMemberStoryListener;

        FamilyMemberViewHolder(@NonNull View itemView, WeakReference<OnClickMemberStoryListener> onClickMemberStoryListener) {
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
            onClickMemberStoryListener.get().onClickMemberStory(getAdapterPosition());
        }
    }

    public interface OnClickMemberStoryListener {

        void onClickMemberStory(int position);
    }

    public void refreshData(ArrayList<FamilyMember> familyMembers) {
        this.familyMembers.clear();
        this.familyMembers = familyMembers;
        notifyDataSetChanged();
    }

}
