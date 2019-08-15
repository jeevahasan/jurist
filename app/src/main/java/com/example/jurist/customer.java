package com.example.jurist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;


public class customer extends AppCompatActivity {


    private static Button login_customer;
    private static Button sign_up_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        logging();
        signing();





    }



    public void logging(){
        login_customer = (Button)findViewById(R.id.login_customer);
        login_customer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".loginCustomer");
                        startActivity(intent);
                    }
                }
        );
    }


    public void signing(){
        sign_up_customer = (Button)findViewById(R.id.sign_up_customer);
        sign_up_customer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".homeCustomer");
                        startActivity(intent);
                    }
                }
        );
    }
}
