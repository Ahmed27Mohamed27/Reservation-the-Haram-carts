package com.haram3rbat.tanaqol.uiMotabara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.haram3rbat.tanaqol.Model.Motabara_payment;
import com.haram3rbat.tanaqol.R;
import com.haram3rbat.tanaqol.SliderScreen.SplashActivity;
import com.haram3rbat.tanaqol.SliderScreen.splash;
import com.haram3rbat.tanaqol.uiUser.MainActivity;

public class MainMotabaraActivity extends AppCompatActivity {

    Spinner withdraw_motabara, kind_tabara3;
    TextView logout, profile, OrdersMotabara;
    RelativeLayout withdraw;
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;
    String deviceID;
    Button complete;
    private String selectedState, selectedState2;
    ProgressBar progressBar;
    DatabaseReference reference;
    String selectedState22;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_motabara);

        deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("Users_Motabara3");

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OrdersMotabara = findViewById(R.id.OrdersMotabara);
        kind_tabara3 = findViewById(R.id.kind_tabaro3);
        withdraw = findViewById(R.id.withdraw);
        withdraw_motabara = findViewById(R.id.withdraw_motabara);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);
        complete = findViewById(R.id.complete);
        progressBar = findViewById(R.id.progressBar);

        set_data_in_firebase();

        onClick();

    }

    private void onClick() {

        OrdersMotabara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMotabaraActivity.this, OrdersMotabaraActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMotabaraActivity.this, splash.class);
                startActivity(intent);
                auth.signOut();
                finishAffinity();
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMotabaraActivity.this, ProfileMotabaraActivity.class));
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedState.equals("نوع التطوع")) {
                    Toast.makeText(MainMotabaraActivity.this, "يرجي تحديد نوع التطوع", Toast.LENGTH_SHORT).show();
                }else {
                    if (selectedState.equals("التطوع بالمال")) {
                        if (selectedState2.equals("انواع التطوع بالمال")) {
                            Toast.makeText(MainMotabaraActivity.this, "يرجي تحديد نوع التطوع بالمال", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            complete.setVisibility(View.GONE);
                            selectedState22 = selectedState2;
                            set_data_motabara();
                        }
                    }else {
                        progressBar.setVisibility(View.VISIBLE);
                        complete.setVisibility(View.GONE);
                        set_data_motabara();
                    }
                }

            }
        });

    }

    private void set_data_in_firebase() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.kind_tabaro3, // Assume you have an array resource with items
                R.layout.spinner_item_hint1
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        kind_tabara3.setAdapter(adapter);

        kind_tabara3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedState = kind_tabara3.getSelectedItem().toString();
                if (selectedState.equals("التطوع بالمال")) {
                    withdraw.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.withdraw_motabara, // Assume you have an array resource with items
                R.layout.spinner_item_hint1
        );

        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        withdraw_motabara.setAdapter(adapter2);

        withdraw_motabara.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedState2 = withdraw_motabara.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void set_data_motabara() {

        Motabara_payment items = new Motabara_payment();
        items.setKind_withdraw(selectedState22);
        items.setKind_tabara3(selectedState);
        items.setNone("جاري التنفيذ");

        reference.child(deviceID).child("Tabaro3").setValue(items)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        complete.setVisibility(View.VISIBLE);
                        Toast.makeText(MainMotabaraActivity.this, "تم التطوع بنجاح", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainMotabaraActivity.this, OrdersMotabaraActivity.class));

                    }
                });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}