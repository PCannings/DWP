package com.example.thejobsseeker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button jobSeach, Diary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//getWindow().getDecorView().setBackgroundColor(Color.BLACK);
		
		jobSeach = (Button) findViewById(R.id.bJobSearch);
		Diary = (Button) findViewById(R.id.bDiary);
		
		// setup an on click listener
		jobSeach.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//start the other activity
				Intent myIntent = new Intent(MainActivity.this, SearchJobs.class);
				
				MainActivity.this.startActivity(myIntent);

			}
		});
		
		// setup an on click listener
		Diary.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//start the other activity
				Intent myIntent = new Intent(MainActivity.this, Diary.class);
				
				MainActivity.this.startActivity(myIntent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
