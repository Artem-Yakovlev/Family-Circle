package com.tydeya.familycircle.livepart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.user.User;

public class MainLivePage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_live_page, container, false);

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.familyMemberViewFragment);
        RecyclerView recyclerView = root.findViewById(R.id.main_live_page_family_recycler_view);
        RecyclerView.Adapter recyclerViewAdapter = new FamilyMembersRecyclerView(getContext(),
                User.getInstance().getFamily().getFamilyMembers());

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        return root;
    }
}
