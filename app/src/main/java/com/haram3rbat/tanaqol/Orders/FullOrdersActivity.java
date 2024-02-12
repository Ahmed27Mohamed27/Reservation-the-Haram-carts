package com.haram3rbat.tanaqol.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.R;

import java.time.LocalDate;
import java.util.HashMap;

public class FullOrdersActivity extends AppCompatActivity {

    Button cancel_hagz, show_details, edit_hagz;
    DatabaseReference reference;
    String deviceID;
    Toolbar toolbar;
    RelativeLayout re;
    LinearLayout rere;
    TextView data_time, kind_hagz, state, state_car, kind_car, size_car, name_user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_orders);

        cancel_hagz = findViewById(R.id.cancel_hagz);
        show_details = findViewById(R.id.show_datails);
        edit_hagz = findViewById(R.id.edit_hagz);
        size_car = findViewById(R.id.size_car);
        data_time = findViewById(R.id.data_time);
        kind_hagz = findViewById(R.id.kind_hagz);
        state = findViewById(R.id.state);
        state_car = findViewById(R.id.state_car);
        kind_car = findViewById(R.id.kind_car);
        name_user = findViewById(R.id.name_user);
        rere=findViewById(R.id.rere);
        re=findViewById(R.id.re);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        reference= FirebaseDatabase.getInstance ().getReference ().child ( "Users" ).child(deviceID);

        show_data();
        show_details();
        cancel_hagz();

    }
    private void show_data() {

        reference.child("Orders").child("تم التنفيذ").addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    LocalDate currentDate = LocalDate.now();
                    int day = currentDate.getDayOfMonth();
                    int month = currentDate.getMonthValue();
                    int year = currentDate.getYear();
                    String formattedDate = String.format("%02d/%02d/%04d", day, month, year);
                    String selectedData = snapshot.child("time_data_recive").getValue().toString();
                    boolean check = formattedDate.equals(selectedData);
                    if (check) {
                        state.setText("تم التنفيذ");
                    }

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String  name = snapshot.child("name").getValue().toString();
                            name_user.setText(name);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    if (snapshot.child("none").exists()) {

                        String c = snapshot.child("kind_car").getValue().toString();
                        String z = snapshot.child("size_car").getValue().toString();
                        String d = snapshot.child("data").getValue().toString();
                        String f = snapshot.child("time").getValue().toString();
                        String m = snapshot.child("state_car").getValue().toString();
                        String s = snapshot.child("state_of_hagz").getValue().toString();
                        if (check) {
                            reference.child(deviceID).child("Orders").child("تم التنفيذ").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot ds) {
                                    state.setText("تم التنفيذ");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            String v = snapshot.child("none").getValue().toString();
                            if (v.equals("ملغي")) {
                                state.setText("ملغي");
                                state.setBackgroundResource(R.drawable.background_hagz_red);
                            } else if (v.equals("تم التنفيذ")) {
                                state.setText("تم التنفيذ");
                                state.setBackgroundResource(R.drawable.background_hagz_green);
                            } else {
                                state.setText("جاري التنفيذ");
                                state.setBackgroundResource(R.drawable.background_hagz_green);
                            }
                        }
                        data_time.setText(d + " @ " + f);
                        kind_hagz.setText(s);
                        state_car.setText(m);
                        kind_car.setText(c);
                        size_car.setText(z);

                    } else {

                        String c = snapshot.child("kind_car").getValue().toString();
                        String z = snapshot.child("size_car").getValue().toString();
                        String d = snapshot.child("data").getValue().toString();
                        String f = snapshot.child("time").getValue().toString();
                        String m = snapshot.child("state_car").getValue().toString();
                        String s = snapshot.child("state_of_hagz").getValue().toString();

                        data_time.setText(d + " @ " + f);
                        kind_hagz.setText(s);
                        state_car.setText(m);
                        kind_car.setText(c);
                        size_car.setText(z);
                        state.setText("تم التنفيذ");
                        state.setBackgroundResource(R.drawable.background_hagz_green);
                        rere.setVisibility(View.GONE);

                    }
                } else {
                    re.setVisibility(View.GONE);
                    rere.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( FullOrdersActivity.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );

    }
    private void show_details() {

        show_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FullOrdersActivity.this, DetailsHagzActivity.class));
            }
        });

        edit_hagz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FullOrdersActivity.this, EditHagzActivity.class));
            }
        });

    }
    private void cancel_hagz() {

        cancel_hagz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap map = new HashMap();
                map.put("none", "ملغي");

                reference.child("Orders").child("تم التنفيذ").updateChildren(map);
                Toast.makeText(FullOrdersActivity.this, "تم الغاء الحجز بنجاح", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}