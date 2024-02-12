package com.haram3rbat.tanaqol.uiUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.haram3rbat.tanaqol.Model.User_Hagz;
import com.haram3rbat.tanaqol.Orders.DetailsHagzActivity;
import com.haram3rbat.tanaqol.Orders.OrdersActivity;
import com.haram3rbat.tanaqol.R;
import com.haram3rbat.tanaqol.SliderScreen.SliderImages;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.SliderScreen.SplashActivity;
import com.haram3rbat.tanaqol.SliderScreen.splash;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference ProductsRef;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ProgressBar progressBar;
    Button complete;
    TextView data_time, none;
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;
    String deviceID;
    String selectedDate;
    String selectedTime;
    String selectedTimeDataRecive;
    ProgressDialog progressDialog;
    CheckBox checkBox;
    Spinner kind_order, kind_cars, opstion, size_cars;
    ImageView image_chck;
    private String selectedState, selectedState2, selectedState3, selectedState4;
    private DatabaseReference reference;
    private static final int PICK_IMAGE = 1;
    public static final int REQUEST_MAPS = 1;
    private Uri photoUri;
    private String imageUrl;
    int sol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Users");

        deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("برجاء الأنتظار...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        Random random = new Random();
        sol = random.nextInt(1000);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data_time = findViewById(R.id.data_time);
        progressBar = findViewById(R.id.progressBar);
        checkBox = findViewById(R.id.checkbox);
        kind_order = findViewById(R.id.kind_order);
        size_cars = findViewById(R.id.size_cars);
        kind_cars = findViewById(R.id.kind_cars);
        opstion = findViewById(R.id.opstion);
        image_chck = findViewById(R.id.image_chack);
        complete = findViewById(R.id.complete);
        none = findViewById(R.id.none);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        data_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        OnClick();

    }

    @Override
    protected void onStart() {
        getDataFromDatabase();

        super.onStart();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.list) {
            Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.help) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        } else if (id == R.id.profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.info) {
            Intent intent = new Intent(MainActivity.this, SliderImages.class);
            startActivity(intent);
        } else if (id == R.id.chat) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://wa.me/+9660509713755"));
            startActivity(intent);
        } else if (id == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, splash.class);
            startActivity(intent);
            auth.signOut();
            finishAffinity();
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getDataFromDatabase() {

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_name);
        TextView userEmailTextView = headerView.findViewById(R.id.user_email);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        userEmailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });


        ProductsRef.child(deviceID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n = snapshot.child("name").getValue().toString();
                String e = snapshot.child("email").getValue().toString();
                String p = snapshot.child("image").getValue().toString();

                userNameTextView.setText(n);
                userEmailTextView.setText(e);

                Glide.with(getApplicationContext()).load(p)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(profileImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Unable to fetch data" + error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Handle the selected date
                        selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);

                        selectedTimeDataRecive = String.format("%02d/%02d/%04d", selectedDay + 1, selectedMonth + 1, selectedYear);

                    }
                },
                year, month, day);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Handle the selected time
                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "مساءً";
                            if (hourOfDay > 12) {
                                hourOfDay -= 12;
                            }
                        } else {
                            amPm = "صباحاً";
                            if (hourOfDay == 0) {
                                hourOfDay = 12;
                            }
                        }
                        selectedTime = String.format("%02d:%02d %s", hourOfDay, minute, amPm);

                        if (selectedDate == null || selectedTime == null) {
                            Toast.makeText(MainActivity.this, "يرجي تحديد الوقت و التاريخ والضغط علي تأكيد", Toast.LENGTH_SHORT).show();
                        } else {
                            data_time.setText(selectedTime + "  " + selectedDate);
                        }

                    }
                },
                hour, minute, false);

        timePickerDialog.show();
        datePickerDialog.show();

    }

    private void OnClick() {

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    image_chck.setVisibility(View.VISIBLE);
                } else {
                    image_chck.setVisibility(View.GONE);
                }
            }
        });

        image_chck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(MainActivity.this)
                        .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, PICK_IMAGE);

                                } else {
                                    Toast.makeText(MainActivity.this, "برجاء تفعيل الأذونات", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                            }
                        }).check();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.kind_order, // Assume you have an array resource with items
                R.layout.spinner_item_hint1
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        kind_order.setAdapter(adapter);

        kind_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedState = kind_order.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.kind_cars, // Assume you have an array resource with items
                R.layout.spinner_item_hint1
        );

        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        kind_cars.setAdapter(adapter2);

        kind_cars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedState2 = kind_cars.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this,
                R.array.opstion, // Assume you have an array resource with items
                R.layout.spinner_item_hint1
        );

        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        opstion.setAdapter(adapter3);

        opstion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedState3 = opstion.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this,
                R.array.size_cars, // Assume you have an array resource with items
                R.layout.spinner_item_hint1
        );

        // Specify the layout to use when the list of choices appears
        adapter4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        size_cars.setAdapter(adapter4);

        size_cars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedState4 = size_cars.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                complete.setVisibility(View.GONE);

                if (selectedState3.equals("أضافة (أختياري)")) {
                    selectedState3 = "لا";
                } else {
                    selectedState3 = opstion.getSelectedItem().toString();
                }

                if (selectedDate == null || selectedTime == null) {
                    progressBar.setVisibility(View.GONE);
                    complete.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "يرجي تحديد الوقت و التاريخ", Toast.LENGTH_SHORT).show();
                } else if (selectedState.equals("نوع الحجز")) {
                    progressBar.setVisibility(View.GONE);
                    complete.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "يرجي تحديد نوع الحجز", Toast.LENGTH_SHORT).show();
                } else if (selectedState2.equals("نوع العربة")) {
                    progressBar.setVisibility(View.GONE);
                    complete.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "يرجي تحديد نوع العربة", Toast.LENGTH_SHORT).show();
                } else if (selectedState4.equals("حجم العربة")) {
                    progressBar.setVisibility(View.GONE);
                    complete.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "يرجي تحديد حجم العربة", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage();
                    set_data_hagz();
//                    reference.child(deviceID).child("Orders").child("تم التنفيذ").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.child("none").exists()) {
//                                Toast.makeText(MainActivity.this, "برجاء الأنتهاء من الحجز السابق لأجراء حجز جديد", Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
//                                complete.setVisibility(View.VISIBLE);
//                            } else if (snapshot.child("none").getValue().toString().equals("جاري التنفيذ")) {
//                                Toast.makeText(MainActivity.this, "برجاء الأنتهاء من الحجز السابق لأجراء حجز جديد", Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
//                                complete.setVisibility(View.VISIBLE);
//                            }
//                            uploadImage();
//                            set_data_hagz();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(MainActivity.this, "Unable to fetch data" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    });
                }
            }
        });

    }

    private void set_data_hagz() {

        User_Hagz item = new User_Hagz();
        item.setData(selectedDate);
        item.setTime(selectedTime);
        item.setState_of_hagz(selectedState);

        User_Hagz items = new User_Hagz();
        items.setData(selectedDate);
        items.setTime(selectedTime);
        items.setCode_car("رقم العربة" + " @ " + sol);
        items.setKind_car(selectedState2);
        items.setSize_car(selectedState4);
        items.setState_car(selectedState3);
        items.setTime_data_recive(selectedTimeDataRecive);
        items.setState_of_hagz(selectedState);
        items.setTime_of_hagz("24 ساعة");
        items.setNone("جاري التنفيذ");

        reference.child(deviceID).child("Orders").child("تم التنفيذ").setValue(items)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "تم الحجز بنجاح", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, DetailsHagzActivity.class));
                        progressBar.setVisibility(View.GONE);
                        complete.setVisibility(View.VISIBLE);
                        reference.child(deviceID).child("Orders").child("ملغي").setValue(item);
                    }
                });

    }

    private void uploadImage() {
        if (photoUri == null) {
            return;
        }
        String filename = user.getUid() + ".jpg";
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReference().child("Images/" + filename);

        progressDialog.show();
        storageReference.putFile(photoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageUrl = uri.toString();

                                uploadImageUrlToDatabase();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                        long p1 = taskSnapshot.getBytesTransferred();
                        long p2 = taskSnapshot.getTotalByteCount();

                        long r = (p1 / 1024);
                        long r2 = (p2 / 1024);

                        progressDialog.setMessage("تحديث صورة الحساب");

                    }
                });
    }

    private void uploadImageUrlToDatabase() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("image_check", imageUrl);
        reference.child("Orders").child("تم التنفيذ")
                .updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                photoUri = data.getData();
                image_chck.setImageURI(photoUri);

            }
        }
    }

}