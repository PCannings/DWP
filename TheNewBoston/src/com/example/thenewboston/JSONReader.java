package com.example.thenewboston;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class JSONReader extends Activity {

	JSONModel obj = new JSONModel();;
	Gson gson = new Gson();
	
	TextView content;
	
	String returned;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.jsonreader);
		content = (TextView) findViewById(R.id.tvJSON_POJO);

		//java_Object_To_JSON_Object();

		//JSON_To_POJO();

		JSON_From_Site();
		
	}
	
	private void JSON_From_Site(){
		GetMethodEx test = new GetMethodEx();
		try {
			URI website = new URI("http://ec2-50-18-26-146.us-west-1.compute.amazonaws.com:8080/JobSeekerServer/search");
			content.append("\n"+"Sending request to this url: " + website+ "\n");
			returned = test.getInternetData(website);
			content.append("\n"+"response received was " + returned+ "\n");
			//Tranform the string into a json object
            final JSONObject json = new JSONObject(returned);
            content.append("-------------------------------------------------------------------------------");
            JobStore aStore = new JobStore();
            aStore.parseJSON(json);
            String title = (String) json.get("jobs");
            content.append("\n"+"JOBS = " + title+ "\n");
            
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}

	private void JSON_To_POJO() {

			String json = gson.toJson(obj);
			//convert the json string back to object
			JSONModel obj1 = gson.fromJson(json, JSONModel.class);
			
			Toast.makeText(JSONReader.this, obj1.toString(), Toast.LENGTH_LONG).show();
			content.append("JSON to POJO = "+obj1.toString()+ "\n");
	}

	private void java_Object_To_JSON_Object() {
		// TODO Auto-generated method stub

		String json = gson.toJson(obj);

		try {

			FileOutputStream fos = openFileOutput("file.json",
					Context.MODE_PRIVATE);

			fos.write(json.toString().getBytes());

			fos.close();
			
			//Toast.makeText(JSONReader.this, json, Toast.LENGTH_LONG).show();
			content.append("Java object to JSON object = " + json+ "\n");

		} catch (Exception e) {
			//json.getJSONArray("routes").getJSONObject(0);

			e.printStackTrace();

		}

		
	}

}
