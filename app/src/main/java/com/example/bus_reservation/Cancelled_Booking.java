package com.example.bus_reservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.bus_reservation.Adapter.cancel_booking_adapter;
import com.example.bus_reservation.Adapter.mybooking_adapter;
import com.example.bus_reservation.Model.cancel_booking_model;
import com.example.bus_reservation.Model.mybooking_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Cancelled_Booking extends Fragment {
    RecyclerView recyclerView;
    ArrayList<cancel_booking_model> model;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancelled__booking, container, false);

        textView=view.findViewById(R.id.no_cancel);
        recyclerView=view.findViewById(R.id.recycler_id);
        model = new ArrayList<cancel_booking_model>();
        SharedPreferences editors = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String passenger_id = editors.getString("passenger_id","Null");

        showData(passenger_id);



        return view;
    }

    private void showData(final String passenger_id) {
        final android.app.AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Wait...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_Cancel_Booking+"?id_no="+passenger_id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;
                try {
                    status = response.getBoolean("status");
                    JSONArray jsonArray = response.getJSONArray("details");
                    if (status) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            cancel_booking_model value = new cancel_booking_model();
                            JSONObject object = jsonArray.getJSONObject(i);
                            value.setCancelationFees(object.getString("cancelation_fees"));
                            value.setCauses(object.getString("causes"));
                            value.setDate(object.getString("date"));
                            value.setTktBookingIdNo(object.getString("tkt_booking_id_no"));
                            value.setImage(object.getString("payment_image"));
                            value.setRefund_fee(object.getString("refund_amount"));
                            model.add(value);
                            loading.dismiss();
                        }
                    }

                    else {
                        loading.dismiss();
                        textView.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(getActivity(),"No Data Found", LENGTH_SHORT).show();
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new cancel_booking_adapter(getActivity(),model));
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
