package com.tydeya.familycircle.presentation.ui.registrationpart.authorization.presentation.details;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.presentation.ui.registrationpart.authorization.presentation.abstraction.StartPresentationPagerListener;


public class StartPresentationFragment extends Fragment implements StartPresentationPagerListener {

    private ViewPager startPresentationViewPager;
    private PagerAdapter startPresentationPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private MaterialButton startButton;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_start_presentation, container, false);
        navController = NavHostFragment.findNavController(this);

        startPresentationViewPager = root.findViewById(R.id.start_presentation_view_pager);
        dotsLayout = root.findViewById(R.id.start_presentation_slide_dots);
        startButton = root.findViewById(R.id.start_presentation_start_button);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startPresentationPagerAdapter = new StartPresentationPagerAdapter(view.getContext());
        startPresentationViewPager.setAdapter(startPresentationPagerAdapter);

        startPresentationViewPager.addOnPageChangeListener(new StartPresentationOnPageListener(this));
        startButton.setOnClickListener(v -> navController.navigate(R.id.startInputNumberFragment));

        createDotsIndicator(dotsLayout);
    }

    private void createDotsIndicator(LinearLayout linearLayout) {

        dots = new TextView[startPresentationPagerAdapter.getCount()];

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(linearLayout.getContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(ContextCompat.getColor(getContext(), R.color.colorTransparentGray));
            linearLayout.addView(dots[i]);
        }
        dots[0].setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onPageSelected(int position) {
        for (TextView dot : dots) {
            dot.setTextColor(getResources().getColor(R.color.colorTransparentGray));
        }
        dots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}