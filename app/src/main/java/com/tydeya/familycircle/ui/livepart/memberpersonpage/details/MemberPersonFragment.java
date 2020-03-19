package com.tydeya.familycircle.ui.livepart.memberpersonpage.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.domain.familyassistant.abstraction.FamilyAssistant;
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl;
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyInteractorCallback;
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.data.familymember.dto.FamilyMemberDto;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonPresenter;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonView;

import javax.inject.Inject;


public class MemberPersonFragment extends Fragment implements MemberPersonView, FamilyInteractorCallback {

    private TextView nameText;
    private TextView birthdateText;
    private TextView zodiacSignText;
    private Toolbar toolbar;
    private MemberPersonPresenter presenter;
    private ImageButton settingsButton;

    private NavController navController;

    @Inject
    FamilyInteractor familyInteractor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().injectFragment(this);

        View root = inflater.inflate(R.layout.fragment_family_member_view, container, false);

        nameText = root.findViewById(R.id.family_member_view_name_text);
        birthdateText = root.findViewById(R.id.family_member_view_birthdate_text);
        toolbar = root.findViewById(R.id.family_member_view_toolbar);
        zodiacSignText = root.findViewById(R.id.family_member_view_zodiac_sign);
        settingsButton = root.findViewById(R.id.family_member_view_settings);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new MemberPersonPresenterImpl(this, getFamilyMember());

        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

        navController = NavHostFragment.findNavController(this);
    }

    private FamilyMember getFamilyMember() {
        FamilyAssistant familyAssistant = new FamilyAssistantImpl(familyInteractor.getActualFamily());
        return familyAssistant.getUserByPhone(getArguments().getString("personFullPhoneNumber", ""));
    }

    @Override
    public void setCurrentData(FamilyMemberDto dto) {
        nameText.setText(dto.getName());

        birthdateText.setText(dto.getBirthDate().equals("") ?
                getResources().getString(R.string.family_member_view_datebirthd_not_known) :
                dto.getBirthDate());

        zodiacSignText.setText(dto.getZodiacSign());

    }

    @Override
    public void setManagerMode(boolean managerMode) {
        if (managerMode) {
            settingsButton.setVisibility(View.VISIBLE);
            settingsButton.setEnabled(true);
            settingsButton.setOnClickListener(this::showPopUpSettingsMenu);
        } else {
            settingsButton.setVisibility(View.INVISIBLE);
            settingsButton.setEnabled(false);
        }
    }

    private void showPopUpSettingsMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.settings_person_page_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit_person_page:
                    Bundle bundle = new Bundle();
                    bundle.putString("personFullPhoneNumber", getFamilyMember().getFullPhoneNumber());
                    navController.navigate(R.id.memberPersonEditFragment);
                    return true;
                case R.id.add_post_to_person_page:
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        familyInteractor.subscribe(this);
    }

    @Override
    public void memberDataUpdated() {
        setCurrentData(new FamilyMemberDto(getFamilyMember()));
    }

    @Override
    public void onPause() {
        super.onPause();
        familyInteractor.unsubscribe(this);
    }
}
