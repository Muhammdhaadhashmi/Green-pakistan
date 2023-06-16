package com.example.greenplants_pak.Admin_side;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Model.UserModelClass;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class AccountsViewAdapterForSentEmailPasswordToPhone extends RecyclerView.Adapter<AccountsViewAdapterForSentEmailPasswordToPhone.ViewHolder> implements Filterable {
    DatabaseReference LoggerDetail  ;
    public Context mContext ;
    public List<UserModelClass> list ;
    public List<UserModelClass> mlistFull ;
    public AccountsViewAdapterForSentEmailPasswordToPhone(Context mContext, List<UserModelClass> list) {
        LoggerDetail = FirebaseDatabase.getInstance().getReference("users");
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);
}




    @NonNull
    @Override
    public AccountsViewAdapterForSentEmailPasswordToPhone.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.userlistd , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AccountsViewAdapterForSentEmailPasswordToPhone.ViewHolder holder, int position) {
        final UserModelClass usersModelClass= list.get(position);
        holder.EmailTextview.setText(usersModelClass.getEmails());
        Picasso.with(mContext).load(usersModelClass.getProfilePhoto()).fit().centerCrop().into(holder.imageView);
        holder.Status.setText(usersModelClass.getUserName());
        holder.button.setText("Send");
        holder.button.setBackgroundResource(R.drawable.bt_uisquare_gree);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                        .setTitle("Verifiy User Account")
                        .setMessage("Are You Want Email Password to  Phone !")
                        .setPositiveButton("Send",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        DatabaseReference
                                                databaseReference = FirebaseDatabase.getInstance()
                                                .getReference("users") ;
                                        UserModelClass userModelClass = new UserModelClass(
                                                usersModelClass.getUUID(), usersModelClass.getUserName()
                                                , usersModelClass.getEmails(), usersModelClass.getPassword(),
                                                usersModelClass.getCnic(), usersModelClass.getProfilePhoto(),
                                                usersModelClass.getPhone(),
                                                usersModelClass.getUserType()
                                                , usersModelClass.getLattitude() , usersModelClass.getLongitude()
                                                , "true"   , usersModelClass.getIsOnline() , usersModelClass.getRating() );
                                        databaseReference.child(userModelClass.getUUID()).setValue(userModelClass);

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
        });





    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        ImageView imageView = itemView.findViewById(R.id.profile_image);
        TextView EmailTextview = itemView.findViewById(R.id.email);
        TextView Status = itemView.findViewById(R.id.t2);
         Button button = itemView.findViewById(R.id.btnn);
    }

    @Override
    public Filter getFilter() {
        return Dataresult;
    }

    private Filter Dataresult = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<UserModelClass> FilterList = new ArrayList<>() ;

            if(constraint == null && constraint.length()==0){
                FilterList.addAll(mlistFull);
            }else{
                String Characters = constraint.toString().toLowerCase().trim() ;
                for (UserModelClass usersModelClass : mlistFull){
                    if(usersModelClass.getUserName().toLowerCase().contains(Characters)){
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
            list.addAll((Collection<? extends UserModelClass>) results.values);
            notifyDataSetChanged();
        }


    };


}
