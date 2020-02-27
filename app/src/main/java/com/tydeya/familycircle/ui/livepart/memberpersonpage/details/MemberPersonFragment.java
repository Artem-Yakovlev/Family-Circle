package com.tydeya.familycircle.ui.livepart.memberpersonpage.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.domain.familymember.dto.FamilyMemberDto;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonPresenter;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonView;

import javax.inject.Inject;


public class MemberPersonFragment extends Fragment implements MemberPersonView {

    private TextView nameText;
    private TextView birthdateText;
    private TextView zodiacSignText;
    private Toolbar toolbar;
    private MemberPersonPresenter presenter;


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

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new MemberPersonPresenterImpl(this, familyInteractor.getActualFamily()
                .getFamilyMembers().get(getArguments() != null ? getArguments().getInt("personPosition") : 0));

        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

    }

    @Override
    public void setCurrentData(FamilyMemberDto dto) {
        nameText.setText(dto.getName());

        birthdateText.setText(dto.getBirthDate().equals("") ?
                getResources().getString(R.string.family_member_view_datebirthd_not_known) :
                dto.getBirthDate());

        zodiacSignText.setText(dto.getZodiacSign());

    }
}
