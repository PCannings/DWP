package com.example.thenewboston;

import java.util.GregorianCalendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Appointments extends Activity implements View.OnClickListener{

	TextView date_time;
	Button setFortNightlyAppointment;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.appointments);

		date_time = (TextView) findViewById(R.id.textView1);

		setFortNightlyAppointment = (Button) findViewById(R.id.bSigningAppointmentSet);
		
		setFortNightlyAppointment.setOnClickListener(this);
		// start calendar event
		//startCalendarEvent();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bSigningAppointmentSet:
			//start the other activity
			Intent myIntent = new Intent(Appointments.this, SigningDateSetup.class);
			
			Appointments.this.startActivity(myIntent);
			break;
	}
	}

		public void startCalendarEvent() {
			
			Intent intent = new Intent(Intent.ACTION_INSERT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra(Events.TITLE, "Learn Android");
			intent.putExtra(Events.EVENT_LOCATION, "Home suit home");
			intent.putExtra(Events.DESCRIPTION, "Download Examples");

			// Setting dates
			GregorianCalendar calDate = new GregorianCalendar(2012, 10, 02);
			intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
					calDate.getTimeInMillis());
			intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
					calDate.getTimeInMillis());

			// Make it a full day event
			// intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

			// Make it a recurring Event
			intent.putExtra(Events.RRULE,
					"FREQ=WEEKLY;COUNT=11;WKST=SU");

			// Making it private and shown as busy
			intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
			intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);

			startActivity(intent);
			
		}
		
}
