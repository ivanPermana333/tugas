package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {

    private Context mCtx;

    private ArrayList<modelAdmin> dataList;

    public Adapter(Context mCtx, ArrayList<modelAdmin> dataList) {
        this.dataList = dataList;
        this.mCtx = mCtx;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_data, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtNoHp.setText(dataList.get(position).getNohp());
        holder.txtemail.setText(dataList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtNoHp, txtemail;


        public AdapterViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.tvNama);
            txtNoHp = (TextView) itemView.findViewById(R.id.txtnohp);
            txtemail = (TextView) itemView.findViewById(R.id.txtemail1);

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

