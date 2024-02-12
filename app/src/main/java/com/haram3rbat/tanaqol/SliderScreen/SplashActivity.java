package com.haram3rbat.tanaqol.SliderScreen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haram3rbat.tanaqol.Admin.AdminLoginActivity;
import com.haram3rbat.tanaqol.LoginAndRegister.LoginActivity;
import com.haram3rbat.tanaqol.R;
import com.haram3rbat.tanaqol.uiMotabara.HomeMotabaraActivity;
import com.haram3rbat.tanaqol.LoginMotabara.LoginMotabaraActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.haram3rbat.tanaqol.uiUser.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout dotIndicator;
    Button nextButton, skipButton;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    FirebaseAuth auth;
    DatabaseReference reference, reference2;
    FirebaseUser user;
    String deviceID;

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {

            setDotIndicator(position);

            if (position == 4){
                nextButton.setText("أنتهاء");
            } else {
                nextButton.setText("التالي");
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth=FirebaseAuth.getInstance ();
        user=auth.getCurrentUser();

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        reference2= FirebaseDatabase.getInstance().getReference().child("Users_Motabara3");

        if (user != null) {

            reference.child(deviceID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String email = snapshot.child("email").getValue().toString();

                        if (snapshot.child("phoneCode").exists() && user.getEmail().equals(email)) {

                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                        } else {
                            if (user.getEmail().equals(email)) {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            reference2.child(deviceID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String email = snapshot.child("email").getValue().toString();

                        if (snapshot.child("phoneCode").exists() && user.getEmail().equals(email)) {

                            startActivity(new Intent(SplashActivity.this, HomeMotabaraActivity.class));

                        } else {
                            if (user.getEmail().equals(email)) {
                                startActivity(new Intent(SplashActivity.this, LoginMotabaraActivity.class));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 4)
                    slideViewPager.setCurrentItem(getItem(1), true);
                else {
                    DialogBox();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DialogBox();
            }
        });

        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotIndicator = (LinearLayout) findViewById(R.id.dotIndicator);

        viewPagerAdapter = new ViewPagerAdapter(this);
        slideViewPager.setAdapter(viewPagerAdapter);

        setDotIndicator(0);
        slideViewPager.addOnPageChangeListener(viewPagerListener);

    }

    public void setDotIndicator(int position) {

        dots = new TextView[5];
        dotIndicator.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorAccent, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.offwhite, getApplicationContext().getTheme()));
    }

    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }

    private void DialogBox() {
        Dialog dialog=new Dialog(this);

        dialog.setContentView(R.layout.payment_dialog);
        TextView hag = dialog.findViewById(R.id.confirm);
        TextView motabara = dialog.findViewById(R.id.cancel);
        TextView admin = dialog.findViewById(R.id.Admin);

        dialog.setCancelable(true);

        dialog.show();
        clickOnConfirm(admin,hag,motabara,dialog);

    }

    private void clickOnConfirm(final TextView admin,final TextView hag,final TextView motabara,final Dialog dialog) {
        hag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
                dialog.dismiss();
            }
        });
        motabara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, LoginMotabaraActivity.class));
                finish();
                dialog.dismiss();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, AdminLoginActivity.class));
                finish();
                dialog.dismiss();
            }
        });

    }
}