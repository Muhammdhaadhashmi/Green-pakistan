package com.example.greenplants_pak.Customer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greenplants_pak.Model.Response;
import com.example.greenplants_pak.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowResponseToShopKeeper#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowResponseToShopKeeper extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowResponseToShopKeeper() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowResponseToShopKeeper.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowResponseToShopKeeper newInstance(String param1, String param2) {
        ShowResponseToShopKeeper fragment = new ShowResponseToShopKeeper();
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
        FirebaseAuth mAuth =FirebaseAuth.getInstance();
        View v  =inflater.inflate(R.layout.responsenotification, container, false);

        final RecyclerView recyclerView = v.findViewById(R.id.responsenotify);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() ));
        DatabaseReference ResponseDataBaseFirebase = FirebaseDatabase.getInstance().getReference("response");
        final List<Response> responseList = new ArrayList<>();
        ResponseDataBaseFirebase.addValueEventListener(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                               responseList.clear();
                                                               for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                                                   Response response = dataSnapshot1.getValue(Response.class);
                                                                   if(response.getLoggerKey().equals(mAuth.getUid())){
                                                                       responseList.add(response);}
                                                               }
                                                               ResponseAdapter   responseAdapter = new ResponseAdapter(getActivity() , responseList  );

                                                               recyclerView.setAdapter(responseAdapter);
                                                               // Item Touch helper swiping animation //
                                                               ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                                                                   @Override
                                                                   public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                                                       return false;
                                                                   }
                                                                   @Override
                                                                   public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                                                                       new RecyclerViewSwipeDecorator.Builder(getActivity() , c ,recyclerView , viewHolder ,dX, dY , actionState , isCurrentlyActive )
                                                                               .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity()  , R.color.colorAccent))
                                                                               .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                                                                               .addBackgroundColor(ContextCompat.getColor(getActivity()  , R.color.colorAccent))
                                                                               .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp).create().decorate();
                                                                       super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                                                                   }
                                                                   @Override
                                                                   public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                                                       viewHolder.itemView.setBackgroundColor(Color.parseColor("#fe104d"));
                                                                       responseAdapter.DeleteNodeByPosition(responseAdapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));
                                                                   }
                                                               });
                                                               itemTouchHelper.attachToRecyclerView(recyclerView);
                                                               //////////////////////////
                                                           }
                                                           @Override
                                                           public void onCancelled(@NonNull DatabaseError databaseError) {
                                                           }
                                                       }
        );
        return  v ;
    }
}