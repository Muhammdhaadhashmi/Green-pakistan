package com.example.greenplants_pak.HireActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplants_pak.MapPackage.SetLocation;
import com.example.greenplants_pak.Model.Notification;
import com.example.greenplants_pak.R;
import com.example.greenplants_pak.HireAndRating.RatingBarActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HireActivity extends AppCompatActivity {

    DatabaseReference
            UserTokenTableFirebaseDatabase ;

    String Lattitude = " " ;
    String Longitude = " " ;

    String DateofDay = "" ;
    DatabaseReference NotifcationHire ;
    ImageView HirePic ;
    TextView HireSalery  , NameofHire , Age  , setDayofHire  ;
    CircularImageView Plusbtn , Minnusbtn  ;
    ImageView WrokPlace ;
    TextView SetPrice ;
    Button CAllNow , Hire , Ratings ;
    ImageButton SelectDateof;
    int number = 1 ;
    int calclulatelaborsalery ;
    TextView DateView ;
    DatabaseReference databaseReference ;
    FirebaseAuth mAuth ;
    /////////////////////////////////
    private int Year_x  , Month_x , Dat_x ;
    static final int  DIALOG_ID = 0 ;
    String value ;
    String Dateof = "" ;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

try {

    if (requestCode == 2) {
        Lattitude = data.getStringExtra("Lattitude");
        Longitude = data.getStringExtra("Longitude");


        if (!Lattitude.isEmpty() && !Lattitude.isEmpty()) {
            getAddress(Double.valueOf(Lattitude), Double.valueOf(Longitude));
        }
        Toast.makeText(this, data.getStringExtra("Longitude") + "", Toast.LENGTH_SHORT).show();
    }

}catch (Exception e){
    Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
}
    }

    DatabaseReference HireRecommenderLogic;

    ///////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creativehireactitvity);

        mAuth = FirebaseAuth.getInstance();
        SelectDateof = findViewById(R.id.selectdate);

        final Calendar calendar = Calendar.getInstance();
        Year_x = calendar.get(calendar.YEAR);
        Month_x = calendar.get(calendar.MONTH);
        Dat_x = calendar.get(calendar.DAY_OF_MONTH);

        HireRecommenderLogic = FirebaseDatabase.getInstance().getReference("recommeder").child(mAuth.getUid());




        DateView = findViewById(R.id.date);
        Showdialog();


        UserTokenTableFirebaseDatabase = FirebaseDatabase.getInstance().getReference("users/"
        + getIntent().getStringExtra("pushid") + "/") ;

        UserTokenTableFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
            try {    // whenever data at this location is updated.
                 value = dataSnapshot.child("token").child("generatedToken").getValue().toString();
//                Toast.makeText(HireActivity.this, "val" + value, Toast.LENGTH_SHORT).show();



                //Log.d(TAG, "Value is: " + value);
            }catch (Exception e){
                Toast.makeText(HireActivity.this, "No Token", Toast.LENGTH_SHORT).show();

            }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        Ratings = findViewById(R.id.ratactvity);
        HirePic = findViewById(R.id.hirepichire);
        setDayofHire = findViewById(R.id.setday);
        HireSalery  = findViewById(R.id.pricehire);
        Plusbtn = findViewById(R.id.Plus);
        Minnusbtn = findViewById(R.id.Minus);
        NameofHire = findViewById(R.id.hirename);
        SetPrice = findViewById(R.id.setpricehire);
        Hire = findViewById(R.id.hire);
        WrokPlace  = findViewById(R.id.workplace);
        Age = findViewById(R.id.hireage);
        NotifcationHire = FirebaseDatabase.getInstance().getReference("Notification/" + getIntent().getStringExtra("pushid"));
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("PicUri")).into(HirePic);
        setDayofHire.setText(number+"");
        Age.setText("price  : " +  getIntent().getStringExtra("price"));
        final String Price = getIntent().getStringExtra("price");
        HireSalery.setText("Price : " +Price);
        NameofHire.setText(getIntent().getStringExtra("username"));


        calclulatelaborsalery = Integer.parseInt(Price) * number ;
        SetPrice.setText(String.valueOf(calclulatelaborsalery));
        Plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                setDayofHire.setText(number+"");
                calclulatelaborsalery = Integer.parseInt(Price) * number ;
                SetPrice.setText(String.valueOf(calclulatelaborsalery));
            }
        });



        WrokPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetLocation.class);
                startActivityForResult(intent,2);
            }
        });


        Minnusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(number>=2){
                number--;}
                setDayofHire.setText(number+"");
                calclulatelaborsalery = Integer.parseInt(Price) * number ;
                SetPrice.setText(String.valueOf(calclulatelaborsalery));

            }
        });
    Ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HireActivity.this , RatingBarActivity.class);
                i.putExtra("LoggerName" ,  getIntent().getStringExtra("loggername"));
                i.putExtra("itemkey" , getIntent().getStringExtra("itemid"));
              startActivity(i);
            }
        });
        Hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameHire;
                String hireSalery;
                String hireSetPrice;
                String picturewhowanttohire;
                String days ;
                String namepersonwanttohire;
                String location;
                String Hirepersonskills  = getIntent().getStringExtra("Skill");
                String logkey = mAuth.getUid()  ;
                String LoggerSkill = "Customer";
                nameHire = NameofHire.getText().toString() ;
                hireSalery = Price;
                hireSetPrice = SetPrice.getText().toString();




           //     Toast.makeText(HireActivity.this, "", Toast.LENGTH_SHORT).show();

                picturewhowanttohire = getIntent().getStringExtra("PicUri");
                days = setDayofHire.getText().toString() ;
                namepersonwanttohire = getIntent().getStringExtra("loggername");

                if(Lattitude.isEmpty()){
                    Toast.makeText(HireActivity.this, "Please Select Location", Toast.LENGTH_SHORT).show();
                 return;
                }

                location = Lattitude + "/" + Longitude ;

                if(!Dateof.isEmpty()){

                    try {
                        String NotifcationPushKey = NotifcationHire.push().getKey();

                        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                        String time = dateFormat.format(calendar.getTime());



                        Notification notification = new Notification(nameHire, hireSalery, hireSetPrice, picturewhowanttohire, days
                                , namepersonwanttohire, location, logkey , LoggerSkill , Hirepersonskills, Dateof+"" +time  , NotifcationPushKey
                         , getIntent().getStringExtra("pushid")  , "true" ,"false");



                        if(Integer.valueOf(Function(DateofDay , getCurrentDateOFDay()))>=0){

                            NotifcationHire.child(NotifcationPushKey).setValue(notification);

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AddFavourite")
                                    .child(mAuth.getUid());
                            Map<String  , String > map = new HashMap<>();
                            map.put("cata" ,getIntent().getStringExtra("cat") );
                            databaseReference.child(databaseReference.push().getKey())
                                    .setValue(map);


                            Toast.makeText(HireActivity.this, "Successfull Hire Notification Sent", Toast.LENGTH_SHORT).show();



                        }else{

                            showSnakbar(getApplicationContext(), WrokPlace.getRootView(), "Pleause Enter Valid Date");

                        }




                    }catch (Exception e){
                        Toast.makeText(getApplicationContext() , e.toString() , Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext() , "Date or place empty" , Toast.LENGTH_LONG).show();
                }
            }
        });



    }
    private void BottomSheetFuncation(final String notifcationPushKey, final Notification notification) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                HireActivity.this , R.style.BtoonSheetDialogTheme
        );

        View Bottomsheetview = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_botton_sheet , (LinearLayout) findViewById(R.id.bottom_sheet_container)
                );
        Button submit = Bottomsheetview.findViewById(R.id.sheetsub) ;
        TextView NameSheet = Bottomsheetview.findViewById(R.id.sheetname) ;
        TextView AgeSheet = Bottomsheetview.findViewById(R.id.sheetphone) ;
        TextView SalerySheet = Bottomsheetview.findViewById(R.id.sheetsalery) ;
        CircularImageView PicSheet = Bottomsheetview.findViewById(R.id.picsheet) ;
        NameSheet.setText(notification.getName().toString());
        SalerySheet.setText(notification.getHireSetPrice().toString());


        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("PicUri")).into(PicSheet);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotifcationHire.child(notifcationPushKey).setValue(notification);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AddFavourite")
                        .child(mAuth.getUid());
                Map<String  , String > map = new HashMap<>();
map.put("cata" ,getIntent().getStringExtra("cat") );
                databaseReference.child(databaseReference.push().getKey())
                        .setValue(map);


                Toast.makeText(HireActivity.this, "Successfull Hire Notification Sent", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
                try {
                //    new MyFirebaseInstanceService().sendMessageSingle(getApplicationContext(), value, "Notification", "You have Notification from " + getIntent().getStringExtra("loggername"), null);
                }catch (Exception e){
                    Toast.makeText(HireActivity.this, "No Token", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView Fav = Bottomsheetview.findViewById(R.id.faveourite) ;
        Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AddFavourite")
                         .child(mAuth.getUid());
                         databaseReference.child(databaseReference.push().getKey())
                         .setValue(getIntent().getStringExtra("cat"));
            //    Toast.makeText(HireActivity.this, "FAveourite", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        Bottomsheetview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       // Toast.makeText(HireActivity.this, "Share", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(Bottomsheetview);
        bottomSheetDialog.show();
    }
    public  void Showdialog(){
        SelectDateof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_ID)
            return  new DatePickerDialog(this , dpickerListener , Year_x ,Month_x , Dat_x);
        return null ;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Year_x = year ; Month_x = month+1 ; Dat_x = dayOfMonth ;
          //  Toast.makeText(HireActivity.this , "yaer"  + Year_x + "month "+Month_x +"Day"+ Dat_x + " " , Toast.LENGTH_LONG).show();
            Dateof = Dat_x + "-" +Month_x + "-" + Year_x + " " ;
            DateView.setText(Dat_x + "-" +Month_x + "-" + Year_x );
            DateofDay = String.valueOf(Dat_x) + " " + String.valueOf(Month_x)+ " " + String.valueOf(Year_x) ;
            DateView.setText(DateofDay);
            if(Integer.valueOf(Function(DateofDay , getCurrentDateOFDay()))<0){
                Toast.makeText(getApplicationContext(), "Pleause Enter Valid Date", Toast.LENGTH_SHORT).show();
                return ;
            }
        }
    };


    @NotNull
    private String Function(String inp1 , String inp2){

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String inputString1 = inp1;
        String inputString2 = inp2;
        long diff = 0;
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            diff = date1.getTime() - date2.getTime();
            Log.d("Days: " , TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + "" ;
    }

    public String getCurrentDateOFDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public  void showSnakbar(Context context, View parentLayout, String message){
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }




    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(HireActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
           // add = add + "\n" + obj.getCountryName();
           // add = add + "\n" + obj.getCountryCode();
          //  add = add + "\n" + obj.getAdminArea();
          //  add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();
            // TennisAppActivity.showDialog(add);
        } catch ( IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
