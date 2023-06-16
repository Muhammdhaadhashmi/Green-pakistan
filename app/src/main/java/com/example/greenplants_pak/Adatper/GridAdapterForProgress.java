package com.example.greenplants_pak.Adatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Model.Progress;
import com.example.greenplants_pak.Model.Progress;
import com.example.greenplants_pak.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapterForProgress extends RecyclerView.Adapter<GridAdapterForProgress.ViewHolder> {
    Context activity ;
    List<Progress> itemList ;

    public GridAdapterForProgress(Context activity, List<Progress> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public GridAdapterForProgress.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.card_layout_item , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapterForProgress.ViewHolder holder, int position) {

        Progress items = itemList.get(position);
        holder.Title.setText(items.getSubject());
        Picasso.with(activity).load(items.getPlant_pic()).fit().centerCrop().into(holder.Picture);

        holder.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent i = new Intent(activity , )
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class  ViewHolder extends  RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView Title  = itemView.findViewById(R.id.nameitems);
        ImageView Picture= itemView.findViewById(R.id.pictureitem) ;

    }
}
