package com.example.bus_reservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Activity.Booking;
import com.example.bus_reservation.Adapter.booking_adapter;
import com.example.bus_reservation.Adapter.mybooking_adapter;
import com.example.bus_reservation.Model.booking_model;
import com.example.bus_reservation.Model.mybooking_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Mybooking extends Fragment {

    RecyclerView recyclerView;
    ArrayList<mybooking_model> model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mybooking, container, false);

        recyclerView=view.findViewById(R.id.recycler_id);
        model = new ArrayList<mybooking_model>();
        SharedPreferences editors = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String passenger_id = editors.getString("passenger_id","Null");

        showData(passenger_id);

        return view;
    }

    public void showData(final String passenger_id) {

        final android.app.AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Wait...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_My_Booking+"?id_no="+passenger_id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("status");
                    JSONArray jsonArray = response.getJSONArray("details");
                    if (status) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            mybooking_model value = new mybooking_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            String temp = object.getString("tkt_refund_id");
                            if (temp.equals("null")) {
                                value.setTktRefundId(object.getString("tkt_refund_id"));
                                value.setBookingDate(object.getString("booking_date"));
                                value.setIdNo(object.getString("id_no"));
                                value.setTotalSeat(object.getString("total_seat"));
                                value.setSeatNumbers(object.getString("seat_numbers"));
                                value.setPrice(object.getString("price"));
                                value.setPaymentStatus(object.getString("payment_status"));
                                value.setRouteName(object.getString("trip_route_name"));
                                value.setPickupTripLocation(object.getString("pickup_trip_location"));
                                value.setDropTripLocation(object.getString("drop_trip_location"));
                                model.add(value);
                                loading.dismiss();
                            }
                            else {
                                loading.dismiss();
                            }
                        }
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(getActivity(),"No trip available", LENGTH_SHORT).show();
                    }


                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(getActivity(),"No Data Found", LENGTH_SHORT).show();
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new mybooking_adapter(getActivity(),model,passenger_id));
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_LONG).show();
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

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonRequest);
    }

    }
