package com.example.bus_reservation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus_reservation.Activity.SeatDetail;
import com.example.bus_reservation.R;

import java.util.ArrayList;

public class seat_adapter extends RecyclerView.Adapter<seat_adapter.GithubViewHolder> {
    private final String price;
    private final String routn;
    private final String rout_id;
    private final String booking_date;
    private final String first;
    private final String last;
    private final String trip_id;
    private final String fleet_id;
    private final ArrayList<String> data;
    public ArrayList<String> seats;
    private ArrayList<String> list;
    private Context context;
    private ArrayList<String> right;
    private Button button;
    private Interface recyclerInterface;

    public seat_adapter(Context context, ArrayList<String> data, ArrayList<String> list, Button button, String price, String routn
            , String rout_id, String booking_date, String first, String last, String trip_id, String fleet_id,Interface recyclerInterface){
        this.context = context;
        this.data= data;
        this.list= list;
        this.button=button;
        this.price=price;
        this.routn=routn;
        this.rout_id=rout_id;
        this.booking_date=booking_date;
        this.first=first;
        this.last=last;
        this.trip_id=trip_id;
        this.fleet_id=fleet_id;
        this.recyclerInterface=recyclerInterface;
    }

    @NonNull
    @Override
    public seat_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.seat_cardview,viewGroup,false);
        return new seat_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder holder, int position) {

        final String user = data.get(position);
        holder.textView.setText(user);

        seats = new ArrayList<>();

        if (list.contains(String.valueOf(user))) {
                holder.textView.setBackgroundColor(context.getResources().getColor(R.color.pink));
                holder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"Seat Already Booked",Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (seats.contains(String.valueOf(user))){
                        holder.textView.setBackgroundColor(context.getResources().getColor(R.color.grey));
                        holder.textView.setTextColor(Color.parseColor("#000000"));
                        seats.remove(user);
                    }
                    else {
                        holder.textView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                        holder.textView.setTextColor(Color.parseColor("#ffffff"));
                        String temp = String.valueOf(user);
                        seats.add(temp);
                        recyclerInterface.onItemClick(seats);

                    }

                }
            });

//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent i = new Intent(context, SeatDetail.class);
//                    i.putExtra("price",price);
//                    i.putExtra("routn",routn);
//                    i.putStringArrayListExtra("seat",seats);
//                    i.putExtra("rout_id",rout_id);
//                    i.putExtra("fleet_id",fleet_id);
//                    i.putExtra("trip_id",trip_id);
//                    i.putExtra("booking_date",booking_date);
//                    i.putExtra("first",first);
//                    i.putExtra("last",last);
//                    context.startActivity(i);
//                }
//            });

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.seat_id);
        }
    }
}
