package com.tydeya.familycircle.firststart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.tydeya.familycircle.R;


public class StartInputNumberFragment extends Fragment {

    private MaterialButton nextButton;
    private View root;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_start_input_number, container, false);

        navController = NavHostFragment.findNavController(this);

        nextButton = root.findViewById(R.id.start_input_phone_number_next);

        nextButton.setOnClickListener(view -> navController.navigate(R.id.getCodeFromSmsFragment));
        return root;
    }
}