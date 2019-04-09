package sh.com.myapplication10;


        import android.app.AlarmManager;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.NotificationCompat;
        import android.widget.Toast;

        import java.util.Calendar;

/**
 * Created by Anik on 8/30/2017.
 */

public class Notification_Receiver extends BroadcastReceiver {

   String notificationTitle = "Ami eshe gechi :')";
    String notificationDescription = "Amake marben na :'(";
    String tune = "Ringtone1";
    int notificationID = 101;



    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b =  intent.getExtras();
        if(b!=null)
        {
            notificationTitle =(String) b.get("notiTitle");
            notificationDescription= (String) b.get("notiDes");
            tune = (String) b.get("tune");
            notificationID = (int) b.get("id");
        }


        NotificationManager notificationManager  = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent repeatingIntent = new Intent(context, Repeating_Activity.class);
//        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,1234,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder  = new NotificationCompat.Builder(context)
//                .setContentIntent(pendingIntent)
//                .setContentTitle("Yo Yo Yo")
//                .setContentText("Sup Bruh???")
//                .setChannel("1234")
//                .setAutoCancel(true);
//
//        notificationManager.notify(1234,builder.build());
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(notificationTitle);
        mBuilder.setContentText(notificationDescription);
        long[] pattern = {500,500,500,500,500,500,500,500};
        mBuilder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        switch (tune){
        case "Ringtone1":
             alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone1);
            break;
            case "Ringtone2":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone2);
                break;
            case "Ringtone3":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone3);
                break;
            case "Ringtone4":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone4);
                break;
            case "Ringtone5":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone5);
                break;
            case "Ringtone6":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone6);
                break;
            case "Ringtone7":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone7);
                break;
            case "Ringtone8":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone8);
                break;
            case "Ringtone9":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone9);
                break;
            case "Ringtone10":
                alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ringtone10);
                break;
        }



        mBuilder.setSound(alarmSound);
        notificationManager.notify(notificationID,mBuilder.build());
    }



}
