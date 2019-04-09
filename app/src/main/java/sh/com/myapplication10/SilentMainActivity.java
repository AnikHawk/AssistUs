package sh.com.myapplication10;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sh.com.myapplication10.DateDialog.dateFormatting;


import static sh.com.myapplication10.SaveAndLoad.Load;
import static sh.com.myapplication10.SaveAndLoad.Save;
import static sh.com.myapplication10.SaveAndLoad.generateCode;
import static sh.com.myapplication10.SaveAndLoad.updateCalendarFromTo;
import static sh.com.myapplication10.TimeDialog.timeformatting;

/**
 * Created by anika on 9/6/17.
 */

public class SilentMainActivity extends AppCompatActivity {

    public static int SilentFlag = 0, dltSilentFlag = 0;
    private RecyclerView recyclerView;
    private MyAdapterSilent adapter;
    private ImageButton floatButton;
    public static List<RecyclerItem> listItemsSilent = new ArrayList<>();
    static File root1Silent = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/SilentTitles.txt");
    File parent1Silent = root1Silent.getParentFile();
    static File root2Silent = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/SilentTimes.txt");
    File parent2Silent = root2Silent.getParentFile();
    public static Map<Integer, Calendar> silentMap = new HashMap<Integer, Calendar>();
    static ArrayList<String> savedDataTitleSilent = new ArrayList<String>();
    static ArrayList<String> savedDataSilentTime = new ArrayList<String>();

    public void initializer() {
        isStoragePermissionGranted();
        if (parent1Silent != null) parent1Silent.mkdirs();
        if (parent2Silent != null) parent2Silent.mkdirs();
        try {
            if (isStoragePermissionGranted() == true) {
                savedDataTitleSilent = Load(root1Silent);
                savedDataSilentTime = Load(root2Silent);
                listItemsSilent.clear();
                for (int j = 0; j < savedDataTitleSilent.size(); j++) {
                    String[] splited = savedDataSilentTime.get(j).trim().split("\\s+");
                    int sp1 = Integer.parseInt(splited[0]);
                    int sp2 = Integer.parseInt(splited[1]);
                    int sp3 = Integer.parseInt(splited[2]);
                    int sp4 = Integer.parseInt(splited[3]);
                    String TimeStart = timeformatting(sp1, sp2);
                    String TimeEnd = timeformatting(sp3, sp4);
                    listItemsSilent.add(new RecyclerItem(savedDataTitleSilent.get(j), TimeStart + " to " + TimeEnd));
                }
                adapter = new MyAdapterSilent(listItemsSilent, this);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            String messageTitle = data.getExtras().getString("key");
            String messageTime = data.getExtras().getString("key2");
            String mssgFileTime = data.getExtras().getString("key3");

            try {
                ArrayList<Calendar> list = updateCalendarFromTo(mssgFileTime);
                Calendar calFrom = list.get(0);
                Calendar calTo = list.get(1);
                int codeFrom = generateCode(calFrom);
                int codeTo = generateCode(calTo);
                SilentAt(calFrom, codeFrom);
                DisableSilentAt(calTo, codeTo);
                savedDataTitleSilent.add(messageTitle);
                savedDataSilentTime.add(mssgFileTime);
                Save(root1Silent, savedDataTitleSilent);
                Save(root2Silent, savedDataSilentTime);
                if (!messageTitle.equals("") || !messageTime.equals("")) {
                    listItemsSilent.add(new RecyclerItem(messageTitle, messageTime));
                }
            } catch (Exception e) {
                Toast.makeText(this, "Add: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onDestroy() {
        super.onDestroy();
        if (isStoragePermissionGranted() == true) {
            Save(root1Silent, savedDataTitleSilent);
            Save(root2Silent, savedDataSilentTime);
        } else {
            Toast.makeText(getApplicationContext(), "permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStart() {
        super.onStart();
        initializer();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (SilentFlag == 1) {
            try {
                Intent intent = getIntent();
                String EditedTitle = intent.getExtras().getString("EditedTitle");
                String EditedTimeFileFormat = intent.getExtras().getString("EditedTimeFileFormat");
                String PreviousTime = intent.getExtras().getString("PreviousTime");
                ArrayList<Calendar> prevList = updateCalendarFromTo(PreviousTime);
                Calendar prevCalFrom = prevList.get(0);
                Calendar prevCalTo = prevList.get(1);
                int prevCodeFrom = generateCode(prevCalFrom);
                int prevCodeTo = generateCode(prevCalTo);
                cancelSilent(prevCodeFrom);
                cancelDisableSilent(prevCodeTo);
                ArrayList<Calendar> list = updateCalendarFromTo(EditedTimeFileFormat);
                Calendar calFrom = list.get(0);
                Calendar calTo = list.get(1);
                int codeFrom = generateCode(calFrom);
                int codeTo = generateCode(calTo);
                SilentAt(calFrom, codeFrom);
                DisableSilentAt(calTo, codeTo);
                SilentFlag = 0;
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "edit: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        if (dltSilentFlag == 1) {
            try {
                Intent intent = getIntent();
                String deletedTime = intent.getExtras().getString("deletedTime");
                ArrayList<Calendar> list = updateCalendarFromTo(deletedTime);
                Calendar calFrom = list.get(0);
                Calendar calTo = list.get(1);
                int codeFrom = generateCode(calFrom);
                int codeTo = generateCode(calTo);
                cancelSilent(codeFrom);
                cancelDisableSilent(codeTo);
                dltSilentFlag = 0;
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "dlt: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializer();
        floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SilentMainActivity.this, SilentAdd.class);
                startActivityForResult(intent, 200);
            }

        });


    }


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted");
                return true;
            } else {

                // Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            //  Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    public void SilentAt(Calendar cal, int notificationID) {
        Intent silentServiceIntent = new Intent(getApplicationContext(), SilentService.class);
        silentServiceIntent.putExtra("cal", cal);
        silentServiceIntent.putExtra("notificationID", notificationID);
        startService(silentServiceIntent);
    }


    public void cancelSilent(int notificationID) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Silent_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


    public void DisableSilentAt(Calendar cal, int notificationID) {
        Intent disableSilentServiceIntent = new Intent(getApplicationContext(), DisableSilentService.class);
        disableSilentServiceIntent.putExtra("cal", cal);
        disableSilentServiceIntent.putExtra("notificationID", notificationID);
        startService(disableSilentServiceIntent);
    }

    public void cancelDisableSilent(int notificationID) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Disable_Silent_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
