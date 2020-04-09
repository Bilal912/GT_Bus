package com.example.bus_reservation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus_reservation.Activity.price_detail;
import com.example.bus_reservation.Model.seatdetail_model;
import com.example.bus_reservation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;

public class seatdetail_adapter extends RecyclerView.Adapter<seatdetail_adapter.GithubViewHolder> {

    private final String code = "+91";
    private final String price;
    private final String drop;
    private final String pick;
    private final String routn;
    private final String rout_id;
    private final String booking_date;
    private final String trip_id;
    private final String fleet_id;
    private final ArrayList<String> list;
    private Button button;
    private Context context;
    private ArrayList<String> data;
    private final ArrayList<String> name = new ArrayList<String>();
    private final ArrayList<String> number = new ArrayList<String>();
    private final ArrayList<String> gender = new ArrayList<String>();

    public seatdetail_adapter(Context context, ArrayList<String> data, Button button, String pick, String drop, String price
            , String routn,String rout_id,String booking_date,String trip_id,String fleet_id ,ArrayList<String> list){
        this.context = context;
        this.data= data;
        this.button = button;
        this.price=price;
        this.drop=drop;
        this.pick=pick;
        this.routn=routn;
        this.rout_id=rout_id;
        this.booking_date=booking_date;
        this.trip_id=trip_id;
        this.fleet_id=fleet_id;
        this.list=list;
    }

    @NonNull
    @Override
    public seatdetail_adapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.seat_detail_cardview,viewGroup,false);
        return new seatdetail_adapter.GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GithubViewHolder holder, final int position) {

        final String user = data.get(position);
        holder.Seat.setText(user);
        holder.Name.setTag(position);
        holder.Number.setTag(position);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gender.removeAll(Collections.singleton(new String("Select Gender")));

                if (TextUtils.isEmpty(holder.Name.getText().toString())){
                    Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(holder.Number.getText().toString())){
                    Toast.makeText(context, "Number is required", Toast.LENGTH_SHORT).show();
                }
                else if (holder.s.getSelectedItem().toString().equals("Select Gender")){
                    Toast.makeText(context, "Gender is required", Toast.LENGTH_SHORT).show();
                }
                else {

                    holder.Number.clearFocus();
                    holder.Name.clearFocus();

                    Intent i = new Intent(context, price_detail.class);
                    i.putExtra("price",price);
                    i.putExtra("pick",pick);
                    i.putExtra("drop",drop);
                    i.putExtra("routn",routn);
                    i.putStringArrayListExtra("seat",data);
                    i.putStringArrayListExtra("Name", name);
                    i.putStringArrayListExtra("Number", number);
                    i.putStringArrayListExtra("Gender", gender);
                    i.putExtra("rout_id",rout_id);
                    i.putExtra("fleet_id",fleet_id);
                    i.putExtra("trip_id",trip_id);
                    i.putExtra("booking_date",booking_date);
                    context.startActivity(i);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{

        TextView Seat;
        EditText Name,Number;
        Spinner s;
        ArrayList<String> temp;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            temp=new ArrayList<>();
            Seat = itemView.findViewById(R.id.seatno);
            Name=itemView.findViewById(R.id.name);
            Number=itemView.findViewById(R.id.number);
            s=itemView.findViewById(R.id.spinner);
            temp.add("Select Gender");
            temp.add("Male");
            temp.add("Female");
            temp.add("Other");

            s.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, temp));

            Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        //Here user finished the typing, Now save userTypedValue as required
                        String userTypedValue = code.concat(Name.getText().toString());
                        name.add(userTypedValue);
                    }
                }
            });

            Number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        //Here user finished the typing, Now save userTypedValue as required
                        String Value = Number.getText().toString();
                        number.add(Value);
                    }
                }
            });

            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String Gender = s.getSelectedItem().toString();
                    gender.add(Gender);
//                    Toast.makeText(context,String.valueOf(gender),Toast.LENGTH_LONG).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }
}
