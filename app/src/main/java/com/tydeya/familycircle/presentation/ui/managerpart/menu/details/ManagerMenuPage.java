package com.tydeya.familycircle.presentation.ui.managerpart.menu.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.presentation.ui.managerpart.menu.abstraction.ManagerMenuView;
import com.tydeya.familycircle.presentation.ui.managerpart.menu.details.recyclerview.ManagerMenuItem;
import com.tydeya.familycircle.presentation.ui.managerpart.menu.details.recyclerview.ManagerMenuItemType;
import com.tydeya.familycircle.presentation.ui.managerpart.menu.details.recyclerview.ManagerMenuRecyclerViewAdapter;
import com.tydeya.familycircle.presentation.ui.managerpart.menu.details.recyclerview.OnClickManagerMenuItemListener;
import com.tydeya.familycircle.presentation.ui.registrationpart.FirstStartActivity;

import java.util.ArrayList;

import static com.tydeya.familycircle.data.constants.Application.CURRENT_FAMILY_ID;
import static com.tydeya.familycircle.data.constants.Application.REGISTRATION_MODE;
import static com.tydeya.familycircle.data.constants.Application.REGISTRATION_ONLY_FAMILY_SELECTION;
import static com.tydeya.familycircle.data.constants.Application.SHARED_PREFERENCE_USER_SETTINGS;
import static com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_FULL_PHONE_NUMBER;


public class ManagerMenuPage extends Fragment implements OnClickManagerMenuItemListener, ManagerMenuView {

    private RecyclerView recyclerView;
    private ArrayList<ManagerMenuItem> managerMenuItems = new ArrayList<>();
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_panel_page, container, false);
        recyclerView = root.findViewById(R.id.main_panel_recycler_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        generateDataForManagerMenuItems();
        ManagerMenuRecyclerViewAdapter recyclerViewAdapter = new ManagerMenuRecyclerViewAdapter(getContext(), managerMenuItems, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void generateDataForManagerMenuItems() {
        managerMenuItems.clear();

        managerMenuItems.add(new ManagerMenuItem(
                R.drawable.ic_account_circle_blue_24dp,
                getString(R.string.manager_menu_item_your_profile_title),
                ManagerMenuItemType.PROFILE));

        managerMenuItems.add(new ManagerMenuItem(
                R.drawable.ic_cached_black_24dp,
                getString(R.string.manager_menu_item_select_family_title),
                ManagerMenuItemType.SELECT_FAMILY));

        managerMenuItems.add(new ManagerMenuItem(
                R.drawable.ic_exit_to_app_blue_24dp,
                getString(R.string.manager_menu_item_sign_out_title),
                ManagerMenuItemType.EXIT));
    }

    /**
     * Recycler view callbacks
     */

    @Override
    public void onClickPanelItem(ManagerMenuItemType managerMenuItemType) {
        switch (managerMenuItemType) {
            case PROFILE:
            case SELECT_FAMILY:
                openPage(managerMenuItemType);
                break;
            case EXIT:
                signOut();
                break;
            default:
        }
    }

    @Override
    public void signOut() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(recyclerView.getContext());

        alertDialogBuilder.setTitle(R.string.manager_menu_item_sign_out_title);
        alertDialogBuilder.setMessage(R.string.manager_menu_sign_out_dialog_message);

        alertDialogBuilder.setPositiveButton(R.string.yes_text,
                (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getContext(), FirstStartActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                });
        alertDialogBuilder.setNegativeButton(R.string.no_text,
                null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void openPage(ManagerMenuItemType managerMenuItemType) {
        switch (managerMenuItemType) {
            case PROFILE:
                Bundle bundle = new Bundle();
                bundle.putString(BUNDLE_FULL_PHONE_NUMBER, FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                navController.navigate(R.id.memberPersonFragment, bundle);
                break;
            case SELECT_FAMILY:
                requireActivity()
                        .getSharedPreferences(SHARED_PREFERENCE_USER_SETTINGS, Context.MODE_PRIVATE)
                        .edit().putString(CURRENT_FAMILY_ID, "").apply();
                Intent intent = new Intent(requireContext(), FirstStartActivity.class);
                intent.putExtra(REGISTRATION_MODE, REGISTRATION_ONLY_FAMILY_SELECTION);
                startActivity(intent);
                requireActivity().finish();
                break;
        }
    }
}
