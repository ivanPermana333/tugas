package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sepeda extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private AdapterSepeda adapterS;
    private ArrayList<modelSepeda> modelSepedaArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepeda);
        recyclerView = (RecyclerView) findViewById(R.id.rview1);
        swipeRefresh = findViewById(R.id.swipe_view1);

        FloatingActionButton floatingActionButton=findViewById(R.id.fab2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fab1 = new Intent(getApplicationContext(),TambahSepeda.class);
                startActivity(fab1);

            }
        });
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

    private void show() {
        adapterS = new AdapterSepeda(Sepeda.this, modelSepedaArrayList);
        recyclerView.setAdapter(adapterS );
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        getCustomer();
        adapterS.notifyDataSetChanged();
        show();
    }
    private void getCustomer (){
        swipeRefresh.setRefreshing(true);
        AndroidNetworking.get("http://192.168.43.92/tugasapi/show_databarang.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        swipeRefresh.setRefreshing(false);
                        if (adapterS != null) {
                            adapterS.clearData();
                            adapterS.notifyDataSetChanged();
                        }
                        if (modelSepedaArrayList != null)  modelSepedaArrayList.clear();
                        try {

                            String status = response.getString("STATUS");
                            if (status.equalsIgnoreCase("SUCCESS")) {
                                JSONObject payload = response.getJSONObject("PAYLOAD");
                                JSONArray data = payload.getJSONArray("DATA");
                                Log.d("data", String.valueOf(data));
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject item = data.getJSONObject(i);

                                    modelSepeda msepeda = new modelSepeda();

                                    msepeda.setId(item.getString("ID"));
                                    msepeda.setJenis(item.getString("jenis"));
                                    msepeda.setKode(item.getString("kode"));
                                    msepeda.setMerk(item.getString("merk"));
                                    msepeda.setWarna(item.getString("warna"));
                                    msepeda.setHargasewa(item.getString("hargasewa"));
                                    modelSepedaArrayList.add(msepeda);

                                }
                                show();
                                Log.d("Array", String.valueOf(modelSepedaArrayList.size()));
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