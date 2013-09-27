package com.example.appointments;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	EditText timeResult;
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        saveDate(year,month,day);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
        
    }


	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		// Do something with the date chosen by the user
		TextView textview = (TextView)getActivity().findViewById(R.id.textView1);
		textview.setText("Date: " + Integer.toString(year)+"-"+Integer.toString(monthOfYear + 1)+"-"+Integer.toString(dayOfMonth));
		
		saveDate(year,monthOfYear,dayOfMonth);
		
	}
	
	public void saveDate(int year,int month,int day){
		
		DateStore takeDate = new DateStore(year,month,day);

		try {

			FileOutputStream fos;

			fos = getActivity().openFileOutput("date", Context.MODE_PRIVATE);

			ObjectOutputStream os = new ObjectOutputStream(fos);

			os.writeObject(takeDate);

			os.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Log.d("Complete", "completed");
		}
	}

}
