package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.R;
import com.example.bus_reservation.volley.CustomRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static android.widget.Toast.*;

public class Login extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    TextView register, forget;
    Button login;
    EditText username, password;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        forget = findViewById(R.id.forget);
        login = findViewById(R.id.login);
        username = findViewById(R.id.name);
        password = findViewById(R.id.pass);

        token = FirebaseInstanceId.getInstance().getToken();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(username.getText().toString())){
                    Toast.makeText(Login.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(Login.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                else {
                    getData(username.getText().toString(), password.getText().toString(),token);
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Forget_Password.class);
                startActivity(i);
            }
        });

    }

    private void getData(final String Email, final String Password, final String Token) {

        final android.app.AlertDialog loading = new ProgressDialog(Login.this);
        loading.setMessage("Checking...");
        loading.setCancelable(false);
        loading.show();

        Map<String, String> params = new Hashtable<String, String>();
        params.put("email", Email);
        params.put("password", Password);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url_login,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Boolean status = response.getBoolean("response");

                    JSONArray jsonArray = response.getJSONArray("data");
                    if (status){
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            Toast.makeText(Login.this, "Login Successfully !!!", LENGTH_SHORT).show();

                            String id = object.getString("id");
                            String firstname = object.getString("firstname");
                            String lastname = object.getString("lastname");
                            String phone = object.getString("phone");
                            String address = object.getString("address");
                            String nid = object.getString("nid");
                            String pass = object.getString("password");
                            String passenger_id = object.getString("id_no");

                            SharedPreferences.Editor editors = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editors.putString("email", Email);
                            editors.putString("id", id);
                            editors.putString("firstname", firstname);
                            editors.putString("lastname", lastname);
                            editors.putString("phone", phone);
                            editors.putString("address", address);
                            editors.putString("nid", nid);
                            editors.putString("pass", pass);
                            editors.putString("passenger_id",passenger_id);
                            editors.apply();

                            gettokenrequest(Token,passenger_id);
                            loading.dismiss();

                            Intent intent = new Intent(Login.this, Menu.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }

                    }
                    else {
                        loading.dismiss();
                        String error = response.getString("error");

                        Toast.makeText(Login.this,error, LENGTH_SHORT).show();
                    }

                } catch (JSONException error) {
                    loading.dismiss();
                    Toast.makeText(Login.this,"Something Went Wrong", LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(getApplicationContext(), "Something went wrong" + error, LENGTH_LONG).show();
            }
        });

        jsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonRequest);

    }

    private void gettokenrequest(String token, String id) {

        Map<String, String> params = new Hashtable<String, String>();
        params.put("device_id", FirebaseInstanceId.getInstance().getToken());
        params.put("user_id", id);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url_token,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences.Editor editors = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editors.putString("token", FirebaseInstanceId.getInstance().getToken());
                editors.apply();
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loading.dismiss();
//                makeText(getApplicationContext(), "Something went wrong" + error, LENGTH_LONG).show();
            }
        });

        jsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonRequest);
    }
}
