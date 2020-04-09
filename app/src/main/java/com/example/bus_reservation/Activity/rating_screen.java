package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.bus_reservation.Constant;
import com.example.bus_reservation.R;
import com.example.bus_reservation.volley.CustomRequest;
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class rating_screen extends AppCompatActivity {
    RatingBar ratingBar;
    TextView ratingtext,back,skip;
    ElasticButton elasticButton;
    EditText editText;
    String Rate = "0";
    String trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_screen);

        back=findViewById(R.id.back);
        skip=findViewById(R.id.skip);
        ratingBar=findViewById(R.id.ratingbar);
        editText=findViewById(R.id.comment);
        ratingtext=findViewById(R.id.ratingtext);
        elasticButton=findViewById(R.id.submit);

        trip_id=getIntent().getStringExtra("trip_id");

//        trip_id="200114035325";

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(rating_screen.this, Menu.class);
                startActivity(i);
                finish();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingtext.setVisibility(View.VISIBLE);
                switch ((int) rating){
                    case 1:
                        ratingtext.setText("Hate it");
                        Rate = "1";
                        break;
                    case 2:
                        ratingtext.setText("Not Good");
                        Rate = "2";
                        break;
                    case 3:
                        ratingtext.setText("Good");
                        Rate = "3";
                        break;
                    case 4:
                        ratingtext.setText("Like it");
                        Rate = "4";
                        break;
                    case 5:
                        ratingtext.setText("Love it");
                        Rate = "5";
                        break;
                    default:
                        ratingtext.setText("");
                        Rate = "0";
                        break;

                }
            }
        });

        elasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Rate.equals("0")){
                    Toast.makeText(rating_screen.this,"Select Your Star",Toast.LENGTH_LONG).show();
                }
                else if (editText.getText().toString().isEmpty()){
                    editText.setFocusable(true);
                    Toast.makeText(rating_screen.this,"Please Enter Comment",Toast.LENGTH_LONG).show();
                }
                else {

                    submit_rating(Rate,editText.getText().toString(),trip_id);

                }
            }
        });

    }

    private void submit_rating(String rate, String text,String Trip) {

        final SweetAlertDialog loading = new SweetAlertDialog(rating_screen.this,SweetAlertDialog.PROGRESS_TYPE);
        loading.setTitleText("Submitting Your Rate...");
        loading.setCancelable(false);
        loading.show();

        SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String Id = editors.getString("id", "Null");


        Map<String, String> params = new Hashtable<String, String>();
        params.put("user_id", Id);
        params.put("comment", text);
        params.put("stars", rate);
        params.put("trip_id", Trip);

        CustomRequest jsonRequest = new CustomRequest(Request.Method.POST, Constant.Base_url_rating,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Boolean status = null;
                String msg = "";

                try {
                    status = response.getBoolean("status");
                    if (status) {
                        loading.dismiss();
                        msg = response.getString("message");

                        loading.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        loading.setTitleText(msg);
                        loading.setConfirmText("Ok");
                        loading.show();
                        loading.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                loading.dismiss();
                                Intent i = new Intent(rating_screen.this, Menu.class);
                                startActivity(i);
                                finish();
                            }
                        });

                    }
                    else {
                        loading.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        loading.setTitleText(msg);
                        loading.setConfirmText("Ok");
                        loading.show();
                        loading.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                loading.dismiss();
                                Intent i = new Intent(rating_screen.this, Menu.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }
                }
                catch (JSONException e) {
                    loading.dismiss();
                    makeText(rating_screen.this,"Connection Error", LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(getApplicationContext(), "Something went wrong" + error, LENGTH_LONG).show();
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

//        MySingleton.getInstance(this).addToRequestQueue(jsonRequest);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonRequest);
    }
    }

