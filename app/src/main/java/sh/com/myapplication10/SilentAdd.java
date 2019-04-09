package sh.com.myapplication10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static sh.com.myapplication10.DateDialog.SYear;
import static sh.com.myapplication10.DateDialog.sDay;
import static sh.com.myapplication10.DateDialog.sMonth;
import static sh.com.myapplication10.TimeDialog.sHour;
import static sh.com.myapplication10.TimeDialog.sMin;

/**
 * Created by anika on 9/6/17.
 */

public class SilentAdd extends AppCompatActivity {

    String toSendTitle="",toSendTime="",toSendTimeFile="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.silent_edit_add);


        final EditText title= (EditText) findViewById(R.id.title);

        final Button StartTime = (Button) findViewById(R.id.chooseTimeFrom);
        final EditText StartTimeText= (EditText) findViewById(R.id.timeFrom);
        final Button EndTime = (Button) findViewById(R.id.chooseTimeTo);
        final EditText EndTimeText= (EditText) findViewById(R.id.timeTo);
        Button save = (Button) findViewById(R.id.save);


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
            @Override
            public void onClick(View view) {


                String t1= String.valueOf(title.getText());
                String t2= String.valueOf(StartTimeText.getText());
                String t3= String.valueOf(EndTimeText.getText());


                if(t1.equals("") ||t2.equals("") || t3.equals("") )
                {
                    Toast.makeText(getApplicationContext(),
                            "Fill up all the required fields! ", Toast.LENGTH_SHORT).show();
                }
                // Intent intent = new Intent(TaskManagementActivity.this, MainActivity.class);
                //  intent.putExtra("what", toSend);
                //  startActivity(intent);

                else {

                    toSendTitle=""+title.getText();
                    toSendTitle =  toSendTitle.replaceAll("\\r\\n|\\r|\\n", " ");
                    toSendTime=""+StartTimeText.getText() + " to "+EndTimeText.getText();


                    SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a",Locale.US);
                    SimpleDateFormat date24Format = new SimpleDateFormat("H m",Locale.US);


                    try {
                        toSendTimeFile= ""+(date24Format.format(date12Format.parse(""+StartTimeText.getText())));
                        toSendTimeFile += " "+(date24Format.format(date12Format.parse(""+EndTimeText.getText())));





                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    onBackPressed();
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra("key",toSendTitle);
        i.putExtra("key2",toSendTime);
        i.putExtra("key3",toSendTimeFile);


        setResult(RESULT_OK,i);
        finish();
    }
}
