package com.haram3rbat.tanaqol.Orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.R;

import java.time.LocalDate;

public class OrdersActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    Toolbar toolbar;
    String deviceID;
    RelativeLayout re, rere, rel;
    TextView data_time, kind_hagz, state, data_time2, kind_hagz2, state2, more;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        data_time = findViewById(R.id.data_time);
        kind_hagz = findViewById(R.id.kind_hagz);
        state = findViewById(R.id.state);
        data_time2 = findViewById(R.id.data_time2);
        kind_hagz2 = findViewById(R.id.kind_hagz2);
        state2 = findViewById(R.id.state2);
        more = findViewById(R.id.more);
        rere = findViewById(R.id.rere);
        re = findViewById(R.id.re);
        rel = findViewById(R.id.rel);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        show_data();

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the Up button click
                onBackPressed(); // or navigate to the parent activity as needed
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void show_data() {

        reference.child(deviceID).child("Orders").child("ملغي").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("none").exists()) {

                    String d = snapshot.child("data").getValue().toString();
                    String f = snapshot.child("time").getValue().toString();
                    String s = snapshot.child("state_of_hagz").getValue().toString();

                    data_time2.setText(d + " @ " + f);
                    kind_hagz2.setText(s);
                    state2.setText("ملغي");
                    state2.setBackgroundResource(R.drawable.background_hagz_red);

                } else {
                    rere.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrdersActivity.this, "Unable to fetch data" + error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        reference.child(deviceID).child("Orders").child("تم التنفيذ").addValueEventListener(new ValueEventListener() {
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
                        reference.child(deviceID).child("Orders").child("تم التنفيذ").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot ds) {
                                state.setText("تم التنفيذ");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if (snapshot.child("none").exists()) {

                        String d = snapshot.child("data").getValue().toString();
                        String f = snapshot.child("time").getValue().toString();
                        String s = snapshot.child("state_of_hagz").getValue().toString();

                        data_time.setText(d + " @ " + f);
                        kind_hagz.setText(s);

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
                                more.setVisibility(View.GONE);
                            } else if (v.equals("تم التنفيذ")) {
                                state.setText("تم التنفيذ");
                                state.setBackgroundResource(R.drawable.background_hagz_green);
                            } else {
                                state.setText("جاري التنفيذ");
                                state.setBackgroundResource(R.drawable.background_hagz_green);
                            }
                        }

                    } else {
                        String d = snapshot.child("data").getValue().toString();
                        String f = snapshot.child("time").getValue().toString();
                        String s = snapshot.child("state_of_hagz").getValue().toString();

                        data_time.setText(d + " @ " + f);
                        kind_hagz.setText(s);
                        state.setText("تم التنفيذ");
                        state.setBackgroundResource(R.drawable.background_hagz_green);
                    }

                } else {
                    re.setVisibility(View.GONE);
                    rel.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrdersActivity.this, "Unable to fetch data" + error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersActivity.this, FullOrdersActivity.class);
                startActivity(intent);
            }
        });

    }

}