package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Adapter.Interface;
import com.example.bus_reservation.Adapter.right_adapter;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.R;
import com.example.bus_reservation.Adapter.seat_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Seat_Layout extends AppCompatActivity implements Interface {

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ArrayList<String> model,Right;
    Button button;
    TextView back,Seat_bus;
    ArrayList<String> book;
    ArrayList<String> Left_seat;
    ArrayList<String> Final;
    String rout_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat__layout);

        Final=new ArrayList<String>();
        SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        editors.edit().remove("right_seat").apply();

        Seat_bus=findViewById(R.id.bus_seats);
        Left_seat = new ArrayList<String>();
        Right=new ArrayList<String>();
        book = new ArrayList<String>();
        recyclerView2=findViewById(R.id.seat_rv2);
        recyclerView = findViewById(R.id.seat_rv);
        model = new ArrayList<String>();
        button = findViewById(R.id.next);
        back = findViewById(R.id.back);

        String bus_seat = getIntent().getStringExtra("bus_seat");
        Seat_bus.setText((bus_seat.concat(" ")).concat("SEAT(S)"));
        String trip_id = getIntent().getStringExtra("trip_id");
        String fleet_id = getIntent().getStringExtra("fleet_reg_no");
        String date = getIntent().getStringExtra("date");
        rout_id = getIntent().getStringExtra("rout_id");

        getdata(rout_id, fleet_id,date);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = getIntent().getStringExtra("price");
                String routn = getIntent().getStringExtra("routn");
                String booking_date = getIntent().getStringExtra("booking_date");
                String first = getIntent().getStringExtra("first");
                String last = getIntent().getStringExtra("last");
                String trip_id = getIntent().getStringExtra("trip_id");
                String fleet_id = getIntent().getStringExtra("fleet_reg_no");

                Intent i = new Intent(Seat_Layout.this, SeatDetail.class);
                i.putExtra("price",price);
                i.putExtra("routn",routn);
                i.putStringArrayListExtra("seat",Final);
                i.putExtra("rout_id",rout_id);
                i.putExtra("fleet_id",fleet_id);
                i.putExtra("trip_id",trip_id);
                i.putExtra("booking_date",booking_date);
                i.putExtra("first",first);
                i.putExtra("last",last);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
                editors.edit().remove("right_seat").apply();
                finish();
            }
        });

    }

    public void getdata(String Trip, String Fleet,String Date) {

        final android.app.AlertDialog loading = new ProgressDialog(Seat_Layout.this);
        loading.setMessage("Getting Data...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                Constant.Base_url_Bus_Seat + "start_date=" + Date
                + "trip_route_id=" + Trip
                + "&fleet_registration_id=" + Fleet, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String layout = null;
                try {
                    layout = response.getString("seat_layout");

                    JSONArray jsonArray = response.getJSONArray("seat_left");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //seat_model value = new seat_model();
                        JSONObject object = jsonArray.getJSONObject(i);
                        String value = object.getString("Seat_no");
                        model.add(value);
                    }

                    JSONArray jsonArray2 = response.getJSONArray("seat_right");
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject object = jsonArray2.getJSONObject(i);
                        String value = object.getString("Seat_no");
                        Right.add(value);
                    }

                    JSONArray jsonArray1 = response.getJSONArray("booked_seats");
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            JSONObject object = jsonArray1.getJSONObject(i);
                            String val = object.getString("scalar");
                            book.add(val);
                        }
                        loading.dismiss();

                } catch (Exception e) {
                    loading.dismiss();
                    makeText(Seat_Layout.this, "Something Went Wrong", LENGTH_SHORT).show();
                }
                String[] separated = layout.split("-");
                int left = Integer.parseInt(separated[0].trim());
                int right = Integer.parseInt(separated[1].trim());

                String price = getIntent().getStringExtra("price");
                String routn = getIntent().getStringExtra("routn");
                String rout_id = getIntent().getStringExtra("rout_id");
                String booking_date = getIntent().getStringExtra("booking_date");
                String first = getIntent().getStringExtra("first");
                String last = getIntent().getStringExtra("last");
                String trip_id = getIntent().getStringExtra("trip_id");
                String fleet_id = getIntent().getStringExtra("fleet_reg_no");

                GridLayoutManager layoutManager = new GridLayoutManager(Seat_Layout.this, left);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new seat_adapter(Seat_Layout.this, model, book, button,price,routn,rout_id,booking_date,first,last,trip_id,fleet_id,Seat_Layout.this));

                GridLayoutManager layoutManager2 = new GridLayoutManager(Seat_Layout.this, right);
                recyclerView2.setLayoutManager(layoutManager2);
                recyclerView2.setAdapter(new right_adapter(Seat_Layout.this, Right, book, button,price,routn,rout_id,booking_date,first,last,trip_id,fleet_id));

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(Seat_Layout.this, "Connection Error", LENGTH_LONG).show();
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
        RequestQueue queue = Volley.newRequestQueue(Seat_Layout.this);
        queue.add(jsonRequest);
    }
@Override
    public void onBackPressed(){
    SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
    editors.edit().remove("right_seat").apply();
    finish();
}

    @Override
    public void onItemClick(ArrayList<String> arrayList) {
        Final=arrayList;
    }
}
