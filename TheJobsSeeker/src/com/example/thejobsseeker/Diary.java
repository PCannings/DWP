package com.example.thejobsseeker;

import java.util.ArrayList;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class Diary extends ListActivity implements View.OnClickListener,
		OnDateChangeListener {

	CalendarView calendar;

	Button getDate;

	TextView date;

	ListView list;

	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();

	// DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
	ArrayAdapter<String> adapter;

	//RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.diary);
		date = (TextView) findViewById(R.id.tvDate);
		getDate = (Button) findViewById(R.id.button1);
		getDate.setOnClickListener(this);
		
		calendar = (CalendarView) findViewById(R.id.calendarView1);
		
		calendar.setOnDateChangeListener(this);

		//list = (ListView) findViewById(R.id.listView1);	
        //adapter=new ArrayAdapter<String>(Diary.this,
                //android.R.layout.simple_list_item_1,
                //listItems);
            //setListAdapter(adapter);
		
		
	    new Thread(new Runnable() {
	        public void run() {
	    		//setListAdapter(new ArrayAdapter<String>(Diary.this,
	    				//android.R.layout.simple_list_item_1, listItems));
	        }
	    }).start();


	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	       //listItems.add("Clicked : ");
	        //adapter.notifyDataSetChanged();
	}

	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		// add one because month starts at 0
		month = month + 1;
		// output to log cat **not sure how to format year to two places here**
		String newDate = year + "-" + month + "-" + dayOfMonth;
		date.setText("Year: " + year + "Month: " + month + "Day of Month: "
				+ dayOfMonth);
		Log.d("NEW_DATE", newDate);
	}

}
