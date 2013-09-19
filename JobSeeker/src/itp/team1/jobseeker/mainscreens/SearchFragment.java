package itp.team1.jobseeker.mainscreens;

import itp.team1.jobseeker.R;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchFragment extends Fragment{
	private EditText titleField;
	private EditText employerField;
	private Spinner  typeField;
	private Spinner  industrySpinner;
	private EditText locationField;
	private Spinner  hoursSpinner;
	
	private Button   searchButton;
	
	private MainSlidingActivity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.search_activity, null);
		
		activity = ((MainSlidingActivity)this.getActivity());
		// Get UI elements
		titleField 		= (EditText) v.findViewById(R.id.search_edit_title);
		employerField 	= (EditText) v.findViewById(R.id.search_edit_employer);
		typeField 		= (Spinner)  v.findViewById(R.id.search_edit_type);
		industrySpinner = (Spinner)  v.findViewById(R.id.search_edit_industry);
		locationField 	= (EditText) v.findViewById(R.id.search_edit_location);
		hoursSpinner 	= (Spinner)  v.findViewById(R.id.search_edit_hours);
		
		searchButton    = (Button)   v.findViewById(R.id.search_button);
		
		// Search button click event
		searchButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				activity.switchContent(new ResultsFragment());
			}
		});
		
		return v;
	}
	
}
