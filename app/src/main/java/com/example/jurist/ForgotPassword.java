package com.example.jurist;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.*;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.regex.*;
public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private Button reset;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email=(EditText) findViewById(R.id.forgot_email) ;
        reset=(Button)findViewById(R.id.forgot_button) ;
        firebaseAuth=FirebaseAuth.getInstance();


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {

                                    Toast.makeText(ForgotPassword.this, "password reset link sent to your email!", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(ForgotPassword.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
