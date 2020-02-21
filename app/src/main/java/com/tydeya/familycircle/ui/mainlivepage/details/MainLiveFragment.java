package com.tydeya.familycircle.ui.mainlivepage.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.family.member.FamilyMember;
import com.tydeya.familycircle.ui.mainlivepage.abstraction.LivePagePresenter;
import com.tydeya.familycircle.ui.mainlivepage.abstraction.LivePageView;
import com.tydeya.familycircle.ui.mainlivepage.details.membersstoriesadapter.FamilyMembersStoriesRecyclerViewAdapter;
import com.tydeya.familycircle.ui.mainlivepage.details.membersstoriesadapter.OnClickMemberStoryListener;
import com.tydeya.familycircle.user.DataUpdatedResultRecipient;
import com.tydeya.familycircle.user.User;

import java.util.ArrayList;

public class MainLiveFragment extends Fragment implements OnClickMemberStoryListener,
        DataUpdatedResultRecipient, LivePageView {

    private NavController navController;

    private RecyclerView familyStoriesRecyclerView;
    private FamilyMembersStoriesRecyclerViewAdapter recyclerViewAdapter;

    private LivePagePresenter livePagePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_live_page, container, false);
        navController = NavHostFragment.findNavController(this);
        familyStoriesRecyclerView = root.findViewById(R.id.main_live_page_family_recycler_view);
        User.getInstance().updateDataFromServer(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView(User.getInstance().getFamily().getFamilyMembers());
        livePagePresenter = new SimpleLivePagePresenter(this, null);

    }

    @Override
    public void onClickMemberStory(int position) {
        livePagePresenter.clickOnPersonView(position);
    }

    @Override
    public void familyMemberUpdated() {
        recyclerViewAdapter.refreshData(User.getInstance().getFamily().getFamilyMembers());
    }

    @Override
    public void openPersonPage(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("personPosition", id);
        navController.navigate(R.id.familyMemberViewFragment, bundle);
    }

    private void setRecyclerView(ArrayList<FamilyMember> familyActiveMembers) {
        recyclerViewAdapter = new FamilyMembersStoriesRecyclerViewAdapter(getContext(), familyActiveMembers,
                this);
        familyStoriesRecyclerView.setAdapter(recyclerViewAdapter);
        familyStoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
    }
}
