package com.tydeya.familycircle.personviewpage;

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

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.DateRefactoring;
import com.tydeya.familycircle.family.member.OldFamilyMember;
import com.tydeya.familycircle.user.User;


public class FamilyMemberViewFragment extends Fragment {

    private TextView nameText;
    private TextView birthdateText;
    private int personPosition;
    private OldFamilyMember oldFamilyMember;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_family_member_view, container, false);

        nameText = root.findViewById(R.id.family_member_view_name_text);
        birthdateText = root.findViewById(R.id.family_member_view_birthdate_text);
        toolbar = root.findViewById(R.id.family_member_view_toolbar);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        personPosition = getArguments().getInt("personPosition", -1);
        if (personPosition == -1) {
            throw new IllegalArgumentException("personPosition is not found");
        }

        oldFamilyMember = User.getInstance().getFamily().getOldFamilyMembers().get(personPosition);

        nameText.setText(oldFamilyMember.getName());
        if (oldFamilyMember.getDescription() != null && oldFamilyMember.getDescription().getBirthDate() != null) {
            birthdateText.setText(DateRefactoring.getDateLocaleText(oldFamilyMember.getDescription().getBirthDate()));
        } else {
            birthdateText.setText(getResources().getString(R.string.family_member_view_datebirthd_not_known));
        }

        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

    }
}
