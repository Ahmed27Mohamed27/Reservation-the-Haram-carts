package com.haram3rbat.tanaqol.uiMotabara;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.R;

import java.util.HashMap;

public class FullOrdersHagzMotabaraActivity extends AppCompatActivity {

    DatabaseReference reference;
    DatabaseReference room2;
    String deviceID;
    Toolbar toolbar;
    RelativeLayout re;
    LinearLayout rere;
    TextView data_time, kind_hagz, state, state_car, kind_car, size_car, name_user;
    Button cancel_hagz, done;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_orders_hagz_motabara);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancel_hagz = findViewById(R.id.cancel_hagz);
        done = findViewById(R.id.done);
        data_time = findViewById(R.id.data_time);
        kind_hagz = findViewById(R.id.kind_hagz);
        size_car = findViewById(R.id.size_car);
        state = findViewById(R.id.state);
        state_car = findViewById(R.id.state_car);
        kind_car = findViewById(R.id.kind_car);
        name_user = findViewById(R.id.name_user);
        rere = findViewById(R.id.rere);
        re = findViewById(R.id.re);

        deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        reference = FirebaseDatabase.getInstance().getReference().child("Users_Motabara3").child(deviceID).child("InfoHag");

        show_data();
        done();
        cancel();

    }

    private void show_data() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String uid = snapshot.child("uid").getValue().toString();
                    DatabaseReference room = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Orders").child("تم التنفيذ");
                    room2 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    room.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("none").exists()) {
                                room2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name = snapshot.child("name").getValue().toString();
                                        name_user.setText(name);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                String c = snapshot.child("kind_car").getValue().toString();
                                String z = snapshot.child("size_car").getValue().toString();
                                String d = snapshot.child("data").getValue().toString();
                                String f = snapshot.child("time").getValue().toString();
                                String m = snapshot.child("state_car").getValue().toString();
                                String s = snapshot.child("state_of_hagz").getValue().toString();
                                String n = snapshot.child("none").getValue().toString();
                                if (n.equals("ملغي")) {
                                    state.setText("ملغي");
                                    state.setBackgroundResource(R.drawable.background_hagz_red);
                                } else {
                                    state.setText("جاري التنفيذ");
                                    state.setBackgroundResource(R.drawable.background_hagz_green);
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
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    re.setVisibility(View.GONE);
                    rere.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void done() {

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                room2.child("Orders").child("تم التنفيذ").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {
                        ds.child("none").getRef().removeValue();

                        reference.child("name_hag").getRef().removeValue();

                        Toast.makeText(FullOrdersHagzMotabaraActivity.this, "تم التنفيذ بنجاح", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private void cancel() {

        cancel_hagz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap map = new HashMap();
                map.put("none", "ملغي");

                reference.getRef().removeValue();

                room2.child("Orders").child("تم التنفيذ").updateChildren(map);
                Toast.makeText(FullOrdersHagzMotabaraActivity.this, "تم الغاء الحجز بنجاح", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}