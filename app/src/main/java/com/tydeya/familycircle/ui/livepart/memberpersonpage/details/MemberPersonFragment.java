package com.tydeya.familycircle.ui.livepart.memberpersonpage.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.data.familymember.dto.FamilyMemberDto;
import com.tydeya.familycircle.domain.familyassistant.abstraction.FamilyAssistant;
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl;
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyInteractorCallback;
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.domain.onlinemanager.details.OnlineInteractorImpl;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonPresenter;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonView;

import javax.inject.Inject;

import static com.tydeya.familycircle.utils.DipKt.getDp;


public class MemberPersonFragment extends Fragment implements MemberPersonView, FamilyInteractorCallback {

    private ImageView profileImage;
    private TextView nameText;
    private TextView onlineStatusText;
    private TextView birthdateText;
    private TextView zodiacSignText;
    private TextView workPlaceText;
    private TextView studyPlaceText;

    private Toolbar toolbar;
    private ImageButton settingsButton;

    private NavController navController;

    @Inject
    FamilyInteractor familyInteractor;

    @Inject
    OnlineInteractorImpl onlineInteractor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().injectFragment(this);

        View root = inflater.inflate(R.layout.fragment_family_member_view, container, false);

        toolbar = root.findViewById(R.id.family_member_view_toolbar);
        settingsButton = root.findViewById(R.id.family_member_view_settings);

        nameText = root.findViewById(R.id.family_member_view_name_text);
        profileImage = root.findViewById(R.id.family_view_photo);
        onlineStatusText = root.findViewById(R.id.family_member_view_online_text);
        birthdateText = root.findViewById(R.id.family_member_view_birthdate_text);
        zodiacSignText = root.findViewById(R.id.family_member_view_zodiac_sign);
        studyPlaceText = root.findViewById(R.id.family_member_view_study_place);
        workPlaceText = root.findViewById(R.id.family_member_view_work_place);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MemberPersonPresenter presenter = new MemberPersonPresenterImpl(this, getFamilyMember());

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
        assert getContext() != null;
        nameText.setText(dto.getName());

        if (!dto.getImageAddress().equals("")) {
            profileImage.setPadding(0,0,0,0);
            Glide.with(getContext()).load(dto.getImageAddress()).into(profileImage);
        } else {
            int dpForPadding = getDp(getContext(), 20);
            profileImage.setPadding(dpForPadding,dpForPadding,dpForPadding,dpForPadding);
        }

        if (dto.getBirthDate().equals("")) {
            birthdateText.setText(getResources()
                    .getString(R.string.family_member_view_date_of_birth_not_known));

            zodiacSignText.setVisibility(View.GONE);
        } else {
            birthdateText.setText(dto.getBirthDate());
            zodiacSignText.setText(dto.getZodiacSign());
            zodiacSignText.setVisibility(View.VISIBLE);
        }

        if (dto.getStudyPlace().equals("")) {
            studyPlaceText.setVisibility(View.GONE);
        } else {
            studyPlaceText.setVisibility(View.VISIBLE);
            studyPlaceText.setText(dto.getStudyPlace());
        }

        if (dto.getWorkPlace().equals("")) {
            workPlaceText.setVisibility(View.GONE);
        } else {
            workPlaceText.setVisibility(View.VISIBLE);
            workPlaceText.setText(dto.getWorkPlace());
        }

        if (onlineInteractor.isUserOnline(dto.getPhone())) {
            onlineStatusText.setText(getContext().getString(R.string.online_text));
            onlineStatusText.setBackgroundColor(getResources().getColor(R.color.colorOnlineGreen));
        } else {
            onlineStatusText.setText(getContext().getString(R.string.offline_text));
            onlineStatusText.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }

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
