package com.example.bus_reservation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus_reservation.Activity.Booking;
import com.example.bus_reservation.Activity.price_detail;
import com.example.bus_reservation.R;
import com.skydoves.elasticviews.ElasticCardView;

import java.util.ArrayList;
import java.util.Collections;

public class home_adapter extends RecyclerView.Adapter<home_adapter.GithubViewHolder> {

    private Button button;
    private Context context;
    private ArrayList<String> data;
    private final ArrayList<String> name = new ArrayList<String>();
    private final ArrayList<String> number = new ArrayList<String>();
    private final ArrayList<String> gender = new ArrayList<String>();

    private String fleet_id;
    public home_adapter(Context context, ArrayList<String> data,String fleet_id){
        this.context = context;
        this.data= data;
        this.fleet_id=fleet_id;
    }

    @NonNull
    @Override
    public home_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.home_cardview,viewGroup,false);
        return new home_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder holder, final int position) {

        holder.textView.setText(data.get(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent in = new Intent(context, Booking.class);
                    in.putExtra("first",data.get(position));
                    in.putExtra("last","");
                    in.putExtra("date","");
                    in.putExtra("vtype",fleet_id);
                    context.startActivity(in);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ElasticCardView cardView;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardview);
            textView=itemView.findViewById(R.id.text);

        }
    }
}
