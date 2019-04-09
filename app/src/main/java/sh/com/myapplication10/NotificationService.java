package sh.com.myapplication10;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Anik on 9/9/2017.
 */

public class NotificationService extends Service {

 static    String notificationTitle = "Ami eshe gechi :')";
   static String notificationDescription = "Amake marben na :'(";
   static int notificationID = 101;
  static   Calendar cal = Calendar.getInstance();
    static String tune = "Ringtone1";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        try {
            Bundle b = intent.getExtras();
            if (b != null) {
                cal = (Calendar) b.get("cal");
                notificationTitle = (String) b.get("notiTitle");
                notificationDescription = (String) b.get("notiDes");
                tune = (String) b.get("tune");
                notificationID = (int) b.get("id");
                NotifyAt(cal, notificationTitle, notificationDescription, tune, notificationID);
            }
        }
        catch (Exception e ){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, NotificationService.class));
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }



    private void NotifyAt(Calendar cal, String notificationTitle, String notificationDescription, String tune, int notificationID) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationRecieverIntent = new Intent(getApplicationContext(), Notification_Receiver.class);
        notificationRecieverIntent.putExtra("notiTitle", notificationTitle);
        notificationRecieverIntent.putExtra("notiDes", notificationDescription);
        notificationRecieverIntent.putExtra("tune", tune);
        notificationRecieverIntent.putExtra("id", notificationID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, notificationRecieverIntent, 0);
        // alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}
