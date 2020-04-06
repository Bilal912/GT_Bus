package com.example.bus_reservation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.Dashboard;
import com.example.bus_reservation.Model.booking_model;
import com.example.bus_reservation.Model.mybooking_model;
import com.example.bus_reservation.R;
import com.example.bus_reservation.volley.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class mybooking_adapter extends RecyclerView.Adapter<mybooking_adapter.GithubViewHolder> {

    private final Context context;
    private final String passenger_id;
    private ArrayList<mybooking_model> data;
    public mybooking_adapter(Context context, ArrayList<mybooking_model> data, String passenger_id){
        this.context = context;
        this.data= data;
        this.passenger_id=passenger_id;
    }

    @NonNull
    @Override
    public mybooking_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.sample_cardview,viewGroup,false);
        return new mybooking_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder holder, int position) {

        holder.bookingdate.setText("Booking Date  :  ".concat(data.get(position).getBookingDate()));
        holder.bookingid.setText(data.get(position).getIdNo());
        holder.seatnumber.setText(data.get(position).getSeatNumbers());
   //     holder.totalseat.setText(data.get(position).getTotalSeat());
        holder.price.setText(data.get(position).getPrice());
        holder.routname.setText(data.get(position).getRouteName());
        holder.Pick.setText(data.get(position).getPickupTripLocation());
        holder.Drop.setText(data.get(position).getDropTripLocation());

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        try {
            Date date2 = timeStampFormat.parse(filename);
            Date date1 = timeStampFormat.parse(data.get(position).getBookingDate());

            long different = date2.getTime() - date1.getTime();

            long hoursInMilli = 60000 * 60;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            if (elapsedHours >= 24){
                holder.button.setVisibility(View.GONE);
            }
            else {

            }
        } catch (ParseException e) {
            Toast.makeText(context,"Error Occur", LENGTH_SHORT).show();
        }

        final mybooking_model finalUser = data.get(position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                pDialog.setTitleText("Are you sure?");
                pDialog.setContentText("You want to refund");
                pDialog.setConfirmText("Yes");
                pDialog.show();
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        pDialog.dismiss();
                        final SweetAlertDialog pDialog2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog2.getProgressHelper().setBarColor(Color.parseColor("#29166f"));
                        pDialog2.setTitle("Loading...");
                        pDialog2.setCancelable(false);
                        pDialog2.show();

                        final Map<String, String> params = new Hashtable<String, String>();
                        params.put("passenger_id_no", passenger_id);
                        params.put("booking_id_no", finalUser.getIdNo());

                        final CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url_Refund,params, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Boolean status = null;
                                String msg = "";

                                try {
                                    status = response.getBoolean("status");
                                    msg = response.getString("message");
                                    if (status) {
                                        pDialog2.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        pDialog2.setTitle("Work Done!");
                                        pDialog2.setCancelable(false);
                                        pDialog2.setContentText(msg);
                                        pDialog2.setConfirmText("OK");
                                        pDialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                pDialog2.dismiss();
                                            }
                                        });

                                    }
                                    else {
                                        pDialog2.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                        pDialog2.setTitle("Invalid Data");
                                        pDialog2.setContentText("Sorry");
                                    }
                                } catch (JSONException e) {

                                    pDialog2.dismiss();
                                    makeText(context,"Something Went Wrong", LENGTH_SHORT).show();
                                }

                            }
                        }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();
                                makeText(context, "Something went wrong" + error, LENGTH_LONG).show();
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
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(jsonRequest);
                    }
                });
            }
        });

}
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView bookingdate,bookingid,routname,price,totalseat,seatnumber,Pick,Drop;
        Button button;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            Pick=itemView.findViewById(R.id.pickup);
            Drop=itemView.findViewById(R.id.dropup);
            routname= itemView.findViewById(R.id.route_name);
            bookingdate=itemView.findViewById(R.id.booking_date);
            bookingid=itemView.findViewById(R.id.booking_id);
            totalseat=itemView.findViewById(R.id.totalseat);
            seatnumber = itemView.findViewById(R.id.seat_number);
            price=itemView.findViewById(R.id.price);
            button = itemView.findViewById(R.id.refund);

        }
    }
}
