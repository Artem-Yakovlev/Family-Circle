package com.tydeya.familycircle.firststart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.tydeya.familycircle.R;


public class StartPresentationFragment extends Fragment {

    private View root;
    private ViewPager startPresentationViewPager;
    private StartPresentationPagerAdapter startPresentationPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_start_presentation, container, false);
        startPresentationViewPager = root.findViewById(R.id.start_presentation_view_pager);
        startPresentationPagerAdapter = new StartPresentationPagerAdapter(root.getContext());
        startPresentationViewPager.setAdapter(startPresentationPagerAdapter);

        return root;
    }

}