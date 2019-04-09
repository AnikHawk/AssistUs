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

public class SilentService  extends Service{
    Calendar cal = Calendar.getInstance();
    int notificationID = 0;
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Bundle b = intent.getExtras();
            if (b != null) {
                cal = (Calendar) b.get("cal");
                notificationID = (int) b.get("notificationID");
                SilentAt(cal, notificationID);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, SilentService.class));
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
     void SilentAt(Calendar cal, int notificationID) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent silentRecieverIntent = new Intent(getApplicationContext(), Silent_Receiver.class);
       /* notificationRecieverIntent.putExtra("notiTitle", notificationTitle);
        notificationRecieverIntent.putExtra("notiDes", notificationDescription);
        notificationRecieverIntent.putExtra("id", notificationID);*/
         Toast.makeText(this, Integer.toString(notificationID), Toast.LENGTH_SHORT).show();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, silentRecieverIntent, 0);
        // alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }
}
