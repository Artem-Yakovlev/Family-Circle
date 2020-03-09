package com.tydeya.familycircle.ui.livepart.main.details.recyclerview;

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
        extends RecyclerView.Adapter<FamilyMemberViewHolder> {

    private Context context;
    private ArrayList<FamilyMember> members;
    private OnClickMemberStoryListener onClickMemberStoryListener;

    public FamilyMembersStoriesRecyclerViewAdapter(Context context, ArrayList<FamilyMember> members,
                                                   OnClickMemberStoryListener onClickListener) {
        this.onClickMemberStoryListener = onClickListener;
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public FamilyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FamilyMemberViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cardview_family_member_live_page, parent, false),
                onClickMemberStoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberViewHolder holder, int position) {
        holder.bindData(members.get(position));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void refreshData(ArrayList<FamilyMember> oldFamilyMembers) {
        this.members.clear();
        this.members = oldFamilyMembers;
        notifyDataSetChanged();
    }

}
