package com.tydeya.familycircle.registrationlogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tydeya.familycircle.R;


public class RegistrationFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        // getArguments().getString("EmailFromLogin", "")

        return rootView;

    }


}
