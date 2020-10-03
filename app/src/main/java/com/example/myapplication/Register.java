package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    TextView readyaccount;
    private Button btnregister;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnregister = findViewById(R.id.btnregister);
        final EditText txtemail = findViewById(R.id.txtemail);
        final EditText txtusername1 = findViewById(R.id.txtusername1);
        final EditText txtnohp = findViewById(R.id.txtnomerhp);
        final EditText txtnoktp = findViewById(R.id.txtnoktp);
        final EditText txtalamat = findViewById(R.id.txtalamat);
        final EditText txtpass1 = findViewById(R.id.txtpass1);

        readyaccount = (TextView)findViewById(R.id.readyaccount);
        readyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnregister = findViewById(R.id.btnregister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtemail.getText().toString();
                String username1 = txtusername1.getText().toString();
                String nohp = txtnohp.getText().toString();
                String noktp = txtnoktp.getText().toString();
                String alamat = txtalamat.getText().toString();
                String pass = txtpass1.getText().toString();

                HashMap<String, String> body = new HashMap<>();
                body.put("noktp", noktp);
                body.put("email", email);
                body.put("password", pass);
                body.put("nama", username1);
                body.put("nohp", nohp);
                body.put("alamat", alamat);
                body.put("role", "1");
                AndroidNetworking.post("http://192.168.43.92/tugasapi/register.php")
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
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                    finishAffinity();
                                }
                                else {
                                    Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(Register.this, "Kesalahan Internal", Toast.LENGTH_SHORT).show();
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