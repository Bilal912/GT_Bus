package com.example.bus_reservation.Adapter;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus_reservation.Activity.Booking;
import com.example.bus_reservation.Activity.Pick_Drop;
import com.example.bus_reservation.Activity.Seat_Layout;
import com.example.bus_reservation.R;
import com.example.bus_reservation.Model.booking_model;
import com.skydoves.elasticviews.ElasticButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class booking_adapter extends RecyclerView.Adapter<booking_adapter.GithubViewHolder> {
    private final String first;
    private final String last;
    private TextView textView;
    private Context context;
    ElasticButton button;
    private ArrayList<booking_model> data;
    public booking_adapter(Context context, ArrayList<booking_model> data,String first,String last,ElasticButton button,TextView textView){
        this.context = context;
        this.data= data;
        this.textView=textView;
        this.button=button;
        this.first=first;
        this.last=last;
    }

    @NonNull
    @Override
    public booking_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.booking_sample,viewGroup,false);
        return new booking_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final booking_adapter.GithubViewHolder githubViewHolder, final int i) {

        String space = "  ";
        githubViewHolder.routname.setText(data.get(i).getTripRouteName());
//        githubViewHolder.duration.setText(data.get(i).getPickupTripLocation());
        githubViewHolder.vehiclenumber.setText(data.get(i).getFleetRegistrationNo());
        githubViewHolder.departure.setText("PickUp Timing: ".concat(data.get(i).getStartTime()));
//        githubViewHolder.arrival.setText(data.get(i).getDropTripLocation());

        if (data.get(i).getPrice().equals("null")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            pDialog.setTitleText("No Route Available Right Now");
            pDialog.setConfirmText("OK");
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ((Activity)context).finish();
                }
            });
        }
        else {
            githubViewHolder.price.setText(data.get(i).getPrice());
        }

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

//                button.setBackgroundColor(Color.parseColor("#29166f"));
                //#29166f
                // TODO Auto-generated method stub

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //updateLabel();

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                textView.setText(sdf.format(myCalendar.getTime()));

                if (textView.getText().equals("Select Journey Date")){
                    Toast.makeText(context,"Please Select Your Journey Date",Toast.LENGTH_LONG).show();
                }
                else {
                    String rout_id = data.get(i).getTripRouteId();
                    String fleet_id = data.get(i).getFleetRegistrationId();
                    String trip_id = data.get(i).getTripIdNo();
                    String price = data.get(i).getPrice();
                    String rout_name = data.get(i).getTripRouteName();
                    //String booking_date = data.get(i).getBookingDate();
                    String booking_date = textView.getText().toString();
                    String bus_seat = data.get(i).getFleetSeats();
                    String pickup = data.get(i).getPickupTripLocation();
                    String dropup=data.get(i).getDropTripLocation();

                    Intent i = new Intent(context, Seat_Layout.class);
                    i.putExtra("routn", rout_name);
                    i.putExtra("rout_id", rout_id);
                    i.putExtra("fleet_reg_no", fleet_id);
                    i.putExtra("trip_id", trip_id);
                    i.putExtra("price", price);
                    i.putExtra("booking_date", booking_date);
                    i.putExtra("first", pickup);
                    i.putExtra("last", dropup);
                    i.putExtra("bus_seat", bus_seat);
                    i.putExtra("date",textView.getText().toString());
                    context.startActivity(i);
                }

            }
        };
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (textView.getText().equals("Select Journey Date")){
//                    Toast.makeText(context,"Please Select Your Journey Date",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    String rout_id = data.get(i).getTripRouteId();
//                    String fleet_id = data.get(i).getFleetRegistrationId();
//                    String trip_id = data.get(i).getTripIdNo();
//                    String price = data.get(i).getPrice();
//                    String rout_name = data.get(i).getTripRouteName();
//                    String booking_date = data.get(i).getBookingDate();
//                    String bus_seat = data.get(i).getFleetSeats();
//
//                    Intent i = new Intent(context, Seat_Layout.class);
//                    i.putExtra("routn", rout_name);
//                    i.putExtra("rout_id", rout_id);
//                    i.putExtra("fleet_reg_no", fleet_id);
//                    i.putExtra("trip_id", trip_id);
//                    i.putExtra("price", price);
//                    i.putExtra("booking_date", booking_date);
//                    i.putExtra("first", first);
//                    i.putExtra("last", last);
//                    i.putExtra("bus_seat", bus_seat);
//                    i.putExtra("date",textView.getText().toString());
//                    context.startActivity(i);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView routname,vehiclenumber,departure,duration,arrival,price;
        //Button button;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            routname= itemView.findViewById(R.id.routename);
            vehiclenumber=itemView.findViewById(R.id.booking_id);
            departure=itemView.findViewById(R.id.departure);
            duration=itemView.findViewById(R.id.pickup);
            arrival = itemView.findViewById(R.id.dropup);
            price=itemView.findViewById(R.id.price);
            //button = itemView.findViewById(R.id.book);

        }
    }
}
