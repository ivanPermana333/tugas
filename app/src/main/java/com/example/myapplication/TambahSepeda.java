package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.HashMap;

public class TambahSepeda extends AppCompatActivity {

    private  Button btnadd;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_sepeda);

        final EditText txtkode = findViewById(R.id.txtkode);
        final EditText txtmerk = findViewById(R.id.txtmerk);
        final EditText txtjenis = findViewById(R.id.txtjenis);
        final EditText txtwarna = findViewById(R.id.txtwarna);
        final EditText txthargasewa = findViewById(R.id.txthargasewa);

        btnadd = findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kode = txtkode.getText().toString();
                String merk = txtmerk.getText().toString();
                String jenis = txtjenis.getText().toString();
                String warna = txtwarna.getText().toString();
                String hargasewa = txthargasewa.getText().toString();

                HashMap<String, String> body = new HashMap<>();
                body.put("kode", kode);
                body.put("merk", merk);
                body.put("jenis", jenis);
                body.put("warna", warna);
                body.put("hargasewa", hargasewa);
                body.put("role", "2");
                AndroidNetworking.post("http://192.168.43.92/tugasapi/add_sepeda.php")
                        .addBodyParameter(body)
                        .setOkHttpClient(((Initial) getApplication()).getOkHttpClient())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("ABR", "respon : " + response);
                                String status = response.optString("STATUS");
                                String message = response.optString("MESSAGE");
                                if (status.equalsIgnoreCase("SUCCESS")) {
                                    Intent intent = new Intent(TambahSepeda.this, Sepeda.class);
                                    startActivity(intent);
                                    Toast.makeText(TambahSepeda.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                    finishAffinity();
                                }
                                else {
                                    Toast.makeText(TambahSepeda.this, message, Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(TambahSepeda.this, "Kesalahan Internal", Toast.LENGTH_SHORT).show();
                                Log.d("IVN", "onError: " + anError.getErrorBody());
                                Log.d("IVN", "onError: " + anError.getLocalizedMessage());
                                Log.d("IVN", "onError: " + anError.getErrorDetail());
                                Log.d("IVN", "onError: " + anError.getResponse());
                                Log.d("IVN", "onError: " + anError.getErrorCode());
                            }
                        });
            }
        });
    }
}