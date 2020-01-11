package com.tydeya.familycircle.firststart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    // This method is processing page switch
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.start_presentation_slide_page,
                container, false);

        ImageView image = view.findViewById(R.id.start_presentation_slide_image);
        TextView title = view.findViewById(R.id.start_presentation_slide_title);
        TextView text = view.findViewById(R.id.start_presentation_slide_text);

        image.setImageResource(this.presentationImages[position]);
        title.setText(this.presentationTitles[position]);
        text.setText(this.presentationTexts[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ViewGroup) object);
    }
}
