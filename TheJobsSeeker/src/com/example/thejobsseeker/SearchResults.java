package com.example.thejobsseeker;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchResults extends Activity{

	TextView content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_results);
		content = (TextView) findViewById(R.id.tvTempResult);
		
		getJobsFromServer();
	}

	public void getJobsFromServer(){
		try {
			// Create a new HTTP Client
			DefaultHttpClient defaultClient = new DefaultHttpClient();
			// Setup the get request
			HttpGet httpGetRequest = new HttpGet("http://ec2-50-18-26-146.us-west-1.compute.amazonaws.com:8080/JobSeekerServer/search");

			// Execute the request in the client
			HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
			// Grab the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			String json = reader.readLine();

			// Instantiate a JSON object from the request response
			JSONObject jsonObject = new JSONObject(json);
			
			try {
		    // Getting Array of Contacts
			JSONArray routesArray = jsonObject.getJSONArray("jobs");
		     
		    // looping through All Contacts
		    for(int i = 0; i < routesArray.length(); i++){
		        JSONObject c = routesArray.getJSONObject(i);
		        
		        content.append("Job" + i + "\n");
		         
				String jobDescription = c.getString("description");	
				String jobURL = c.getString("url");
				String jobSource = c.getString("source");
				String jobTimeStamp = c.getString("timestamp");
				String jobLatitude = c.getString("latitude");
				String jobLongitude = c.getString("longitude");
				content.append("\n"+ "Job Description: " +jobDescription+ "\n" + 
								"url: "+ jobURL+ "\n" + 
								"Source: " + jobSource + "\n" +
								"Time Stamp: "+ jobTimeStamp + "\n" +
								"Latitude: " + jobLatitude + "\n" +
								"Longitude: " + jobLongitude + "\n");
				content.append("------------------------------------------------");
		         
		    }
		} catch (JSONException e) {
		    e.printStackTrace();
		}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		;		
	}
	
}
