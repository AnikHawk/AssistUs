package sh.com.myapplication10;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public static String monthName[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    public static int SYear,sMonth,sDay;


    EditText txtdate;

    public DateDialog(View view) {
        txtdate = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {


// Use the current date as the default date in the dialog
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);



    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //show to the selected date in the text box
        SYear=year;
        sMonth=month+1;
        sDay = day;
        String date = monthName[month]+" "+day+ ", " + year;
        txtdate.setText(date);

       // TimeDialog dialog = new TimeDialog(txtdate);
       // dialog.show(getFragmentManager().beginTransaction(), "TimePicker");
    }

    public static String dateFormatting(int day,int month,int year)
    {
        String date = monthName[month-1]+" "+day+ ", " + year+" ";
        return date;
    }




}

