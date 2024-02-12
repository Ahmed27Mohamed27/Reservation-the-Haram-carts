package com.haram3rbat.tanaqol.uiMotabara;

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

public class FullOrdersMotabaraActivity extends AppCompatActivity {

    Button edit_hagz, info_hag;
    DatabaseReference reference;
    DatabaseReference reference1;
    String deviceID;
    Toolbar toolbar;
    RelativeLayout re;
    LinearLayout rere;
    TextView kind_tabara3, kind_withdraw, name_user, state, data_time_hagz, s;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_orders_motabara);

        data_time_hagz = findViewById(R.id.data_time_hagz);
        kind_tabara3 = findViewById(R.id.kind_tabara3);
        edit_hagz = findViewById(R.id.edit_hagz);
        kind_withdraw = findViewById(R.id.kind_withdraw);
        name_user = findViewById(R.id.name_user);
        state = findViewById(R.id.state);
//        cancel = findViewById(R.id.cancel);
        s = findViewById(R.id.s);
//        done = findViewById(R.id.done);
        rere = findViewById(R.id.rere);
        re = findViewById(R.id.re);
        info_hag = findViewById(R.id.info_hag);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        reference= FirebaseDatabase.getInstance ().getReference ().child ( "Users_Motabara3" );
        reference1= FirebaseDatabase.getInstance ().getReference ().child ( "Users" );

        show_data();

        done_cancel();

    }
    private void show_data() {

        reference.child(deviceID).child("Tabaro3").addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                        reference.child(deviceID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String name = snapshot.child("name").getValue().toString();
                                name_user.setText(name);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    if (snapshot.child("none").exists()) {

                        if (snapshot.child("kind_tabara3").getValue().toString().equals("التطوع بالوقت")) {
                            data_time_hagz.setText("سوف يتم التواصل معاك من قبل الحاج في اسرع وقت");
                            kind_withdraw.setVisibility(View.GONE);
                            s.setVisibility(View.GONE);
                        } else if (snapshot.child("kind_tabara3").getValue().toString().equals("التطوع بالمال")) {
                            String d = snapshot.child("kind_withdraw").getValue().toString();
                            kind_withdraw.setText(d);
                        } else {
                            kind_withdraw.setVisibility(View.GONE);
                            s.setVisibility(View.GONE);
                        }

                        String c = snapshot.child("kind_tabara3").getValue().toString();
                        String s = snapshot.child("none").getValue().toString();
                        if (c.equals("التطوع بدف عربة")) {
                            info_hag.setVisibility(View.VISIBLE);
                        }
                        kind_tabara3.setText(c);
                        state.setText(s);
                        state.setBackgroundResource(R.drawable.background_hagz_red);

                    } else {

                        if (snapshot.child("kind_tabara3").getValue().toString().equals("التطوع بالوقت")) {
                            data_time_hagz.setText("سوف يتم التواصل معاك من قبل الحاج في اسرع وقت");
                            kind_withdraw.setVisibility(View.GONE);
                            s.setVisibility(View.GONE);
                        } else if (snapshot.child("kind_tabara3").getValue().toString().equals("التطوع بالمال")) {
                            String d = snapshot.child("kind_withdraw").getValue().toString();
                            kind_withdraw.setText(d);
                        } else {
                            kind_withdraw.setVisibility(View.GONE);
                            s.setVisibility(View.GONE);
                        }

                        String c = snapshot.child("kind_tabara3").getValue().toString();
                        if (c.equals("التطوع بدف عربة")) {
                            info_hag.setVisibility(View.VISIBLE);
                        }
                        kind_tabara3.setText(c);
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
                Toast.makeText ( FullOrdersMotabaraActivity.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );

    }
    private void done_cancel() {

        info_hag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FullOrdersMotabaraActivity.this, InfoHag.class));
            }
        });

        edit_hagz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FullOrdersMotabaraActivity.this, EditTabaro3Activity.class));
            }
        });

//        done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                reference.child(deviceID).child("InfoHag").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot ds) {
//                        if (ds.exists()) {
//                            String uid = ds.child("uid").getValue().toString();
//
//                            reference1.child(uid).addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot ds) {
//
//                                    ds.child("ListMotabaraOFF").getRef().removeValue();
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                reference.child(deviceID).child("Tabaro3").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot ds) {
//
//                        ds.child("none").getRef().removeValue();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                reference.child(deviceID).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot ds) {
//
//                        ds.child("InfoHag").getRef().removeValue();
//                        Toast.makeText(FullOrdersMotabaraActivity.this, "تم التنفيذ بنجاح", Toast.LENGTH_SHORT).show();
//                        finish();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//            }
//        });

//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                HashMap map = new HashMap();
//                map.put("none", "ملغي");
//
//                reference.child(deviceID).child("Tabaro3").updateChildren(map);
//                Toast.makeText(FullOrdersMotabaraActivity.this, "تم الغاء الحجز بنجاح", Toast.LENGTH_SHORT).show();
//                finish();
//
//            }
//        });


    }

}