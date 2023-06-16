package com.example.greenplants_pak.ShopKeeper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Customer.RecipieView;
import com.example.greenplants_pak.HireActivity.HireActivity;
import com.example.greenplants_pak.Management.Login;
import com.example.greenplants_pak.MapPackage.MapsActivityViewLocation;
import com.example.greenplants_pak.Model.ItemClass;
import com.example.greenplants_pak.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowItemAdapterForDelete extends RecyclerView.Adapter<ShowItemAdapterForDelete.ViewHolder> implements Filterable {
    DatabaseReference LoggerDetail  ;
    public Activity mContext ;
    public List<ItemClass> list ;
    public List<ItemClass> mlistFull ;
    String LoggerKey , LoggerName , LoggerPicture  , LoggerSkills ;
    EditText editText  ;

    public ShowItemAdapterForDelete(Activity mContext, List<ItemClass> list) {
        LoggerDetail = FirebaseDatabase.getInstance().getReference("users");
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);
    }
 @NonNull
    @Override
    public ShowItemAdapterForDelete.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.usersidelists , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowItemAdapterForDelete.ViewHolder holder, int position) {
        final ItemClass usersModelClass= list.get(position);
        holder.UserNameTextView.setText(usersModelClass.getItem_Name());
        try {
            holder.EmailTextview.setMovementMethod(new ScrollingMovementMethod());
        }catch (Exception e){
            Toast.makeText(mContext, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        holder.EmailTextview.setText(usersModelClass.getDishType());

        if(usersModelClass.getDishType().equals("item_picture")) {
            Picasso.with(mContext).load(usersModelClass.getUri()).fit().centerCrop().into(holder.imageView);
        }else{
            holder.imageView.setBackgroundResource(R.drawable.ved);
        }
        holder.MobileTextView.setText(usersModelClass.getDate());
        holder.SkillsTextView.setText(usersModelClass.getTime());
        holder.RupeesTextView.setText(usersModelClass.getPrice());
        holder.Status.setText(usersModelClass.getItem_Name());





        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                        .setTitle("Delete")
                        .setMessage("Are You Want Delete")
                        .setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ItemDatabase");
                                        databaseReference.child(usersModelClass.getItem_ID()).removeValue();

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
                return true;
            }
        });

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(mContext , MapsActivityViewLocation.class);
                    i.putExtra("longi" , usersModelClass.getLongitude());
                    i.putExtra("lat" , usersModelClass.getLattitude());
                    i.putExtra("name" , usersModelClass.getItem_Name());
                    mContext.startActivity(i);
                }

        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setLoggerName(String loggername) {
     LoggerName = loggername ;

    }

    public void GetNodeAtPosition(int adapterPosition) {

            ItemClass usersModelClass= list.get(adapterPosition);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ItemDatabase");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("ItemUpload") ;
            FirebaseStorage mFirebaseStorage
                     = FirebaseStorage.getInstance(); ;

        StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(usersModelClass.getUri());
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                databaseReference.child(usersModelClass.getItem_ID()).removeValue();
                Toast.makeText(mContext, " Delete Successull", Toast.LENGTH_SHORT).show();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Toast.makeText(mContext, "Picture Not Delete", Toast.LENGTH_SHORT).show();

            }
        });







    }


    public class ViewHolder extends  RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        ImageView imageView = itemView.findViewById(R.id.viewimg);
        ImageView location = itemView.findViewById(R.id.locationview);
        TextView EmailTextview = itemView.findViewById(R.id.viewemail);
        TextView UserNameTextView = itemView.findViewById(R.id.viewusername);
        TextView MobileTextView = itemView.findViewById(R.id.viewmobile);
        TextView SkillsTextView = itemView.findViewById(R.id.viewtype);
        TextView RupeesTextView = itemView.findViewById(R.id.viewruppes);



        TextView Status = itemView.findViewById(R.id.descriptionv);

        CircularImageView circularImageView = itemView.findViewById(R.id.onli);
    }

    @Override
    public Filter getFilter() {
        return Dataresult;
    }

    private Filter Dataresult = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ItemClass> FilterList = new ArrayList<>() ;

            if(constraint == null && constraint.length()==0){
                FilterList.addAll(mlistFull);
            }else{
                String Characters = constraint.toString().toLowerCase().trim() ;
                for (ItemClass usersModelClass : mlistFull){
                    if(usersModelClass.getItem_Name().toLowerCase().contains(Characters)){
                        FilterList.add(usersModelClass);
                    }
                }

            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = FilterList ;
            return filterResults ;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends ItemClass>) results.values);
            notifyDataSetChanged();
        }


    };


}
