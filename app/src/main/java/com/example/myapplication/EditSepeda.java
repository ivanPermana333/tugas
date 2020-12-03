package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditSepeda extends AppCompatActivity {
    private TextView txtkode, txtjenis, txtmerk, txtwarna, txthargasewa;
    private Button btnEdit;
    private SharedPreferences sharedPreferences;
    private  String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sepeda);

        txtkode = findViewById(R.id.txtkode1);
        txtjenis = findViewById(R.id.txtjenis1);
        txtmerk = findViewById(R.id.txtmerk1);
        txtwarna = findViewById(R.id.txtwarna1);
        txthargasewa = findViewById(R.id.txthargasewa1);

        final modelSepeda dataCust = getIntent().getExtras().getParcelable("EXTRA_CUSTOMER");
        if (dataCust != null){
            txtkode.setText(dataCust.getKode());
            txtjenis.setText(dataCust.getJenis());
            txtmerk.setText(dataCust.getMerk());
            txtwarna.setText(dataCust.getWarna());
            txthargasewa.setText(dataCust.getHargasewa());
            id = dataCust.getId();

        }else {
            finish();
        }

        getData();

        btnEdit = (Button) findViewById(R.id.btnedit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kode = txtkode.getText().toString();
                String jenis = txtjenis.getText().toString();
                String merk = txtmerk.getText().toString();
                String warna = txtwarna.getText().toString();
                String hargasewa = txthargasewa.getText().toString();
                String u_id = id.toString();
                editData(u_id, kode, jenis, merk, warna, hargasewa);
//                Toast.makeText(UserActivity.this, "edit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        String id = getIntent().getStringExtra("u_id");
        AndroidNetworking.post("http://192.168.43.92/tugasapi/edit_sepeda.php")
                .addBodyParameter("id", id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            if (status.equals("success")) {
                                JSONObject payload = response.getJSONObject("payload");
                                String u_id = payload.optString("ID");
                                String u_kode = payload.optString("kode");
                                String u_jenis = payload.optString("jenis");
                                String u_merk = payload.optString("merk");
                                String u_waarna = payload.optString("warna");
                                String u_hargasewa = payload.optString("hargasewa");
                                String u_role = payload.optString("ROLE");

                                txtkode.setText(u_kode);
                                txtjenis.setText(u_jenis);
                                txtmerk.setText(u_merk);
                                txtwarna.setText(u_waarna);
                                txthargasewa.setText(u_hargasewa);
                                Toast.makeText(EditSepeda.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditSepeda.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("test", String.valueOf(response));
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("anError", error.getLocalizedMessage());
                    }
                });
    }

    public void editData(String id, String kode, String jenis, String merk, String warna, String hargasewa) {
//            sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
//            final String id_auth = sharedPreferences.getString("id", "");
        HashMap<String, String> body = new HashMap<>();
//            body.put("id_auth", id_auth);
        body.put("id", id);
        body.put("kode", kode);
        body.put("jenis", jenis);
        body.put("merk", merk);
        body.put("warna", warna);
        body.put("hargasewa", hargasewa);

        AndroidNetworking.post("http://192.168.43.92/tugasapi/edit_sepeda.php")
                .addBodyParameter(body)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ABR", "respon : " + response);
                        String status = response.optString("STATUS");
                        String message = response.optString("MESSAGE");
                        if (status.equalsIgnoreCase("SUCCESS")) {
                            Toast.makeText(EditSepeda.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditSepeda.this, message, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(EditSepeda.this, "Kesalahan Internal", Toast.LENGTH_SHORT).show();
                        Log.d("Soy", "onError: " + anError.getErrorBody());
                        Log.d("Soy", "onError: " + anError.getLocalizedMessage());
                        Log.d("Soy", "onError: " + anError.getErrorDetail());
                        Log.d("Soy", "onError: " + anError.getResponse());
                        Log.d("Soy", "onError: " + anError.getErrorCode());
                    }
                });
    }
}