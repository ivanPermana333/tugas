package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Admin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<modelAdmin> modelAdminArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = (RecyclerView) findViewById(R.id.rview);


        AndroidNetworking.get("http://192.168.1.14/tugasapi/show_user.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("STATUS");
                            if (status.equalsIgnoreCase("SUCCESS")) {
                                JSONObject payload = response.getJSONObject("PAYLOAD");
                                JSONArray data = payload.getJSONArray("DATA");
                                Log.d("Data", String.valueOf(data));
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject item = data.getJSONObject(i);

                                    modelAdmin madmin = new modelAdmin();

                                    madmin.setId(item.getString("ID"));
                                    madmin.setNama(item.getString("NAMA"));
                                    madmin.setNohp(item.getString("NOHP"));
                                    madmin.setEmail(item.getString("EMAIL"));
                                    modelAdminArrayList.add(madmin);

                                }
                                Log.d("Array", String.valueOf(modelAdminArrayList.size()));
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("anError", anError.getLocalizedMessage());
                    }
                });

        adapter = new Adapter(modelAdminArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                modelAdminArrayList.clear();
//                AndroidNetworking.post("http://192.168.1.14/tugasapi/show_user.php")
//                        .setPriority(Priority.MEDIUM)
//                        .build()
//                        .getAsJSONObject(new JSONObjectRequestListener() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    Log.d("GZS", "respon : " + response);
//
//                                    String status = response.optString("STATUS");
//                                    String message = response.optString("MESSAGE");
//                                    String sender = response.optString("SENDER");
//                                    if (status.equalsIgnoreCase("SUCCESS")) {
//                                        JSONArray orders = response.optJSONObject("PAYLOAD").optJSONArray("DATA");
//
//                                        if (orders == null) return;
//                                        System.out.println(orders.length()+"gzs");
//
//                                        for (int i = 0; i < orders.length(); i++) {
//                                            final JSONObject aData = orders.optJSONObject(i);
//                                            System.out.println(aData.get("ID")+"ayo ojo error :(");
//                                            modelAdmin item = new modelAdmin();
//                                            item.setId(aData.getString("ID"));
//                                            item.setEmail(aData.getString("EMAIL"));
//                                            item.setNama(aData.getString("NAMA"));
//                                            item.setNama(aData.getString("NOHP"));
//
//                                            modelAdminArrayList.add(item);
//                                        }adapter.notifyDataSetChanged();
//
//
//
//                                    } else {
//                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                            @Override
//                            public void onError(ANError anError) {
//                                Toast.makeText(getApplicationContext(), "ERROR LUR", Toast.LENGTH_SHORT).show();
//                                Log.d("GZS", "onError: " + anError.getErrorBody());
//                                Log.d("GZS", "onError: " + anError.getLocalizedMessage());
//                                Log.d("GZS", "onError: " + anError.getErrorDetail());
//                                Log.d("GZS", "onError: " + anError.getResponse());
//                                Log.d("GZS  ", "onError: " + anError.getErrorCode());
//                            }
//                        });
//
//                adapter = new Adapter(modelAdminArrayList);
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                recyclerView.setAdapter(adapter);
//
//                swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });

    }


}