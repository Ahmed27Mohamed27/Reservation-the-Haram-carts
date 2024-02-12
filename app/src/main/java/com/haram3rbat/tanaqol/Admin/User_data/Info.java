package com.haram3rbat.tanaqol.Admin.User_data;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.haram3rbat.tanaqol.R;

import java.util.HashMap;

public class Info extends AppCompatActivity {

    TextView name, email, password, number, id, deviceID;
    Button btnHagz, btnEditText;
    EditText Text_To_User;
    FirebaseUser user;
    FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnHagz = findViewById(R.id.btnHagz);
        btnHagz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Info.this, InfoHagz.class));
            }
        });

        btnEditText = findViewById(R.id.btnEditText);
        Text_To_User = findViewById(R.id.Text_To_User);

        name = findViewById(R.id.txtName);
        email = findViewById(R.id.txtCountry);
        password = findViewById(R.id.txtId);

        name = findViewById(R.id.name);
        number = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        id = findViewById(R.id.Id);
        deviceID = findViewById(R.id.deviceID);

        String s = getIntent().getExtras().getString("name");
        name.setText(s);
        String n = getIntent().getExtras().getString("email");
        email.setText(n);
        String m = getIntent().getExtras().getString("mobileNumber");
        number.setText(m);
        String o = getIntent().getExtras().getString("password");
        password.setText(o);
        String d = getIntent().getExtras().getString("id");
        id.setText(d);
        String a = getIntent().getExtras().getString("deviceID");
        deviceID.setText(a);

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Users");
                HashMap hashMap = new HashMap();
                hashMap.remove("");
                root.child(deviceID.getText().toString()).setValue(hashMap);
                finish();
                Toast.makeText(Info.this, "تم", Toast.LENGTH_SHORT).show();
            }
        });

        btnEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Text_To_User.getText().toString().isEmpty()) {
                    Text_To_User.setError("يرجي كتابة رسالة للمستخدم");
                } else {
                    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Users");
                    HashMap hashMap = new HashMap();
                    hashMap.put("chat", Text_To_User.getText().toString());
                    root.child(deviceID.getText().toString()).updateChildren(hashMap);
                    Toast.makeText(Info.this, "تم", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}