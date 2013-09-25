package itp.team1.jobseeker.model;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Job {
	static public Job job;

	static public LinkedList<Job> jobs;

	public String  id;
	public String  title;
	public String  description;
	public String  location;
	public String  hours;
	public String  employer;
	public String  source;
	public double    latitude;
	public double    longitude;
	public long    	time;
	public String  	externalLink;
	public String 	openingDate;
	public String 	closingDate;
    public String 	salary;

	/*
	 *  private int id;

    private String description;
    private String url;
    private String source;
    private long   timestamp;   // Always STORED in UNIX Timestamp format (seconds since epoch)
                                // Can be returned as millis from epoch (Java- and SQL-compatible)
    private String employer;
    private String title;
    private String city;

    private double latitude;
    private double longitude;
    private String openingDate;
    private String pageName;
    private String closingDate;
    private String hours;
    private String industry;
    private String type;
	 */

	static public Job parseJSON(JSONObject j) throws JSONException
	{
		if ( j == null ) {
			return null;
		}

		Job d = new Job();

		d.id = j.optString("id");
		d.title = j.optString("title");
		d.description = j.optString("description");
		d.location = j.optString("location");

		d.hours = j.optString("hours");
		d.employer = j.optString("employer");
		d.source = j.optString("source");

		d.time = j.optLong("timestamp");
		d.latitude = j.optDouble("latitude");
		d.longitude = j.optDouble("longitude");

		d.externalLink = j.optString("url");
		
		d.openingDate = j.optString("openingDate");
		d.closingDate = j.optString("closingDate");
		d.salary =  j.optString("salary");

		return d;
	}

	static public Job parseJSONObject(JSONObject j) throws JSONException
	{
		job = parseJSON(j);

		return job;
	}

	static public LinkedList<Job> parseJSONArray(JSONArray j) throws JSONException
	{
		if ( j == null ) {
			return null;
		}

		LinkedList<Job> d = new LinkedList<Job>();
		for(int i =0; i <j.length(); i++){
			Job p = parseJSON(j.optJSONObject(i));
			d.add(p);
		}

		jobs = d;

		return jobs;
	}

}