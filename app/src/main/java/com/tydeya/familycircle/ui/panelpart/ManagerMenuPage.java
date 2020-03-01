package com.tydeya.familycircle.ui.panelpart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.ui.panelpart.recyclerview.ManagerMenuItem;
import com.tydeya.familycircle.ui.panelpart.recyclerview.ManagerMenuItemType;
import com.tydeya.familycircle.ui.panelpart.recyclerview.ManagerMenuRecyclerViewAdapter;
import com.tydeya.familycircle.ui.panelpart.recyclerview.OnClickManagerMenuItemListener;

import java.util.ArrayList;


public class ManagerMenuPage extends Fragment implements OnClickManagerMenuItemListener {

    private RecyclerView recyclerView;
    private ManagerMenuRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<ManagerMenuItem> managerMenuItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_panel_page, container, false);
        recyclerView = root.findViewById(R.id.main_panel_recycler_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateDataForManagerMenuItems();
        recyclerViewAdapter = new ManagerMenuRecyclerViewAdapter(getContext(), managerMenuItems, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void generateDataForManagerMenuItems() {
        managerMenuItems.add(new ManagerMenuItem(R.drawable.ic_exit_to_app_black_24dp, "Exit", ManagerMenuItemType.EXIT));
    }

    private String getStringById(int stringId) {
        return getContext().getResources().getString(stringId);
    }

    @Override
    public void onClickPanelItem(ManagerMenuItemType managerMenuItemType) {

    }
}
