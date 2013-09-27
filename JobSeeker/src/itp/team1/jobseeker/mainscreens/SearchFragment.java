package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.session.Delegate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFragment extends Fragment{
	private EditText titleField;
	private EditText employerField;
	private Spinner  typeField;
	private Spinner  salarySpinner;
	private Spinner locationField;
	private Spinner  hoursSpinner;

	private LinearLayout searchButton;
	private LinearLayout advancedLayout;
	private TextView advanced;

	private boolean advancedOpen = false;

	private MainSlidingActivity activity;
	private View actionHeader;
	private ImageView logo;
	private TextView mTitle;
	private Spinner radiusSpinner;
	private Spinner wageSpinner;
	
	private View mainWindow;
	private String search, location, type, hours, employer, radius, salary, wage;
	
	public SearchFragment(){
		
	}
	
	public SearchFragment(String search, String location, String type, String hours, String employer, String radius, String salary, String wage){
		this.search = search.toLowerCase();
		this.location = location.toLowerCase();
		this.type = type;
		this.hours = hours; 
		this.employer = employer.toLowerCase();
		this.radius = radius;
		this.salary = salary;
		this.wage = wage;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		mainWindow = inflater.inflate(R.layout.view_search, null);

		activity = ((MainSlidingActivity)this.getActivity());
		
		actionHeader = mainWindow.findViewById(R.id.top_navigation);
		logo = (ImageView)actionHeader.findViewById(R.id.logo_btn);
		logo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				activity.toggle();
			}
		});
		mTitle = (TextView)actionHeader.findViewById(R.id.title);
		
		// Get UI elements
		titleField 		= (EditText) mainWindow.findViewById(R.id.search_edit_title);
		employerField 	= (EditText) mainWindow.findViewById(R.id.search_edit_employer);
		typeField 		= (Spinner)  mainWindow.findViewById(R.id.search_edit_type);
		ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Select", "Part Time", "Full Time"});
		typeField.setAdapter(typeArrayAdapter);
		salarySpinner = (Spinner)  mainWindow.findViewById(R.id.search_edit_salary);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Select", "under 20 000", "20 000 - 40 000", "over 40 000"});
		salarySpinner.setAdapter(spinnerArrayAdapter);
		wageSpinner = (Spinner)  mainWindow.findViewById(R.id.search_edit_wage);
		ArrayAdapter<String> wageArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Select", "minimum", "less than £10.00", "over £10.00"});
		wageSpinner.setAdapter(wageArrayAdapter);
		locationField 	= (Spinner) mainWindow.findViewById(R.id.search_edit_location);
		ArrayAdapter<String> locationArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Dundee", "Aberdeen", "Edinburgh", "Glasgow", "Perth"});
		locationField.setAdapter(locationArrayAdapter);
		hoursSpinner 	= (Spinner)  mainWindow.findViewById(R.id.search_edit_hours);
		ArrayAdapter<String> hoursArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Select", "under 16", "16 - 24", "over 24"});
		hoursSpinner.setAdapter(hoursArrayAdapter);
		
		radiusSpinner 	= (Spinner)  mainWindow.findViewById(R.id.search_edit_radius);
		ArrayAdapter<String> radiusArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Select", "1", "5", "10", "20"});
		radiusSpinner.setAdapter(radiusArrayAdapter);

		searchButton    = (LinearLayout)   mainWindow.findViewById(R.id.search_button);
		advancedLayout  = (LinearLayout)   mainWindow.findViewById(R.id.advanced_layout);
		advanced  		= (TextView)   mainWindow.findViewById(R.id.advanced_link);

		advanced.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if(advancedOpen) {
					advancedLayout.setVisibility(View.GONE);
					advanced.setText("Advanced Search");
					advancedOpen = false;
				} else {
					advancedLayout.setVisibility(View.VISIBLE);
					advanced.setText("Simple Search");
					advancedOpen = true;
				}
			}
		});

		// Search button click event
		searchButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Log.v("Search Location", locationField.getSelectedItem().toString());
				InputMethodManager imm = (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mainWindow.getWindowToken(), 0);
				//if(titleField.getText().toString().isEmpty()) Toast.makeText(activity,  "Please enter search parameters :)", Toast.LENGTH_LONG).show();
				//else 
					if (locationField.getSelectedItem().toString().isEmpty()) Toast.makeText(activity,  "Please select location :)", Toast.LENGTH_LONG).show();
				else {
					((Delegate)activity.getApplicationContext()).getSession().incrementSearches();
					activity.addContent(new ResultsFragment(titleField.getText().toString(), locationField.getSelectedItem().toString(), typeField.getSelectedItem().toString(), hoursSpinner.getSelectedItem().toString(), employerField.getText().toString(), radiusSpinner.getSelectedItem().toString(), salarySpinner.getSelectedItem().toString(), wageSpinner.getSelectedItem().toString()));
				}
				
			}
		});

		return mainWindow;
	}

}
