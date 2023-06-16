package com.example.greenplants_pak.ShopKeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.greenplants_pak.AboutUs;
import com.example.greenplants_pak.CalculatorView;
import com.example.greenplants_pak.Model.Notification;
import com.example.greenplants_pak.R;
import com.example.greenplants_pak.Customer.UpdateProfileForCheff;
import com.example.greenplants_pak.WetherUpdate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopKeeperDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopKeeperDashboardFragment extends Fragment {

    Button ViewDish  , Update , Chat  , AddDishess , ViewNotifications;  ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "personpic";
    private static final String ARG_PARAM2 = "personname";
    FirebaseAuth mAuth ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView Badge ;
    public ShopKeeperDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChecffDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopKeeperDashboardFragment newInstance(String param1, String param2) {
        ShopKeeperDashboardFragment fragment = new ShopKeeperDashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        mAuth = FirebaseAuth.getInstance() ;
        View v =  inflater.inflate(R.layout.shopkeeperdashboard, container, false);

        Badge = v.findViewById(R.id.bage) ;

        (Update = v.findViewById(R.id.profile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , UpdateProfileForCheff.class);
                i.putExtra("type" , "Cheff");
                startActivity(i);
            }
        });

        Button Help;
        (Help = v.findViewById(R.id.help)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , CalculatorView.class);
                i.putExtra("type" , "Cheff");
                startActivity(i);
            }
        });




        (Chat = v.findViewById(R.id.chat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowCustomerForChat.class);
                intent.putExtra(mParam1 , getArguments().getString("personpic") ) ;
                intent.putExtra(mParam2 , getArguments().getString("personname") ) ;
                startActivity(intent);// To clean up all activities
            }
        });

        (ViewNotifications = v.findViewById(R.id.notification)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ShowNotification.class);
                startActivity(i);
            }
        });
        (ViewDish = v.findViewById(R.id.viewdish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , WetherUpdate.class);
                startActivity(i);
            }
        });
        AddDishess = v.findViewById(R.id.adddishes) ;
        AddDishess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , AddPlants.class));
            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notification/"+mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 int i = 0 ;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()    ){
                    Notification notification
                             = dataSnapshot.getValue(Notification.class);
                    if(notification.getIsRead().equals("false")){
                        i= i + 1 ;
                    }
                }
                if(i==0){
                    Badge.setVisibility(View.INVISIBLE);
                }else{
                    Badge.setVisibility(View.VISIBLE);
                    Badge.setText(i+"");
                  }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
     return  v ;
    }
}