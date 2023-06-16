package com.example.greenplants_pak.ShopKeeper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greenplants_pak.Model.UserModelClass;
import com.example.greenplants_pak.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileViewFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileViewFragement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    StorageReference UserStorageDatabase  ;;
    CircularImageView Choose ;
    EditText Username, Emails, Passowrd, Phone , Profession , Location ;
//    ImageView EditBtn ;
    ImageView EditBtn;
    DatabaseReference UserDatabaseRef  ;
    String picture ;
    String Lattitude  = "" ;
    String Longitude = "" ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileViewFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileViewFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileViewFragement newInstance(String param1, String param2) {
        ProfileViewFragement fragment = new ProfileViewFragement();
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
        View v =  inflater.inflate(R.layout.activity_myprofile, container, false);
        Choose = v.findViewById(R.id.picture);
        Username = v.findViewById(R.id.name);
        Emails = v.findViewById(R.id.email);
        Passowrd = v.findViewById(R.id.passw);
        Phone = v.findViewById(R.id.phone);
        Location = v.findViewById(R.id.loc);
        EditBtn = v.findViewById(R.id.edit_btn);
        Profession=v.findViewById(R.id.prof);

        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext() , ViewPicture.class);
                i.putExtra("uri", picture);
                startActivity(i);
            }
        });
        FirebaseAuth   mAuth = FirebaseAuth.getInstance();
        FirebaseUser CurrentUser = mAuth.getCurrentUser() ;
        UserDatabaseRef = FirebaseDatabase.getInstance().getReference("users") ;
        UserDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UserModelClass userModelClass = dataSnapshot1.getValue(UserModelClass.class);
                    if(userModelClass.getUUID().equals(CurrentUser.getUid())) {
                        Lattitude = userModelClass.getLattitude();
                        Longitude = userModelClass.getLongitude();
                        Username.setText(userModelClass.getUserName());
                        Emails.setText(userModelClass.getEmails());
                        Passowrd.setText(userModelClass.getPassword());
                        Phone.setText(userModelClass.getPhone());
                        Profession.setText(userModelClass.getUserType());

                        Username.setEnabled(false);
                        Emails.setEnabled(false);
                        Passowrd.setEnabled(false);
                        Phone.setEnabled(false);
                        Profession.setEnabled(false);
                        Picasso.with(getContext()).load(userModelClass.getProfilePhoto()).into(Choose);

                        picture = userModelClass.getProfilePhoto();
                        Location.setText(SetLocation(Lattitude , Longitude));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return  v ;
     }

    @Override
    public void onStart() {
        super.onStart();




    }
    private  String SetLocation(String Lat , String Long){
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        //List<Address> addresses =geocoder.getFromLocation(latitude, longitude, 1);
        String cityName ="" ;
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(Lat), Double.parseDouble(Long), 1);
            String address = addresses.get(0).getSubLocality();
             cityName = addresses.get(0).getLocality();


        } catch (IOException e) {
            e.printStackTrace();
        }
    return cityName;
    }
}