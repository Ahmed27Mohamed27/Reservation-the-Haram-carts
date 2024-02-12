package com.haram3rbat.tanaqol.uiMotabara;

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

public class OrdersMotabaraActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference, reference2;
    Toolbar toolbar;
    String deviceID;
    RelativeLayout rel, rel2, re, reHag;
    TextView data_time, kind_hagz, state, more, s, data_time2, kind_hagz2, state2, more2, s2, HagHagz;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_motabara);

        data_time=findViewById(R.id.data_time);
        kind_hagz=findViewById(R.id.kind_hagz);
        state=findViewById(R.id.state);
        more=findViewById(R.id.more);
        rel=findViewById(R.id.rel);
        re=findViewById(R.id.re);
        s=findViewById(R.id.s);
        data_time2=findViewById(R.id.data_time2);
        kind_hagz2=findViewById(R.id.kind_hagz2);
        state2=findViewById(R.id.state2);
        more2=findViewById(R.id.more2);
        rel2=findViewById(R.id.rel2);
        reHag=findViewById(R.id.reHag);
        s2=findViewById(R.id.s2);
        HagHagz=findViewById(R.id.HagHagz);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar ( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        reference= FirebaseDatabase.getInstance().getReference().child("Users_Motabara3");
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users_Motabara3")
                .child(deviceID).child("InfoHag");

        show_data();

    }

    @Override
    public void onBackPressed() {
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

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("none").exists()) {
                        String uid = snapshot.child("uid").getValue().toString();
                        DatabaseReference room = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(uid).child("Orders")
                                .child("تم التنفيذ");

                        room.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    if (snapshot.child("none").exists()) {

                                        String f = snapshot.child("state_of_hagz").getValue().toString();
                                        String d = snapshot.child("data").getValue().toString();
                                        String c = snapshot.child("time").getValue().toString();
                                        kind_hagz2.setText(f);

                                        String s = snapshot.child("none").getValue().toString();

                                        if (s.equals("ملغي")) {
                                            state2.setText("ملغي");
                                            rel2.setVisibility(View.GONE);
                                            state2.setBackgroundResource(R.drawable.background_hagz_red);
                                        } else {
                                            state2.setText("جاري التنفيذ");
                                            state2.setBackgroundResource(R.drawable.background_hagz_green);
                                        }

                                        data_time2.setText(d + "@" + c);

                                    } else {
                                        rel2.setVisibility(View.GONE);

                                        String f = snapshot.child("state_of_hagz").getValue().toString();
                                        String d = snapshot.child("data").getValue().toString();
                                        String c = snapshot.child("time").getValue().toString();
                                        kind_hagz2.setText(f);

                                        data_time2.setText(d + "@" + c);

                                        state2.setText("تم التنفيذ");
                                        state2.setBackgroundResource(R.drawable.background_hagz_green);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        reHag.setVisibility(View.GONE);
                        rel2.setVisibility(View.GONE);
                        HagHagz.setVisibility(View.GONE);
                    }
                } else {
                    reHag.setVisibility(View.GONE);
                    rel2.setVisibility(View.GONE);
                    HagHagz.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child(deviceID).child("Tabaro3").addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("none").exists()) {

                        String d = snapshot.child("kind_tabara3").getValue().toString();
                        if (snapshot.child("kind_withdraw").exists()) {
                            String f = snapshot.child("kind_withdraw").getValue().toString();
                            kind_hagz.setText(f);
                        } else {
                            kind_hagz.setVisibility(View.GONE);
                            s.setVisibility(View.GONE);
                        }

                        String s = snapshot.child("none").getValue().toString();

                        if (s.equals("ملغي")) {
                            more.setVisibility(View.GONE);
                        }

                        data_time.setText(d);
                        state.setText(s);
                        state.setBackgroundResource(R.drawable.background_hagz_red);

                    } else {
                        rel.setVisibility(View.GONE);

                        String d = snapshot.child("kind_tabara3").getValue().toString();
                        if (snapshot.child("kind_withdraw").exists()) {
                            String f = snapshot.child("kind_withdraw").getValue().toString();
                            kind_hagz.setText(f);
                        } else {
                            kind_hagz.setVisibility(View.GONE);
                            s.setVisibility(View.GONE);
                        }

                        data_time.setText(d);

                        state.setText("تم التنفيذ");
                        state.setBackgroundResource(R.drawable.background_hagz_green);
                        more.setVisibility(View.GONE);
                    }
                } else {
                    rel.setVisibility(View.GONE);
                    re.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( OrdersMotabaraActivity.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersMotabaraActivity.this, FullOrdersMotabaraActivity.class);
                startActivity(intent);
            }
        });

        more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersMotabaraActivity.this, FullOrdersHagzMotabaraActivity.class);
                startActivity(intent);
            }
        });

    }

}