package com.example.greenplants_pak;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewReminder extends AppCompatActivity {

    RecyclerView recyclerView ;
    DatabaseReference databaseReference ;
    FirebaseAuth mAuth ;
    List<AddremClass> addremClassList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);
    recyclerView = findViewById(R.id.rec);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    mAuth = FirebaseAuth.getInstance();
    databaseReference = FirebaseDatabase.getInstance().getReference("Add_Remider").child(mAuth.getUid());
 addremClassList = new ArrayList<>();
 databaseReference.addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot snapshot) {
         for(DataSnapshot snapshot1 : snapshot.getChildren()){
             AddremClass addremClass = snapshot1.getValue(AddremClass.class);
             addremClassList.add(addremClass);
         }
         RatingAdapterView ratingAdapter
                  = new RatingAdapterView( ViewReminder.this , addremClassList);
         recyclerView.setAdapter(ratingAdapter);
     }

     @Override
     public void onCancelled(@NonNull DatabaseError error) {

     }
 });

    }
}