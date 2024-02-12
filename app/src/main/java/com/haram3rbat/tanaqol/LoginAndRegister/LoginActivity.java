package com.haram3rbat.tanaqol.LoginAndRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.Adapter.Prevalent;
import com.haram3rbat.tanaqol.R;
import com.haram3rbat.tanaqol.uiUser.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    EditText emailEdit, passEdit;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth auth;
    TextView goToRegister, forgot;
    DatabaseReference reference;
    FirebaseUser user;
    String deviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
        auth = FirebaseAuth.getInstance();

        deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        user = auth.getCurrentUser();

        if (user != null) {

            reference.child(deviceID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("phoneCode").exists()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        clickListener();

        Paper.init(this);

    }

    private void clickListener() {
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String pass = passEdit.getText().toString();

                Paper.book().write(Prevalent.UserEmailKey, email);
                Paper.book().write(Prevalent.UserPasswordKey, pass);


                if (TextUtils.isEmpty(emailEdit.getText().toString())) {
                    emailEdit.setError("يرجي أدخال البريد الألكتروني");
                    return;
                } else if (TextUtils.isEmpty(passEdit.getText().toString())) {
                    passEdit.setError("يرجي أدخال كلمة المرور");
                    return;
                }
                signIn(email, pass);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetActivity.class));
            }
        });

    }

    private void signIn(String email, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            reference.child(deviceID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {

                                        if (snapshot.child("email").getValue().toString()
                                                .equals(email) && snapshot.child("password").getValue()
                                                .toString().equals(password)) {

                                            if (snapshot.child("phoneCode").exists()) {
                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            } else {
                                                startActivity(new Intent(LoginActivity.this, OTPActivity.class));
                                            }

                                        } else {
                                            Toast.makeText(LoginActivity.this, "هذا الحساب خطأ", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);

                            reference.child(deviceID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.child("email").getValue().toString().equals(email)) {
                                        Toast.makeText(LoginActivity.this, "البريد الالكتروني خطأ", Toast.LENGTH_LONG).show();
                                    } else if (!snapshot.child("password").getValue().toString().equals(password)) {
                                        Toast.makeText(LoginActivity.this, "كلمة المرور خطأ", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                });

    }

    private void initialize() {
        emailEdit = findViewById(R.id.l_email);
        passEdit = findViewById(R.id.l_password);
        login = findViewById(R.id.loginBtn);
        forgot = findViewById(R.id.forget);
        progressBar = findViewById(R.id.progressBar);
        goToRegister = findViewById(R.id.goToRegister);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}