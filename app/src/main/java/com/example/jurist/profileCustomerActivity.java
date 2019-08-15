package com.example.jurist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class profileCustomerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST =234 ;
    private Button upload,choose;
    private ImageView profile_image;
    private Uri filePath;
    private StorageReference storageReference;

    private FirebaseAuth firebaseAuth;
    private TextView name;
    private TextView last;
    private TextView email;
    private TextView phone;

    private DataSnapshot dataSnapshot;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private DatabaseError databaseError;
    private String lawyername;
    private String lawyerlast;
    private String lawyeremail;
    private String lawyerphone;
    private String lawyerreference;
    Button edit_button;
    private String img;
    private static boolean flag;

    private static Button edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_customer);
        storageReference= FirebaseStorage.getInstance().getReference();

        edit_button = (Button) findViewById(R.id.edit_button);


        edit_button.setOnClickListener(profileCustomerActivity.this);




        name=(TextView) findViewById(R.id.profile_name);
        last=(TextView) findViewById(R.id.profile_last);
        email=(TextView) findViewById(R.id.profile_email);
        phone=(TextView) findViewById(R.id.profile_phone);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().getRoot();
        databaseReference.child("Customers").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lawyername=(String) dataSnapshot.child("first_name").getValue();
                lawyerlast=(String) dataSnapshot.child("last_name").getValue();
                lawyeremail=(String) dataSnapshot.child("email").getValue();
                lawyerphone=(String) dataSnapshot.child("phone_no").getValue();






                name.setText("First Name:  "+lawyername);
                last.setText("Last Name:  "+lawyerlast);
                email.setText("Email Address:  "+lawyeremail);
                phone.setText("Phone Number:  "+lawyerphone);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onClick(View v) {


        if(v==edit_button){

        }
        switch (v.getId()) {
            case R.id.edit_button:
                Intent intent = new Intent(profileCustomerActivity.this, updateCustomerprofile.class);
                startActivity(intent);

        }

    }






}
