package com.example.greenplants_pak.Customer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Model.ItemClass;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ShowLocalRecipieOnly extends AppCompatActivity implements LocationListener {
    androidx.appcompat.widget.Toolbar toolbar;
    DatabaseReference CheffFirebaseDatabase;
    List<ItemClass> ShowItemList;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ShowItemAdapter Adapter;
    Boolean isCall = false ;


    //
    protected LocationManager locationManager;
    protected LocationListener locationListener;


    String lat;
    String provider;
     String latitude1 = "" ;
    String longitude1 = "";
    protected boolean gps_enabled, network_enabled;

    //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        ///////////////////////////////////////////////////
        getMenuInflater().inflate(R.menu.sortaction, menu);
        MenuItem Newest = menu.findItem(R.id.newest);
        MenuItem Oldest = menu.findItem(R.id.oldest);
        Newest.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                linearLayoutManager = new LinearLayoutManager(getBaseContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(Adapter);
                return false;
            }
        });
        Oldest.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                linearLayoutManager = new LinearLayoutManager(getBaseContext());
                linearLayoutManager.setReverseLayout(false);
                linearLayoutManager.setStackFromEnd(false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(Adapter);
                return false;
            }
        });
        ///////////////////////////////////////////////////
        MenuItem searchmenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchmenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    Adapter.getFilter().filter(newText);
                } catch (Exception e) {

                }
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchmenuItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Set styles for expanded state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Set styles for collapsed state here
                if (getSupportActionBar() != null) {
                    //                  getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.containerbtnstyle));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }
        });


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cheff);

        init();
        locationManager = (LocationManager) getSystemService(getApplication().LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        toolbar = findViewById(R.id.toolbarlab);
        toolbar.setTitle("Show Recipie");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.searchmenu);



    }

    @Override
    public void onLocationChanged(Location location) {

     latitude1 = String.valueOf(location
     .getLongitude());
        longitude1 = String.valueOf(location
                .getLatitude());
        Toast.makeText(getApplicationContext(), "isCall" + isCall, Toast.LENGTH_SHORT).show();
        if(isCall == false){
            try{
                CheffFirebaseDatabase = FirebaseDatabase.getInstance().getReference("ItemDatabase");
                CheffFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ShowItemList.clear();

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            ItemClass usersModelClass = dataSnapshot1.getValue(ItemClass.class);
                            if(latitude1.toString().isEmpty()){
                                Toast.makeText(getApplicationContext(), "latitude1  empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(longitude1.toString().isEmpty()){
                                Toast.makeText(getApplicationContext(), "longitude1 empty", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if(usersModelClass.getLattitude().toString().isEmpty()){
                                Toast.makeText(getApplicationContext(), "usersModelClass.getLattitude empty", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if(usersModelClass.getLongitude().toString().isEmpty()){
                                Toast.makeText(getApplicationContext(), "usersModelClass.getLongitude empty", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (distance(Double.valueOf(latitude1.trim()), Double.valueOf(longitude1.trim()), Double.valueOf(usersModelClass.getLattitude().trim()), Double.valueOf(usersModelClass.getLattitude())) < 7) {
                                ShowItemList.add(usersModelClass);
                            }
                        }

                        try {
                            Collections.sort(ShowItemList);
                            Adapter = new ShowItemAdapter(ShowLocalRecipieOnly.this, ShowItemList);
                            Adapter.setLoggerName(getIntent().getStringExtra("loggername"));
                            recyclerView.setAdapter(Adapter);
                            final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT| ItemTouchHelper.LEFT) {
                                @Override
                                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                    return false;
                                }
                                @Override
                                public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

//                         new RecyclerViewSwipeDecorator.Builder(ShowLaborActivity.this , c ,recyclerView , viewHolder ,dX, dY , actionState , isCurrentlyActive )
//                                 .addSwipeRightBackgroundColor(ContextCompat.getColor(ShowLaborActivity.this , R.color.green))
//                                 .addSwipeRightActionIcon(R.drawable.ic_call_black_24dp).addSwipeLeftBackgroundColor((ContextCompat.getColor(getApplicationContext() , R.color.yello))).addSwipeLeftActionIcon(R.drawable.ic_call_black_24dp)
//                                 .addSwipeLeftBackgroundColor(ContextCompat.getColor(ShowLaborActivity.this , R.color.yello))
//                                 .addSwipeLeftActionIcon(R.drawable.ic_message_black_24dp).create().decorate();



                                    super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                                }
                                @Override
                                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                    if(direction == 8) {
                                        //          viewHolder.itemView.setBackgroundColor((Color.GREEN));
                                        //            Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition());
                                        //              CAllByPhone(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));
                                        recyclerView.refreshDrawableState();
                                    }else{
                                        //      Toast.makeText(ShowItems.this, "Message Sent", Toast.LENGTH_SHORT).show();
                                        //        MessageOnMbile(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));
                                        recyclerView.setAdapter(Adapter);
                                        recyclerView.refreshDrawableState();
                                    }
                                }
                            });
                            itemTouchHelper.attachToRecyclerView(recyclerView);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext() , e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });}catch (Exception e){
                Toast.makeText(getApplicationContext() , "Net Slow to load data " + e.toString(), Toast.LENGTH_LONG ).show();
            }
        }
        isCall = true ;



    }



    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
        Toast.makeText(getApplicationContext(), "service disbale", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getApplicationContext(), "service enable", Toast.LENGTH_SHORT).show();

        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
        Toast.makeText(getApplicationContext(), "service status " + status , Toast.LENGTH_SHORT).show();

    }



    private void init() {
    CheffFirebaseDatabase = FirebaseDatabase.getInstance().getReference("ItemDatabase");
    ShowItemList = new ArrayList<>( );
    recyclerView = findViewById(R.id.recyclelabor);
    linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();



    }
    private void MessageOnMbile(String getNodeAtPosition) {
        String message = "i Want to Hire for day time job Are you want to work plz contact me " + getNodeAtPosition ;
        String number =getNodeAtPosition ; ;
        SmsManager smsManager =  SmsManager.getDefault();
        if (!TextUtils.isEmpty(message) && !TextUtils.isEmpty(number)) {
            try {
                smsManager.sendTextMessage(number, null, message, null, null);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "MEssage Send " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
            //   Toast.makeText(getApplicationContext(), "MEssage Send ", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Message Is Empty", Toast.LENGTH_LONG).show();
        }
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
            if (ActivityCompat.checkSelfPermission(ShowLocalRecipieOnly.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);}
        else {
        }
        recyclerView.setAdapter(Adapter);
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ShowLocalRecipieOnly.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(ShowLocalRecipieOnly.this,new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist/1000);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
