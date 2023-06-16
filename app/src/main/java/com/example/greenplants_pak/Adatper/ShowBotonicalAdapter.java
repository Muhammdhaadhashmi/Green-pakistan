package com.example.greenplants_pak.Adatper;

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

import com.example.greenplants_pak.MapPackage.MapsActivityViewLocation;
import com.example.greenplants_pak.Model.Botonical_infomation;
import com.example.greenplants_pak.Model.Botonical_infomation;
import com.example.greenplants_pak.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowBotonicalAdapter extends RecyclerView.Adapter<ShowBotonicalAdapter.ViewHolder> implements Filterable {
    DatabaseReference LoggerDetail  ;
    public Activity mContext ;
    public List<Botonical_infomation> list ;
    public List<Botonical_infomation> mlistFull ;
    String LoggerKey , LoggerName , LoggerPicture  , LoggerSkills ;
    EditText editText  ;

    public ShowBotonicalAdapter(Activity mContext, List<Botonical_infomation> list) {
        LoggerDetail = FirebaseDatabase.getInstance().getReference("users");
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);
    }
 @NonNull
    @Override
    public ShowBotonicalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.usersidelists_info , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowBotonicalAdapter.ViewHolder holder, int position) {
        final Botonical_infomation usersModelClass= list.get(position);
        holder.Subj.setText(usersModelClass.getSubject());
        holder.Desc.setText(usersModelClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setLoggerName(String loggername) {
     LoggerName = loggername ;

    }




    public class ViewHolder extends  RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        TextView Subj = itemView.findViewById(R.id.subj);
        TextView Desc = itemView.findViewById(R.id.desc);



    }

    @Override
    public Filter getFilter() {
        return Dataresult;
    }

    private Filter Dataresult = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Botonical_infomation> FilterList = new ArrayList<>() ;

            if(constraint == null && constraint.length()==0){
                FilterList.addAll(mlistFull);
            }else{
                String Characters = constraint.toString().toLowerCase().trim() ;
                for (Botonical_infomation usersModelClass : mlistFull){
                    if(usersModelClass.getSubject().toLowerCase().contains(Characters)){
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
            list.addAll((Collection<? extends Botonical_infomation>) results.values);
            notifyDataSetChanged();
        }


    };


}
