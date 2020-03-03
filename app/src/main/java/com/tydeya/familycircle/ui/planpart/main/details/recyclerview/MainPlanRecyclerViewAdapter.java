package com.tydeya.familycircle.ui.planpart.main.details.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;

import java.util.ArrayList;

public class MainPlanRecyclerViewAdapter extends RecyclerView.Adapter<MainPlanViewHolder> {

    private Context context;
    private ArrayList<MainPlanItem> mainPlanItems;
    private OnClickMainPlanItemListener onClickMainPlanItemListener;

    public MainPlanRecyclerViewAdapter(Context context, ArrayList<MainPlanItem> mainPlanItems,
                                       OnClickMainPlanItemListener onClickMainPlanItemListener) {

        this.onClickMainPlanItemListener = onClickMainPlanItemListener;
        this.context = context;
        this.mainPlanItems = mainPlanItems;
    }


    @NonNull
    @Override
    public MainPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainPlanViewHolder(LayoutInflater.from(context).inflate(R.layout.main_plan_card_layout,
                parent, false), onClickMainPlanItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPlanViewHolder holder, int position) {
        MainPlanItem mainPlanItem = mainPlanItems.get(position);
        holder.setTitleText(mainPlanItem.getTitle());
        holder.setDescriptionText(mainPlanItem.getText());
        holder.setType(mainPlanItem.getItemType());
        holder.setImageView(mainPlanItem.getIconId());
    }

    @Override
    public int getItemCount() {
        return mainPlanItems.size();
    }
}

