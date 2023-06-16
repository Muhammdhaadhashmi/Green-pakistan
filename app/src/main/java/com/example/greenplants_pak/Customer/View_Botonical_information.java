package com.example.greenplants_pak.Customer;

import androidx.annotation.BoolRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.greenplants_pak.Adatper.ShowBotonicalAdapter;
import com.example.greenplants_pak.Model.Botonical_infomation;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_Botonical_information extends AppCompatActivity {

    List<Botonical_infomation > botonical_infomationList ;

    RecyclerView recipieView ;
    DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__botonical_information);
     botonical_infomationList = new ArrayList<>();
     recipieView = findViewById(R.id.rec);
     recipieView.setLayoutManager(new LinearLayoutManager(this));

     databaseReference = FirebaseDatabase.getInstance().getReference("botonical_info");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Botonical_infomation botonical_infomation = dataSnapshot.getValue(Botonical_infomation.class);
                    botonical_infomationList.add(botonical_infomation);
                }
                ShowBotonicalAdapter showBotonicalAdapter = new ShowBotonicalAdapter(View_Botonical_information.this ,
                        botonical_infomationList);
                recipieView.setAdapter(showBotonicalAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}