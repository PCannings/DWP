package com.example.appointments;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Time extends Activity implements OnClickListener {

	TextView result, timeResult;

	String date, time;

	Button addToCalender;

	DateStore dateObject;

	TimeStore timeObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.time);

		result = (TextView) findViewById(R.id.textView2);

		timeResult = (TextView) findViewById(R.id.textView1);

		addToCalender = (Button) findViewById(R.id.button2);
		addToCalender.setOnClickListener(this);

		getWindow().getDecorView().setBackgroundColor(Color.BLACK);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button2:

			LoadDate();
			LoadTime();

			int year,
			month,
			day;

			year = dateObject.getYear();

			month = dateObject.getMonth();

			day = dateObject.getDay();

			int hour,
			minute;

			hour = timeObject.getHour();

			minute = timeObject.getMinute();

			startCalendarEvent(year, month, day, hour, minute);

			break;

		}
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");

	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");

	}

	public void LoadDate() {

		try {

			FileInputStream fis;

			fis = Time.this.openFileInput("date");

			ObjectInputStream is = new ObjectInputStream(fis);

			dateObject = (DateStore) is.readObject();

			is.close();

			date = dateObject.getDateAsString();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LoadTime() {

		try {

			FileInputStream fis;

			fis = Time.this.openFileInput("time");

			ObjectInputStream is = new ObjectInputStream(fis);

			timeObject = (TimeStore) is.readObject();

			is.close();

			time = timeObject.getTimeAsString();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startCalendarEvent(int year, int month, int day, int hour,
			int minute) {

		long startMillis = 0;

		Calendar beginTime = Calendar.getInstance();
		beginTime.set(year, month, day, hour, minute);
		startMillis = beginTime.getTimeInMillis();

		long endMillis = 0;

		Calendar endTime = Calendar.getInstance();
		endTime.set(year, month, day, hour, (minute + 30));
		endMillis = endTime.getTimeInMillis();

		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra(Events.TITLE, "Advisor Meeting");
		intent.putExtra(Events.EVENT_LOCATION,
				"Department of Work and Pensions");
		intent.putExtra(Events.DESCRIPTION, "Meet advisor and show diary.");

		intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis);
		intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis);

		intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
		intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);

		startActivity(intent);

	}

}