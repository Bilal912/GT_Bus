package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.R;
import com.example.bus_reservation.Adapter.booking_adapter;
import com.example.bus_reservation.Model.booking_model;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class Booking extends AppCompatActivity {
    TextView back;
    RecyclerView recyclerView;
    ArrayList<booking_model> model;
    TextView textView;
    ElasticButton button;
    String stat = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        button=findViewById(R.id.book_button);
        textView=findViewById(R.id.date);
        back=findViewById(R.id.back);
        recyclerView=findViewById(R.id.recycler_id);
        model = new ArrayList<>();
        String first = getIntent().getStringExtra("first");
        String last = getIntent().getStringExtra("last");
        String Date = getIntent().getStringExtra("date");
        String vtype = getIntent().getStringExtra("vtype");
        getData(first,last,Date,vtype);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getData(final String first , final String last , final String date , final String Vtype){
        final android.app.AlertDialog loading = new ProgressDialog(Booking.this);
        loading.setMessage("Wait...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_Search+"start_point="+first
                //+"&end_point="+last+"&date="+date+"&fleet_type="+Vtype
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("response");
                    if (status) {
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            booking_model value = new booking_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            value.setTripIdNo(object.getString("trip_id_no"));
                            value.setFleetRegistrationId(object.getString("fleet_registration_id"));
                            value.setTripRouteId(object.getString("trip_route_id"));
                            value.setTripRouteName(object.getString("trip_route_name"));
                            value.setPickupTripLocation(object.getString("pickup_trip_location"));
                            value.setDropTripLocation(object.getString("drop_trip_location"));
                            value.setFleetRegistrationNo(object.getString("fleet_registration_no"));
                            value.setFleetSeats(object.getString("fleet_seats"));
                            value.setStartTime(object.getString("display_time"));
                            value.setEndTime(object.getString("end_time"));
                            value.setDuration(object.getString("duration"));
                            value.setPrice(object.getString("price"));
                            value.setBookingDate(object.getString("booking_date"));
                            value.setFleetSeats(object.getString("fleet_seats"));
                            model.add(value);
                            loading.dismiss();
                        }
                    }
                    else {
                        loading.dismiss();

                        final SweetAlertDialog pDialog = new SweetAlertDialog(Booking.this, SweetAlertDialog.WARNING_TYPE);
                        pDialog.setTitleText("No Trip Available");
                        pDialog.setConfirmText("OK");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        });
                    }
                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(Booking.this,"No Data Found", LENGTH_SHORT).show();
                }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Booking.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new booking_adapter(Booking.this,model,first,last,button,textView));
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(Booking.this, "Connection Error", LENGTH_LONG).show();
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

        RequestQueue queue = Volley.newRequestQueue(Booking.this);
        queue.add(jsonRequest);
    }

}
