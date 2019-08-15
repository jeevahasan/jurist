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
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.graphics.Color;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase.*;




import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.util.Log;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.*;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.*;




public class home extends AppCompatActivity implements View.OnClickListener, OnItemSelectedListener {


    private Button signup;
    private Button signup_cancel;
    private EditText signup_first_name;
    private EditText signup_last_name;
    private EditText signup_email;
    private EditText signup_phone_no;
    private EditText signup_reference_no;
    private EditText signup_password;
    private EditText signup_re_enter_password;
    private TextView below_login;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String firebaseUserid;
    private String citySelected;
    public String caseSelected;



    DatabaseReference databaseReference;


    CustomOnSelectedListener x = new CustomOnSelectedListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Spinner spinner = (Spinner) findViewById(R.id.city_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city, R.layout.color_spinner_layout);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource( R.layout.spinner_dropdown_layout);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        Spinner spinner1 = (Spinner) findViewById(R.id.case_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.case_type, R.layout.color_spinner_layout);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource( R.layout.spinner_dropdown_layout);
// Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(x);




        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("NewLawyers");


        progressDialog = new ProgressDialog(this);

        signup=(Button)findViewById(R.id.lawyer_sign_up) ;
        signup_cancel = (Button)findViewById(R.id.lawyer_cancel);
        signup_first_name=(EditText) findViewById(R.id.first_name);
        signup_last_name=(EditText) findViewById(R.id.last_name);
        signup_email=(EditText) findViewById(R.id.email);
        signup_phone_no=(EditText) findViewById(R.id.phone_no);
        signup_reference_no=(EditText) findViewById(R.id.reference_no);
        signup_password=(EditText) findViewById(R.id.password);
        signup_re_enter_password=(EditText) findViewById(R.id.re_enter_password);
        below_login=(TextView) findViewById(R.id.below_login);


        signup.setOnClickListener(this);
        signup_cancel.setOnClickListener(this);
        below_login.setOnClickListener(this);


    }



    //email validation
    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //password validation
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    private void registerLawyer(){
        final String first_name = signup_first_name.getText().toString().trim();
        final String last_name = signup_last_name.getText().toString().trim();
        final String email = signup_email.getText().toString().trim();
        final String phone_no = signup_phone_no.getText().toString().trim();
        final String reference_no = signup_reference_no.getText().toString().trim();
        String password = signup_password.getText().toString().trim();
        String re_enter_password = signup_re_enter_password.getText().toString().trim();
        //final String district = "Jaffna";



            //email validation
        if(!isValidEmail(email)){
            Toast.makeText(this,"Please enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }


        //password==re enter password
        if (!password.equals(re_enter_password))
        {
            Toast.makeText(this,"Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        //validation for empty fields
        if(TextUtils.isEmpty(first_name)){

            Toast.makeText(this,"Please enter first name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(last_name)){
            Toast.makeText(this,"Please enter last name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(phone_no)){
            Toast.makeText(this,"Please enter Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(reference_no)){
            Toast.makeText(this,"Please enter reference no", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(re_enter_password)){
            Toast.makeText(this,"Please enter confirm password", Toast.LENGTH_SHORT).show();
            return;
        }


        //phone no validation
        String regex = "^7|0|(?:\\+94)[0-9]{9,10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone_no);
        if (!matcher.matches()) {
            Toast.makeText(this,"Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<8 &&!isValidPassword(password)){
            Toast.makeText(this,"Password is too weak", Toast.LENGTH_SHORT).show();
            return;
        }





        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete (@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(home.this,Jurist.class ));
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            firebaseUserid= user.getUid();
                           // String id=databaseReference.push().getKey();

                            jurist_lawyer lawyer=new jurist_lawyer(first_name,last_name,email,phone_no,reference_no);
                            lawyer.setDistrict(citySelected);

                            lawyer.setCase_type(x.caseSelected1);

                            databaseReference.child(firebaseUserid).setValue(lawyer);

                            Toast.makeText(home.this,"Registered Successfully",Toast.LENGTH_SHORT).show();

                            sendEmailVerification();
                            progressDialog.dismiss();
                        }
                        else {
                            Toast.makeText(home.this,"Couldn't Registered, Try again!",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                    }
                });



    }


    private void sendEmailVerification() {
        // Disable button


        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(home.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button

                        Log.v( "SSSS", String.valueOf(user.isEmailVerified()));

                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }




    @Override
    public void onClick(View view) {
        if(view==signup){
            registerLawyer();
        }
        
        if(view==below_login){

        }
        switch (view.getId()) {
            case R.id.below_login:
                finish();
                Intent intent = new Intent(this, login.class);
                startActivity(intent);

        }

               if(view==signup_cancel){

        }
        switch (view.getId()) {
            case R.id.lawyer_cancel:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        citySelected=parent.getItemAtPosition(position).toString();
        //caseSelected=parent.getItemAtPosition(position).toString();

        //debugging purpose
        Toast.makeText(this,citySelected,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }





}
