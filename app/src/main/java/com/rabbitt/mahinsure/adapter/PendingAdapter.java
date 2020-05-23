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
import com.rabbitt.mahinsure.model.insp_loop;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.holder>{

    private static final String TAG = "maluk";
    private List<insp_loop> dataModelArrayList;
    private HomePage context;
    private OnRecyleItemListener mOnRecycleItemListener;

    public PendingAdapter(List<insp_loop> dataModelArrayList, HomePage context, OnRecyleItemListener mOnRecycleItemListener) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
        this.mOnRecycleItemListener = mOnRecycleItemListener;
    }

    @NonNull
    @Override
    public PendingAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, null);
        return new holder(view, mOnRecycleItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAdapter.holder holder, int position) {
        insp_loop dataModel = dataModelArrayList.get(position);

        holder.template.setBackgroundResource(dataModel.getColor() == 1 ? R.color.warning : R.color.colorPrimary);

        holder.ref_no.setText(dataModel.getRef_no());
        holder.v_no.setText(dataModel.getV_no());
        holder.cus_name.setText(dataModel.getCus_name());
        holder.date.setText(dataModel.getDate());
        holder.month.setText(dataModel.getMonth());
        holder.year.setText(dataModel.getYear());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ref_no, v_no, cus_name;
        TextView date, month, year;
        View template;
        OnRecyleItemListener onRecyleItemListener;

        public holder(@NonNull View itemView, OnRecyleItemListener mOnRecycleItemListener) {
            super(itemView);
            this.onRecyleItemListener = mOnRecycleItemListener;
            ref_no = itemView.findViewById(R.id.refno);
            v_no = itemView.findViewById(R.id.vehi_txt);
            cus_name = itemView.findViewById(R.id.cus_txt);
            year = itemView.findViewById(R.id.year);
            month = itemView.findViewById(R.id.month);
            date = itemView.findViewById(R.id.day);
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