package sh.com.myapplication10;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


import static sh.com.myapplication10.SaveAndLoad.Save;
import static sh.com.myapplication10.SilentMainActivity.SilentFlag;
import static sh.com.myapplication10.SilentMainActivity.listItemsSilent;
import static sh.com.myapplication10.SilentMainActivity.root1Silent;
import static sh.com.myapplication10.SilentMainActivity.root2Silent;
import static sh.com.myapplication10.SilentMainActivity.savedDataSilentTime;
import static sh.com.myapplication10.SilentMainActivity.savedDataTitleSilent;
import static sh.com.myapplication10.TimeDialog.timeformatting;

/**
 * Created by anika on 9/6/17.
 */

public class SilentEdit extends AppCompatActivity {

    String toSendTitle="",toSendTime="",toSendTimeFile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.silent_edit_add);

        final EditText title= (EditText) findViewById(R.id.title);

        final Button StartTime = (Button) findViewById(R.id.chooseTimeFrom);
        final EditText StartTimeText= (EditText) findViewById(R.id.timeFrom);
        final Button EndTime = (Button) findViewById(R.id.chooseTimeTo);
        final EditText EndTimeText= (EditText) findViewById(R.id.timeTo);
        Button save = (Button) findViewById(R.id.save);



        Intent intent = getIntent();
        String result=""+intent.getExtras().getString("position");
        final int position = (int)Integer.parseInt(result);


        String editTitle= ""+savedDataTitleSilent.get(position);
        String[] splited= savedDataSilentTime.get(position).trim().split("\\s+");
        int sp1=Integer.parseInt(splited[0]);
        int sp2=Integer.parseInt(splited[1]);
        int sp3=Integer.parseInt(splited[2]);
        int sp4=Integer.parseInt(splited[3]);

        String TimeFrom= timeformatting(sp1,sp2);
        String TimeTo= timeformatting(sp3,sp4);

        title.setText(editTitle);
        StartTimeText.setText(TimeFrom);
        EndTimeText.setText(TimeTo);


        StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog dialog = new TimeDialog(StartTimeText);
                dialog.show(getFragmentManager().beginTransaction(), "TimePicker");
            }
        });

        EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog dialog = new TimeDialog(EndTimeText);
                dialog.show(getFragmentManager().beginTransaction(), "TimePicker");
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


                String t1= String.valueOf(title.getText());
                String t2= String.valueOf(StartTimeText.getText());
                String t3= String.valueOf(EndTimeText.getText());


                if(t1.equals("") ||t2.equals("") || t3.equals("") )
                {
                    Toast.makeText(getApplicationContext(),
                            "Fill up all the required fields! ", Toast.LENGTH_LONG).show();
                }

                else{
                    toSendTitle = ""+title.getText();;
                    toSendTime=StartTimeText.getText()+" to "+EndTimeText.getText();
                    toSendTitle =  toSendTitle.replaceAll("\\r\\n|\\r|\\n", " ");

                    String gotTimeFromEditText = ""+StartTimeText.getText();

                    SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a", Locale.US);

                    SimpleDateFormat date24Format = new SimpleDateFormat("H m",Locale.US);


                    try {
                        toSendTimeFile= ""+(date24Format.format(date12Format.parse(""+StartTimeText.getText())));
                        toSendTimeFile += " "+(date24Format.format(date12Format.parse(""+EndTimeText.getText())));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                    String PreviousTime = savedDataSilentTime.get(position);
                    savedDataTitleSilent.set(position,toSendTitle);
                    savedDataSilentTime.set(position,toSendTimeFile);

                    Save(root1Silent,savedDataTitleSilent);
                    Save(root2Silent,savedDataSilentTime);


                    listItemsSilent.set(position,(new RecyclerItem(toSendTitle, toSendTimeFile)));
                    //sending edited info into MainActivity

                    Intent intent = new Intent(SilentEdit.this,SilentMainActivity.class);
                    intent.putExtra("EditedTitle", toSendTitle);
                    intent.putExtra("EditedTimeFileFormat",toSendTimeFile);
                    intent.putExtra("PreviousTime",PreviousTime);

                    SilentFlag=1;
                    startActivity(intent);
                    finish();


                    // Intent intent = new Intent(TaskManagementActivity.this, MainActivity.class);
                    //  intent.putExtra("what", toSend);
                    //  startActivity(intent);

                }

            }
        });



    }
}
