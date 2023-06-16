package com.example.greenplants_pak.Ministry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.greenplants_pak.Adatper.ShowCustomer_Adapter;
import com.example.greenplants_pak.Model.Complain;
import com.example.greenplants_pak.Model.UserModelClass;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Response_ extends AppCompatActivity {

    List<String> CustomerKey ;
    List<UserModelClass> userModelClasses ;
    DatabaseReference databaseReference;
    DatabaseReference User_Ref ;
    RecyclerView recyclerView ;
    ShowCustomer_Adapter showCustomer_adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_);
    recyclerView = findViewById(R.id.rec);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CustomerKey= new ArrayList<>();
        userModelClasses = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("add_complain");
        User_Ref = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerKey.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Complain complain = dataSnapshot.getValue(Complain.class);
                    if(!CustomerKey.contains(complain.getCus_id())){
                        CustomerKey.add(complain.getCus_id());
                    }
                }

                User_Ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         userModelClasses.clear();
                        for(DataSnapshot dataSnapshot :snapshot.getChildren()){

                            UserModelClass userModelClass = dataSnapshot.getValue(UserModelClass.class);
                            if(CustomerKey.contains(userModelClass.getUUID())){
                                userModelClasses.add(userModelClass);
                            }
                        }
                        showCustomer_adapter = new ShowCustomer_Adapter(Response_.this ,
                                userModelClasses);

                        recyclerView.setAdapter(showCustomer_adapter);
                        try {
                            showCustomer_adapter = new ShowCustomer_Adapter(Response_.this ,
                                    userModelClasses);

                            recyclerView.setAdapter(showCustomer_adapter);


                            final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT| ItemTouchHelper.LEFT) {
                                @Override
                                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                    return false;
                                }
                                @Override
                                public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                         new RecyclerViewSwipeDecorator.Builder(Response_.this , c ,recyclerView , viewHolder ,dX, dY , actionState , isCurrentlyActive )
                                 .addSwipeRightBackgroundColor(ContextCompat.getColor(Response_.this , R.color.green))
                                 .addSwipeRightActionIcon(R.drawable.ic_call_black_24dp).addSwipeLeftBackgroundColor((ContextCompat.getColor(getApplicationContext() , R.color.yello))).addSwipeLeftActionIcon(R.drawable.ic_call_black_24dp)
                                 .addSwipeLeftBackgroundColor(ContextCompat.getColor(Response_.this , R.color.yello))
                                 .addSwipeLeftActionIcon(R.drawable.ic_message_black_24dp).create().decorate();





                                    super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                                }
                                @Override
                                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                                    if(direction == 8) {
                                        viewHolder.itemView.setBackgroundColor((Color.GREEN));
                                        showCustomer_adapter.GetNodeAtPosition(viewHolder.getAdapterPosition());
                                        CAllByPhone(showCustomer_adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));
                                        recyclerView.refreshDrawableState();
                                    }else{


                                    }
                                }
                            });
                            itemTouchHelper.attachToRecyclerView(recyclerView);




                        }catch (Exception e){
                            Toast.makeText(getApplicationContext() , e.toString(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CAllByPhone(String getNodeAtPosition) {

        Toast.makeText(getApplicationContext() , getNodeAtPosition , Toast.LENGTH_LONG).show();

        if(!checkPermission()){
            requestPermission();
        }

        // "tel:0377778888" fromat //
        if(!getNodeAtPosition.isEmpty()){
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"  +getNodeAtPosition));
            if (ActivityCompat.checkSelfPermission(Response_.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);}
        else {
        }



        recyclerView.setAdapter(showCustomer_adapter);

    }




    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Response_.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(Response_.this,new String[]{Manifest.permission.CALL_PHONE}, 1);

    }
}