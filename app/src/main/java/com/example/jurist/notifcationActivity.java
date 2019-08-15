package com.example.jurist;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class notifcationActivity extends AppCompatActivity {

    private TextView name;
    Button edit_button;
    private DatabaseReference databaseReference;
    private String firebaseUserid;
    private FirebaseAuth firebaseAuth;
    private String time;
    private DatabaseReference myRef;
    private FreeEvents events;
    private String hello;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifcation);
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        firebaseAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("PastOrders");

        events = new FreeEvents();

        name=(TextView) findViewById(R.id.time);
        edit_button = (Button) findViewById(R.id.finished);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseUserid= user.getUid();

        databaseReference.child("-LmLUki8CoTr77Mip2Do").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                time = (String) dataSnapshot.child("date").getValue();

                name.setText("Next Event "+time);

                Toast.makeText(notifcationActivity.this,time, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        edit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                databaseReference.child("-LmLUki8CoTr77Mip2Do").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // str = (String) dataSnapshot.child("date").getValue();

                        hello = (String) dataSnapshot.child("date").getValue();
                        events.setDate(hello);
                        events.setCustomer_uid((String) dataSnapshot.child("customer_uid").getValue());
                        events.setLawyer_uid((String) dataSnapshot.child("lawyer_uid").getValue());

                        String key = FirebaseDatabase.getInstance().getReference().child("PastOrders").push().getKey();
                        myRef.child(key).setValue(events);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                //databaseReference.child("-LmLDSG3CtkxiXQcACs_").removeValue();


            }


        });
    }







}
