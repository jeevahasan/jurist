package com.example.jurist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class updateCustomerprofile extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth mAuth;
    private String firebaseUserid;


    //view objects
    private EditText profFirst;
    private EditText profLast;
    private EditText profPhone;
    private TextView profEmail;
    private Button updateProfileButton;
    //defining a database reference
    private DatabaseReference mUsersDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customerprofile);


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mUsersDB= FirebaseDatabase.getInstance().getReference("Customers");


        updateProfileButton = (Button) findViewById(R.id.updateProfileButton);


        FirebaseUser user = mAuth.getCurrentUser();

        //initializing views
        profFirst = (EditText) findViewById(R.id.profFirst);
        profLast = (EditText) findViewById(R.id.profLast);
        profPhone=(EditText) findViewById(R.id.profPhone);
        profEmail = (TextView) findViewById(R.id.profEmail);

        updateProfileButton.setOnClickListener(this);

    }





    private void saveUserInformation() {
        //Getting values from database
        String first_name = profFirst.getText().toString().trim();
        String last_name = profLast.getText().toString().trim();
        String phone_no = profPhone.getText().toString().trim();
        String email = profEmail.getText().toString().trim();

        //validation for empty fields
        if (TextUtils.isEmpty(first_name) && TextUtils.isEmpty(last_name) && TextUtils.isEmpty(phone_no)) {

            Toast.makeText(this, "Please fill at least one field", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone_no.length()>=1) {


            //phone no validation
            String regex = "^7|0|(?:\\+94)[0-9]{9,10}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phone_no);
            if (!matcher.matches()) {
                Toast.makeText(updateCustomerprofile.this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //validation for phone no
        if (phone_no.length()<10) {

            Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseUser user = mAuth.getCurrentUser();
        firebaseUserid = user.getUid();
        // String id=databaseReference.push().getKey();
        jurist_customer customer = new jurist_customer(first_name, last_name, email, phone_no);
        mUsersDB.child(firebaseUserid).child("first_name").setValue(first_name);
        mUsersDB.child(firebaseUserid).child("last_name").setValue(last_name);
        mUsersDB.child(firebaseUserid).child("phone_no").setValue(phone_no);


        //displaying a success toast
        Toast.makeText(updateCustomerprofile.this, "Profile Updated...", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onClick(View view) {

        if(view == updateProfileButton){
            saveUserInformation();
        }

    }
}

