package com.example.bus_reservation;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Activity.Login;
import com.example.bus_reservation.volley.CustomRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Contact_us extends Fragment {
EditText Message;
TextInputEditText Title;
ElasticButton elasticButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        Message=view.findViewById(R.id.request_id);
        Title=view.findViewById(R.id.title);
        elasticButton = view.findViewById(R.id.send);

        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Title.getText().toString())){
                    Toast.makeText(getContext(), "Subject is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Message.getText().toString())){
                    Toast.makeText(getContext(), "Message Field is Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences editors = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    String id = editors.getString("passenger_id", "Null");
                    getData(Title.getText().toString(), Message.getText().toString(),id);

                }

            }
        });

        return view;
    }

    private void getData(String title, String message, String id) {
        final AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Sending...");
        loading.show();

        Map<String, String> params = new Hashtable<String, String>();
        params.put("passenger_id", id);
        params.put("title", title);
        params.put("request", message);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url_contact_us, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Boolean status = null;
                String msg = "";

                try {
                    status = response.getBoolean("status");

                    if (status) {
                        loading.dismiss();
                        msg = response.getString("message");
                        makeText(getContext(), msg, LENGTH_SHORT).show();

                        Fragment fragment = new Home();
                        FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.nav_fragment, fragment);
                        transaction2.commit();

                    } else {
                        loading.dismiss();
                        Toast.makeText(getContext(),"Request Not Submitted", LENGTH_SHORT).show();                    }
                } catch (JSONException e) {
                    loading.dismiss();
                    makeText(getActivity(), "Something Went Wrong", LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(getContext(), "Connection Problem" + error, LENGTH_LONG).show();
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
