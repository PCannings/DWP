package com.example.thenewboston;

import org.json.JSONException;
import org.json.JSONObject;

public class JobStore {
	
	
	public String  title;
	public String  description;
	public String  location;
	public String  hours;
	public String  employer;
	public String  source;
	public double    latitude;
	public double    longitude;
	public long    	time;
	public String  externalLink;
	
	public JobStore parseJSON(JSONObject j) throws JSONException
	{
		if ( j == null ) {
			return null;
		}

		JobStore d = new JobStore();

		d.title = j.optString("title");
		d.description = j.optString("description");
		d.location = j.optString("location");
	
		d.hours = j.optString("hours");
		d.employer = j.optString("employer");

		d.time = j.optLong("timestamp");
		d.latitude = j.optDouble("latitude");
		d.longitude = j.optDouble("longitude");

		d.externalLink = j.optString("url");
		
		return d;
	}

}
