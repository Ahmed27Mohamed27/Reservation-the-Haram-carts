package com.haram3rbat.tanaqol.uiMotabara;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
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

public class InfoHag extends AppCompatActivity {

    TextView name_hag, numPhone_hag, location_hag, number_old, sick_hag, text, Accept, Cancel, Call;
    DatabaseReference reference;
    DatabaseReference room;
    String deviceID;
    Toolbar toolbar;
    LinearLayout re;
    String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_hag);

        name_hag = findViewById(R.id.name_hag);
        numPhone_hag = findViewById(R.id.numPhone_hag);
        location_hag = findViewById(R.id.location_hag);
        number_old = findViewById(R.id.number_old);
        sick_hag = findViewById(R.id.sick_hag);
        Accept = findViewById(R.id.Accept);
        Cancel = findViewById(R.id.Cancel);
        text = findViewById(R.id.text);
        Call = findViewById(R.id.Call);
        re = findViewById(R.id.re);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        reference = FirebaseDatabase.getInstance().getReference().child("Users_Motabara3")
                .child(deviceID).child("InfoHag");

        show_data();
        onClick();
    }

    private void show_data() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("name_hag").exists()) {

                    uid = snapshot.child("uid").getValue().toString();
                    room = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String name = snapshot.child("name_hag").getValue().toString();
                    String numPhone = snapshot.child("numPhone_hag").getValue().toString();
                    String location = snapshot.child("location_hag").getValue().toString();
                    String numberold = snapshot.child("number_old_hag").getValue().toString();
                    String sick = snapshot.child("sick_hag").getValue().toString();

                    name_hag.setText(name);
                    numPhone_hag.setText(numPhone);
                    location_hag.setText(location);
                    number_old.setText(numberold);
                    sick_hag.setText(sick);

                } else {
                    re.setVisibility(View.GONE);
                    text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void onClick() {

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoHag.this, HomeMotabaraActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(InfoHag.this, "تمت الموافقة", Toast.LENGTH_SHORT).show();

                room.child("ListMotabaraOFF").getRef().removeValue();

                HashMap map = new HashMap();
                map.put("none", "تم التنفيذ");

                room.child("Orders").child("تم التنفيذ").updateChildren(map);
                reference.updateChildren(map);

                reference.child("name_hag").getRef().removeValue();

                HashMap map2 = new HashMap();
                map2.put("chat", "المتطوع وافق علي الطلب");

                room.updateChildren(map2);

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoHag.this, HomeMotabaraActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(InfoHag.this, "تم الرفض", Toast.LENGTH_SHORT).show();

                room.child("ListMotabaraOFF").getRef().removeValue();

                reference.child("name_hag").getRef().removeValue();

                HashMap map2 = new HashMap();
                map2.put("chat", "المتطوع رفض الطلب");

                room.updateChildren(map2);

            }
        });

        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String numPhone = snapshot.child("numPhone_hag").getValue().toString();

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + numPhone));
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

    }

}
