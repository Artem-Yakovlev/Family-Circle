package com.tydeya.familycircle.ui.mainlivepage.details.membersstoriesadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;
import com.tydeya.domain.member.FamilyMember;

import java.util.ArrayList;

public class FamilyMembersStoriesRecyclerViewAdapter
        extends RecyclerView.Adapter<FamilyMemberViewHolder> {

    private Context context;
    private ArrayList<FamilyMember> familyMembers;
    private OnClickMemberStoryListener onClickMemberStoryListener;

    public FamilyMembersStoriesRecyclerViewAdapter(Context context, ArrayList<FamilyMember> familyMembers,
                                                   OnClickMemberStoryListener onClickMemberStoryListener) {
        this.onClickMemberStoryListener = onClickMemberStoryListener;
        this.context = context;
        this.familyMembers = familyMembers;
    }

    @NonNull
    @Override
    public FamilyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FamilyMemberViewHolder(LayoutInflater.from(context).
                inflate(R.layout.cardview_family_member_live_page, parent, false), onClickMemberStoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberViewHolder holder, int position) {
        holder.setNameText(familyMembers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return familyMembers.size();
    }


    public void refreshData(ArrayList<FamilyMember> familyMembers) {
        this.familyMembers.clear();
        this.familyMembers = familyMembers;
        notifyDataSetChanged();
    }

}
