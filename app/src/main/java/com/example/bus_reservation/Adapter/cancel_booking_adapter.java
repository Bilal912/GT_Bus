package com.example.bus_reservation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Activity.Register;
import com.example.bus_reservation.Activity.Seat_Layout;
import com.example.bus_reservation.Activity.Show_image;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.Model.booking_model;
import com.example.bus_reservation.Model.cancel_booking_model;
import com.example.bus_reservation.Model.mybooking_model;
import com.example.bus_reservation.R;
import com.example.bus_reservation.volley.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class cancel_booking_adapter extends RecyclerView.Adapter<cancel_booking_adapter.GithubViewHolder> {

    private final Context context;
    private ArrayList<cancel_booking_model> data;
    public cancel_booking_adapter(Context context, ArrayList<cancel_booking_model> data){
        this.context = context;
        this.data= data;
    }

    @NonNull
    @Override
    public cancel_booking_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cancel_cardview,viewGroup,false);
        return new cancel_booking_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder holder, final int position) {

        holder.bookingdate.setText(data.get(position).getDate());
        holder.bookingid.setText(data.get(position).getTktBookingIdNo());
        holder.price.setText(data.get(position).getCancelationFees());
        holder.cause.setText(data.get(position).getCauses());
        holder.refund_fee.setText("Refund Amount : ".concat(data.get(position).getRefund_fee()));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Show_image.class);
                i.putExtra("image",data.get(position).getImage());
                context.startActivity(i);
            }
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView bookingdate,bookingid,price,cause,refund_fee;
        Button button;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            refund_fee=itemView.findViewById(R.id.refund_fee);
            bookingdate=itemView.findViewById(R.id.date_id);
            bookingid=itemView.findViewById(R.id.pickup);
            price=itemView.findViewById(R.id.price);
            cause=itemView.findViewById(R.id.cause_id);
            button=itemView.findViewById(R.id.view);
        }
    }
}
