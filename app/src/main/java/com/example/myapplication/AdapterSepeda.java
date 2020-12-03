package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSepeda extends RecyclerView.Adapter<AdapterSepeda.AdapterViewHolder> {
    private Context mCtx;

    private ArrayList<modelSepeda> dataList;
    public AdapterSepeda(Context mCtx, ArrayList<modelSepeda> dataList) {
        this.dataList = dataList;
        this.mCtx = mCtx;
    }
    @NonNull
    @Override
    public AdapterSepeda.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_data2, parent, false);
        return new AdapterSepeda.AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.txtjenis.setText(dataList.get(position).getJenis());
        holder.txtkode.setText(dataList.get(position).getKode());
        holder.txtmerk.setText(dataList.get(position).getMerk());
        holder.txthargasewa.setText(dataList.get(position).getHargasewa());
        holder.txtwarna.setText(dataList.get(position).getWarna());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void clearData() {
        int size = this.dataList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.dataList.remove(0);
            }
        }
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView txtjenis, txtkode, txtmerk, txthargasewa, txtwarna;


        public AdapterViewHolder(View itemView) {
            super(itemView);
            txtjenis = (TextView) itemView.findViewById(R.id.tvjenis);
            txtkode = (TextView) itemView.findViewById(R.id.tvkode);
            txtmerk = (TextView) itemView.findViewById(R.id.tvmerk);
            txthargasewa = (TextView) itemView.findViewById(R.id.tvhargasewa);
            txtwarna = (TextView) itemView.findViewById(R.id.tvwarna);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mCtx, UserActivity.class);
                    i.putExtra("EXTRA_CUSTOMER", dataList.get(getAdapterPosition()));
                    mCtx.startActivity(i);
                }
            });
        }
    }
}
