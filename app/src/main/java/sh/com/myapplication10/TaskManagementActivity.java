package sh.com.myapplication10;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static sh.com.myapplication10.DateDialog.SYear;
import static sh.com.myapplication10.DateDialog.sDay;
import static sh.com.myapplication10.DateDialog.sMonth;

import static sh.com.myapplication10.MainActivity.savedDataLine1;
import static sh.com.myapplication10.R.id.amount;
import static sh.com.myapplication10.R.id.rt1;
import static sh.com.myapplication10.SaveAndLoad.Save;
import static sh.com.myapplication10.TimeDialog.sHour;
import static sh.com.myapplication10.TimeDialog.sMin;
import static sh.com.myapplication10.MainActivity.TaskfirstFlag;


public class TaskManagementActivity extends AppCompatActivity {
    String toSend="",toSend2="",toSend2File="",toSendDescription="";
    int amnt =10;

    static  String selectedTune="";
     EditText time,date,title ,description;
    TextView tune;

    String alarmTime = "",type ="Minute(s)";
    int toBeMinus=10;

    protected void onStart()
    {

        super.onStart();
        tune.setText(selectedTune);
        Toast.makeText(this, "onstart", Toast.LENGTH_SHORT).show();

    }










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_management_layout);
        title= (EditText) findViewById(R.id.title);
        Button chooseDate = (Button) findViewById(R.id.chooseDate);
        Button chooseTime = (Button) findViewById(R.id.chooseTime);
        Button chooseTune = (Button) findViewById(R.id.chooseTune);
        tune = (TextView) findViewById(R.id.tune);
        time= (EditText) findViewById(R.id.time);
        date= (EditText) findViewById(R.id.date);
        description= (EditText) findViewById(R.id.description);
        Button save = (Button) findViewById(R.id.save);
        Button chooseAlarmTime = (Button) findViewById(R.id.chooseAlarmTime);
        final TextView alarmBefore = (TextView) findViewById(R.id.alarmbefore);





         tune.setText(selectedTune);
         alarmBefore.setText("10 Minute(s)");







        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateDialog dialog = new DateDialog(date);
                dialog.show(getFragmentManager().beginTransaction(), "DatePicker");
            }
        });


        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog dialog = new TimeDialog(time);
                dialog.show(getFragmentManager().beginTransaction(), "TimePicker");
            }
        });


        chooseTune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskManagementActivity.this, TuneSelect.class);
                startActivity(intent);



            }
        });


        chooseAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TaskManagementActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.alarm_time_dialogue, null);

                Button done = (Button) mView.findViewById(R.id.done);
                final EditText amount = (EditText) mView.findViewById(R.id.amount);
                RadioGroup rg = (RadioGroup) mView.findViewById(R.id.radioGroup);

                amount.setText(amnt+"");
                if(type.equals("Hour(s)"))
                {
                    RadioButton rb = (RadioButton) mView.findViewById(R.id.rt2);
                    rb.setChecked(true);
                }

                if(type.equals("Day(s)"))
                {
                    RadioButton rb = (RadioButton) mView.findViewById(R.id.rt3);
                    rb.setChecked(true);
                }





                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        RadioButton rb = (RadioButton) mView.findViewById(i);
                        switch(i)
                        {
                            case rt1:

                                type = "Minute(s)";
                               // Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.rt2:

                                type = "Hour(s)";
                               // Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.rt3:

                                type = "Day(s)";
                                //Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                                break;


                        }
                    }
                });

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(amount.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Fill up the required fields", Toast.LENGTH_LONG).show();
                        }


                        else {
                            String tmp = amount.getText().toString();
                            tmp = tmp.replaceAll("[\\D.]", "");
                            toBeMinus = Integer.parseInt(tmp);
                            alarmTime = toBeMinus + " " + type;
                            alarmBefore.clearComposingText();
                            alarmBefore.setText(alarmTime);


                            dialog.dismiss();
                        }

                    }
                });


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String t1= String.valueOf(title.getText());
                String t2= String.valueOf(time.getText());
                String t3= String.valueOf(date.getText());
                String t4 = String.valueOf(description.getText());
                String t5 = String.valueOf(tune.getText());

                if(t1.equals("") ||t2.equals("") || t3.equals("") || t4.equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Fill up all the required fields! ", Toast.LENGTH_LONG).show();
                }


                else {

                    toSend = "" + title.getText();
                    toSend = toSend.replaceAll("\\r\\n|\\r|\\n", " ");
                    toSend2 = "" + date.getText() + " " + time.getText();
                    toSend2File = "" + sDay + " " + sMonth + " " + SYear + " " + sHour + " " + sMin;

                    //String mytime = "30 7 2017 14 1";
                    SimpleDateFormat df = new SimpleDateFormat("d M yyyy H m");
                    Date d = null;
                    try {
                        d = df.parse(toSend2File);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);

                    if (type.equals("Minute(s)"))
                    {
                        cal.add(Calendar.MINUTE, -toBeMinus);
                }

                    if (type.equals("Hour(s)"))
                    {
                        cal.add(Calendar.HOUR, -toBeMinus);
                    }
                    if (type.equals("Day(s)"))
                    {
                        cal.add(Calendar.DATE, -toBeMinus);
                    }
                    String newTimeAfterMinus = df.format(cal.getTime());


                    Toast.makeText(getApplicationContext(),
                            newTimeAfterMinus, Toast.LENGTH_LONG).show();


                    toSendDescription = description.getText()+"";
                    toSendDescription =  toSendDescription.replaceAll("\\r\\n|\\r|\\n", " ");
                    Intent i = new Intent(TaskManagementActivity.this,MainActivity.class);
                    i.putExtra("key",toSend);
                    i.putExtra("key2",toSend2);
                    i.putExtra("key3",toSend2File);
                    i.putExtra("key4",toSendDescription);
                    i.putExtra("key5",selectedTune);
                    i.putExtra("key6",newTimeAfterMinus);
                    i.putExtra("key7",alarmBefore.getText().toString());
                    TaskfirstFlag=1;
                    startActivity(i);


                  finish();


                }
            }
        });


    }


/*
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("key",toSend);
        i.putExtra("key2",toSend2);
        i.putExtra("key3",toSend2File);
        i.putExtra("key4",toSendDescription);

       // setResult(RESULT_OK,i);
        finish();
    }*/
}