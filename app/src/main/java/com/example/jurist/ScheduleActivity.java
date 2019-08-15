package com.example.jurist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    EditText date;
    Button send_button;
    Button view_button;
    FreeEvents event;
    FirebaseDatabase database; // = FirebaseDatabase.getInstance();
    DatabaseReference myRef; // = database.getReference();
    private FirebaseAuth firebaseAuth;
    private String firebaseUserid;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        date = (EditText) findViewById(R.id.date);

        send_button = (Button) findViewById(R.id.button);
        view_button = (Button) findViewById(R.id.view_button);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Events");
        event = new FreeEvents();
        firebaseAuth = FirebaseAuth.getInstance();
        view_button.setOnClickListener(ScheduleActivity.this);


    }



    private void getValues() {


        event.setDate(date.getText().toString());
        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseUserid= user.getUid();
        event.setLawyer_uid(firebaseUserid);
        //event.setUID("1");

    }

    public void Send(View view) {


        send_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getValues();

                FirebaseUser user = firebaseAuth.getCurrentUser();
                firebaseUserid= user.getUid();




                //date validation
                    String regex = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(date.getText().toString());
                    if (!matcher.matches()) {
                        Toast.makeText(ScheduleActivity.this,"Please enter valid date", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    else {
                        String key = database.getReference().child("Events").push().getKey();
                        Toast.makeText(ScheduleActivity.this,"Date Inserted", Toast.LENGTH_SHORT).show();
                        myRef.child(key).setValue(event);
                        //rootRef.child("friends").child(friend).setValue(true);
                        return;
                    }




            }


        });



        /*


        myRef.addValueEventListener(new ValueEventListener() {


            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                getValues();
        /*String UID = Array.getUID();
        myRef.child(UID).setValue(user);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                firebaseUserid= user.getUid();


                events.add(event);

                myRef.child(firebaseUserid).child("Events").setValue(events);
                Toast.makeText(ScheduleActivity.this, "Date Inserted", Toast.LENGTH_LONG);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

    }


    @Override
    public void onClick(View v) {
        if(v==view_button){

        }
        switch (v.getId()) {
            case R.id.view_button:
                Intent intent = new Intent(ScheduleActivity.this, eventHistory.class);
                startActivity(intent);

        }

    }
}
