package com.haram3rbat.tanaqol.uiUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.CountryPiker.CountryCodePicker;
import com.haram3rbat.tanaqol.R;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    EditText name_user, email_user, mobileNumber_user, password_user;
    Button edit_done;
    CountryCodePicker countryCodePicker;
    ProgressBar progressBar;
    Toolbar toolbar;
    DatabaseReference reference;
    String deviceID;
    String x;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressBar=findViewById ( R.id.progressBar );
        edit_done = findViewById(R.id.edit_done);
        name_user = findViewById(R.id.name_user);
        email_user = findViewById(R.id.email_user);
        countryCodePicker = findViewById( R.id.ccp_phone );
        mobileNumber_user = findViewById(R.id.mobileNumber_user);
        password_user = findViewById(R.id.password_user);

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        reference= FirebaseDatabase.getInstance ().getReference ().child ( "Users" );

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar ( toolbar );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );

        get_data();

        btnEdit();

    }

    private void get_data() {

        reference.child (deviceID).addValueEventListener ( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n=snapshot.child ( "name" ).getValue ().toString ();
                String e=snapshot.child ( "email" ).getValue ().toString ();
                String s=snapshot.child ( "password" ).getValue ().toString ();
                String v=snapshot.child ( "mobileNumber" ).getValue ().toString ();
                x=snapshot.child ( "mobileNumber" ).getValue ().toString ();

                name_user.setText(n);
                email_user.setText(e);
                password_user.setText(s);
                mobileNumber_user.setText(v);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( EditProfileActivity.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );


    }

    private void btnEdit() {

        countryCodePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = countryCodePicker.selectedCountryCode();
            }
        });

        edit_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(name_user.getText().toString())) {
                   name_user.setError("برجاء كتابة اسم للحساب");
                }
                else if (TextUtils.isEmpty(email_user.getText().toString())) {
                    email_user.setError("برجاء كتابة بريد الكتروني");
                }
                else if (TextUtils.isEmpty(password_user.getText().toString())) {
                    password_user.setError("برجاء كتابة كلمة مرور");
                }
                else if (TextUtils.isEmpty(mobileNumber_user.getText().toString()) || mobileNumber_user.getText().toString().length() < 10) {
                    mobileNumber_user.setError("برجاء كتابة رقم هاتف");
                }
                else {

                    HashMap map = new HashMap();
                    map.put("name", name_user.getText().toString());
                    map.put("email", email_user.getText().toString());
                    map.put("password", password_user.getText().toString());

                    if (TextUtils.equals(mobileNumber_user.getText().toString(), x)) {
                        map.put("mobileNumber", x);
                    } else {
                        map.put("mobileNumber", countryCodePicker.selectedCountryCode() + mobileNumber_user.getText().toString());
                    }

                    edit_data(map);
                }

            }
        });

    }

    private void edit_data(HashMap map) {

        reference.child(deviceID).updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        edit_done.setVisibility(View.VISIBLE);
                        Toast.makeText(EditProfileActivity.this, "تم تعديل بياناتك", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

}