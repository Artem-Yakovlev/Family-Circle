package com.tydeya.familycircle.ui.firststartpage.authorization.details;

import androidx.viewpager.widget.ViewPager;

import com.tydeya.familycircle.ui.firststartpage.authorization.abstraction.StartPresentationPagerListener;

public class StartPresentationOnPageListener implements ViewPager.OnPageChangeListener {

    private StartPresentationPagerListener startPresentationPagerListener;

    public StartPresentationOnPageListener(StartPresentationPagerListener startPresentationPagerListener) {
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
