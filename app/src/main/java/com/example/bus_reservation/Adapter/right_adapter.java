package com.example.bus_reservation.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus_reservation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class right_adapter extends RecyclerView.Adapter<right_adapter.GithubViewHolder>{
    private final String price;
    private final String routn;
    private final String rout_id;
    private final String booking_date;
    private final String first;
    private final String last;
    private final String trip_id;
    private final String fleet_id;
    public ArrayList<String> seats;
    private ArrayList<String> list;
    private Context context;
    private ArrayList<String> right;
    private ArrayList<String> left_seat;
    private Button button;

    public right_adapter(Context context, ArrayList<String> right, ArrayList<String> list, Button button, String price, String routn
            , String rout_id, String booking_date, String first, String last, String trip_id, String fleet_id){
        this.context = context;
        this.list= list;
        this.right= right;
        this.button=button;
        this.price=price;
        this.routn=routn;
        this.right=right;
        this.rout_id=rout_id;
        this.booking_date=booking_date;
        this.first=first;
        this.last=last;
        this.trip_id=trip_id;
        this.fleet_id=fleet_id;
    }

    @NonNull
    @Override
    public right_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.seat_cardview,viewGroup,false);
        return new right_adapter.GithubViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder holder, int position) {

        final String user = right.get(position);
        holder.textView.setText(user);

        seats = new ArrayList<>();

        //Toast.makeText(context,String.valueOf(list),Toast.LENGTH_LONG).show();

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
                    }

                    Set<String> set = new ArraySet<>();
                    set.addAll(seats);
                    SharedPreferences.Editor editors = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editors.putStringSet("right_seat",set);
                        editors.apply();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return right.size();
    }


    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.seat_id);
        }
    }
}
