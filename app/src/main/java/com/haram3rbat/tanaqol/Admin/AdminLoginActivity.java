package com.haram3rbat.tanaqol.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import io.paperdb.Paper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.Admin.User_data.MainAdminActivity;
import com.haram3rbat.tanaqol.LoginAndRegister.ResetActivity;
import com.haram3rbat.tanaqol.Adapter.Prevalent;
import com.haram3rbat.tanaqol.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminLoginActivity extends AppCompatActivity {
    EditText emailEdit,passEdit;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth auth;
    TextView forgot;
    DatabaseReference reference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_admin_login );

        initialize ();
        auth=FirebaseAuth.getInstance ();

        reference=FirebaseDatabase.getInstance().getReference().child("Admin");
        user=auth.getCurrentUser();

        if (user != null) {

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String email = snapshot.child("email").getValue().toString();

                    if (user.getEmail().equals(email)) {
                        startActivity(new Intent(AdminLoginActivity.this, MainAdminActivity.class));
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
        login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String pass = passEdit.getText().toString();

                Paper.book().write(Prevalent.UserEmailKey, email);
                Paper.book().write(Prevalent.UserPasswordKey, pass);


                if (TextUtils.isEmpty(emailEdit.getText().toString()))
                {
                    emailEdit.setError ( "يرجي أدخال البريد الألكتروني" );
                    return;
                }
                else if (TextUtils.isEmpty(passEdit.getText().toString()))
                {
                    passEdit.setError ( "يرجي أدخال كلمة المرور" );
                    return;
                }

                signIn(emailEdit.getText().toString(), passEdit.getText().toString());
            }
        } );

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginActivity.this, ResetActivity.class));
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

                            if (email.equals("admin@gmail.com") && password.equals("admin@123")) {

                                progressBar.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                startActivity(new Intent(AdminLoginActivity.this, MainAdminActivity.class));
                                finish();
                                Toast.makeText(AdminLoginActivity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                Toast.makeText(AdminLoginActivity.this, "يوجد خطأ في البريد الألكتروني او كلمة المرور", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(AdminLoginActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
      }

    private void initialize()
    {
        emailEdit=findViewById ( R.id.l_email );
        passEdit=findViewById ( R.id.l_password );
        login=findViewById ( R.id.loginBtn );
        forgot=findViewById ( R.id.forget );
        progressBar=findViewById ( R.id.progressBar );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}