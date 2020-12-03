package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UserActivity extends AppCompatActivity {

    private TextView etName, etNoktp, etNohp, etAddress;
    private Button btnEdit, btnDelete;
    private SharedPreferences sharedPreferences;
    private  String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

//        btnDelete = findViewById(R.id.btndelete);
        etName = findViewById(R.id.txtusername2);
        etNoktp = findViewById(R.id.txtnoktp1);
        etNohp = findViewById(R.id.txtnomerhp1);
        etAddress = findViewById(R.id.txtalamat1);

        final modelAdmin dataCust = getIntent().getExtras().getParcelable("EXTRA_CUSTOMER");
        if (dataCust != null){
            etName.setText(dataCust.getNama());
            etNoktp.setText(dataCust.getNoktp());
            etNohp.setText(dataCust.getNohp());
            etAddress.setText(dataCust.getAlamat());
            id = dataCust.getId();

//            btnDelete = findViewById(R.id.btndelete);
//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    deleteData(id);
//                }
//            });

        }else {
            finish();
        }

        getData();

        btnEdit = (Button) findViewById(R.id.btnedit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String noktp = etNoktp.getText().toString();
                String nohp = etNohp.getText().toString();
                String address = etAddress.getText().toString();
                String u_id = id.toString();
                editData(u_id, name, noktp, nohp, address);
//                Toast.makeText(UserActivity.this, "edit", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getData() {
            String id = getIntent().getStringExtra("u_id");
            AndroidNetworking.post("http://192.168.43.92/tugasapi/edit_user.php")
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
                                    String u_name = payload.optString("NAMA");
                                    String u_email = payload.optString("EMAIL");
                                    String u_noktp = payload.optString("NOKTP");
                                    String u_nohp = payload.optString("NOHP");
                                    String u_alamat = payload.optString("ALAMAT");
                                    String u_role = payload.optString("ROLE");

                                    etName.setText(u_name);
                                    etNoktp.setText(u_noktp);
                                    etNohp.setText(u_nohp);
                                    etAddress.setText(u_alamat);
                                    Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
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

        public void editData(String id, String nama, String noktp, String nohp, String alamat) {
//            sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
//            final String id_auth = sharedPreferences.getString("id", "");
            HashMap<String, String> body = new HashMap<>();
//            body.put("id_auth", id_auth);
            body.put("id", id);
            body.put("nama", nama);
            body.put("noktp", noktp);
            body.put("nohp", nohp);
            body.put("alamat", alamat);

            AndroidNetworking.post("http://192.168.43.92/tugasapi/edit_user.php")
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
                                Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(UserActivity.this, "Kesalahan Internal", Toast.LENGTH_SHORT).show();
                            Log.d("Soy", "onError: " + anError.getErrorBody());
                            Log.d("Soy", "onError: " + anError.getLocalizedMessage());
                            Log.d("Soy", "onError: " + anError.getErrorDetail());
                            Log.d("Soy", "onError: " + anError.getResponse());
                            Log.d("Soy", "onError: " + anError.getErrorCode());
                        }
                    });
        }

//        private void deleteData(String id) {
//            sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
//            String id_auth = sharedPreferences.getString("id", "");
////            String id = getIntent().getStringExtra("u_id");
//            AndroidNetworking.post("http://192.168.43.92/tugasapi/delete_user.php")
//                    .addBodyParameter("id", id)
////                    .addBodyParameter("id_auth", id_auth)
//                    .setTag("test")
//                    .setPriority(Priority.MEDIUM)
//                    .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            // do anything with response
//                            try {
//                                String status = response.getString("status");
//                                String message = response.getString("message");
//                                if (status.equals("success")) {
//                                    Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    Toast.makeText(UserActivity.this, message, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Log.d("test", String.valueOf(response));
//                        }
//
//                        @Override
//                        public void onError(ANError error) {
//                            // handle error
//                            Log.d("anError", error.getLocalizedMessage());
//                        }
//                    });
//        }
}