package com.tydeya.familycircle.livepart;

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

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.user.User;

import java.lang.ref.WeakReference;

public class MainLivePage extends Fragment implements FamilyMembersStoriesRecyclerView.OnClickMemberStoryListener {

    private NavController navController;
    private View root;
    private RecyclerView familyStoriesRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main_live_page, container, false);
        navController = NavHostFragment.findNavController(this);

        familyStoriesRecyclerView = root.findViewById(R.id.main_live_page_family_recycler_view);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.Adapter recyclerViewAdapter = new FamilyMembersStoriesRecyclerView(getContext(),
                User.getInstance().getFamily().getFamilyMembers(), new WeakReference<>(this));

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
}
