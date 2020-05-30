package com.tydeya.familycircle.presentation.ui.livepart.main.details;

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
import com.tydeya.familycircle.domain.cooperationorganizer.interactor.abstraction.CooperationInteractorCallback;
import com.tydeya.familycircle.domain.cooperationorganizer.interactor.details.CooperationInteractor;
import com.tydeya.familycircle.domain.oldfamilyinteractor.abstraction.FamilyInteractorCallback;
import com.tydeya.familycircle.domain.oldfamilyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.presentation.ui.livepart.main.details.storiesrecyclerview.OnClickMemberStoryListener;
import com.tydeya.familycircle.presentation.ui.livepart.main.details.cooperationrecyclerview.CooperationRecyclerViewAdapter;
import com.tydeya.familycircle.presentation.ui.livepart.main.details.storiesrecyclerview.FamilyMembersStoriesRecyclerViewAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_FULL_PHONE_NUMBER;

public class MainLiveFragment extends Fragment implements OnClickMemberStoryListener,
        FamilyInteractorCallback, CooperationInteractorCallback {

    private NavController navController;

    private RecyclerView familyStoriesRecyclerView;
    private FamilyMembersStoriesRecyclerViewAdapter recyclerViewAdapter;

    private RecyclerView cooperationRecyclerView;
    private CooperationRecyclerViewAdapter cooperationRecyclerViewAdapter;

    @Inject
    FamilyInteractor familyInteractor;

    @Inject
    CooperationInteractor cooperationInteractor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_live_page, container, false);
        navController = NavHostFragment.findNavController(this);

        familyStoriesRecyclerView = root.findViewById(R.id.main_live_page_family_recycler_view);
        cooperationRecyclerView = root.findViewById(R.id.main_live_page_family_live_tape_recycler_view);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.getComponent().injectFragment(this);
        setFamilyStoriesRecyclerView();
        setCooperationRecyclerView();

    }

    private void setFamilyStoriesRecyclerView() {
        recyclerViewAdapter = new FamilyMembersStoriesRecyclerViewAdapter(getContext(),
                familyInteractor.getActualFamily().getFamilyMembers(), this);

        familyStoriesRecyclerView.setAdapter(recyclerViewAdapter);
        familyStoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
    }

    private void setCooperationRecyclerView() {
        cooperationRecyclerViewAdapter =
                new CooperationRecyclerViewAdapter(getContext(), new ArrayList<>());

        cooperationRecyclerView.setAdapter(cooperationRecyclerViewAdapter);

        cooperationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onClickMemberStory(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_FULL_PHONE_NUMBER, familyInteractor.getActualFamily()
                .getFamilyMembers().get(position).getFullPhoneNumber());
        navController.navigate(R.id.familyMemberViewFragment, bundle);
    }

    @Override
    public void memberDataUpdated() {
        recyclerViewAdapter.refreshData(familyInteractor.getActualFamily().getFamilyMembers());
    }

    @Override
    public void cooperationDataFromServerUpdated() {
        cooperationRecyclerViewAdapter.refreshData(cooperationInteractor.getCooperationData());
    }

    @Override
    public void onPause() {
        super.onPause();
        familyInteractor.unsubscribe(this);
        cooperationInteractor.unsubscribe(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        familyInteractor.subscribe(this);
        cooperationInteractor.subscribe(this);
    }
}
