package com.tydeya.familycircle.ui.livepart.main.details.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.domain.onlinemanager.details.OnlineInteractorImpl;

import java.util.ArrayList;

import javax.inject.Inject;

public class FamilyMembersStoriesRecyclerViewAdapter
        extends RecyclerView.Adapter<FamilyMemberViewHolder> {

    private Context context;
    private ArrayList<FamilyMember> members;
    private OnClickMemberStoryListener onClickMemberStoryListener;

    @Inject
    FamilyInteractor familyInteractor;

    @Inject
    OnlineInteractorImpl onlineInteractor;

    public FamilyMembersStoriesRecyclerViewAdapter(Context context, ArrayList<FamilyMember> members,
                                                   OnClickMemberStoryListener onClickListener) {
        App.getComponent().injectRecyclerViewAdapter(this);
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
        boolean onlineStatus = onlineInteractor.isUserOnline(members.get(position).getFullPhoneNumber());

        int color = context.getResources().getColor(R.color.colorTransparentGray);
        if (onlineStatus) {
            color = context.getResources().getColor(R.color.colorOnlineGreen);
        }

        holder.bindData(members.get(position), color);
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
