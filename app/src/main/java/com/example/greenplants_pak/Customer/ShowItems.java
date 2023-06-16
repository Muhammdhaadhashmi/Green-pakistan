package com.example.greenplants_pak.Customer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Model.ItemClass;
import com.example.greenplants_pak.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ShowItems extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar  ;
    DatabaseReference CheffFirebaseDatabase;
    ArrayList<ItemClass> ShowItemList ;
    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager   ;
    ShowItemAdapter Adapter ;
    String re = "";
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu , menu);
        ///////////////////////////////////////////////////
        getMenuInflater().inflate(R.menu.sortaction , menu);
        MenuItem Newest = menu.findItem(R.id.newest);
        MenuItem Oldest = menu.findItem(R.id.oldest);
        MenuItem Month = menu.findItem(R.id.month);


        Month.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                CheffFirebaseDatabase = FirebaseDatabase.getInstance().getReference("ItemDatabase");
                CheffFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ShowItemList.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {



                            ItemClass item = dataSnapshot1.getValue(ItemClass.class);

                            String str[] = item.getDate().split(" ");
                            if(str[1].trim().equals(String.valueOf(month))){


                                ShowItemList.add(item);

                            }
else{
                                Log.d("current month" , String.valueOf(month));
                                Log.d("database month" , String.valueOf(str[1]));
                                Toast.makeText(ShowItems.this, "not match", Toast.LENGTH_SHORT).show();
                            }



                        }

                        try{
                            Adapter = new ShowItemAdapter(ShowItems.this, ShowItemList);
                            Adapter.setLoggerName(getIntent().getStringExtra("loggername"));
                            recyclerView.setAdapter(Adapter);
                        }catch (Exception e){

                        }


                        try{
                            for( ItemClass item   : ShowItemList ){
                                flipperview(item.getUri());

                            }
                        }catch (Exception e){


                        }                    Collections.sort(ShowItemList);
                        Collections.reverse(ShowItemList);
                        try {

                            Adapter = new ShowItemAdapter(ShowItems.this, ShowItemList);
                            Adapter.setLoggerName(getIntent().getStringExtra("loggername"));
                            recyclerView.setAdapter(Adapter);
                            final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
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

                                    if (direction == 8) {
                                        //          viewHolder.itemView.setBackgroundColor((Color.GREEN));
                                        //            Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition());
                                        //              CAllByPhone(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));
                                        recyclerView.refreshDrawableState();
                                    } else {
                                        //      Toast.makeText(ShowItems.this, "Message Sent", Toast.LENGTH_SHORT).show();

                                        //        MessageOnMbile(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));

                                        recyclerView.setAdapter(Adapter);
                                        recyclerView.refreshDrawableState();

                                    }
                                }
                            });
                            itemTouchHelper.attachToRecyclerView(recyclerView);


                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                return false;
            }
        });


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
                }catch (Exception e){

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
    ViewFlipper viewFlipper ;
    List<String> Images ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        viewFlipper = findViewById(R.id.viewfliper);
        viewFlipper.setAutoStart(true);
        Images  = new ArrayList<>() ;
        init() ;
        toolbar = findViewById(R.id.toolbarlab);
        toolbar.setTitle("ShowItems");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.searchmenu);
    }

    public  void flipperview(String uri){

        ImageView imageView  =  new ImageView(this);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Picasso.with(getApplicationContext()).load(uri).fit().into(imageView);
        viewFlipper.addView(imageView);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this , android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this , android.R.anim.slide_out_right);
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
        try {
            try{
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
            DatabaseReference Recom
                    = FirebaseDatabase.getInstance().getReference("AddFavourite").child(mAuth.getUid());
            List<String> cata = new ArrayList<>();
            Recom.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     cata.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        try {

                            cata.add(dataSnapshot1.child("cata").getValue().toString());
                        }catch ( NullPointerException e){

                        }finally {

                        }

                    }

if(cata.size()>0) {


    int countA = Collections.frequency(cata, "Jannuary");
    int countB = Collections.frequency(cata, "Fab");
    int countC = Collections.frequency(cata, "March");
    int countD = Collections.frequency(cata, "April");
    int countE = Collections.frequency(cata, "May");
    int countF = Collections.frequency(cata, "june");
    int countG = Collections.frequency(cata, "July");
    int countH = Collections.frequency(cata, "August");
    int countI = Collections.frequency(cata, "September");
    int countJ = Collections.frequency(cata, "Octuber");
    int countK = Collections.frequency(cata, "November");
    int countL = Collections.frequency(cata, "December");

    int[] listvalue = new int[]{countA, countB, countC, countD, countE, countF, countG, countH , countI , countJ ,countK
    , countL};
    List<String> listCat = new ArrayList<>();
    listCat.add("Jannuary");
    listCat.add("Fab");
    listCat.add("March");
    listCat.add("April");
    listCat.add("May");
    listCat.add("june");
    listCat.add("July");
    listCat.add("August");
    listCat.add("September");
    listCat.add("Octuber");
    listCat.add("November");
    listCat.add("December");
    int b = 0;
    int max = listvalue[0];
    Toast.makeText(ShowItems.this, "" + cata.size(), Toast.LENGTH_SHORT).show();
    for (int i = 1; i < listvalue.length; i++) {

        if (max < listvalue[i]) {
            b = i;
        }
    }
    re = listCat.get(b);

//    try{
//        final Python py = Python.getInstance();
//        final PyObject pyObj = py.getModule("matching");
//        PyObject data = pyObj.callAttr("hallo","re");
//
//        String str = data.toString();
//        Toast.makeText(ShowItems.this, "value"+str, Toast.LENGTH_SHORT).show();
//
//            PyObject data1 = pyObj.callAttr("word2vec","re");
//            PyObject data2 = pyObj.callAttr("word2vec","re");
//
//
//            PyObject data3 = pyObj.callAttr("cosdis",data1 , data2);
//
//
//
//
//    }catch (Exception e){
//        Toast.makeText(ShowItems.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
//
//    }




}
                    Collections.sort(ShowItemList);
                    Collections.reverse(ShowItemList);
                    try {

                        Adapter = new ShowItemAdapter(ShowItems.this, ShowItemList);
                        Adapter.setLoggerName(getIntent().getStringExtra("loggername"));
                        recyclerView.setAdapter(Adapter);
                        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
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

                                if (direction == 8) {
                                    //          viewHolder.itemView.setBackgroundColor((Color.GREEN));
                                    //            Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition());
                                    //              CAllByPhone(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));
                                    recyclerView.refreshDrawableState();
                                } else {
                                    //      Toast.makeText(ShowItems.this, "Message Sent", Toast.LENGTH_SHORT).show();

                                    //        MessageOnMbile(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));

                                    recyclerView.setAdapter(Adapter);
                                    recyclerView.refreshDrawableState();

                                }
                            }
                        });
                        itemTouchHelper.attachToRecyclerView(recyclerView);


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Net Slow to load data " + e.toString(), Toast.LENGTH_LONG).show();

            }
            CheffFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ShowItemList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {



                        ItemClass item = dataSnapshot1.getValue(ItemClass.class);

                        if(item.getCata().equals(re)){
                            flipperview(item.getUri());
                        }
                        ShowItemList.add(item);


                    }


                    try{
                        for( ItemClass item   : ShowItemList ){
                            flipperview(item.getUri());

                        }
                    }catch (Exception e){


                    }                    Collections.sort(ShowItemList);
                    Collections.reverse(ShowItemList);
                    try {

                        Adapter = new ShowItemAdapter(ShowItems.this, ShowItemList);
                        Adapter.setLoggerName(getIntent().getStringExtra("loggername"));
                        recyclerView.setAdapter(Adapter);
                        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
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

                                if (direction == 8) {
                                    //          viewHolder.itemView.setBackgroundColor((Color.GREEN));
                                    //            Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition());
                                    //              CAllByPhone(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));
                                    recyclerView.refreshDrawableState();
                                } else {
                                    //      Toast.makeText(ShowItems.this, "Message Sent", Toast.LENGTH_SHORT).show();

                                    //        MessageOnMbile(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));

                                    recyclerView.setAdapter(Adapter);
                                    recyclerView.refreshDrawableState();

                                }
                            }
                        });
                        itemTouchHelper.attachToRecyclerView(recyclerView);


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        catch (Exception e){
            Toast.makeText(getApplicationContext() , "Net Slow to load data " + e.toString(), Toast.LENGTH_LONG ).show();
        }
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
            if (ActivityCompat.checkSelfPermission(ShowItems.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);}
        else {
        }
        recyclerView.setAdapter(Adapter);
    }    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ShowItems.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(ShowItems.this,new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

}
