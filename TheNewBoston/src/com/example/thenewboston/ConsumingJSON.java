package com.example.thenewboston;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jidesoft.utils.Base64.InputStream;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConsumingJSON extends Activity {

	TextView content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.consumingjson);
		content = (TextView) findViewById(R.id.tvConsumeJSON);
		//returns jason from job seeker server, working, not parsed properly.
		//test();
		//returns json from google directions, url is correct, json is valid, end of input at character. not working.
		//test2();
		//puts first record of jobs array into strings and ammends them to the text view.
		//test3();
		test4();
	}

	public void test4(){
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

            content.append("JSON to POJO = "+jsonObject.getString("jobs")+ "\n"); 
            content.append("------------------------------------------------"); 
			
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
	
	public void test3(){
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

            content.append("JSON to POJO = "+jsonObject.getString("jobs")+ "\n"); 
            content.append("------------------------------------------------"); 
			// routesArray contains ALL routes
			JSONArray routesArray = jsonObject.getJSONArray("jobs");
			// Grab the first route
			JSONObject job = routesArray.getJSONObject(1);
			// Take all legs from the route
			//JSONArray legs = job.getJSONArray("legs");
			// Grab first leg
			//JSONObject leg = legs.getJSONObject(0);

			//JSONObject durationObject = leg.getJSONObject("duration");
			String jobDescription = job.getString("description");	
			String jobURL = job.getString("url");
			String jobSource = job.getString("source");
			String jobTimeStamp = job.getString("timestamp");
			String jobLatitude = job.getString("latitude");
			String jobLongitude = job.getString("longitude");
			content.append("\n"+ "Job Description: " +jobDescription+ "\n" + 
							"url: "+ jobURL+ "\n" + 
							"Source: " + jobSource + "\n" +
							"Time Stamp: "+ jobTimeStamp + "\n" +
							"Latitude: " + jobLatitude + "\n" +
							"Longitude: " + jobLongitude + "\n");
			content.append("------------------------------------------------");
		} catch (Exception e) {

			e.printStackTrace();
		}
		;		
	}
	
	public void test2(){
		
		content.append("1-----------------------------------------------"+ "\n"); 
		
		try {
			// Create a new HTTP Client
			DefaultHttpClient defaultClient = new DefaultHttpClient();
			// Setup the get request
			HttpGet httpGetRequest = new HttpGet("http://maps.googleapis.com/maps/api/directions/json?origin=DD24JF&destination=DD39PP&sensor=false");

			// Execute the request in the client
			HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
			// Grab the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			String json = reader.readLine();

			JSONObject jsonObject = new JSONObject(json);

			content.append("JSON to POJO = "+jsonObject.getString("routes")+ "\n"); 
			content.append("2-----------------------------------------------"+ "\n"); 

		} catch (Exception e) {

			e.printStackTrace();
		};	
		content.append("3-----------------------------------------------"+ "\n"); 
	}
	
	public void test() {
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

            content.append("JSON to POJO = "+jsonObject.getString("jobs")+ "\n"); 
            content.append("------------------------------------------------------------------------------------"); ;
						

		} catch (Exception e) {

			e.printStackTrace();
		}
		;
	}
}
