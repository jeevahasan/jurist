package com.example.jurist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;


public class initial extends AppCompatActivity {


    private static Button customer;
    private static Button lawyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        customer();
        lawyer();





    }



    public void customer(){
        customer = (Button)findViewById(R.id.customer);
        customer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".customer");
                        startActivity(intent);
                    }
                }
        );
    }


    public void lawyer(){
        lawyer = (Button)findViewById(R.id.lawyer);
        lawyer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".MainActivity");
                        startActivity(intent);
                    }
                }
        );
    }
}
