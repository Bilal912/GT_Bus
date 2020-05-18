package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.R;
import com.example.bus_reservation.volley.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class Register extends AppCompatActivity {
  TextView back;
  Button Register;
  EditText first,last,email,phone,nid,address,password;
  String code = "91";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    back=findViewById(R.id.back);
    Register=findViewById(R.id.register);

    first=(EditText)findViewById(R.id.first);
    last=findViewById(R.id.last);
    email=findViewById(R.id.email);
    phone=findViewById(R.id.phone);
    nid=findViewById(R.id.nid);
    address=findViewById(R.id.address);
    password=findViewById(R.id.password);

    Register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (TextUtils.isEmpty(first.getText().toString())){
          makeText(Register.this, "First Name is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(last.getText().toString())){
          makeText(Register.this, "Last Name is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email.getText().toString())){
          makeText(Register.this, "Email is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone.getText().toString())){
          makeText(Register.this, "Phone Number is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password.getText().toString())){
          makeText(Register.this, "Password is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address.getText().toString())){
          makeText(Register.this, "Address is required", LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nid.getText().toString())){
          makeText(Register.this, "NID is required", LENGTH_SHORT).show();
        }

        else {

          getData(first.getText().toString(),last.getText().toString(),code.concat(phone.getText().toString().trim()),email.getText().toString(),password.getText().toString(),address.getText().toString(),nid.getText().toString());

        }
      }
    });

    back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
  }
  private void getData(final String First,final String Last,final String Phone,final String Email,final String Password,final String Address,final String NID) {

//        Toast.makeText(Register.this,Phone, LENGTH_LONG).show();

    final android.app.AlertDialog loading = new ProgressDialog(this);
    loading.setMessage("Checking...");
    loading.show();

    Map<String, String> params = new Hashtable<String, String>();
    params.put("firstname", First);
    params.put("lastname", Last);
    params.put("phone", Phone);
    params.put("email", Email);
    params.put("password", Password);
    params.put("address_line_1",Address);
    params.put("nid",NID);

    CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url_register,params, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {

        Boolean status = null;
        String msg = "";

        try {
          status = response.getBoolean("response");
          if (status) {
            loading.dismiss();
            msg = response.getString("success");
            Toast.makeText(Register.this,msg, LENGTH_SHORT).show();
            finish();

          }
          else {
            loading.dismiss();
            String error = response.getString("error");
            Toast.makeText(Register.this,error, LENGTH_SHORT).show();
          }
        }
        catch (JSONException e) {
          loading.dismiss();
          makeText(Register.this,"Something Went Wrong", LENGTH_SHORT).show();
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

//        MySingleton.getInstance(this).addToRequestQueue(jsonRequest);
    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(jsonRequest);
  }
}
