package com.example.greenplants_pak.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.greenplants_pak.AddReminder;
import com.example.greenplants_pak.CalculatorView;
import com.example.greenplants_pak.R;
import com.example.greenplants_pak.ShopKeeper.ShowCustomerForChat;
import com.example.greenplants_pak.ViewReminder;
import com.example.greenplants_pak.WetherUpdate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardCustomer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "personpic";
    private static final String ARG_PARAM2 = "personname";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button Complaints_info ;

    Button WetherBtn ;
    Button ChatView ;
    Button Nursery  ;
    public DashboardCustomer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardCustomer.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardCustomer newInstance(String param1, String param2) {
        DashboardCustomer fragment = new DashboardCustomer();
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
        View v =   inflater.inflate(R.layout.dashboard, container, false);

        Button MinisTry = v.findViewById(R.id.ministry_view);
        MinisTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , ShowMinistry.class));
            }
        });

        Button Add_complain = v.findViewById(R.id.C_complain);
        Add_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , ShowItemsRecipieForComplain.class));
            }
        });
        Button Add_progress = v.findViewById(R.id.add_progress);
        Add_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , Add_progress.class));
            }
        });


        Complaints_info = v.findViewById(R.id.add_complain);
        Complaints_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity() , View_Botonical_information.class));
            }
        });

        Button Clocl = v.findViewById(R.id.clock);
        Clocl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getActivity() , AddReminder.class));
            }
        });


        Button CloclV = v.findViewById(R.id.clockview);
        CloclV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getActivity() , ViewReminder.class));
            }
        });


        Button recommender = v.findViewById(R.id.recomend);
        recommender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , ShowItems.class));
            }
        });

        Button b1 = v.findViewById(R.id.pfview);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , UpdateProfileForCustomer.class));
            }
        });

        Button b = v.findViewById(R.id.notification);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity()  , ShowNotitifcationResponse.class));
            }
        });
        ChatView = v.findViewById(R.id.chat);
        WetherBtn = v.findViewById(R.id.wether);
        Nursery = v.findViewById(R.id.dishes);





        ChatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowshopForChat.class);
                intent.putExtra(mParam1 , getArguments().getString("personpic") ) ;
                intent.putExtra(mParam2 , getArguments().getString("personname") ) ;
                startActivity(intent);
            }
        });

        Nursery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CalculatorView.class);
                intent.putExtra(mParam1 , getArguments().getString("personpic") ) ;
                intent.putExtra(mParam2 , getArguments().getString("personname") ) ;
                startActivity(intent);
            }
        });

        WetherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WetherUpdate.class);
                intent.putExtra(mParam1 , getArguments().getString("personpic") ) ;
                intent.putExtra(mParam2 , getArguments().getString("personname") ) ;
                startActivity(intent);
            }
        });


        return   v ;
    }
}