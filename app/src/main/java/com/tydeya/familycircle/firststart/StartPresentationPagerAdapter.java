package com.tydeya.familycircle.firststart;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tydeya.familycircle.R;

class StartPresentationPagerAdapter extends PagerAdapter {

    // Data for adapter
    private Context context;

    // Information for pages of slide
    private int[] presentationImages = {
            R.drawable.ic_start_presentation_happy_foreground,
            R.drawable.ic_start_presentation_achievement_foreground,
            R.drawable.ic_start_presentation_productivity_foreground};

    private int[] presentationTitles = {
            R.string.start_presentation_happy_family_title,
            R.string.start_presentation_achievement_title,
            R.string.start_presentation_productivity_title};

    private int[] presentationTexts = {
            R.string.start_presentation_happy_family_text,
            R.string.start_presentation_achievement_text,
            R.string.start_presentation_productivity_text};

    StartPresentationPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return presentationTexts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
