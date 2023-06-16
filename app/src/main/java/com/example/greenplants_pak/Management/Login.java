package com.example.greenplants_pak.Management;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenplants_pak.Customer.SuperDashForCustomer;
import com.example.greenplants_pak.Ministry.MinistryDashbaord;
import com.example.greenplants_pak.ShopKeeper.SuperDash;

import com.example.greenplants_pak.Model.UserModelClass;
import com.example.greenplants_pak.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    ProgressDialog pd ;
    public static final String TAG = "Error" ;
    private FirebaseAuth mAuth;
    TextView Register ;

    TextView loginbtn ;
    EditText Emails , Password ;
    TextView forgotpasswordvar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init() ;
        pd = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth!=null){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }
//        if(!Python.isStarted()){
//            Python.start(new AndroidPlatform(this));
//        }
//        final Python py = Python.getInstance();
//        final PyObject pyObj = py.getModule("matching");
//        try{
//            PyObject data = pyObj.callAttr("hallo","word");
//            String str = data.toString();
//            Toast.makeText(this, "value"+str, Toast.LENGTH_SHORT).show();
//
//
//
//            // its a recommender logic//
////            PyObject data1 = pyObj.callAttr("word2vec","Sweet Dish ");
////            PyObject data2 = pyObj.callAttr("word2vec","Sweet Dish ");
////
////
////            PyObject data3 = pyObj.callAttr("cosdis",data1 , data2);
////
////            Toast.makeText(this, "value"+data3.toString(), Toast.LENGTH_SHORT).show();
//
//
//        }catch (Exception e){
//            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
//
//        }

        set_Listenner() ;
        mAuth  = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Name, email address, and profile photo Url
            String key = user.getUid() ;
            final Boolean[] isExisUser = {false};
            DatabaseReference UserDatabaseRef = FirebaseDatabase.getInstance().getReference("users") ;
            UserDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        UserModelClass usersModelClass = dataSnapshot1.getValue(UserModelClass.class);
                        if(mAuth.getUid().equals(usersModelClass.getUUID())){


                            pd.dismiss();


                            if(
                                    (usersModelClass.getUserType().equals("ShopKeeper") ))
                            {
                                Intent  i = new Intent(getApplicationContext() , SuperDash.class);
                                i.putExtra("nid" ,  usersModelClass.getUUID()) ;
                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                i.putExtra("nname" ,  usersModelClass.getUserName()) ;
                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                i.putExtra("personname" ,  usersModelClass.getUserName()) ;
                                i.putExtra("personpic" ,  usersModelClass.getProfilePhoto()) ;
                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                startActivity(i);
                                Emails.setText("");
                                Password.setText("");
                                finish();
                            }
                            if(
                                    (        usersModelClass.getUserType().equals("Customer") )

                            ) {
                                pd.dismiss();
                                Emails.setText("");
                                Password.setText("");
                                Intent  i = new Intent(getApplicationContext() , SuperDashForCustomer.class);
                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                i.putExtra("personname" ,  usersModelClass.getUserName()) ;
                                i.putExtra("personpic" ,  usersModelClass.getProfilePhoto()) ;
                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                startActivity(i);
                                Emails.setText("");
                                Password.setText("");
                                finish();
                            }

                            if(
                                    (usersModelClass.getUserType().equals("Ministry") ))
                            {
                                Intent  i = new Intent(getApplicationContext() , MinistryDashbaord.class);
                                i.putExtra("nid" ,  usersModelClass.getUUID()) ;
                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                i.putExtra("nname" ,  usersModelClass.getUserName()) ;
                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                i.putExtra("personname" ,  usersModelClass.getUserName()) ;
                                i.putExtra("personpic" ,  usersModelClass.getProfilePhoto()) ;
                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                startActivity(i);
                                Emails.setText("");
                                Password.setText("");
                                finish();
                            }



//                                            if(
//                                                    (usersModelClass.getUserType().equals("Admin") && usersModelClass.getIsApprove().equals("true"))
//
//                                            ) {
//                                                Emails.setText("");
//                                                Password.setText("");
//                                                Intent  i = new Intent(getApplicationContext() , AdminDashboard.class);
//                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
//                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
//                                                i.putExtra("uri" ,  usersModelClass.getProfilePhoto()) ;
//                                                startActivity(i);
//
//                                                Emails.setText("");
//                                                Password.setText("");
//                                                finish();
//                                            }
                            isExisUser[0] = true ;
                        }
                    }
                    if(isExisUser[0]==false){
                        pd.dismiss();
                        Snackbar.make(Emails, "User Not Exist in Database", Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void set_Listenner() {
       forgotpasswordvar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext() , ForgotPassword.class));
           }
       });
     
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Signup.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(Emails.getText().toString().isEmpty()){
                    Emails.setText("Field is Empty");
                    return;
                }
                if(Password.getText().toString().isEmpty()){
                    Password.setError("Field is Empty");
                    return;
                }

                ////////////////////////////////////////////
                pd = new ProgressDialog(Login.this);
                pd.setMessage("loading");
                pd.show();
                //////////////////////////////////////////
                siginWithEmailPasswordFunction( Emails.getText().toString()  , Password.getText().toString());
            }
        });
    }

    private void siginWithEmailPasswordFunction(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(Login.this, "successfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Snackbar.make(Emails, "User Not Exist in Authatication", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                            pd.dismiss();
                            updateUI(null);
                        }
                    }
                    public void updateUI(final FirebaseUser user) {
                        if (user != null) {
                            // Name, email address, and profile photo Url
                            String key = user.getUid() ;
                            final Boolean[] isExisUser = {false};
                            DatabaseReference UserDatabaseRef = FirebaseDatabase.getInstance().getReference("users") ;
                            UserDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                        UserModelClass usersModelClass = dataSnapshot1.getValue(UserModelClass.class);
                                        if(mAuth.getUid().toString().equals(usersModelClass.getUUID())){
                                            pd.dismiss();


                                            if(
                                                    (usersModelClass.getUserType().equals("ShopKeeper") ))
                                             {
                                                Intent  i = new Intent(getApplicationContext() , SuperDash.class);
                                                i.putExtra("nid" ,  usersModelClass.getUUID()) ;
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("nname" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personname" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personpic" ,  usersModelClass.getProfilePhoto()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                startActivity(i);
                                                Emails.setText("");
                                                Password.setText("");
                                                finish();
                                            }
                                            if(
                                                    (        usersModelClass.getUserType().equals("Customer") )
                                            
                                            ) {
                                                pd.dismiss();
                                                Emails.setText("");
                                                Password.setText("");
                                                Intent  i = new Intent(getApplicationContext() , SuperDashForCustomer.class);
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personname" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personpic" ,  usersModelClass.getProfilePhoto()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                startActivity(i);

                                                Emails.setText("");
                                                Password.setText("");
                                                finish();
                                            }
                                            if(
                                                    (usersModelClass.getUserType().equals("Ministry") ))
                                            {
                                                Intent  i = new Intent(getApplicationContext() , MinistryDashbaord.class);
                                                i.putExtra("nid" ,  usersModelClass.getUUID()) ;
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("nname" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personname" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personpic" ,  usersModelClass.getProfilePhoto()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                startActivity(i);
                                                Emails.setText("");
                                                Password.setText("");
                                                finish();
                                            }
//                                            if(
//                                                    (usersModelClass.getUserType().equals("Admin") && usersModelClass.getIsApprove().equals("true"))
//
//                                            ) {
//                                                Emails.setText("");
//                                                Password.setText("");
//                                                Intent  i = new Intent(getApplicationContext() , AdminDashboard.class);
//                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
//                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
//                                                i.putExtra("uri" ,  usersModelClass.getProfilePhoto()) ;
//                                                startActivity(i);
//
//                                                Emails.setText("");
//                                                Password.setText("");
//                                                finish();
//                                            }
                                            isExisUser[0] = true ;
                                        }
                                    }
                                    if(isExisUser[0]==false){
                                        pd.dismiss();
                                        Snackbar.make(Emails, "User Not Exist in Database", Snackbar.LENGTH_LONG)
                                                .setAction("CLOSE", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                .show();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                });
    }


    private void init() {


        Register = findViewById(R.id.simplereg) ;

        Emails =   findViewById(R.id.email) ;
        Password = findViewById(R.id.pass) ;
        loginbtn=findViewById(R.id.log);
        mAuth = FirebaseAuth.getInstance();
        forgotpasswordvar = findViewById(R.id.forgot) ;

    }
}