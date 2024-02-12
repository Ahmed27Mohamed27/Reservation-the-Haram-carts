package com.haram3rbat.tanaqol.uiMotabara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
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

public class ChatMotabaraActivity extends AppCompatActivity {

    Button btn;
    TextView message, read;
    DatabaseReference reference;
    String deviceID;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_motabara);

        btn = findViewById(R.id.btn);
        message = findViewById(R.id.message);
        read = findViewById(R.id.read);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        reference= FirebaseDatabase.getInstance ().getReference ().child ( "Users_Motabara3" );

        reference.child(deviceID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("chat").exists()) {
                    if (snapshot.child("chat").getValue().toString().equals("تم")) {
                        message.setVisibility(View.GONE);
                        read.setVisibility(View.VISIBLE);
                    } else {
                        message.setText(snapshot.child("chat").getValue().toString());
                    }
                } else {
                    message.setVisibility(View.GONE);
                    read.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child(deviceID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {

                        HashMap map = new HashMap();
                        map.put("chat", "تم");

                        reference.child(deviceID).updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ChatMotabaraActivity.this, "تم قرأت الرسالة بنجاح", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}