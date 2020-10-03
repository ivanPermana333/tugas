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

public class Admin extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<modelAdmin> modelAdminArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = (RecyclerView) findViewById(R.id.rview);
        swipeRefresh = findViewById(R.id.swipe_view);


        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.post(new Runnable() {
            private void doNothing() {

            }

            @Override
            public void run() {
                getCustomer();
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onRefresh() {
        getCustomer();
    }

    public void show(){
        adapter = new Adapter(Admin.this, modelAdminArrayList);
        recyclerView.setAdapter(adapter );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getCustomer();
        adapter.notifyDataSetChanged();
        show();
    }

    private void getCustomer (){
        swipeRefresh.setRefreshing(true);
        AndroidNetworking.get("http://192.168.43.92/tugasapi/show_user.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        swipeRefresh.setRefreshing(false);
                        if (adapter != null) {
                            adapter.clearData();
                            adapter.notifyDataSetChanged();
                        }
                        if (modelAdminArrayList != null)  modelAdminArrayList.clear();
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
                                    madmin.setNoktp(item.getString("NOKTP"));
                                    madmin.setAlamat(item.getString("ALAMAT"));
                                    modelAdminArrayList.add(madmin);

                                }
                                show();
                                Log.d("Array", String.valueOf(modelAdminArrayList.size()));
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
    }
}