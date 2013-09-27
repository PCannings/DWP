package com.example.appointments;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	Button setFortNightlyAppointment, reset;
	TextView dateNotice;

	DateStore dateStore;
	TimeStore timeStore;

	String time;
	String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		setFortNightlyAppointment = (Button) findViewById(R.id.bSigningAppointmentSet);

		setFortNightlyAppointment.setOnClickListener(this);

		reset = (Button) findViewById(R.id.bReset);

		reset.setOnClickListener(this);

		dateNotice = (TextView) findViewById(R.id.textView1);
		
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);

		dateNotice.setTextColor(Color.WHITE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSigningAppointmentSet:
			// start the other activity
			Intent myIntent = new Intent(MainActivity.this, Time.class);

			this.startActivity(myIntent);
			break;

		case R.id.bReset:
			
			break;
		}
	}
	
}
