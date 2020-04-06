package com.example.bus_reservation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus_reservation.Cancelled_Booking;
import com.example.bus_reservation.Dashboard;
import com.example.bus_reservation.Home;
import com.example.bus_reservation.Mybooking;
import com.example.bus_reservation.R;
import com.example.bus_reservation.Update;
import com.google.android.material.navigation.NavigationView;

import static com.example.bus_reservation.Activity.Login.MY_PREFS_NAME;

public class Menu extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        textView=findViewById(R.id.menu_btn);
        drawer = findViewById(R.id.drawer_layout);
        SharedPreferences editors = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        String Email = editors.getString("email","Null");
        String First = editors.getString("firstname","Null");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView= findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        final TextView Name=view.findViewById(R.id.nav_name);
        final TextView Nav_email=view.findViewById(R.id.nav_email);

        Nav_email.setText(Email);
        Name.setText(First);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch(id)
                {

                    case R.id.nav_home:
                        Fragment newFragment = new Home();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_fragment, newFragment);
                        transaction.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_dash:
                        Fragment newFragment3 = new Dashboard();
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.nav_fragment, newFragment3);
                        transaction3.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_profile:
                        Fragment newFragment2 = new Update();
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.nav_fragment, newFragment2);
                        transaction2.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_mybooking:
                        Fragment newFragment4 = new Mybooking();
                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.nav_fragment, newFragment4);
                        transaction4.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_cancelbooking:
                        Fragment newFragment5 = new Cancelled_Booking();
                        FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                        transaction5.replace(R.id.nav_fragment, newFragment5);
                        transaction5.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_share:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Hey check out our app at: https://play.google.com/store/apps/details?id=com.example.bus_reservation");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_logout:
                        final android.app.AlertDialog loading = new ProgressDialog(Menu.this);
                        loading.setMessage("Logout...");
                        loading.show();

                        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
                        preferences.edit().clear().commit();
                        Intent intent = new Intent(Menu.this,Login.class);
                        loading.dismiss();
                        startActivity(intent);
                        finish();
                        break;

                        default:
                        return true;
                }
                return true;
            }
        });

    }
    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.alert)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    }
}