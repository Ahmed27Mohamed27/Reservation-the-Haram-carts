package com.haram3rbat.tanaqol.LoginAndRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.haram3rbat.tanaqol.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {
    EditText editText_email;
    Button submit;
    FirebaseAuth auth;
    Toolbar toolbar;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        editText_email=findViewById(R.id.edit_email);
        submit=findViewById(R.id.submit);
        progressBar = findViewById(R.id.progressBar);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                String mail=editText_email.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    progressBar.setVisibility(View.GONE);
                                    submit.setVisibility(View.VISIBLE);
                                    Toast.makeText(ResetActivity.this, "تم أرسال رسالة تغيير كلمة المرور الي بريدك الالكتروني", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    submit.setVisibility(View.VISIBLE);
                                    Toast.makeText(ResetActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

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

}