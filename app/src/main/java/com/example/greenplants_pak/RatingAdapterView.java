package com.example.greenplants_pak;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class  RatingAdapterView extends RecyclerView.Adapter<RatingAdapterView.ViewHolder> implements Filterable {
    DatabaseReference LoggerDetail  ;
    public Activity mContext ;
    public List<AddremClass> list ;
    public List<AddremClass> mlistFull ;
    String LoggerKey , LoggerName , LoggerPicture  , LoggerSkills ;
    public  RatingAdapterView(Activity mContext, List<AddremClass> list) {
        LoggerDetail = FirebaseDatabase.getInstance().getReference("users");
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);
    }
    @NonNull
    @Override
    public  RatingAdapterView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_rem , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final  RatingAdapterView.ViewHolder holder, int position) {
        final AddremClass usersModelClass = list.get(position);

        holder.Name.setText(usersModelClass.getName());
        holder.Dose.setText(usersModelClass.getDosage());
        holder.Unit.setText(usersModelClass.getDoesUnit());
        holder.SDate.setText(usersModelClass.getSdate());
        holder.RDate.setText(usersModelClass.getRdate());
        holder.Note.setText(usersModelClass.getNote());
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
        TextView Name = itemView.findViewById(R.id.name);
        TextView Dose = itemView.findViewById(R.id.dosage);
        TextView Unit = itemView.findViewById(R.id.unit);
        TextView SDate = itemView.findViewById(R.id.sdate);
        TextView RDate = itemView.findViewById(R.id.rtime);
        TextView Note = itemView.findViewById(R.id.note);
    }

    @Override
    public Filter getFilter() {
        return Dataresult;
    }

    private Filter Dataresult = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<AddremClass> FilterList = new ArrayList<>() ;


            FilterResults filterResults = new FilterResults();
            filterResults.values = FilterList ;
            return filterResults ;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends AddremClass>) results.values);
            notifyDataSetChanged();
        }
    };
}
