package sh.com.myapplication10;

/**
 * Created by anika on 9/4/17.
 */

import android.content.Context;
import android.content.Intent;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.R.attr.type;
import static sh.com.myapplication10.DateDialog.SYear;
import static sh.com.myapplication10.DateDialog.dateFormatting;
import static sh.com.myapplication10.DateDialog.sDay;
import static sh.com.myapplication10.DateDialog.sMonth;
import static sh.com.myapplication10.MainActivity.TaskFlag;
import static sh.com.myapplication10.MainActivity.listItems;
import static sh.com.myapplication10.MainActivity.map;
import static sh.com.myapplication10.MainActivity.root1;
import static sh.com.myapplication10.MainActivity.root2;
import static sh.com.myapplication10.MainActivity.root3;
import static sh.com.myapplication10.MainActivity.root4;
import static sh.com.myapplication10.MainActivity.savedDataLine1;
import static sh.com.myapplication10.MainActivity.savedDataLine2;
import static sh.com.myapplication10.MainActivity.savedDataLine3;
import static sh.com.myapplication10.MainActivity.savedDataLine4;
import static sh.com.myapplication10.MainActivity.savedDataLine5;
import static sh.com.myapplication10.MainActivity.savedDataLine6;
import static sh.com.myapplication10.R.id.recyclerView;
import static sh.com.myapplication10.R.id.rt1;
import static sh.com.myapplication10.SaveAndLoad.Save;
import static sh.com.myapplication10.SaveAndLoad.updateCalendarFromText;
import static sh.com.myapplication10.TaskManagementActivity.selectedTune;
import static sh.com.myapplication10.TimeDialog.sHour;
import static sh.com.myapplication10.TimeDialog.sMin;
import static sh.com.myapplication10.TimeDialog.timeformatting;
import static sh.com.myapplication10.TuneSelect.tuneflag;




public class TaskManagementEdit extends AppCompatActivity  {

    String toSend="",toSend2="",toSend2File="",shortDate="",time24="",toSendDescrption="",editTune;
    String alarmTime = "",type ="Minute(s)";
    int toBeMinus=10;



    EditText time,date,title ,description;
    static int tuneEditFlag =0;
    TextView tune;

    protected void onStart()
    {

        super.onStart();

        if(tuneEditFlag==1)
        {tune.setText(selectedTune);


        }

         else if(tuneEditFlag==0){
            tune.setText(editTune);



         }



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_management_layout);
        final EditText title= (EditText) findViewById(R.id.title);
        Button chooseDate = (Button) findViewById(R.id.chooseDate);
        Button chooseTime = (Button) findViewById(R.id.chooseTime);
        final EditText time= (EditText) findViewById(R.id.time);
        final EditText date= (EditText) findViewById(R.id.date);
        final EditText description= (EditText) findViewById(R.id.description);
        Button save = (Button) findViewById(R.id.save);
        tune = (TextView) findViewById(R.id.tune);
        Button chooseTune = (Button) findViewById(R.id.chooseTune);
        Button chooseAlarmTime = (Button) findViewById(R.id.chooseAlarmTime);
        final TextView alarmBefore = (TextView) findViewById(R.id.alarmbefore);


        Intent intent = getIntent();
        String result=""+intent.getExtras().getString("position");
        final int position = (int)Integer.parseInt(result);

        String editTitle= ""+savedDataLine1.get(position);
        String editDescription = ""+savedDataLine3.get(position);
        editTune = ""+ savedDataLine4.get(position);
        String AlarmBeforeText = ""+savedDataLine6.get(position);


        String[] splited= savedDataLine2.get(position).trim().split("\\s+");
        int sp1=Integer.parseInt(splited[0]);
        int sp2=Integer.parseInt(splited[1]);
        int sp3=Integer.parseInt(splited[2]);
        int sp4=Integer.parseInt(splited[3]);
        int sp5=Integer.parseInt(splited[4]);
        String Date = dateFormatting(sp1,sp2,sp3);
        String Time= timeformatting(sp4,sp5);

        // listItems.add(new RecyclerItem(savedDataLine1.get(j), Date+Time));
        title.setText(editTitle);
        date.setText(Date);
        time.setText(Time);
        description.setText(editDescription);

        alarmBefore.setText(AlarmBeforeText);




        chooseDate.setText("Edit Date");
        chooseTime.setText("Edit Time");






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
                Intent intent = new Intent(TaskManagementEdit.this, TuneSelect.class);


                if(tuneEditFlag==0)
                {
                    intent.putExtra("ringtoneName",editTune);
                }
                else {
                    intent.putExtra("ringtoneName",selectedTune);
                }
                tuneflag=1;
                startActivity(intent);
                // finish();


            }
        });

        chooseAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(TaskManagementEdit.this);
                final View mView = getLayoutInflater().inflate(R.layout.alarm_time_dialogue, null);

                Button done = (Button) mView.findViewById(R.id.done);
                final EditText amount = (EditText) mView.findViewById(R.id.amount);
                RadioGroup rg = (RadioGroup) mView.findViewById(R.id.radioGroup);





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
            @RequiresApi(api = Build.VERSION_CODES.N)
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

                else{
                    toSend = ""+title.getText();;
                    toSend2=date.getText()+" "+time.getText();
                    toSend =  toSend.replaceAll("\\r\\n|\\r|\\n", " ");
                    toSendDescrption=description.getText()+"";



                    String gotTimeFromEditText = ""+time.getText();


                    SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a", Locale.US);

                    SimpleDateFormat date24Format = new SimpleDateFormat("H m",Locale.US);


                    try {
                        time24= ""+(date24Format.format(date12Format.parse(gotTimeFromEditText)));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    DateFormat shortFormat = new SimpleDateFormat("d M yyyy",Locale.ENGLISH);
                    DateFormat mediumFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
                    String s = ""+date.getText();
                    try {
                        shortDate = shortFormat.format(mediumFormat.parse(s));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    toSend2File=shortDate+" "+time24;

                    SimpleDateFormat df = new SimpleDateFormat("d M yyyy H m");
                    java.util.Date d = null;
                    try {
                        d = df.parse(toSend2File);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar Cal = Calendar.getInstance();
                    Cal.setTime(d);

                    if (type.equals("Minute(s)"))
                    {
                        Cal.add(Calendar.MINUTE, -toBeMinus);
                    }

                    if (type.equals("Hour(s)"))
                    {
                        Cal.add(Calendar.HOUR, -toBeMinus);
                    }
                    if (type.equals("Day(s)"))
                    {
                        Cal.add(Calendar.DATE, -toBeMinus);
                    }
                    String newTimeAfterMinus = df.format(Cal.getTime());


                    Toast.makeText(getApplicationContext(),
                            newTimeAfterMinus, Toast.LENGTH_LONG).show();




                    String PreviousTime = savedDataLine2.get(position);
                    savedDataLine1.set(position,toSend);
                    savedDataLine2.set(position,toSend2File);
                    savedDataLine4.set(position, t5);
                    savedDataLine5.set(position, newTimeAfterMinus);
                    savedDataLine6.set(position, alarmBefore.getText().toString());
                    toSendDescrption =  toSendDescrption.replaceAll("\\r\\n|\\r|\\n", " ");


                    Calendar cal = updateCalendarFromText(toSend2File);
                    Calendar prevCal = updateCalendarFromText(PreviousTime);
                    int code = generateCode(cal);
                    int prevCode = generateCode(prevCal);
                    map.remove(prevCode);
                    if(!map.containsKey(code)) {
                        savedDataLine3.set(position, toSendDescrption);
                        Save(root1,savedDataLine1);
                        Save(root2,savedDataLine2);
                        Save(root3,savedDataLine3);
                        Save(root4, savedDataLine4);
                        Save(root4, savedDataLine5);
                        Save(root4, savedDataLine6);

                        listItems.set(position, (new RecyclerItem(toSend, toSend2)));
                    }
                    //sending edited info into MainActivity

                    Intent intent = new Intent(TaskManagementEdit.this,MainActivity.class);
                    intent.putExtra("EditedTitle", toSend);
                    intent.putExtra("EditedTimeFileFormat",toSend2File);
                    intent.putExtra("PreviousTime",PreviousTime);
                    intent.putExtra("EditedDescription",toSendDescrption);
                    intent.putExtra("EditedTune",t5);
                    intent.putExtra("EditedAlarmTime",newTimeAfterMinus);
                    intent.putExtra("EditedAlarmText",alarmBefore.getText().toString());
                    TaskFlag=1;
                    startActivity(intent);
                    finish();


                    // Intent intent = new Intent(TaskManagementActivity.this, MainActivity.class);
                    //  intent.putExtra("what", toSend);
                    //  startActivity(intent);

                }

            }
        });


    }


    public int generateCode(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
        String TimeStop_Str = sdf.format(cal.getTime());
        return Integer.parseInt(TimeStop_Str);
    }



}