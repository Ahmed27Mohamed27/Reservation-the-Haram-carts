package com.haram3rbat.tanaqol.uiUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.haram3rbat.tanaqol.Orders.DetailsHagzActivity;
import com.haram3rbat.tanaqol.Orders.OrdersActivity;
import com.haram3rbat.tanaqol.R;
import com.haram3rbat.tanaqol.SliderScreen.SplashActivity;
import com.haram3rbat.tanaqol.SliderScreen.splash;
import com.haram3rbat.tanaqol.uiMotabara.ProfileMotabaraActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImage;
    ImageView camera;
    TextView username,email,redeemHistoryTv,logoutTv,contact, edit_profile;
    Button updateBtn;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    private static final int PERMISSION=100;
    String deviceID;
    private static final int PICK_IMAGE=1;
    private Uri photoUri;
    private String imageUrl;
    TextView details_hagz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_profile );


        initialize ();
        setSupportActionBar ( toolbar );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        getSupportActionBar ().setTitle ( "" );

        deviceID= Settings.Secure.getString ( getContentResolver (), Settings.Secure.ANDROID_ID );

        auth=FirebaseAuth.getInstance ();
        user=auth.getCurrentUser ();
        reference= FirebaseDatabase.getInstance ().getReference ().child ( "Users" );

        progressDialog=new ProgressDialog ( ProfileActivity.this );
        progressDialog.setTitle ( "برجاء الأنتظار..." );
        progressDialog.setCanceledOnTouchOutside ( false );
        progressDialog.setCancelable ( false );

        clickListener();
        getDataFromDatabase();




    }
    private void clickListener()
    {
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });

        logoutTv.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, splash.class);
                startActivity(intent);
                auth.signOut();
                finishAffinity();
                finish();
            }
        } );

        redeemHistoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, OrdersActivity.class));
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/+9660509713755"));
                startActivity(intent);
            }
        });

        details_hagz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, DetailsHagzActivity.class));
            }
        });

        camera.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Dexter.withContext ( ProfileActivity.this )
                        .withPermissions ( Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener ( new MultiplePermissionsListener () {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted ())
                                {
                                    Intent intent=new Intent ( Intent.ACTION_PICK );
                                    intent.setType ( "image/*" );
                                    startActivityForResult ( intent,PICK_IMAGE );

                                }else
                                {
                                    Toast.makeText ( ProfileActivity.this, "برجاء تفعيل الأذونات", Toast.LENGTH_SHORT ).show ();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                            }
                        } ).check ();
            }
        } );

        updateBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        } );

    }

    private void uploadImage() {
        if (photoUri ==null)
        {
            return;
        }
        String filename=user.getUid ()+".jpg";
        final FirebaseStorage storage=FirebaseStorage.getInstance ();
        final StorageReference storageReference=storage.getReference ().child ( "Images/"+filename );

        progressDialog.show ();
        storageReference.putFile ( photoUri )
                .addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl ().addOnSuccessListener ( new OnSuccessListener<Uri> () {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageUrl=uri.toString ();

                                uploadImageUrlToDatabase();
                            }
                        } );







                    }
                } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss ();
                Toast.makeText ( ProfileActivity.this, "Error"+e.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } ).addOnProgressListener ( new OnProgressListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                long p1=taskSnapshot.getBytesTransferred ();
                long p2=taskSnapshot.getTotalByteCount ();

                long r=(p1/1024);
                long r2=(p2/1024);

                progressDialog.setMessage ( "تحديث صورة الحساب" );

            }
        } );
    }

    private void uploadImageUrlToDatabase() {
        HashMap<String,Object> map=new HashMap<> (  );
        map.put ( "image",imageUrl );
        reference.child (deviceID)
                .updateChildren ( map )
                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateBtn.setVisibility ( View.GONE );
                        progressDialog.dismiss ();
                    }
                } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK)
        {
            if (data !=null)
            {
                photoUri=data.getData ();
                profileImage.setImageURI ( photoUri );
                updateBtn.setVisibility ( View.VISIBLE );

            }
        }
    }

    private void initialize()
    {
        profileImage=findViewById ( R.id.profile );
        camera=findViewById ( R.id.pick );
        username=findViewById ( R.id.username );
        email=findViewById ( R.id.email );
        redeemHistoryTv=findViewById ( R.id.redeemHistory );
        logoutTv=findViewById ( R.id.logout );
        updateBtn=findViewById ( R.id.updateBtn );
        toolbar=findViewById ( R.id.toolbar );
        contact=findViewById ( R.id.contact );
        details_hagz=findViewById ( R.id.details_hagz );
        edit_profile=findViewById ( R.id.edit_profile );
    }

    private void getDataFromDatabase() {
        reference.child (deviceID).addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n=snapshot.child ( "name" ).getValue ().toString ();
                String e=snapshot.child ( "email" ).getValue ().toString ();
                String p=snapshot.child ( "image" ).getValue ().toString ();

                username.setText(n);
                email.setText(e);

                Glide.with(getApplicationContext()).load(p)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(profileImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( ProfileActivity.this, "Unable to fetch data"+error.getMessage (), Toast.LENGTH_SHORT ).show ();
                finish ();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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