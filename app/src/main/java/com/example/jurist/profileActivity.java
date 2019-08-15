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

public class profileActivity extends AppCompatActivity implements View.OnClickListener {
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
    private TextView reference;
    private ImageView Profile_image;
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
        setContentView(R.layout.activity_profile);
        storageReference= FirebaseStorage.getInstance().getReference();

        upload=(Button)findViewById(R.id.upload);
        choose=(Button)findViewById(R.id.choose);
        profile_image=(ImageView)findViewById(R.id.profile_image);
        edit_button = (Button) findViewById(R.id.edit_button);


        upload.setOnClickListener(this);
        choose.setOnClickListener(this);
        edit_button.setOnClickListener(profileActivity.this);




        name=(TextView) findViewById(R.id.profile_name);
        last=(TextView) findViewById(R.id.profile_last);
        email=(TextView) findViewById(R.id.profile_email);
        phone=(TextView) findViewById(R.id.profile_phone);
        reference=(TextView) findViewById(R.id.profile_reference);
        profile_image=(ImageView)findViewById(R.id.profile_image);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().getRoot();
        databaseReference.child("Lawyers").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lawyername=(String) dataSnapshot.child("first_name").getValue();
                lawyerlast=(String) dataSnapshot.child("last_name").getValue();
                lawyeremail=(String) dataSnapshot.child("email").getValue();
                lawyerphone=(String) dataSnapshot.child("phone_no").getValue();
                lawyerreference=(String) dataSnapshot.child("reference_no").getValue();






                name.setText("First Name:  "+lawyername);
                last.setText("Last Name:  "+lawyerlast);
                email.setText("Email Address:  "+lawyeremail);
                phone.setText("Phone Number:  "+lawyerphone);
                reference.setText("Reference Number:  "+lawyerreference);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"), PICK_IMAGE_REQUEST);
    }
    private void uploadFile(){

        if(filePath!=null) {
            final ProgressDialog progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            Uri file = Uri.fromFile(new File("path/to/images/profile.jpg"));
            final StorageReference riversRef = storageReference.child("images/profile.jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_LONG).show();
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserProfileChangeRequest profileUpdate=new UserProfileChangeRequest.Builder()
                                            .setDisplayName(lawyername)
                                            .setPhotoUri(uri)
                                            .build();

                                    firebaseUser.updateProfile(profileUpdate)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();

                                                    }

                                                }
                                            });



                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int)progress)+" % Uploaded...");
                        }
                    });
        }

        else {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null);
        filePath=data.getData();
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
            profile_image.setImageBitmap(bitmap );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if(v==choose){
            //open file chooser
            showFileChooser();
        }
        else if(v==upload){
            uploadFile();
            //upload to firebase
        }

        if(v==edit_button){

        }
        switch (v.getId()) {
            case R.id.edit_button:
                Intent intent = new Intent(profileActivity.this, updateProfile.class);
                startActivity(intent);

        }

    }






}
