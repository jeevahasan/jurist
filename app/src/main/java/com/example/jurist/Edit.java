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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;




import java.io.IOException;

public class Edit extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST =234 ;
    private Button upload,choose;
    private ImageView profile_image;
    private Uri filePath;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        storageReference= FirebaseStorage.getInstance().getReference();

        upload=(Button)findViewById(R.id.upload);
        choose=(Button)findViewById(R.id.choose);
        profile_image=(ImageView)findViewById(R.id.profile_image);

        upload.setOnClickListener(this);
        choose.setOnClickListener(this);


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
            StorageReference riversRef = storageReference.child("images/profile.jpg");

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_LONG).show();

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
            })
            ;
        }else {

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

    }
}
