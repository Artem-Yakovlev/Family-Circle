package com.tydeya.familycircle.ui.livepart.details;

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

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.familyInteractor.abstraction.FamilyInteractorCallback;
import com.tydeya.familycircle.data.familyInteractor.details.FamilyInteractor;

import javax.inject.Inject;

public class MainLivePage extends Fragment implements FamilyMembersStoriesRecyclerViewAdapter.OnClickMemberStoryListener,
        FamilyInteractorCallback
{

    private NavController navController;
    private RecyclerView familyStoriesRecyclerView;
    private FamilyMembersStoriesRecyclerViewAdapter recyclerViewAdapter;

    @Inject
    FamilyInteractor familyInteractor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_live_page, container, false);
        navController = NavHostFragment.findNavController(this);

        familyStoriesRecyclerView = root.findViewById(R.id.main_live_page_family_recycler_view);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.getComponent().injectFragment(this);
        familyInteractor.subscribe(this);

        recyclerViewAdapter = new FamilyMembersStoriesRecyclerViewAdapter(getContext(),
                familyInteractor.getActualFamily().getFamilyMembers(), this);

        familyStoriesRecyclerView.setAdapter(recyclerViewAdapter);
        familyStoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));

    }
    @Override
    public void onClickMemberStory(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("personPosition", position);
        navController.navigate(R.id.familyMemberViewFragment, bundle);
    }

    @Override
    public void memberDataUpdated() {
        recyclerViewAdapter.refreshData(familyInteractor.getActualFamily().getFamilyMembers());
    }

    @Override
    public void onPause() {
        super.onPause();
        familyInteractor.unsubscribe(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        familyInteractor.subscribe(this);
    }
}
