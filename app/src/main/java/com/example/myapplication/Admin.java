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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = (RecyclerView) findViewById(R.id.rview);
        AndroidNetworking.get("http://192.168.6.182/tugasapi/show_user.php")
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

        adapter = new Adapter(Admin.this, modelAdminArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

}