package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Item> items;

    public Adapter() {
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(items.get(position).getItemName());
        setFadeAnimation(holder.itemView);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
        boolean equals = areItemListsEquals(items, this.items);
        this.items = items;
        if (!equals)
            notifyDataSetChanged();
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
        notifyItemRangeChanged(items.size() - 1, 1);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public List<Item> getItems() {
        return items;
    }

    private boolean areItemListsEquals(List<Item> l1, List<Item> l2){
        if (l1.size() != l2.size())
            return false;

        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).getId().equals(l2.get(i).getId()))
                return false;
        }

        return true;
    }
}
