package com.tydeya.familycircle.presentation.ui.managerpart.menu.details.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;

import java.util.ArrayList;

public class ManagerMenuRecyclerViewAdapter extends RecyclerView.Adapter<ManagerMenuItemViewHolder> {

    private ArrayList<ManagerMenuItem> managerMenuItems;
    private Context context;
    private OnClickManagerMenuItemListener onClickManagerMenuItemListener;

    public ManagerMenuRecyclerViewAdapter(Context context, ArrayList<ManagerMenuItem> managerMenuItems,
                                          OnClickManagerMenuItemListener onClickManagerMenuItemListener) {
        this.managerMenuItems = managerMenuItems;
        this.context = context;
        this.onClickManagerMenuItemListener = onClickManagerMenuItemListener;
    }

    @NonNull
    @Override
    public ManagerMenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ManagerMenuItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_manager_menu,
                parent, false), onClickManagerMenuItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerMenuItemViewHolder holder, int position) {
        ManagerMenuItem managerMenuItem = managerMenuItems.get(position);
        holder.setItemType(managerMenuItem.getItemType());
        holder.setImageView(managerMenuItem.getImageId());
        holder.setTitleText(managerMenuItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return managerMenuItems.size();
    }
}
