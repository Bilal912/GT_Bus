package com.example.bus_reservation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Activity.Register;
import com.example.bus_reservation.volley.CustomRequest;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Update extends Fragment {

    Uri uri;
    private static int RESULT_LOAD_IMAGE = 1;
    private String Id;
    String code = "91";
    Button update;
    EditText first, last, email, phone, nid, address, password;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        image = view.findViewById(R.id.image_id);
        first = (EditText) view.findViewById(R.id.first);
        last = view.findViewById(R.id.last);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        nid = view.findViewById(R.id.nid);
        address = view.findViewById(R.id.address);
        password = view.findViewById(R.id.password);

        update = view.findViewById(R.id.update);

        SharedPreferences editors = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String Email = editors.getString("email", "Null");
        String First = editors.getString("firstname", "Null");
        String Last = editors.getString("lastname", "Null");

        String Call = editors.getString("phone", "Null");
        String Phone = Call.substring(2, Call.length());

        String Nid = editors.getString("nid", "Null");
        String Address = editors.getString("address", "Null");
        String Pass = editors.getString("pass", "Null");
        Id = editors.getString("id", "Null");

        email.setText(Email);
        email.setEnabled(false);
        first.setText(First);
        last.setText(Last);
        phone.setText(Phone);
        nid.setText(Nid);
        address.setText(Address);
        password.setText(Pass);

//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//          }
//     });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(first.getText().toString())) {
                    makeText(getContext(), "First Name is required", LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(last.getText().toString())) {
                    makeText(getContext(), "Last Name is required", LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email.getText().toString())) {
                    makeText(getContext(), "Email is required", LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone.getText().toString())) {
                    makeText(getContext(), "Phone Number is required", LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    makeText(getContext(), "Password is required", LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address.getText().toString())) {
                    makeText(getContext(), "Address is required", LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(nid.getText().toString())) {
                    makeText(getContext(), "NID is required", LENGTH_SHORT).show();
                } else {

                    getData(Id, first.getText().toString(), last.getText().toString(), code.concat(phone.getText().toString().trim()), email.getText().toString(), password.getText().toString(), address.getText().toString(), nid.getText().toString());

                }
            }
        });

        return view;
    }

    private void getData(final String id, final String First, final String Last, final String Phone, final String Email, final String Password, final String Address, final String NID) {

        final AlertDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Checking...");
        loading.show();

        Map<String, String> params = new Hashtable<String, String>();
        params.put("id", id);
        params.put("firstname", First);
        params.put("lastname", Last);
        params.put("phone", Phone);
        params.put("email", Email);
        params.put("password", Password);
        params.put("address_line_1", Address);
        params.put("nid", NID);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url_Update, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Boolean status = null;
                String msg = "";

                try {
                    status = response.getBoolean("response");

                    if (status) {
                        loading.dismiss();
                        msg = response.getString("success");
                        SharedPreferences.Editor editors = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editors.putString("firstname", First);
                        editors.putString("lastname", Last);
                        editors.putString("phone", Phone);
                        editors.putString("address", Address);
                        editors.putString("nid", NID);
                        editors.putString("pass", Password);
                        editors.apply();

                        makeText(getContext(), msg, LENGTH_SHORT).show();

                        Fragment fragment = new Home();
                        FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.nav_fragment, fragment);
                        transaction2.commit();

                    } else {
                        loading.dismiss();
                        String error = response.getString("error");
                        Toast.makeText(getContext(),error, LENGTH_SHORT).show();                    }
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
                makeText(getContext(), "Something went wrong" + error, LENGTH_LONG).show();
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == RESULT_LOAD_IMAGE) {
//                Picasso.with(getContext()).load(data.getData()).noPlaceholder()
//                        .into(image);
//                uri = data.getData();
//
//            }
//        }
//    }

}
