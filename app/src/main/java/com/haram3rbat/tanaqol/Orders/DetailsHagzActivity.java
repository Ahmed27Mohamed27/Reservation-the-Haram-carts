package com.haram3rbat.tanaqol.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.R;
import com.haram3rbat.tanaqol.uiUser.HomeActivity;

public class DetailsHagzActivity extends AppCompatActivity {

    TextView kind_car, data_start_hagz, time_start_hagz, data_end_hagz, time_end_hagz,
             time_of_hagz, kind_hagz, image_check, text, size_car;
    Button complete;
    DatabaseReference reference;
    String deviceID;
    LinearLayout layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_hagz);

        kind_car = findViewById(R.id.kind_car);
        data_start_hagz = findViewById(R.id.data_start_hagz);
        time_start_hagz = findViewById(R.id.time_start_hagz);
        data_end_hagz = findViewById(R.id.data_end_hagz);
        time_end_hagz = findViewById(R.id.time_end_hagz);
        size_car = findViewById(R.id.size_car);
        time_of_hagz = findViewById(R.id.time_of_hagz);
        kind_hagz = findViewById(R.id.kind_hagz);
        image_check = findViewById(R.id.image_check);
        complete = findViewById(R.id.complete);
        text = findViewById(R.id.text);
        layout = findViewById(R.id.linear);

        reference= FirebaseDatabase.getInstance().getReference().child("Users");

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsHagzActivity.this, HomeActivity.class));
                finish();
            }
        });

        get_data();

    }

    private void get_data() {

        reference.child(deviceID).child("Orders").child("تم التنفيذ").addValueEventListener ( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                if (ds.exists()) {
                    String image_check1;

                    if (ds.child("image_check").exists()) {
                        image_check1 = "نعم";
                    } else {
                        image_check1 = "لا";
                    }

                    String n = ds.child("time").getValue().toString();
                    String e = ds.child("data").getValue().toString();
                    String p = ds.child("time_of_hagz").getValue().toString();
                    String a = ds.child("kind_car").getValue().toString();
                    String z = ds.child("size_car").getValue().toString();
                    String w = ds.child("state_of_hagz").getValue().toString();
                    String r = ds.child("time_data_recive").getValue().toString();

                    kind_car.setText(a);
                    size_car.setText(z);
                    data_start_hagz.setText(e);
                    time_start_hagz.setText(n);
                    data_end_hagz.setText(r);
                    time_end_hagz.setText(r);
                    time_of_hagz.setText(p);
                    kind_hagz.setText(w);
                    image_check.setText(image_check1);

                } else {
                    layout.setVisibility(View.GONE);
                    text.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( DetailsHagzActivity.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );

    }

}