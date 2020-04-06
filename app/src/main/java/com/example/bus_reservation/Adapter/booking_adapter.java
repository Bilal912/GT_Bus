package com.example.bus_reservation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus_reservation.Activity.Pick_Drop;
import com.example.bus_reservation.Activity.Seat_Layout;
import com.example.bus_reservation.R;
import com.example.bus_reservation.Model.booking_model;

import java.util.ArrayList;

public class booking_adapter extends RecyclerView.Adapter<booking_adapter.GithubViewHolder> {
    private final String first;
    private final String last;
    private Context context;
    private ArrayList<booking_model> data;
    public booking_adapter(Context context, ArrayList<booking_model> data,String first,String last){
        this.context = context;
        this.data= data;
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
        githubViewHolder.duration.setText(first);
        githubViewHolder.price.setText(data.get(i).getPrice());
        githubViewHolder.vehiclenumber.setText(data.get(i).getFleetRegistrationNo());
        githubViewHolder.departure.setText("PickUp Timing: ".concat(data.get(i).getStartTime()));
        githubViewHolder.arrival.setText(last);

        githubViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rout_id = data.get(i).getTripRouteId();
                String fleet_id = data.get(i).getFleetRegistrationId();
                String trip_id = data.get(i).getTripIdNo();
                String price = data.get(i).getPrice();
                String rout_name = data.get(i).getTripRouteName();
                String booking_date = data.get(i).getBookingDate();
                String bus_seat = data.get(i).getFleetSeats();

                Intent i = new Intent(context, Seat_Layout.class);
                i.putExtra("routn",rout_name);
                i.putExtra("rout_id",rout_id);
                i.putExtra("fleet_reg_no",fleet_id);
                i.putExtra("trip_id",trip_id);
                i.putExtra("price",price);
                i.putExtra("booking_date",booking_date);
                i.putExtra("first",first);
                i.putExtra("last",last);
                i.putExtra("bus_seat",bus_seat);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView routname,vehiclenumber,departure,duration,arrival,price;
        Button button;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            routname= itemView.findViewById(R.id.routename);
            vehiclenumber=itemView.findViewById(R.id.booking_id);
            departure=itemView.findViewById(R.id.departure);
            duration=itemView.findViewById(R.id.pickup);
            arrival = itemView.findViewById(R.id.dropup);
            price=itemView.findViewById(R.id.price);
            button = itemView.findViewById(R.id.book);

        }
    }
}
