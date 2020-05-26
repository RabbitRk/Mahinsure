package com.rabbitt.mahinsure.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rabbitt.mahinsure.DetailActivity;
import com.rabbitt.mahinsure.HomePage;
import com.rabbitt.mahinsure.R;
import com.rabbitt.mahinsure.model.demo;
import com.rabbitt.mahinsure.model.inspection;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.holder>{

    private static final String TAG = "maluk";
    private List<demo> dataModelArrayList;
    private DetailActivity context;
    private OnRecyleItemListener mOnRecycleItemListener;

    public DetailAdapter(List<demo> dataModelArrayList, DetailActivity context, OnRecyleItemListener mOnRecycleItemListener) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
        this.mOnRecycleItemListener = mOnRecycleItemListener;
    }

    @NonNull
    @Override
    public DetailAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_card, null);
        return new holder(view, mOnRecycleItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.holder holder, int position) {
        demo dataModel = dataModelArrayList.get(position);

        holder.lbl.setText(dataModel.getLabel());
        holder.data.setText(dataModel.getData());

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView lbl, data;
        OnRecyleItemListener onRecyleItemListener;

        public holder(@NonNull View itemView, OnRecyleItemListener mOnRecycleItemListener) {
            super(itemView);
            this.onRecyleItemListener = mOnRecycleItemListener;

            lbl = itemView.findViewById(R.id.label);
            data = itemView.findViewById(R.id.data);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: Adapter");
            onRecyleItemListener.OnItemClickFinised(getAdapterPosition());
        }
    }

    public interface OnRecyleItemListener
    {
        void OnItemClickFinised(int position);
    }
}