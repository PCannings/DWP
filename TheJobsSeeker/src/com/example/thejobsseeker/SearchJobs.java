package com.example.thejobsseeker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchJobs extends Activity implements OnClickListener{

	Button search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_jobs);
		
		search = (Button) findViewById(R.id.bSearch);
		search.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bSearch:
			//start the other activity
			Intent myIntent = new Intent(SearchJobs.this, SearchResults.class);
			
			SearchJobs.this.startActivity(myIntent);
			break;
	
		}
	}

}
