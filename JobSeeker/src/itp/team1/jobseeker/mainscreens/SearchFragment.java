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

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.view_search, null);

		activity = ((MainSlidingActivity)this.getActivity());
		
		actionHeader = v.findViewById(R.id.top_navigation);
		logo = (ImageView)actionHeader.findViewById(R.id.logo_btn);
		logo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				activity.toggle();
			}
		});
		mTitle = (TextView)actionHeader.findViewById(R.id.title);
		
		
		// Get UI elements
		titleField 		= (EditText) v.findViewById(R.id.search_edit_title);
		employerField 	= (EditText) v.findViewById(R.id.search_edit_employer);
		typeField 		= (Spinner)  v.findViewById(R.id.search_edit_type);
		ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Part Time", "Full Time"});
		typeField.setAdapter(typeArrayAdapter);
		salarySpinner = (Spinner)  v.findViewById(R.id.search_edit_salary);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Under 20 000", "20 000 - 40 000", "Over 40 000"});
		salarySpinner.setAdapter(spinnerArrayAdapter);
		locationField 	= (Spinner) v.findViewById(R.id.search_edit_location);
		ArrayAdapter<String> locationArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Dundee", "Aberdeen", "Edinburgh", "Glasgow", "Perth"});
		locationField.setAdapter(locationArrayAdapter);
		hoursSpinner 	= (Spinner)  v.findViewById(R.id.search_edit_hours);
		ArrayAdapter<String> hoursArrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, new String [] {"Up to 24", "24 - 30", "Over 30"});
		hoursSpinner.setAdapter(hoursArrayAdapter);

		searchButton    = (LinearLayout)   v.findViewById(R.id.search_button);
		advancedLayout  = (LinearLayout)   v.findViewById(R.id.advanced_layout);
		advanced  		= (TextView)   v.findViewById(R.id.advanced_link);

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
				((Delegate)activity.getApplicationContext()).getSession().incrementSearches();
				activity.switchContent(new ResultsFragment());
			}
		});

		return v;
	}

}
