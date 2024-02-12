package com.haram3rbat.tanaqol.Admin.Motabara_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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

import java.util.HashMap;

public class InfonTabaro3 extends AppCompatActivity {

    Button cancel, done;
    DatabaseReference reference, reference2;
    String deviceID;
    Toolbar toolbar;
    String name;
    RelativeLayout re;
    LinearLayout rere;
    TextView kind_tabara3, kind_withdraw, name_user, state, data_time_hagz, s;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infon_tabaro3);

        data_time_hagz = findViewById(R.id.data_time_hagz);
        kind_tabara3 = findViewById(R.id.kind_tabara3);
        kind_withdraw = findViewById(R.id.kind_withdraw);
        name_user = findViewById(R.id.name_user);
        state = findViewById(R.id.state);
        cancel = findViewById(R.id.cancel);
        done = findViewById(R.id.done);
        rere = findViewById(R.id.rere);
        re = findViewById(R.id.re);
        s = findViewById(R.id.s);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        reference = FirebaseDatabase.getInstance().getReference().child("Users_Motabara3").child(deviceID);

        reference2 = FirebaseDatabase.getInstance().getReference().child("Users");

        show_data();

        done_cancel();

    }

    private void show_data() {

        reference.child("Tabaro3").addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    String name1 = sharedPreferences.getString("name", "");

                    name_user.setText(name1);

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
                Toast.makeText ( InfonTabaro3.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );

    }

    private void done_cancel() {

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("Tabaro3").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {

                        ds.child("none").getRef().removeValue();
                        Toast.makeText(InfonTabaro3.this, "تم التنفيذ بنجاح", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap map = new HashMap();
                map.put("none", "ملغي");

                reference.child("Tabaro3").updateChildren(map);
                Toast.makeText(InfonTabaro3.this, "تم الغاء الحجز بنجاح", Toast.LENGTH_SHORT).show();
                finish();

            }
        });


    }

}