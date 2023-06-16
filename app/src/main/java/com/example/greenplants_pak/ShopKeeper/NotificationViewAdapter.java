package com.example.greenplants_pak.ShopKeeper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenplants_pak.Model.Notification;
import com.example.greenplants_pak.Model.Response;
import com.example.greenplants_pak.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationViewAdapter extends RecyclerView.Adapter<NotificationViewAdapter.ViewHolder> {
    String NotificatonIdTo_Delete ;
    List<Notification> notificationList ;
    Context mContext ;
    Activity ShowNo ;
    DatabaseReference ResponseDataBaseFirebase ;
    public NotificationViewAdapter(List<Notification> notificationList, Context mContext) {
        this.notificationList = notificationList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public NotificationViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.notification_layout , null);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationViewAdapter.ViewHolder holder, int position) {
        final Notification notification = notificationList.get(position);
        holder.Day.setText(notification.getDays());
        holder.Location.setText("Click Here To View Location");
        holder.Name.setText(notification.getNamepersonwanttohire() + " Req  " + notification.getName());
        holder.Yoursalery.setText(notification.getHireSalery());

        holder.setPrice.setText(notification.getHireSetPrice());


        String[] str = notification.getLocation().split("/");





        holder.Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ShowNo)
                        .setTitle("Location")
                        .setMessage(getAddress(Double.valueOf(str[0]) , Double.valueOf(str[1]) , holder.Location))
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {

                                }catch (Exception e){
                               }
                            }
                        }).create().show();
            }
        });


        if(!notification.getPicturewhowanttohire().toString().isEmpty()){
        Picasso.with(mContext).load(notification.getPicturewhowanttohire()).fit().centerCrop().into(holder.notifcationImage);
        }
        else{
            holder.aSwitch.setVisibility(View.INVISIBLE);
            holder.Date.setVisibility(View.INVISIBLE);

        }
        holder.Skill.setText(notification.getLoggerSkill());
        PrettyTime p = new PrettyTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String strDate = notification.getDate();
        Date date = null;
        try {
            date = new Date(sdf.parse(strDate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try{


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notification/"+notification.getNotificationpersonkey());
            Notification notification1 = new Notification(notification.getName(), notification.getHireSalery(), notification.getHireSetPrice(), notification.getPicturewhowanttohire(), notification.getDays()
                    , notification.getNamepersonwanttohire(), notification.getLocation(), notification.getLoggerKey() , notification.getLoggerSkill() , notification.getHirePrsonSkill(), notification.getDate()  , notification.getNotkey()
                    , notification.getNotificationpersonkey(), "true" ,"true");
            databaseReference.child(notification1.getNotkey()).setValue(notification1);

        }catch (Exception e){
            Toast.makeText(mContext, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }


        holder.Date.setText(notification.getDate() + " " + p.format(date));
        //       Log.d("dddd",date.toString());
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ResponseDataBaseFirebase = FirebaseDatabase.getInstance().getReference("response");
                    String noficationpushkey  = ResponseDataBaseFirebase.push().getKey();
                        Response response = new   Response(
                        notification.getName(),
                        notification.getHireSalery(),
                        notification.getHireSetPrice(),
                        notification.getPicturewhowanttohire(),
                        notification.getDays(),
                        notification.getNamepersonwanttohire(),
                        notification.getLocation(),
                        notification.getLoggerKey(),
                        "Accept", noficationpushkey
        ,notification.getHirePrsonSkill() ,
                        notification.getNotificationpersonkey() , "false"
                );
                ResponseDataBaseFirebase.child(noficationpushkey).setValue(response);
                Toast.makeText(mContext , "Accept" , Toast.LENGTH_LONG).show();
                }else{
                    ResponseDataBaseFirebase.removeValue();
                    Toast.makeText(mContext , "Reject" , Toast.LENGTH_LONG).show(); } }        });
    }
    public String getAddress(double lat, double lng   , TextView WrokPlace ) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        String add = "" ;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
             add = obj.getAddressLine(0);
            // add = add + "\n" + obj.getCountryName();
            // add = add + "\n" + obj.getCountryCode();
            //  add = add + "\n" + obj.getAdminArea();
            //  add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
            //WrokPlace.setText("Click Here To View Location");
            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();
            // TennisAppActivity.showDialog(add);
        } catch ( IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
   return  add ;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public String GetNodeAtPosition(int adapterPosition) {
    Notification    notificationfordelete = notificationList.get(adapterPosition);
        String key  = notificationfordelete.getNotkey();
        DatabaseReference DeleteNotifcationFromDatabase = FirebaseDatabase.getInstance().getReference("Notification").child(NotificatonIdTo_Delete);
    //    Toast.makeText(mContext , notificationfordelete.getLoggerKey()  , Toast.LENGTH_LONG).show();
        DeleteNotifcationFromDatabase.child(key).removeValue();
        return  key ;
    }

    public void setIdToDelete(String push) {
        NotificatonIdTo_Delete = push;


    }

    public void setActivity(ShowNotification showNotification) {
         ShowNo = showNotification ;
    }
    public void setActivityFrament(Activity showNotification) {
        ShowNo = showNotification ;
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView)
        {

            super(itemView);
        }
        ImageView notifcationImage = itemView.findViewById(R.id.picwhowanttohire); ;
        TextView Name  = itemView.findViewById(R.id.nameofreqperson);
        TextView Location = itemView.findViewById(R.id.notificationlocation);
        TextView Yoursalery  = itemView.findViewById(R.id.yoursalery);
        TextView Day   = itemView.findViewById(R.id.days);
        TextView setPrice   = itemView.findViewById(R.id.notificationsetrupees);
        TextView Skill = itemView.findViewById(R.id.logskill);
        Switch aSwitch  = itemView.findViewById(R.id.acceptswtich);
        TextView Date  = itemView.findViewById(R.id.date);
    }
}
