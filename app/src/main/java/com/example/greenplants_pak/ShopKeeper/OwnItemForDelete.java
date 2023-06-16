package com.example.greenplants_pak.ShopKeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.greenplants_pak.GridSpacingItemDecoration;
import com.example.greenplants_pak.Model.ItemClass;
import com.example.greenplants_pak.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OwnItemForDelete extends AppCompatActivity {

    RecyclerView recyclerView ;
    DatabaseReference databaseReference ;
    FirebaseAuth mAuth ;
    List<ItemClass> itemsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_item_for_delete);
    recyclerView = findViewById(R.id.rec);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    databaseReference = FirebaseDatabase.getInstance().getReference("");
    mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("ItemDatabase");



        FirebaseAuth mAuth = FirebaseAuth.getInstance() ;
        itemsList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    ItemClass items = dataSnapshot.getValue(ItemClass.class);

                    if(mAuth.getUid().equals(items.getCheff_id())){
                        itemsList.add(items);
                    }
                }


                ShowItemAdapterForDelete showItemAdapterForDelete = new ShowItemAdapterForDelete(
                        OwnItemForDelete.this, itemsList
                );
                recyclerView.setAdapter(showItemAdapterForDelete);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }
}