package com.example.jurist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class loginCustomer extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private Button logging;
    private Button cancel;
    private EditText email;
    private EditText password;
    private TextView signup;
    private TextView forgotPassword;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);

        logging=(Button)findViewById(R.id.login_button) ;
        cancel = (Button)findViewById(R.id.login_cancel);
        email=(EditText) findViewById(R.id.login_email);
        password=(EditText) findViewById(R.id.login_password);
        signup=(TextView) findViewById(R.id.below_signup);
        forgotPassword=(TextView) findViewById(R.id.forgot_password);


        logging.setOnClickListener(this);
        cancel.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    private void signIn() {
        String login_email = email.getText().toString().trim();
        String login_password = password.getText().toString().trim();

        //validation for empty fields
        if(TextUtils.isEmpty(login_email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(login_password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }



        //sign in with email starts here
        firebaseAuth.signInWithEmailAndPassword(login_email, login_password)
                .addOnCompleteListener(loginCustomer.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //finish();
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Toast.makeText(loginCustomer.this, "You are logging...",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginCustomer.this, customerNavigation.class);
                            startActivity(intent);

                        } else {

                            Toast.makeText(loginCustomer.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        //exclude starts here
                        if (!task.isSuccessful()) {
                            Toast.makeText(loginCustomer.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //exclude ends here
                    }
                });
        //sign in with email ends here
    }

    //@Override
    // protected void onStart() {
    //super.onStart();
    //if(firebaseAuth.getCurrentUser()!=null){
    //finish();
    //startActivity(new Intent(login.this,customerNavigation.class));
    //}
    // }

    @Override
    public void onClick(View view) {
        if(view==logging){
            signIn();
        }
        if(view==signup){

        }
        switch (view.getId()) {
            case R.id.below_signup:
                //finish();
                Intent intent = new Intent(loginCustomer.this, homeCustomer.class);
                startActivity(intent);

        }
        if(view==forgotPassword){

        }
        switch (view.getId()) {
            case R.id.forgot_password:
                Intent intent = new Intent(loginCustomer.this, ForgotPassword.class);
                startActivity(intent);

        }

        if(view==cancel){

        }
        switch (view.getId()) {
            case R.id.login_cancel:
                Intent intent = new Intent(this, customer.class);
                startActivity(intent);

        }




    }



}
