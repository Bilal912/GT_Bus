package com.example.bus_reservation.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_reservation.Model.seatdetail_model;
import com.example.bus_reservation.R;
import com.example.bus_reservation.Adapter.seatdetail_adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class SeatDetail extends AppCompatActivity {

    TextView back;
    RecyclerView recyclerView;
    ArrayList<String> temp;
    Button button;
    ArrayList<String> list;
    ArrayList<String> right_seat;
    Set<String> fetch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_detail);
        back = findViewById(R.id.back);
        recyclerView=findViewById(R.id.recycler_id);
        button=findViewById(R.id.next);
        right_seat = new ArrayList<>();
        list=new ArrayList<>();
        temp = new ArrayList<String>();
        temp = this.getIntent().getStringArrayListExtra("seat");

        SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        fetch = editors.getStringSet("right_seat", null);

        String s1=String.valueOf(fetch);
        String replace = s1.replace("[","");
        String replace1 = replace.replace("]","");
        List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));

        if (myList.contains("null")){

        }
        else {
            temp.addAll(fetch);
        }

        if (temp.isEmpty()){
            final SweetAlertDialog pDialog = new SweetAlertDialog(SeatDetail.this, SweetAlertDialog.WARNING_TYPE);
            pDialog.setTitleText("No Seat Selected");
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

        String price = getIntent().getStringExtra("price");
        String pick = getIntent().getStringExtra("first");
        String drop = getIntent().getStringExtra("last");
        String routn = getIntent().getStringExtra("routn");
        String rout_id = getIntent().getStringExtra("rout_id");
        String booking_date = getIntent().getStringExtra("booking_date");
        String trip_id = getIntent().getStringExtra("trip_id");
        String fleet_id = getIntent().getStringExtra("fleet_id");

        LinearLayoutManager layoutManager = new LinearLayoutManager(SeatDetail.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new seatdetail_adapter(SeatDetail.this, temp,button,pick,drop,price,routn,rout_id,booking_date,trip_id,fleet_id,list));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
