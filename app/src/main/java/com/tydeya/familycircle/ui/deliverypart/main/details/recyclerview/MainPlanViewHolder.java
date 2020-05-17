package com.tydeya.familycircle.ui.deliverypart.main.details.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;

public class MainPlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnClickMainPlanItemListener onClickMainPlanItemListener;
    private TextView titleText;
    private TextView descriptionText;
    private ImageView imageView;
    private MainPlanItemType itemType;

    MainPlanViewHolder(@NonNull View itemView, OnClickMainPlanItemListener onClickMainPlanItemListener) {
        super(itemView);
        this.onClickMainPlanItemListener = onClickMainPlanItemListener;
        itemView.setOnClickListener(this);
        findAllViewsById(itemView);
    }

    private void findAllViewsById(View itemView) {
        titleText = itemView.findViewById(R.id.main_plan_card_item_title);
        descriptionText = itemView.findViewById(R.id.main_plan_card_item_text);
        imageView = itemView.findViewById(R.id.main_plan_card_item_image);
    }

    void setTitleText(String title) {
        titleText.setText(title);
    }

    void setDescriptionText(String description) {
        descriptionText.setText(description);
    }

    public void setImageView(int imageId) {
        Glide.with(imageView.getContext())
                .load(imageId)
                .into(imageView);
    }

    public void setType(MainPlanItemType itemType) {
        this.itemType = itemType;
    }

    @Override
    public void onClick(View v) {
        onClickMainPlanItemListener.onMainPlanItemClick(itemType);
    }
}
