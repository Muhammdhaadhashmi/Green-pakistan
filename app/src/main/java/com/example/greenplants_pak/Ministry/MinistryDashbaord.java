package com.example.greenplants_pak.Ministry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.greenplants_pak.CalculatorView;
import com.example.greenplants_pak.Management.Login;
import com.example.greenplants_pak.R;
import com.example.greenplants_pak.ShopKeeper.SuperDash;
import com.google.firebase.auth.FirebaseAuth;

public class MinistryDashbaord extends AppCompatActivity {

    Button ComplainView ;
    Button progree_view ;
    Button Respond ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ministry_dashbaord);
        ComplainView = findViewById(R.id.xomplain);
        ComplainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext() , View_Complain.class));
            }
        });
        Respond  = findViewById(R.id.respind)
                ;
        Respond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Response_.class));
            }
        });

        progree_view = findViewById(R.id.notification);
        progree_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext() , View_Progress.class));
            }
        });
    }
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(MinistryDashbaord.this)
                .setTitle("Logout")
                .setMessage("Are You Want Logout Account !")
                .setPositiveButton("Logout",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                mAuth.signOut();
                                startActivity(new Intent( getApplicationContext() , Login.class));
                                finish();
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
        alertDialog.show();
    }
}