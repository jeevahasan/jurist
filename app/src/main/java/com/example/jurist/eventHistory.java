package com.example.jurist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
import java.util.ArrayList;
import java.util.Arrays;

public class eventHistory extends AppCompatActivity {

    private ListView dateView;
    private TextView event_history;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    //private TextView event_history;
    private FirebaseUser firebaseUser;
    private RecyclerView mResultList;
    FirebaseAuth firebaseAuth;


    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_history);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("Events");

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String firebaseUserid= user.getUid();

        firebaseUserSearch(firebaseUserid);




    }


    private void firebaseUserSearch(String searchText) {

        Toast.makeText(eventHistory.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = databaseReference.orderByChild("lawyer_uid").startAt(searchText).endAt(searchText + "\uf8ff");



        FirebaseRecyclerAdapter<FreeEvents, eventHistory.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FreeEvents, UsersViewHolder>(

                FreeEvents.class,
                R.layout.list_layout1,
                eventHistory.UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, FreeEvents model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getDate());

            }


        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }


    // View Holder Class

    public static class UsersViewHolder extends RecyclerView.ViewHolder {


        View mView;


        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userName){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);


            user_name.setText(userName);




        }



    }




}