package com.haram3rbat.tanaqol.LoginMotabara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.haram3rbat.tanaqol.CountryPiker.CountryCodePicker;
import com.haram3rbat.tanaqol.Model.UserModel;
import com.haram3rbat.tanaqol.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RegisterMotabaraActivity extends AppCompatActivity {
    EditText firstName, lastName, mobileNumber, IdNumber, Email, Password, ConfirmPassword, Number_old;
    Spinner country;
    CheckBox Checkbox;
    CountryCodePicker countryCodePicker;
    Button register;
    ProgressBar progressBar;
    FirebaseAuth auth;
    String deviceID;
    Toolbar toolbar;
    FirebaseUser user;
    String selectedItem, countryPikerItem;
    boolean isChcked1, isCheckcountry;
    String IDnumber, selectedState;
    DatabaseReference reference;
    private static final int REQUEST_MAPS = 1;

    @SuppressLint({"HardwareIds", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register_motabara );
        FirebaseApp.initializeApp(RegisterMotabaraActivity.this);


        initialize();
        auth=FirebaseAuth.getInstance ();
        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        reference=FirebaseDatabase.getInstance().getReference().child("Users_Motabara3");
        user=auth.getCurrentUser();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar ( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> items = generateCountryList();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item_hint1, items);
        country.setAdapter(adapter2);

        clickListener ();

    }

    private void clickListener()
    {

        countryCodePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryPikerItem = countryCodePicker.selectedCountryCode();
            }
        });

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle selection
                if (position > 0) {
                    selectedItem = (String) parentView.getItemAtPosition(position);
                    isCheckcountry = true;
                } else {
                    isCheckcountry = false;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        Checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Checkbox is checked
                    isChcked1 = true;
                } else {
                    // Checkbox is unchecked
                    isChcked1 = false;
                }
            }
        });

        register.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(firstName.getText().toString()))
                {
                    firstName.setError ( "يرجي أدخال الاسم الأول" );
                    return;
                }
                else if (TextUtils.isEmpty(lastName.getText().toString()))
                {
                    lastName.setError ( "يرجي أدخال الاسم الأخير" );
                    return;
                }
                else if (Number_old.getText().toString().isEmpty())
                {
                    Number_old.setError("يرجي تحديد عمرك");
                    return;
                }
                else if (!isCheckcountry)
                {
                    Toast.makeText(RegisterMotabaraActivity.this, "يرجي أدخال دولتك", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(mobileNumber.getText().toString()) || mobileNumber.getText().toString().length() < 10)
                {
                    mobileNumber.setError ( "يرجي أدخال رقم الهاتف بشكل صحيح" );
                    return;
                }
                else if (TextUtils.isEmpty(Email.getText().toString()) || !Email.getText().toString().endsWith(".com"))
                {
                    Email.setError ( "يرجي أدخال البريد الالكتروني بشكل صحيح" );
                    return;
                }
                else if (TextUtils.isEmpty(Password.getText().toString()) || Password.getText().toString().length() < 6)
                {
                    Password.setError ( "يرجي أدخال كلمة مرور مكونة من 6 او اكثر (احرف او ارقام)" );
                    return;
                }
                else if (TextUtils.isEmpty(ConfirmPassword.getText().toString()) || !Password.getText().toString().equals(ConfirmPassword.getText().toString()))
                {
                    ConfirmPassword.setError ( "كلمة المرور خاطئة" );
                    return;
                }
                else if (!isChcked1)
                {
                    Toast.makeText(RegisterMotabaraActivity.this, "يرجي الضغط علي زر التعهد", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = Email.getText().toString();
                String pass = Password.getText().toString();

                createAccount(email, pass);

            }
        });

    }

    private void createAccount(final String email, final String pass) {

        progressBar.setVisibility (View.VISIBLE);
        register.setVisibility(View.GONE);

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            progressBar.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);
                            updateUi(email, pass);
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);
                            Toast.makeText ( RegisterMotabaraActivity.this, "Error"+task.getException ().getMessage (), Toast.LENGTH_SHORT ).show ();
                        }
                    }
                });


    }
    private void updateUi(String email, String password) {

        UserModel userModel=new UserModel();
        userModel.setName(firstName.getText().toString() + " " + lastName.getText().toString());
        userModel.setEmail(email);
        userModel.setID(IdNumber.getText().toString());
        userModel.setLocation("المتطوع ليس لديه عنوان");
        userModel.setMobileNumber(countryCodePicker.selectedCountryCode() + mobileNumber.getText().toString());
        userModel.setCountry(selectedItem);
        userModel.setNumberOld(Number_old.getText().toString());
        userModel.setSickHag("no");
        userModel.setImage("null");
        userModel.setPassword(password);
        userModel.setDeviceID(deviceID);

        reference.child(deviceID).setValue(userModel)
                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ())
                        {
                            Toast.makeText ( RegisterMotabaraActivity.this, "تم تسجيل الحساب بنجاح", Toast.LENGTH_SHORT ).show ();

                            progressBar.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);

                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefMotabara", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("phone", countryCodePicker.selectedCountryCode() + mobileNumber.getText().toString());
                            myEdit.apply();

                            startActivity ( new Intent ( RegisterMotabaraActivity.this, LoginMotabaraActivity.class ) );
                            finish();
                        }else {
                            Toast.makeText ( RegisterMotabaraActivity.this, "Error"+task.getException ().getMessage (), Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } );
    }



    private void initialize()
    {
        firstName = findViewById( R.id.firstName );
        lastName = findViewById( R.id.lastName );
        country = findViewById( R.id.country );
        mobileNumber = findViewById( R.id.mobileNumber );
        IdNumber = findViewById( R.id.IdNumber );
        Email = findViewById( R.id.Email );
        Password = findViewById( R.id.Password );
        ConfirmPassword = findViewById( R.id.confirmPassword );
        Number_old = findViewById( R.id.Number_old );
        Checkbox = findViewById( R.id.checkbox );
        register = findViewById( R.id.register );
        countryCodePicker = findViewById( R.id.ccp_phone );
        progressBar = findViewById( R.id.progressBar );
    }
    private List<String> generateCountryList() {
        Locale[] locales = Locale.getAvailableLocales();
        List<String> countryList = new ArrayList<>();
        countryList.add("الدولة");
        for (Locale locale : locales) {
            if (!locale.getCountry().isEmpty()) {
                String countryName = locale.getDisplayCountry();
                if (!countryList.contains(countryName)) {
                    countryList.add(countryName);
                }
            }
        }

        return countryList;
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