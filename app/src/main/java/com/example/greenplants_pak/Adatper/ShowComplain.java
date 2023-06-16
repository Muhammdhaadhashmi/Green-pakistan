package com.example.greenplants_pak.Adatper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.greenplants_pak.Model.Complain;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowComplain extends RecyclerView.Adapter<ShowComplain.ViewHolder> implements Filterable {
    DatabaseReference LoggerDetail  ;
    public Activity mContext ;
    public List<Complain> list ;
    public List<Complain> mlistFull ;
    String LoggerKey , LoggerName , LoggerPicture  , LoggerSkills ;
    EditText editText  ;

    public ShowComplain(Activity mContext, List<Complain> list) {
        LoggerDetail = FirebaseDatabase.getInstance().getReference("users");
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);
    }
 @NonNull
    @Override
    public ShowComplain.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.usersidelists_info , null);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final ShowComplain.ViewHolder holder, int position) {
        final Complain usersModelClass= list.get(position);
        holder.Subj.setText(usersModelClass.getSubject()  +"\n" );
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

            List<Complain> FilterList = new ArrayList<>() ;

            if(constraint == null && constraint.length()==0){
                FilterList.addAll(mlistFull);
            }else{
                String Characters = constraint.toString().toLowerCase().trim() ;
                for (Complain usersModelClass : mlistFull){
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
            list.addAll((Collection<? extends Complain>) results.values);
            notifyDataSetChanged();
        }
    };
}
