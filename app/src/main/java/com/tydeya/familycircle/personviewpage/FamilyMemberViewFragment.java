package com.tydeya.familycircle.personviewpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tydeya.familycircle.R;


public class FamilyMemberViewFragment extends Fragment {

    private TextView nameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_family_member_view, container, false);
        //nameText = root.findViewById(R.id.family_member_view_name);
        //nameText.setSelected(true);
        return root;
    }

}
