package project.estateWatch;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerController;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by HP on 2/18/2019.
 */


@SuppressLint("ValidFragment")
public class DateDialog extends android.app.DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {
    EditText txtdate;

    public DateDialog(View view) {
        txtdate = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {


// Use the current date as the default date in the dialog
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int days = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new android.app.DatePickerDialog(getActivity(), this, year, month, days);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //show to the selected date in the text box
        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
        view.setMinDate(System.currentTimeMillis() -1000);
        txtdate.setText(date);
    }
}