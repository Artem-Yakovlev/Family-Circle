package com.tydeya.familycircle.ui.planpart.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.ui.planpart.abstraction.MainPlanView;
import com.tydeya.familycircle.ui.planpart.details.recyclerview.MainPlanItem;
import com.tydeya.familycircle.ui.planpart.details.recyclerview.MainPlanItemType;
import com.tydeya.familycircle.ui.planpart.details.recyclerview.MainPlanRecyclerViewAdapter;
import com.tydeya.familycircle.ui.planpart.details.recyclerview.OnClickMainPlanItemListener;

import java.util.ArrayList;


public class MainPlanFragment extends Fragment implements MainPlanView, OnClickMainPlanItemListener {

    private RecyclerView recyclerView;
    private MainPlanRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<MainPlanItem> mainPlanItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_plan_page, container, false);
        recyclerView = root.findViewById(R.id.main_plan_page_recycler_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateDataForMainPlanItems();
        recyclerViewAdapter = new MainPlanRecyclerViewAdapter(getContext(), mainPlanItems, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
                RecyclerView.VERTICAL, false));

    }

    private void generateDataForMainPlanItems() {
        mainPlanItems.clear();
        mainPlanItems.add(new MainPlanItem(getStringById(R.string.main_plan_item_kitchen_organizer_title),
                getStringById(R.string.main_plan_item_kitchen_organizer_text),
                R.drawable.ic_kitchen_black_24dp, MainPlanItemType.FOOD));

        mainPlanItems.add(new MainPlanItem(getStringById(R.string.main_plan_item_events_title),
                getStringById(R.string.main_plan_item_events_text), R.drawable.ic_event_black_24dp,
                MainPlanItemType.EVENTS));

        mainPlanItems.add(new MainPlanItem(getStringById(R.string.main_plan_item_planning_title),
                getStringById(R.string.main_plan_item_planning_text),
                R.drawable.ic_planning_black_24dp,
                MainPlanItemType.PLANNING));

        mainPlanItems.add(new MainPlanItem(getStringById(R.string.main_plan_item_important_title),
                getStringById(R.string.main_plan_item_important_text),
                R.drawable.ic_priority_high_black_24dp,
                MainPlanItemType.IMPORTANT));
    }

    private String getStringById(int stringId) {
        return getContext().getResources().getString(stringId);
    }

    @Override
    public void onMainPlanItemClick(MainPlanItemType itemType) {

    }
}
