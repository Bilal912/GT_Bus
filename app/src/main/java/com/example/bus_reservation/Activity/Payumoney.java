package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Payumoney extends AppCompatActivity {
    String passenger_id;
    String Seat_number,Seat_name,Seat_no,Price,Seat_gender;
    WebView webView;
    String transaction_id;
    ArrayList<String> temp,Name,Number,Gender;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payumoney);

        temp=new ArrayList<>();
        Name=new ArrayList<>();
        Number=new ArrayList<>();
        Gender = new ArrayList<>();

        Gender = this.getIntent().getStringArrayListExtra("Gender");
        temp = this.getIntent().getStringArrayListExtra("seat");
        Name = this.getIntent().getStringArrayListExtra("Name");
        Number = this.getIntent().getStringArrayListExtra("Number");
        Seat_number = temp.toString();
        Seat_name = Name.toString();
        Seat_no = Number.toString();
        Seat_gender = Gender.toString();
        Seat_number = Seat_number.substring(1, Seat_number.length() - 1);
        Seat_name = Seat_name.substring(1, Seat_name.length() - 1);
        Seat_no = Seat_no.substring(1, Seat_no.length() - 1);
        Seat_gender = Seat_gender.substring(1, Seat_gender.length() - 1);

        Price = getIntent().getStringExtra("price");

        this.webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String Email = editors.getString("email", "Null");
        passenger_id = editors.getString("passenger_id","Null");
        webView.loadUrl("https://gtbus.org/payumoney/index.php?email="+Email+"&amount="+Price);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
             //   checkConnection();
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

                //view.loadUrl(webView.getUrl());

                String Url = view.getUrl();
                AdWebViewClient(Url);

            }

        });

        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress>=60)
                {

                }

            }
        });

    }

    public void AdWebViewClient(String url) {

        //view.loadUrl(webView.getUrl());

        if (url.contains("success.php")) {
                //parse uri
                Uri uri= Uri.parse(webView.getUrl());
                transaction_id = uri.getQueryParameter("txtid");
                getData(transaction_id);

            }
            else if (url.contains("failure.php")){
                Toast.makeText(Payumoney.this, "Payment was not received", Toast.LENGTH_SHORT).show();

                webView.destroy();
                final SweetAlertDialog pDialog = new SweetAlertDialog(Payumoney.this, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Error");
                        pDialog.setContentText("Payment was not received");
                        pDialog.setConfirmText("OK");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(Payumoney.this,Menu.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            }
                        });

                finish();

            }

    }

    public void getData(String trans_id) {

        final String rout_id = getIntent().getStringExtra("rout_id");
        final String booking_date = getIntent().getStringExtra("booking_date");
        final String trip_id = getIntent().getStringExtra("trip_id");
        final String fleet_id = getIntent().getStringExtra("fleet_id");
        final String pick = getIntent().getStringExtra("pick");
        final String drop = getIntent().getStringExtra("drop");
        final String routn = getIntent().getStringExtra("routn");
        final String Size = getIntent().getStringExtra("size");

        Map<String, String> params = new Hashtable<String, String>();
        params.put("trip_id_no", trip_id);
        params.put("fleet_registration_id", fleet_id);
        params.put("trip_route_id", rout_id);
        params.put("passenger_id_no", passenger_id);
        params.put("seat_number", Seat_number);
        params.put("total_seat",Size);
        params.put("pickup_location",pick);
        params.put("drop_location",drop);
        params.put("booking_date",booking_date);
        params.put("price",Price);
        params.put("request_facilities[]","free breakfast");
        params.put("transaction_id", trans_id);
        params.put("seats[]", Seat_number);
        params.put("names[]", Seat_name);
        params.put("numbers[]", Seat_no);
        params.put("gender[]", Seat_gender);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url_Create_Booking,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Boolean status = null;
                String msg = "";

                try {
                    status = response.getBoolean("status");
                    if (status){
                        String temp2 = response.getString("message");
                        webView.destroy();
                        final SweetAlertDialog pDialog = new SweetAlertDialog(Payumoney.this, SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setTitleText("Congratulation !!!");
                        pDialog.setCancelable(false);
                        pDialog.setContentText(temp2);
                        pDialog.setConfirmText("Done");
                        pDialog.show();
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(Payumoney.this,rating_screen.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.putExtra("trip_id",trip_id);
                                startActivity(i);
                                finish();
                            }
                        });

                    }
                    else {

                        msg = response.getString("exception");
                        webView.destroy();
//                        makeText(Payumoney.this,msg, LENGTH_SHORT).show();
//                        finish();
                        final SweetAlertDialog pDialog = new SweetAlertDialog(Payumoney.this, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Error");
                        pDialog.setContentText(msg);
                        pDialog.setConfirmText("OK");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                Intent i = new Intent(Payumoney.this,Menu.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(i);
                                finish();
                            }
                        });
                    }
                } catch (JSONException e) {
                    makeText(Payumoney.this,"Connection Error", LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

        RequestQueue queue = Volley.newRequestQueue(Payumoney.this);
        queue.add(jsonRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        checkConnection();
    }

    public void checkConnection() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                webView.setVisibility(View.VISIBLE);

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                webView.setVisibility(View.VISIBLE);

            }
        } else {
            webView.setVisibility(View.GONE);
        }
    }
}
