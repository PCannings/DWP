package com.example.appointments;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {

	Calendar c;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
        // Use the current time as the default values for the picker
        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        
        saveTime(hour, minute);
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

	}

	@Override
	public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		// Do something with the time chosen by the user

		TextView textview = (TextView)getActivity().findViewById(R.id.textView2);
		textview.setText("Time: "+Integer.toString(arg1)+":"+Integer.toString(arg2));
				
		saveTime(arg1, arg2);
				
	}
	
	public void saveTime(int arg1, int arg2){
		
		TimeStore takeTime = new TimeStore(arg1, arg2);
		
		Log.d("ButtonPress", "Save Button Pressed");
		try {
			Log.d("Creating", "FileOutputStream");
			FileOutputStream fos;
			Log.d("Created", "FileOutputStream");
			Log.d("open", "openFileOutput");
			fos = getActivity()
					.openFileOutput("time", Context.MODE_PRIVATE);
			Log.d("creating", "ObjectOutputStream");
			ObjectOutputStream os = new ObjectOutputStream(fos);
			Log.d("created", "ObjectOutputStream");
			Log.d("Writing", "to ObjectOutputStream");
			os.writeObject(takeTime);
			Log.d("Writing Complete", "to ObjectOutputStream");
			Log.d("Closing", "ObjectOutputStream");
			os.close();
			Log.d("Closed", "ObjectOutputStream");
	
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
