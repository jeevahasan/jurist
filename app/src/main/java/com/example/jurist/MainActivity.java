package com.example.jurist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {


    private static Button signup_main_button;
    private static Button login_main_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logging();
        signing();





    }



    public void logging(){
        login_main_button = (Button)findViewById(R.id.login_main);
        login_main_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".login");
                        startActivity(intent);
                    }
                }
        );
    }


    public void signing(){
        signup_main_button = (Button)findViewById(R.id.sign_up_main);
        signup_main_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".home");
                        startActivity(intent);
                    }
                }
        );
    }
}
