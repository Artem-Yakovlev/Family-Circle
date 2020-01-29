package com.tydeya.familycircle.livepart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.family.member.FamilyMember;

import java.util.ArrayList;

public class MainLivePage extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_live_page, container, false);

        ArrayList<FamilyMember> familyMembers = new ArrayList<>();
        recyclerView = root.findViewById(R.id.main_live_page_family_recycler_view);
        recyclerViewAdapter = new FamilyMembersRecyclerView(getContext(), familyMembers);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        return root;
    }
}
