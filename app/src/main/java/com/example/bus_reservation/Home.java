package com.example.bus_reservation;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.bus_reservation.Activity.Login;
import com.example.bus_reservation.Activity.Menu;
import com.example.bus_reservation.Activity.Pick_Drop;
import com.example.bus_reservation.Activity.SeatDetail;
import com.example.bus_reservation.Adapter.home_adapter;
import com.example.bus_reservation.Adapter.seatdetail_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class Home extends Fragment {

    Spinner Start,End,Type;
    RecyclerView recyclerView;
    Button button;
    ArrayList<String> Startpoint,type,Endpoint,Id,Vid;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView=view.findViewById(R.id.recycler_id);
        Vid=new ArrayList<>();
        Id= new ArrayList<String>();
        textView=view.findViewById(R.id.date);
        final Calendar myCalendar = Calendar.getInstance();
        Startpoint = new ArrayList<>();
        Endpoint = new ArrayList<>();
        type = new ArrayList<>();
//        Start=view.findViewById(R.id.start_point);
        End=view.findViewById(R.id.end_point);
        Type=view.findViewById(R.id.vehicle_type);
        button=view.findViewById(R.id.search);
//        Startpoint.add("From");
        Endpoint.add("To");
//        type.add("Select Type");
        Id.add("id");
        Vid.add("id");

        showData();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //updateLabel();
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                textView.setText(sdf.format(myCalendar.getTime()));
            }
        };
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (Start.getSelectedItem().toString().equals("From")){
//                    Toast.makeText(getActivity(),"Please Select Start Point", LENGTH_SHORT).show();
//                }
////                else if (End.getSelectedItem().toString().equals("To")){
////                    Toast.makeText(getActivity(),"Please Select End Point", LENGTH_SHORT).show();
////                }
//                else if (Type.getSelectedItem().toString().equals("Select Type")){
//                    Toast.makeText(getActivity(),"Please Select Vehicle Type", LENGTH_SHORT).show();
//                }
////                else if (String.valueOf(textView.getText()).equals("Select Journey Date")){
////                    Toast.makeText(getActivity(),"Please Select Date", LENGTH_SHORT).show();
////                }
//                else {
//
//                    int val = Startpoint.indexOf(Start.getSelectedItem().toString());
//                    String id = Id.get(val);
//                    int val2 = Endpoint.indexOf(End.getSelectedItem().toString());
//                    String id2 = Id.get(val2);
//                    int vval = type.indexOf(Type.getSelectedItem().toString());
//                    String vid = Vid.get(vval);
//                    String date = String.valueOf(textView.getText());
//
//                    Intent in = new Intent(getActivity(), Booking.class);
//                    in.putExtra("first",id);
//                    in.putExtra("last",id2);
//                    in.putExtra("date",date);
//                    in.putExtra("vtype",vid);
//                    startActivity(in);
//                }
//            }
//        });

    return view;
    }

    public void showData(){

        final android.app.AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Getting Data...");
        loading.show();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_Index, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;

                try {

                    status = response.getBoolean("response");
                    JSONArray jsonArray = response.getJSONArray("location_dropdown");
//                    JSONArray jsonArray2 = response.getJSONArray("fleet_dropdown");
                    if (status){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String temp = object.getString("name");
                            Startpoint.add(temp);
                            Endpoint.add(temp);
                            String temp2 = object.getString("id");
                            Id.add(temp2);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new home_adapter(getContext(), Startpoint));
                        loading.dismiss();

//                        Start.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Startpoint));
//                        End.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Endpoint));
//
//                        for (int j = 0; j < jsonArray2.length(); j++) {
//                            JSONObject object2 = jsonArray2.getJSONObject(j);
//                            String tem = object2.getString("type");
//                            type.add(tem);
//                            String tem2 = object2.getString("id");
//                            Vid.add(tem2);
//                        }
//                        Type.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, type));

                    }
                    else {

                    }

                }
                catch (Exception e) {

                    Toast.makeText(getActivity(),"Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

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
