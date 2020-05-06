package com.example.bus_reservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.bus_reservation.Activity.Booking;
import com.example.bus_reservation.Adapter.booking_adapter;
import com.example.bus_reservation.Model.booking_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Dashboard extends Fragment {
    private SliderLayout mDemoSlider;
    TextView booked_seat,booked,refund;
    String book,seat,ref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        booked=view.findViewById(R.id.bookoo);
        booked_seat=view.findViewById(R.id.booked_seat);
        refund=view.findViewById(R.id.refund_id);

        mDemoSlider = view.findViewById(R.id.slider);
        Slider_view();
        SharedPreferences editors = getContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        String passenger_id = editors.getString("passenger_id",null);

        getData(passenger_id);


        return view;
    }

    private void getData(String passenger_id) {
        final android.app.AlertDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Getting Data...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Base_url_Dashboard+"?customer_id="+passenger_id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;

                try {
                        status = response.getBoolean("response");
                        book = response.getString("total_booking");
                        seat = response.getString("total_seats");
                        ref = response.getString("total_refund");
                    loading.dismiss();
                }
                catch (Exception e){
                    loading.dismiss();
                    Toast.makeText(getContext(),"No Data Found", LENGTH_SHORT).show();
                }
                booked_seat.setText(seat);
                refund.setText(ref);
                booked.setText(book);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(getContext(), "Connection Error", LENGTH_LONG).show();
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


    public void Slider_view(){

        //from image link
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Fast Bus", "https://image.freepik.com/free-vector/city-street-skyscraper-buildings-road-view-modern-cityscape-singapore-downtown_48369-12979.jpg");
        url_maps.put("GTBus", "https://image.freepik.com/free-vector/city-street-skyscraper-buildings-road-view-modern-cityscape-singapore-downtown_48369-12983.jpg");
        url_maps.put("Good Seats", "https://image.freepik.com/free-vector/bus-inside_93732-2.jpg");
        url_maps.put("Happy Journey", "https://image.freepik.com/free-vector/man-standing-inside-public-transport_107173-10516.jpg");

        //from drawable
//        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Hannibal",R.drawable.hannibal);

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
    }
}
