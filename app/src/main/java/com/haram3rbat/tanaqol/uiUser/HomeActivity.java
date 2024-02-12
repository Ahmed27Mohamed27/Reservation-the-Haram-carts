package com.haram3rbat.tanaqol.uiUser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.Orders.OrdersActivity;
import com.haram3rbat.tanaqol.R;
import com.haram3rbat.tanaqol.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    BottomNavigationView navView;
    TextView name_user, plus;
    ImageView chat_user, imageView2;
    DatabaseReference reference;
    String deviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        name_user = findViewById(R.id.name_user);
        chat_user = findViewById(R.id.chat_user);
        imageView2 = findViewById(R.id.imageView2);
        plus = findViewById(R.id.plus);

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        reference= FirebaseDatabase.getInstance ().getReference ().child ( "Users" );

        getDataFromDatabase();

        chat_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ChatActivity.class));
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.hagz:
                        startActivity(new Intent(HomeActivity.this, ListMotabara.class));
                        break;
                    case R.id.OrdersHagz:
                        startActivity(new Intent(HomeActivity.this, OrdersActivity.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        break;
                    case R.id.list:
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        break;
                }
                return false;
            }
        });

    }

    private void getDataFromDatabase() {
        reference.child (deviceID).addValueEventListener ( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n=snapshot.child ( "name" ).getValue ().toString ();

                name_user.setText(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( HomeActivity.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("هل تريد الخروج؟")
                .setMessage("هل انت متأكد انك تريد الخروج؟")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        HomeActivity.super.onBackPressed();
                        finishAffinity();
                        System.exit(0);
                    }
                }).create().show();
    }

}