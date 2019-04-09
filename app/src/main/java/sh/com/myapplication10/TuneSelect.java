package sh.com.myapplication10;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static sh.com.myapplication10.R.id.rt1;
import static sh.com.myapplication10.TaskManagementActivity.selectedTune;
import static sh.com.myapplication10.TaskManagementEdit.tuneEditFlag;


/**
 * Created by anika on 10/5/17.
 */

public class TuneSelect extends AppCompatActivity{

    RadioGroup rg;
    String tune="";
    RadioButton rb;
    String title="",date="",time="",des="";
    static int tuneflag=0;

    protected void onDestroy()
    {
        super.onDestroy();


        stopPlaying();


    }



    private MediaPlayer mp;









    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tune_selector);

        RadioButton rButton ;
        if(tuneflag==1)
        {
            tuneEditFlag=1;
            Intent intent = getIntent();
            String ringtone=""+intent.getExtras().getString("ringtoneName");
            if(ringtone.equals("Ringtone1"))
            {
                rButton = (RadioButton) findViewById(R.id.rt1);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone2"))
            {
                rButton = (RadioButton) findViewById(R.id.rt2);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone3"))
            {
                rButton = (RadioButton) findViewById(R.id.rt3);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone4"))
            {
                rButton = (RadioButton) findViewById(R.id.rt4);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone5"))
            {
                rButton = (RadioButton) findViewById(R.id.rt5);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone6"))
            {
                rButton = (RadioButton) findViewById(R.id.rt6);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone7"))
            {
                rButton = (RadioButton) findViewById(R.id.rt7);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone8"))
            {
                rButton = (RadioButton) findViewById(R.id.rt8);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone9"))
            {
                rButton = (RadioButton) findViewById(R.id.rt9);
                rButton.setChecked(true);
            }
            if(ringtone.equals("Ringtone10"))
            {
                rButton = (RadioButton) findViewById(R.id.rt10);
                rButton.setChecked(true);
            }
            tuneflag=0;

        }



        rg = (RadioGroup) findViewById(R.id.radioGroup);


         rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                 rb = (RadioButton) findViewById(i);
                 switch(i)
                 {
                     case rt1:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone1);
                         mp.start();
                         selectedTune="Ringtone1";
                         break;

                     case R.id.rt2:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone2);
                         mp.start();
                         selectedTune="Ringtone2";
                         break;

                     case R.id.rt3:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone3);
                         mp.start();
                         selectedTune="Ringtone3";
                         break;

                     case R.id.rt4:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone4);
                         mp.start();
                         selectedTune="Ringtone4";
                         break;

                     case R.id.rt5:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone5);
                         mp.start();
                         selectedTune="Ringtone5";
                         break;

                     case R.id.rt6:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone6);
                         mp.start();
                         selectedTune="Ringtone6";
                         break;
                     case R.id.rt7:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone7);
                         mp.start();
                         selectedTune="Ringtone7";
                         break;
                     case R.id.rt8:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone8);
                         mp.start();
                         selectedTune="Ringtone8";
                         break;
                     case R.id.rt9:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone9);
                         mp.start();
                         selectedTune="Ringtone9";
                         break;
                     case R.id.rt10:
                         stopPlaying();
                         mp = MediaPlayer.create(getApplicationContext(), R.raw.ringtone10);
                         mp.start();
                         selectedTune="Ringtone10";
                         break;

                 }
             }
         });





    }

    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }








}
