package sh.com.myapplication10;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import static sh.com.myapplication10.DateDialog.dateFormatting;
import static sh.com.myapplication10.SaveAndLoad.Load;
import static sh.com.myapplication10.SaveAndLoad.Save;
import static sh.com.myapplication10.SaveAndLoad.updateCalendarFromText;
import static sh.com.myapplication10.SaveAndLoad.updateMapFromList;
import static sh.com.myapplication10.TaskManagementActivity.selectedTune;
import static sh.com.myapplication10.TimeDialog.timeformatting;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ImageButton floatButton;
    public static List<RecyclerItem> listItems = new ArrayList<>();
    public static Map<Integer, Calendar> map = new HashMap<Integer, Calendar>();

    public static int TaskFlag = 0,TaskfirstFlag=0;
    public static int dltFlag = 0;
    static File root1 = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/TaskListsLine1.txt");
    File parent1 = root1.getParentFile();
    static File root2 = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/TaskListsLine2.txt");
    File parent2 = root2.getParentFile();
    static File root3 = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/TaskListsDescription.txt");
    File parent3 = root3.getParentFile();
    static File root4 = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/TaskTune.txt");
    File parent4 = root4.getParentFile();

    static File root5 = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/NewAlarmTime.txt");
    File parent5 = root5.getParentFile();
    static File root6 = new File(Environment.getExternalStorageDirectory(), "/AppData/MyDataBase/Task/AlarmBeforeTexts.txt");
    File parent6 = root6.getParentFile();

    static ArrayList<String> savedDataLine1 = new ArrayList<String>();
    static ArrayList<String> savedDataLine2 = new ArrayList<String>();
    static ArrayList<String> savedDataLine3 = new ArrayList<String>();
    static ArrayList<String> savedDataLine4 = new ArrayList<String>();
    static ArrayList<String> savedDataLine5 = new ArrayList<String>();
    static ArrayList<String> savedDataLine6 = new ArrayList<String>();


    void AdapterSetting() {
        adapter = new MyAdapter(listItems, this);
        recyclerView.setAdapter(adapter);
    }


    public void initializer() {
        isStoragePermissionGranted();
        if (parent1 != null) parent1.mkdirs();
        if (parent2 != null) parent2.mkdirs();
        if (parent3 != null) parent3.mkdirs();
        if (parent4 != null) parent4.mkdirs();
        if (parent5 != null) parent5.mkdirs();
        if (parent6 != null) parent6.mkdirs();

        try {
            if (isStoragePermissionGranted() == true) {
                savedDataLine1 = Load(root1);
                savedDataLine2 = Load(root2);
                savedDataLine3 = Load(root3);
                savedDataLine4 = Load(root4);
                savedDataLine5 = Load(root5);
                savedDataLine6 = Load(root6);
                updateMapFromList(map, savedDataLine2);
                listItems.clear();
                for (int j = 0; j < savedDataLine1.size(); j++) {
                    Calendar cal = updateCalendarFromText(savedDataLine2.get(j));
                    map.put(generateCode(cal), cal);
                    String[] splited = savedDataLine2.get(j).trim().split("\\s+");
                    int sp1 = Integer.parseInt(splited[0]);
                    int sp2 = Integer.parseInt(splited[1]);
                    int sp3 = Integer.parseInt(splited[2]);
                    int sp4 = Integer.parseInt(splited[3]);
                    int sp5 = Integer.parseInt(splited[4]);
                    String Date = dateFormatting(sp1, sp2, sp3);
                    String Time = timeformatting(sp4, sp5);
                    listItems.add(new RecyclerItem(savedDataLine1.get(j), Date + Time));
                }
                adapter = new MyAdapter(listItems, this);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            Toast.makeText(this, "initializer: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }




    public void onDestroy() {
        super.onDestroy();

        if (isStoragePermissionGranted() == true) {
            Save(root1, savedDataLine1);
            Save(root2, savedDataLine2);
            Save(root3, savedDataLine3);
            Save(root4, savedDataLine4);
            Save(root5, savedDataLine5);
            Save(root6, savedDataLine6);

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



        if(TaskfirstFlag==1)
        {
            Intent data = getIntent();
            String message = data.getExtras().getString("key");
            String message2 = data.getExtras().getString("key2");
            String realTime = data.getExtras().getString("key3");
            String timeForFile = realTime;

            DateFormat formatTo = new SimpleDateFormat("d MMM, yyyy h:m a",Locale.US);
            DateFormat formatFrom = new SimpleDateFormat("d M yyyy H m",Locale.US);

            try {
                realTime = formatTo.format(formatFrom.parse(realTime));
            } catch (ParseException e) {
                //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            String description = data.getExtras().getString("key4");
            String tune = data.getExtras().getString("key5");
            String NewAlarmTimeAfterMinus = data.getExtras().getString("key6");
            String AlarmBeforeText = data.getExtras().getString("key7");
            try {
                Calendar cal = updateCalendarFromText(NewAlarmTimeAfterMinus);
                int code = 0;
                code = generateCode(cal);

                if (!map.containsKey(code)) {
                    Toast.makeText(this,"Added: " + Integer.toString(code), Toast.LENGTH_LONG).show();
                    map.put(code, cal);
                    NotifyAt(cal, message, realTime, tune, code);
                    savedDataLine1.add(message);
                    savedDataLine2.add(timeForFile);
                    savedDataLine3.add(description);
                    savedDataLine4.add(tune);
                    savedDataLine5.add(NewAlarmTimeAfterMinus);
                    savedDataLine6.add(AlarmBeforeText);
                    Save(root1, savedDataLine1);
                    Save(root2, savedDataLine2);
                    Save(root3, savedDataLine3);
                    Save(root4, savedDataLine4);
                    Save(root5, savedDataLine5);
                    Save(root6, savedDataLine6);
                    if (!message.equals("") || !message2.equals("")) {
                        listItems.add(new RecyclerItem(message, message2));
                    }
                } else
                    Toast.makeText(this, "Oops! Another task already exists at selected time", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "Task Not Saved", Toast.LENGTH_SHORT).show();
            }


            TaskfirstFlag=0;
            finish();
        }



        if (TaskFlag == 1) {
            Intent intent = getIntent();
            String EditedTitle = intent.getExtras().getString("EditedTitle");
            String EditedTimeFileFormat = intent.getExtras().getString("EditedTimeFileFormat");
            String PreviousTime = intent.getExtras().getString("PreviousTime");
            String EditedDescription = intent.getExtras().getString("EditedDescription");
            String EditedTune = intent.getExtras().getString("EditedTune");
            String EditedAlarmTime = intent.getExtras().getString("EditedAlarmTime");
            String EditedAlarmText = intent.getExtras().getString("EditedAlarmText");


            try {
                Calendar prevCal = updateCalendarFromText(PreviousTime);
                Calendar cal = updateCalendarFromText(EditedTimeFileFormat);
                int code = 0;
                int prevCode = 0;
                code = generateCode(cal);
                prevCode= generateCode(prevCal);
               // Toast.makeText(this,"Edit code: " + Integer.toString(prevCode), Toast.LENGTH_LONG).show();
               // if(map.containsKey(prevCode)){
                //    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    map.remove(prevCode);
                    cancelAlarm(prevCode);
               // }
               //else if (!map.containsKey(code)) {
                    map.put(code, cal);
                    NotifyAt(cal, EditedTitle, EditedDescription, EditedTune, code);
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
//                } else
//                    Toast.makeText(this, "Oops! Another task already exists at selected time", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "Time not changed", Toast.LENGTH_LONG).show();
            }
            TaskFlag = 0;
            finish();
        }

        if(dltFlag==1){
            Intent intent = getIntent();
            String deletedTime = intent.getExtras().getString("deletedTime");
            Calendar cal = updateCalendarFromText(deletedTime);
            int code = generateCode(cal);
            cancelAlarm(code);
            if(map.containsKey(code)) map.remove(code);
            dltFlag = 0;
            finish();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initializer();
        // adapter = new MyAdapter(listItems,this);
        // recyclerView.setAdapter(adapter);
        floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AdapterSetting();
                Intent intent = new Intent(MainActivity.this, TaskManagementActivity.class);
                //  startActivityForResult(intent,2);
                selectedTune ="Ringtone1";
                startActivity(intent);

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

    public void NotifyAt(Calendar cal, String notificationTitle, String notificationDescription, String tune, int notificationID) {
        Intent notificationServiceIntent = new Intent(getApplicationContext(), NotificationService.class);
        notificationServiceIntent.putExtra("cal", cal);
        notificationServiceIntent.putExtra("notiTitle", notificationTitle);
        notificationServiceIntent.putExtra("notiDes", notificationDescription);
        notificationServiceIntent.putExtra("tune", tune);
        notificationServiceIntent.putExtra("id", notificationID);
        startService(notificationServiceIntent);
    }

    public void cancelAlarm(int notificationID){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationRecieverIntent = new Intent(getApplicationContext(), Notification_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationID, notificationRecieverIntent, 0);
        alarmManager.cancel(pendingIntent);
    }


    public int generateCode(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm",Locale.US);
        String TimeStop_Str = sdf.format(cal.getTime());
        return Integer.parseInt(TimeStop_Str);
    }




}
