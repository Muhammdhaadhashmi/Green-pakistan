package com.example.greenplants_pak.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.GridSpacingItemDecoration;
import com.example.greenplants_pak.Model.ItemClass;
import com.example.greenplants_pak.R;
import com.example.greenplants_pak.ShopKeeper.GridAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemViewFragementForCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemViewFragementForCustomer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<ItemClass> itemsList ;

    DatabaseReference databaseReference ;
    RecyclerView recyclerView ;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ItemViewFragementForCustomer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemViewFragementForCustomer newInstance(String param1, String param2) {
        ItemViewFragementForCustomer fragment = new ItemViewFragementForCustomer();
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
                             final Bundle savedInstanceState) {

        databaseReference = FirebaseDatabase.getInstance().getReference("ItemDatabase");
        View v = inflater.inflate(R.layout.fragment_item_view, container, false);

        recyclerView = v.findViewById(R.id.recylerview);
        int spanCount = 2; // 3 columns
        int spacing = 15; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext() ,2));

        FirebaseAuth mAuth = FirebaseAuth.getInstance() ;
        itemsList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    ItemClass items = dataSnapshot.getValue(ItemClass.class);
itemsList.add(items);



                }
                GridAdapterForCustomer
                        gridAdapter
                        = new GridAdapterForCustomer( getContext() , itemsList );
                gridAdapter.setLoggerName("");
                recyclerView.setAdapter(gridAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return  v;
    }
}
