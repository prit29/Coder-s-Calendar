package com.noobsever.codingcontests.Adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.noobsever.codingcontests.Constants;
import com.noobsever.codingcontests.DatabaseHandler;
import com.noobsever.codingcontests.Models.Notification;
import com.noobsever.codingcontests.Models.SingleObj;
import com.noobsever.codingcontests.Notifications.AlertReceiver;
import com.noobsever.codingcontests.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.view.View.*;
import static androidx.recyclerview.widget.RecyclerView.*;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {

    private List<SingleObj> data;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public Context context;

    public RecycleAdapter(List<SingleObj> data, Context ccc)
    {
        this.data=data;
        this.context =ccc;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recycler_item_view,viewGroup,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder recycleViewHolder, final int i) {

        final int k=i;
        recycleViewHolder.event.setText(data.get(i).getEvent());
        recycleViewHolder.start_time.setText(getTime(data.get(i).getStart()));
        recycleViewHolder.end_time.setText(getTime(data.get(i).getEnd()));


        int s=1;
        try {
            s= data.get(i).getResource().getId();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(s==1)
        {
            recycleViewHolder.handle_image.setImageResource(R.drawable.codeforces_logo);
            recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(201,201,201));
        }
        else if(s==12)
        {
            recycleViewHolder.handle_image.setImageResource(R.drawable.topcoder_logo);
            recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(127,237,255));
        }
        else if(s==73)
        {
            recycleViewHolder.handle_image.setImageResource(R.drawable.hackerearth_logo);
            recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(255  ,174,150));
            //recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(106,238,153));
        }
        else if(s==63)
        {
            recycleViewHolder.handle_image.setImageResource(R.drawable.hackerrank_logo);
            recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(106,238,153));
        }
        else if(s==2)
        {
            recycleViewHolder.handle_image.setImageResource(R.drawable.codechef_logo);
            recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(255,241,113));
        }
        else if(s==93)
        {
            recycleViewHolder.handle_image.setImageResource(R.drawable.atcoder_logo);
            //recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(0,127,255));
            recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(255,160,189));
        }
        else
        {
            recycleViewHolder.handle_image.setImageResource(R.drawable.spoj_logo);
            recycleViewHolder.cardView.setCardBackgroundColor(Color.rgb(186,129,238));
        }


        if(isBefore(data.get(k).getStart()))
        {
                recycleViewHolder.set.setVisibility(GONE);
                recycleViewHolder.run.setVisibility(VISIBLE);

        }
        else
        {
            recycleViewHolder.set.setVisibility(VISIBLE);
            recycleViewHolder.run.setVisibility(GONE);
        }

        recycleViewHolder.set_reminder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | hh:mm aa");
                sdf.setTimeZone(TimeZone.getDefault());
                Notification notification  = new Notification();
                notification.setTime(data.get(k).getStart());
                notification.setEvent(data.get(k).getEvent());
                Date date = null;
                try {
                    date = sdf.parse(getTime(data.get(k).getStart()));
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.MINUTE,-15);
                    StartAlarm(cal,data.get(k).getResource().getName()+"  \n"+data.get(k).getEvent(),notification);

                } catch (ParseException e) {
                    e.printStackTrace();

                }


            }
        });

        recycleViewHolder.bell_reminder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | hh:mm aa");
                sdf.setTimeZone(TimeZone.getDefault());
                Date date = null;
                Date date_end = null;
                try {
                    date = sdf.parse(getTime(data.get(k).getStart()));
                    date_end =sdf.parse(getTime(data.get(k).getEnd()));

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", cal.getTimeInMillis());
                    intent.putExtra("allDay", false);
                    intent.putExtra(CalendarContract.Events.DESCRIPTION,data.get(k).getResource().getName());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION,data.get(k).getHref());
                    intent.putExtra("rrule", "FREQ=DAILY");
                    cal.setTime(date_end);
                    intent.putExtra("endTime",cal.getTimeInMillis());
                    //intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                    intent.putExtra("title", data.get(k).getEvent());
                    context.startActivity(intent);


                } catch (ParseException e) {
                    e.printStackTrace();

                }



            }
        });

        recycleViewHolder.itemView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ShareItem(k);
                return true;
            }
        });

        recycleViewHolder.handle_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenContest(k);
                return;
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RecycleViewHolder extends ViewHolder {

       ImageView handle_image;
       CardView cardView;
       TextView event,start_time,end_time,bell_reminder;
       RelativeLayout run,set;
       TextView set_reminder;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.mCardview);
            run= itemView.findViewById(R.id.runningcontest);
            set= itemView.findViewById(R.id.R1);
            handle_image = itemView.findViewById(R.id.handle_img);
            event = itemView.findViewById(R.id.event);
            start_time = itemView.findViewById(R.id.start_time);
            end_time = itemView.findViewById(R.id.end_time);
            set_reminder = itemView.findViewById(R.id.set_reminder);
            bell_reminder = itemView.findViewById(R.id.bell_reminder);

        }



    }

    public void ShareItem(int pos)
    {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        try {
           // Toast.makeText(context,data.get(pos).getEvent(),Toast.LENGTH_SHORT ).show();
            sendIntent.putExtra(Intent.EXTRA_TEXT, data.get(pos).getHref() + "\n\n" + "*Checkout this Contest*");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            context.startActivity(shareIntent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void OpenContest(int pos)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(pos).getHref()));
        context.startActivity(browserIntent);
        notifyDataSetChanged();
    }

    void remove(int i)
    {
            data.remove(i);
            notifyDataSetChanged();
    }


    private  String getTime(String timedate)
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Constants c= new Constants();
        SharedPreferences preferences= context.getSharedPreferences(c.TABS,Context.MODE_PRIVATE);

        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        try {
            value = formatter.parse(timedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        SimpleDateFormat dateFormatter;

        if(preferences.getInt(c.FORMAT,1)==1)
        {
             dateFormatter = new SimpleDateFormat("dd-MM-yyyy | hh:mm aa");
        }
        else
        {
            dateFormatter = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        }

        dateFormatter.setTimeZone(TimeZone.getDefault());
        String time = dateFormatter.format(value);
        return time;

    }


    //------------------------------------------NOtifications-------------------------------------------------------

    private void StartAlarm(Calendar c,String s,Notification nt) {


        final  Notification noti  = nt;
        final Calendar cc= c;
        final String ss =s;


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("By clicking OK you will be notified before the contest.")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(context, AlertReceiver.class);
                        intent.putExtra("contest",ss);
                        final int _id = (int) System.currentTimeMillis();
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,_id ,intent, PendingIntent.FLAG_ONE_SHOT);
                        noti.setNotiId(_id);

                        DatabaseHandler db = new DatabaseHandler(context);
                        db.AddNotification(noti);


                        if (cc.before(Calendar.getInstance())) {
                            cc.add(Calendar.DATE, 1);
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Toast.makeText(context,"Notification Set",Toast.LENGTH_LONG).show();
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cc.getTimeInMillis(), pendingIntent);

                        } else{
                            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                        }

                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    boolean isBefore(String time)
    {

        Date date,d2;
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            date = sdf.parse(time);
            sdf.setTimeZone(TimeZone.getDefault());
            String s= sdf.format(date);
            d2= sdf.parse(s);

            if(d2.before(new Date()))
            {
               return true;
            }
            else
            {
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }




}