package com.example.greenplants_pak.Customer;

import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.HireActivity.HireActivity;
import com.example.greenplants_pak.MapPackage.MapsActivityViewLocation;
import com.example.greenplants_pak.Model.ItemClass;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowItemAdapter extends RecyclerView.Adapter<ShowItemAdapter.ViewHolder> implements Filterable {
    DatabaseReference LoggerDetail  ;
    public Activity mContext ;
    public List<ItemClass> list ;
    public List<ItemClass> mlistFull ;
    String LoggerKey , LoggerName , LoggerPicture  , LoggerSkills ;
    public ShowItemAdapter(Activity mContext, List<ItemClass> list) {
        LoggerDetail = FirebaseDatabase.getInstance().getReference("users");
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);
    }
 @NonNull
    @Override
    public ShowItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.usersidelists , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowItemAdapter.ViewHolder holder, int position) {
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
      try {
          int v = (Integer.valueOf(usersModelClass.getRating()) * 100) / 5;
          if(v>=60 &&v<=100){
              holder.circularImageView.setBackgroundResource(R.drawable.bt_uisquare_gree);
          }
          else if(v>=40 &&v<60){
              holder.circularImageView.setBackgroundResource(R.drawable.bt_uisquare);
          }
          else{
              holder.circularImageView.setBackgroundResource(R.drawable.reddot);
          }


          holder.RatingValue.setText(v + "");
      }catch (Exception e){
          Toast.makeText(mContext, "Format Error ", Toast.LENGTH_SHORT).show();
      }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usersModelClass.getDishType().equals("recipie"))
                {

                    Intent i = new Intent(mContext , RecipieView.class);
                    i.putExtra("uri" ,usersModelClass.getUri());
                    mContext.startActivity(i);

                }else{


                    Intent i  = new Intent( mContext , HireActivity.class);

                    i.putExtra("pushid" , usersModelClass.getCheff_id());
                    i.putExtra("itemid" , usersModelClass.getItem_ID());
                    i.putExtra("username" , usersModelClass.getItem_Name());
                    i.putExtra("loggername" , LoggerName);
                    i.putExtra("price" , usersModelClass.getPrice());
                    i.putExtra("PicUri" , usersModelClass.getUri());
                    i.putExtra("lat" , usersModelClass.getLattitude());
                    i.putExtra("cat" , usersModelClass.getCata());
                    i.putExtra("lon" , usersModelClass.getLongitude());
                    mContext.startActivity( i );
                   }

            }
        });


        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    holder.progress.setVisibility(View.INVISIBLE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();


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


    public class ViewHolder extends  RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        ImageView imageView = itemView.findViewById(R.id.viewimg);
        ProgressBar progress = itemView.findViewById(R.id.progressbarforrat);
        ImageView location = itemView.findViewById(R.id.locationview);
        TextView EmailTextview = itemView.findViewById(R.id.viewemail);
        TextView UserNameTextView = itemView.findViewById(R.id.viewusername);
        TextView MobileTextView = itemView.findViewById(R.id.viewmobile);
        TextView SkillsTextView = itemView.findViewById(R.id.viewtype);
        TextView RupeesTextView = itemView.findViewById(R.id.viewruppes);
        TextView RatingValue = itemView.findViewById(R.id.pvalue);

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
