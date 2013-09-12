package itp.team1.jobseeker;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class SearchActivity extends Activity 
{
	private EditText titleField;
	private EditText employerField;
	private Spinner  typeField;
	private Spinner  industrySpinner;
	private EditText locationField;
	private Spinner  hoursSpinner;
	
	private Button   searchButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		
		// Get UI elements
		titleField 		= (EditText) findViewById(R.id.search_edit_title);
		employerField 	= (EditText) findViewById(R.id.search_edit_employer);
		typeField 		= (Spinner)  findViewById(R.id.search_edit_type);
		industrySpinner = (Spinner)  findViewById(R.id.search_edit_industry);
		locationField 	= (EditText) findViewById(R.id.search_edit_location);
		hoursSpinner 	= (Spinner)  findViewById(R.id.search_edit_hours);
		
		searchButton    = (Button)   findViewById(R.id.search_button);
		
		// Search button click event
		searchButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO: Go to server to scrape all sources	
				// TODO: ASyncTask progress bar
				
			    // Check for Internet access
//                if (!hasInternetConnectivity())
//                {
//                    Toast.makeText(SearchActivity.this, "Internet Access Required...", Toast.LENGTH_LONG).show();
//                    return;
//                }

				
				startActivity(new Intent(SearchActivity.this, ResultsActivity.class));
			}
		});
	}

	// Check for Internet access
	public boolean hasInternetConnectivity() 
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo().isConnectedOrConnecting() == false)
			return false;
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	
}
