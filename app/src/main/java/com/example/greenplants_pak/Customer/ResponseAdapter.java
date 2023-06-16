package com.example.greenplants_pak.Customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Model.Response;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder> {
    Context mContext ;
    List<Response> responseList;

    Response response ;

    ShowNotitifcationResponse showNotitifcationResponse ;
    public ResponseAdapter(Context mContext, List<Response> responseList ) {
        this.mContext = mContext;
        this.responseList = responseList;

    }

    @NonNull
    @Override
    public ResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.responselayout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseAdapter.ViewHolder holder, int position) {
         Response notification = responseList.get(position);

        holder.Day.setText(notification.getDays());
        holder.Location.setText("Click Location Address");
        holder.Name.setText(notification.getNamepersonwanttohire()+" req  " + notification.getNameHire() );
        holder.Yoursalery.setText(notification.getHireSalery());
        holder.setPrice.setText(notification.getHireSetPrice());
        Picasso.with(mContext).load(notification.getPicturewhowanttohire()).fit().centerCrop().into(holder.notifcationImage);
        holder.responseStatus.setText(notification.getRespose());
        holder.skill.setText(notification.getHireSkills());







        holder.Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = responseList.get(position).getLocation();
                String[] str = location.split("/");


            }
        });





    /////////////////////////
      DatabaseReference  ResponseDataBaseFirebase = FirebaseDatabase.getInstance().getReference("response").child(notification.getResposnsenotifcationkey());
        ResponseDataBaseFirebase.child("isRead").setValue("true");
        /////////////////////



    }
          @Override
    public int getItemCount() {
        return responseList.size();
    }

    public String GetNodeAtPosition(int adapterPosition) {
        response = responseList.get(adapterPosition);
        String key = response.getResposnsenotifcationkey();
        return key ;





    }

    public void DeleteNodeByPosition(String getNodeAtPosition) {
        DatabaseReference ResponseDataBaseFirebase = FirebaseDatabase.getInstance().getReference("response");
        ResponseDataBaseFirebase.child(getNodeAtPosition).removeValue();

    }

    public void setIdToDelete(String push) {

    }


    public void ContectCreation(ShowNotitifcationResponse showNotitifcationResponse) {
        this.showNotitifcationResponse = showNotitifcationResponse;
    }

    public  class  ViewHolder extends  RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ImageView notifcationImage = itemView.findViewById(R.id.picwhowanttohire); ;
        TextView Name  = itemView.findViewById(R.id.nameofreqperson);
        TextView Location = itemView.findViewById(R.id.notificationlocation);
        TextView Yoursalery  = itemView.findViewById(R.id.yoursalery);
        TextView Day   = itemView.findViewById(R.id.days);
        TextView setPrice   = itemView.findViewById(R.id.notificationsetrupees);
        Switch aSwitch  = itemView.findViewById(R.id.acceptswtich);
        TextView responseStatus = itemView.findViewById(R.id.responsestatus);
         TextView skill = itemView.findViewById(R.id.hireskill);

    }

    public Address getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        Address obj = null  ;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return obj ;
    }
}
