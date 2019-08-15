package com.example.jurist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ScheduleCustomerActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_customer);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Lawyers");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(ScheduleCustomerActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("first_name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Lawyers, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Lawyers, UsersViewHolder>(

                Lawyers.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Lawyers model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getLastName(),model.getLocation(),model.getCase_type());

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

        public void setDetails(Context ctx, String userName, String userLastName,String userLocation,String userCaseType){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_last_name = (TextView) mView.findViewById(R.id.status_text);
            TextView user_location = (TextView) mView.findViewById(R.id.location_text);
            TextView user_case_type = (TextView) mView.findViewById(R.id.case_type);





            user_name.setText(userName);
            user_last_name.setText(userLastName);
            user_location.setText(userLocation);
            user_case_type.setText(userCaseType);




        }






    }
    /*public void sendMessage(View view)
    {
        Intent intent = new Intent(ScheduleCustomerActivity.this, PaymentStart.class);
        startActivity(intent);
    }*/


}
