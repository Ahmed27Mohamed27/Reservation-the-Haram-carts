package com.haram3rbat.tanaqol.uiUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.Adapter.AdapterData3;
import com.haram3rbat.tanaqol.Model.Fetch3;
import com.haram3rbat.tanaqol.R;

import java.util.ArrayList;

public class ListMotabara extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Fetch3> list = new ArrayList<>();
    AdapterData3 adpterData;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference root;
    String deviceID, sickHag, numberOld, location, mobileNumber, name;
    boolean check = true;
    TextView t;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_motabara);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        t = findViewById(R.id.t);
        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adpterData = new AdapterData3(list, this);
        recyclerView.setAdapter(adpterData);
        root = FirebaseDatabase.getInstance().getReference();
        deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        root.child("Users").child(deviceID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("ListMotabaraOFF").exists()) {
                    check = false;
                } else {
                    check = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Users").child(deviceID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("name").getValue().toString();
                mobileNumber = snapshot.child("mobileNumber").getValue().toString();
                location = snapshot.child("location").getValue().toString();
                numberOld = snapshot.child("numberOld").getValue().toString();
                sickHag = snapshot.child("sickHag").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Users_Motabara3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (snapshot1.child("Tabaro3").exists()) {
                            if (check) {
                                Fetch3 fetch = new Fetch3();
                                fetch.setName(snapshot1.child("name").getValue().toString());
                                fetch.setPassword(snapshot1.child("password").getValue().toString());
                                fetch.setEmail(snapshot1.child("email").getValue().toString());
                                fetch.setMobile(snapshot1.child("mobileNumber").getValue().toString());
                                fetch.setUid(snapshot1.child("deviceID").getValue().toString());
                                fetch.setImage(snapshot1.child("image").getValue().toString());
                                fetch.setLocation(snapshot1.child("location").getValue().toString());
                                fetch.setNumber_old(snapshot1.child("numberOld").getValue().toString());
                                fetch.setSick(snapshot1.child("sickHag").getValue().toString());
                                fetch.setUid_hag(deviceID);
                                fetch.setName_hag(name);
                                fetch.setMobile_hag(mobileNumber);
                                fetch.setLocation_hag(location);
                                fetch.setNumber_old_hag(numberOld);
                                fetch.setSick_hag(sickHag);
                                list.add(fetch);
                            } else {
                                t.setText("تمت الموافقة علي متطوع وسوف يتم التواصل معك في اسرع وقت");
                                t.setTextSize(30);
                                t.setVisibility(View.VISIBLE);
                            }
                     } else {
                        Fetch3 fetch = new Fetch3();
                        fetch.setName(snapshot1.child("name").getValue().toString());
                        fetch.setPassword(snapshot1.child("password").getValue().toString());
                        fetch.setEmail(snapshot1.child("email").getValue().toString());
                        fetch.setMobile(snapshot1.child("mobileNumber").getValue().toString());
                        fetch.setUid(snapshot1.child("deviceID").getValue().toString());
                        fetch.setImage(snapshot1.child("image").getValue().toString());
                        fetch.setLocation(snapshot1.child("location").getValue().toString());
                        fetch.setNumber_old(snapshot1.child("numberOld").getValue().toString());
                        fetch.setSick(snapshot1.child("sickHag").getValue().toString());
                        fetch.setUid_hag(deviceID);
                        fetch.setName_hag(name);
                        fetch.setMobile_hag(mobileNumber);
                        fetch.setLocation_hag(location);
                        fetch.setNumber_old_hag(numberOld);
                        fetch.setSick_hag(sickHag);
                        list.add(fetch);
                    }
                }
                adpterData.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}