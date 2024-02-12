package com.haram3rbat.tanaqol.Admin.User_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.R;

import java.util.HashMap;

public class InfoHagz extends AppCompatActivity {

    Button cancel_hagz, done;
    DatabaseReference reference;
    String deviceID;
    Toolbar toolbar;
    String name;
    RelativeLayout re;
    LinearLayout rere;
    TextView data_time, kind_hagz, state, state_car, kind_car, size_car, name_user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infon_hagz);

        cancel_hagz = findViewById(R.id.cancel_hagz);
        done = findViewById(R.id.done);
        data_time = findViewById(R.id.data_time);
        kind_hagz = findViewById(R.id.kind_hagz);
        size_car = findViewById(R.id.size_car);
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
                    if (snapshot.child("none").exists()) {

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                name = snapshot.child("name").getValue().toString();
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
                } else {
                    re.setVisibility(View.GONE);
                    rere.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( InfoHagz.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );

    }
    private void show_details() {

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("Orders").child("تم التنفيذ").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {

                        HashMap map = new HashMap();
                        map.put("none", "ملغي");

                        reference.child("Orders")
                                .child("ملغي").updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        ds.child("none").getRef().removeValue();
                                        Toast.makeText(InfoHagz.this, "تم التنفيذ بنجاح", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }
    private void cancel_hagz() {

        cancel_hagz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("Orders").child("تم التنفيذ").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {

                        HashMap map = new HashMap();
                        map.put("none", "ملغي");

                        reference.child("Orders")
                                .child("تم التنفيذ").updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(InfoHagz.this, "تم الغاء الحجز بنجاح", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }

}