package com.example.greenplants_pak.Ministry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Adatper.ShowBotonicalAdapter;
import com.example.greenplants_pak.Adatper.ShowComplain;
import com.example.greenplants_pak.Model.Botonical_infomation;
import com.example.greenplants_pak.Model.Complain;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_Complain extends AppCompatActivity {

    List<Complain> botonical_infomationList ;

    RecyclerView recipieView ;
    DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__botonical_information);
     botonical_infomationList = new ArrayList<>();
     recipieView = findViewById(R.id.rec);
     recipieView.setLayoutManager(new LinearLayoutManager(this));

     databaseReference = FirebaseDatabase.getInstance().getReference("add_complain");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Complain botonical_infomation = dataSnapshot.getValue(Complain.class);
                    botonical_infomationList.add(botonical_infomation);
                }
                ShowComplain showBotonicalAdapter = new ShowComplain(View_Complain.this ,
                        botonical_infomationList);
                recipieView.setAdapter(showBotonicalAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}