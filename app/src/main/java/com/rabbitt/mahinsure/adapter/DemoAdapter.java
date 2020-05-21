package com.rabbitt.mahinsure.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rabbitt.mahinsure.HomePage;
import com.rabbitt.mahinsure.R;
import com.rabbitt.mahinsure.model.demo;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.holder>{

    private static final String TAG = "maluk";
    private List<demo> dataModelArrayList;
    private HomePage context;
    private OnRecyleItemListener mOnRecycleItemListener;

    public DemoAdapter(List<demo> dataModelArrayList, HomePage context, OnRecyleItemListener mOnRecycleItemListener) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
        this.mOnRecycleItemListener = mOnRecycleItemListener;
    }

    @NonNull
    @Override
    public DemoAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, null);
        return new holder(view, mOnRecycleItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoAdapter.holder holder, int position) {
        demo dataModel = dataModelArrayList.get(position);
        holder.template.setBackgroundColor(Color.GREEN);
        holder.demo.setText(dataModel.getData());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView demo;
        View template;
        OnRecyleItemListener onRecyleItemListener;
        public holder(@NonNull View itemView, OnRecyleItemListener mOnRecycleItemListener) {
            super(itemView);
            this.onRecyleItemListener = mOnRecycleItemListener;
            demo = itemView.findViewById(R.id.refno);
            template = itemView.findViewById(R.id.tem_plate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: Adapter");
            onRecyleItemListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface OnRecyleItemListener
    {
        void OnItemClick(int position);
    }
}
