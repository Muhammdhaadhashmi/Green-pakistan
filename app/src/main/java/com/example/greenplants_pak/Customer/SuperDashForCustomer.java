package com.example.greenplants_pak.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenplants_pak.Management.Login;
import com.example.greenplants_pak.R;
import com.example.greenplants_pak.ShopKeeper.ItemViewFragement;
import com.example.greenplants_pak.ShopKeeper.ProfileViewFragement;
import com.example.greenplants_pak.ShopKeeper.ShopKeeperDashboardFragment;
import com.example.greenplants_pak.ShopKeeper.ViewNotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class SuperDashForCustomer extends AppCompatActivity {
    BottomNavigationView bottomNavigation ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_dash);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.getMenu().findItem(R.id.cat).setChecked(true);
        openFragment(DashboardCustomer.newInstance("",""));

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        try {
                            openFragment(ItemViewFragementForCustomer.newInstance(getIntent().getStringExtra("loggername"), ""));
                        } catch (Exception e ){

                        }
                        return true;
                    case R.id.navigation_profile:
                        openFragment(ProfileViewFragement.newInstance("", ""));
                        return true;
                    case R.id.navigation_notifications:
                        openFragment(ShowResponseToShopKeeper.newInstance("", ""));
                        return true;
                    case R.id.cat:
                        openFragment(DashboardCustomer.newInstance("" , ""));
                        return true;
                }
                return false;
            }
        });

    }
 /*
    public void removeYourFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (StackFragment != null) {
            transaction.remove(StackFragment);
            transaction.commit();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
           // StackFragment = null;
        }
    }

  */

    public void openFragment(Fragment fragment) {

        TabLayout tabLayout = findViewById(R.id.tablayout);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(SuperDashForCustomer.this)
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