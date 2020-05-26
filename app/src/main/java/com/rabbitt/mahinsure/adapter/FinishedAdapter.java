package com.rabbitt.mahinsure.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rabbitt.mahinsure.HomePage;
import com.rabbitt.mahinsure.R;
import com.rabbitt.mahinsure.model.inspection;

import java.util.List;

public class FinishedAdapter extends RecyclerView.Adapter<FinishedAdapter.holder>{

    private static final String TAG = "maluk";
    private List<inspection> dataModelArrayList;
    private HomePage context;
    private OnRecyleItemListener mOnRecycleItemListener;

    public FinishedAdapter(List<inspection> dataModelArrayList, HomePage context, OnRecyleItemListener mOnRecycleItemListener) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
        this.mOnRecycleItemListener = mOnRecycleItemListener;
    }

    @NonNull
    @Override
    public FinishedAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, null);
        return new holder(view, mOnRecycleItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FinishedAdapter.holder holder, int position) {
        inspection dataModel = dataModelArrayList.get(position);

        holder.template.setBackgroundResource(R.color.success);

        holder.ref_no.setText(dataModel.getRef_no());
        holder.v_no.setText(dataModel.getV_no());
        holder.cus_name.setText(dataModel.getCus_name());
        holder.date.setText(dataModel.getDate());
        holder.month.setText(dataModel.getMonth());
        holder.year.setText(dataModel.getYear());

        holder.sub.setVisibility(View.VISIBLE);
        holder.submitted.setText(String.valueOf(dataModel.getSub()));

        holder.app.setVisibility(View.VISIBLE);
        holder.approved.setText(String.valueOf(dataModel.getApp()));

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ref_no, v_no, cus_name;
        TextView date, month, year;
        TextView approved, submitted;
        View template;
        ViewGroup sub, app;
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

            approved = itemView.findViewById(R.id.approved);
            submitted = itemView.findViewById(R.id.submited_on);

            sub = itemView.findViewById(R.id.submit_layout);
            app = itemView.findViewById(R.id.approved_layout);

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