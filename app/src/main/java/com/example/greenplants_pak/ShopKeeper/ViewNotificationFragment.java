package com.example.greenplants_pak.ShopKeeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Model.Notification;
import com.example.greenplants_pak.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewNotificationFragment extends Fragment {

    Button ViewDish  , Update , Chat  , AddDishess , ViewNotifications;  ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "personpic";
    private static final String ARG_PARAM2 = "personname";
    List<Notification> notificationslist;
    DatabaseReference NotifcationHire;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView Badge ;
    public ViewNotificationFragment() {
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
    public static ViewNotificationFragment newInstance(String param1, String param2) {
        ViewNotificationFragment fragment = new ViewNotificationFragment();
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

        mAuth = FirebaseAuth.getInstance();

        notificationslist = new ArrayList<>();
        NotifcationHire = FirebaseDatabase.getInstance().getReference("Notification/"+mAuth.getUid()  );

        View v =  inflater.inflate(R.layout.activity_show__notification, container, false);
        recyclerView = v.findViewById(R.id.archinotificationview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NotifcationHire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificationslist.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Notification notification = dataSnapshot1.getValue(Notification.class);
                    //    Toast.makeText(getApplicationContext(), notification.getNameHire(), Toast.LENGTH_LONG).show();
                    notificationslist.add(notification);

                }
                if(notificationslist.size() == 0){
                    Notification no_notifcation = new Notification("No Notifcation" ,""  ,"" ,"" ,"" , ""

                            , "" ,      "" , "" , "" , "" , "" , "" , ""  ,"");

                    notificationslist.add(no_notifcation);
                }
                Collections.reverse(notificationslist);
                final NotificationViewAdapter Adapter = new NotificationViewAdapter(notificationslist, getActivity());
                Adapter.setActivityFrament(getActivity());
                recyclerView.setAdapter(Adapter);
                final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        new RecyclerViewSwipeDecorator.Builder(getActivity(), c ,recyclerView , viewHolder ,dX, dY , actionState , isCurrentlyActive )
                                .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity() , R.color.colorAccent))
                                .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                                .addBackgroundColor(ContextCompat.getColor(getActivity() , R.color.colorAccent))
                                .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp).create().decorate();
                        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        viewHolder.itemView.setBackgroundColor(Color.parseColor("#fe104d"));
                        Adapter.setIdToDelete(mAuth.getUid());
                        Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition());
                    }
                });
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




     return  v ;
    }
}