package com.tydeya.familycircle.presentation.ui.registrationpart.authorization.presentation.details;

import androidx.viewpager.widget.ViewPager;

import com.tydeya.familycircle.presentation.ui.registrationpart.authorization.presentation.abstraction.StartPresentationPagerListener;

public class StartPresentationOnPageListener implements ViewPager.OnPageChangeListener {

    private StartPresentationPagerListener startPresentationPagerListener;

    StartPresentationOnPageListener(StartPresentationPagerListener startPresentationPagerListener) {
        this.startPresentationPagerListener = startPresentationPagerListener;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        startPresentationPagerListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
