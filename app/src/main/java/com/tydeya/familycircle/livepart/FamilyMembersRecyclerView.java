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

import java.util.ArrayList;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

public class FamilyMembersRecyclerView
        extends RecyclerView.Adapter<FamilyMembersRecyclerView.FamilyMemberViewHolder> {

    private Context context;
    private ArrayList<FamilyMember> familyMembers;

    FamilyMembersRecyclerView(Context context, ArrayList<FamilyMember> familyMembers) {
        this.context = context;
        this.familyMembers = familyMembers;
    }

    @NonNull
    @Override
    public FamilyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new FamilyMemberViewHolder(layoutInflater.inflate(R.layout.cardview_family_member_live_page,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberViewHolder holder, int position) {
        holder.setNameText(familyMembers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return familyMembers.size();
    }

    static class FamilyMemberViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private ShapedImageView userShapedImage;

        FamilyMemberViewHolder(@NonNull View itemView) {
            super(itemView);

            userShapedImage = itemView.findViewById(R.id.family_member_live_page_image);
            nameText = itemView.findViewById(R.id.family_member_live_page_text);
            nameText.setSelected(true);
        }

        void setNameText(String name) {
            nameText.setText(name);
        }

        void setImage(Uri imageUri) {
            Glide.with(nameText.getContext())
                    .load(imageUri)
                    .into(userShapedImage);
        }
    }

}
