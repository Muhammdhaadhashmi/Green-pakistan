package com.example.greenplants_pak.ShopKeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Model.ItemClass;
import com.example.greenplants_pak.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    Context activity ;
    List<ItemClass> itemList ;

    public GridAdapter(Context activity, List<ItemClass> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.card_layout_item , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, int position) {

        ItemClass items = itemList.get(position);
        holder.Title.setText(items.getItem_Name());
        Picasso.with(activity).load(items.getUri()).fit().centerCrop().into(holder.Picture);

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
