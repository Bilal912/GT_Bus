package com.example.bus_reservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_reservation.R;
import com.skydoves.elasticviews.ElasticButton;

public class rating_screen extends AppCompatActivity {
    RatingBar ratingBar;
    TextView ratingtext,back,skip;
    ElasticButton elasticButton;
    EditText editText;
    String Rate = "0";

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                    Toast.makeText(rating_screen.this,"Submit your Review",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(rating_screen.this, Menu.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}
