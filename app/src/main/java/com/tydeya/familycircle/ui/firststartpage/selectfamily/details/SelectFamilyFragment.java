package com.tydeya.familycircle.ui.firststartpage.selectfamily.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.tydeya.familycircle.MainActivity;
import com.tydeya.familycircle.R;


public class SelectFamilyFragment extends Fragment {

    private MaterialButton materialButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_family, container, false);

        materialButton = root.findViewById(R.id.select_family_next_button);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getActivity() != null;

        materialButton.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }
}
