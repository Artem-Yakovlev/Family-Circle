package com.tydeya.familycircle.presentation.ui.managerpart.menu.details.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;

class ManagerMenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView;
    private TextView titleText;
    private ManagerMenuItemType itemType;
    private OnClickManagerMenuItemListener onClickManagerMenuItemListener;

    ManagerMenuItemViewHolder(@NonNull View itemView, OnClickManagerMenuItemListener onClickMainPlanItemListener) {
        super(itemView);
        this.onClickManagerMenuItemListener = onClickMainPlanItemListener;
        imageView = itemView.findViewById(R.id.panel_common_item_image);
        titleText = itemView.findViewById(R.id.panel_common_item_title);
        itemView.setOnClickListener(this);
    }

    void setTitleText(String title) {
        titleText.setText(title);
    }

    void setImageView(int imageId) {
        Glide.with(imageView.getContext())
                .load(imageId)
                .into(imageView);
    }

    void setItemType(ManagerMenuItemType itemType) {
        this.itemType = itemType;
    }

    @Override
    public void onClick(View v) {
        onClickManagerMenuItemListener.onClickPanelItem(itemType);
    }
}
