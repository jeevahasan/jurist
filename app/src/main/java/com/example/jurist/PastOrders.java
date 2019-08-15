package com.example.jurist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PastOrders extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private DatabaseReference myRef;
    private FreeEvents events;
    private String hello;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        events = new FreeEvents();
        myRef = FirebaseDatabase.getInstance().getReference().child("PastOrders");

        addPastOrders("-LmKrYxXdi1BsYBaauM5");
    }



    private void addPastOrders(String id){

        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // str = (String) dataSnapshot.child("date").getValue();

                hello = (String) dataSnapshot.child("date").getValue();
                events.setDate(hello);
                events.setCustomer_uid("KOKBDecTKBeIo3LVdRkCK4hYKMP2");
                events.setLawyer_uid((String) dataSnapshot.child("lawyer_uid").getValue());

                String key = FirebaseDatabase.getInstance().getReference().child("PastOrders").push().getKey();
                myRef.child(key).setValue(events);
                Toast.makeText(PastOrders.this,"Date Inserted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //databaseReference.child(id).removeValue();
    }
}
