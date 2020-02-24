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
import com.tydeya.familycircle.domain.familymember.FamilyMember;

import javax.inject.Inject;


public class FamilyMemberPersonPage extends Fragment {

    private TextView nameText;
    private TextView birthdateText;
    private int personPosition;
    private FamilyMember member;
    private Toolbar toolbar;

    @Inject
    FamilyInteractor familyInteractor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().injectFragment(this);

        View root = inflater.inflate(R.layout.fragment_family_member_view, container, false);

        nameText = root.findViewById(R.id.family_member_view_name_text);
        birthdateText = root.findViewById(R.id.family_member_view_birthdate_text);
        toolbar = root.findViewById(R.id.family_member_view_toolbar);

        setCurrentData();

        return root;
    }

    private void setCurrentData() {

        assert getArguments() != null;
        personPosition = getArguments().getInt("personPosition", -1);
        if (personPosition == -1) {
            throw new IllegalArgumentException("personPosition is not found");
        }

        member = familyInteractor.getActualFamily().getFamilyMembers().get(personPosition);


        nameText.setText(member.getDescription().getName());
        if (member.getDescription() != null && member.getDescription().getBirthDate() != null) {
            birthdateText.setText(member.getDescription().getBirthDate());
        } else {
            birthdateText.setText(getResources().getString(R.string.family_member_view_datebirthd_not_known));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

    }
}
