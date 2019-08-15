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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class pastActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;
    private String firebaseUserid;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_customer);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("PastOrders");


        firebaseAuth = FirebaseAuth.getInstance();

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseUserid= user.getUid();

        firebaseUserSearch(firebaseUserid);



    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(pastActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("lawyer_uid").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<FreeEvents, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FreeEvents, UsersViewHolder>(

                FreeEvents.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, FreeEvents model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getDate(),model.getCustomer_uid(),model.getLawyer_uid());

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

        public void setDetails(Context ctx, String userName, String userLastName,String userLocation){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_last_name = (TextView) mView.findViewById(R.id.status_text);
            TextView user_location = (TextView) mView.findViewById(R.id.location_text);



            user_name.setText(userName);
            user_last_name.setText(userLastName);
            user_location.setText(userLocation);


        }






    }
    /*public void sendMessage(View view)
    {
        Intent intent = new Intent(ScheduleCustomerActivity.this, PaymentStart.class);
        startActivity(intent);
    }*/


}
