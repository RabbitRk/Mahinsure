package com.rabbitt.mahinsure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rabbitt.mahinsure.R;
import com.rabbitt.mahinsure.model.grid;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder>{

    private Context context;
    private List<grid> dataModelArrayList;
    private OnRecyleItemListener mOnRecycleItemListener;

    public GridAdapter(Context context, List<grid> grid, OnRecyleItemListener onRecyleItemListener) {
        this.context = context;
        this.dataModelArrayList = grid;
        this.mOnRecycleItemListener = onRecyleItemListener;

    }

    @NonNull
    @Override
    public GridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v, mOnRecycleItemListener); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        grid dataModel = dataModelArrayList.get(position);
        holder.name.setText(String.valueOf(dataModel.getEvent_name()));
        holder.image.setImageBitmap(dataModel.getImage());

        Glide.with(context)
                .load(dataModel.getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // init the item view's
        TextView name;
        ImageView image;
        OnRecyleItemListener onRecyleItemListener;

        public MyViewHolder(View itemView, OnRecyleItemListener onRecyleItemListener) {
            super(itemView);
            this.onRecyleItemListener = onRecyleItemListener;
            name = itemView.findViewById(R.id.texthead);
            image = itemView.findViewById(R.id.image_taken);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyleItemListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface OnRecyleItemListener
    {
        void OnItemClick(int position);
    }
}
