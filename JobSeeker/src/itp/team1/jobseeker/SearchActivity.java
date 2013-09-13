package itp.team1.jobseeker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
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
	// TODO: Deploy to AWS (http://ec2-54-241-217-77.us-west-1.compute.amazonaws.com:8080/JobSeekerServer/search)
	private static final String JOB_SEEKER_SERVER = "http://localhost:8080/JobSeekerServer/search";
	
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
				List<Job> jobResults = null;
				// TODO: Go to server to scrape all sources	
				// TODO: ASyncTask progress bar
				
			    // Check for Internet access
                if (!hasInternetConnectivity())
                {
                    Toast.makeText(SearchActivity.this, "Internet Access Required...", Toast.LENGTH_LONG).show();
                    return;
                }

    			try 
    			{
                    // Send ("POST") search data to server
                    HttpPost postMessage = createPostMessage();
        			HttpClient httpClient = new DefaultHttpClient();

					HttpResponse response = httpClient.execute(postMessage);
					
	    			// Parse JSON return for Job results
					jobResults = parseJSONResponse(response);

				} 
    			catch (ClientProtocolException e) 
    			{
					System.out.println("ClientPrototcolException: Can't send POST data to server");
				} 
    			catch (IOException e) 
    			{
					System.out.println("IOException: Can't send POST data to server");
				}

    			Intent intent = new Intent(SearchActivity.this, ResultsActivity.class);
    			intent.putParcelableArrayListExtra("jobResults", (ArrayList<? extends Parcelable>) jobResults);
				startActivity(new Intent(SearchActivity.this, ResultsActivity.class));
			}
		});
	}

	/**
	 * Sets up the post request
	 */
	@SuppressWarnings("deprecation")
	private static HttpPost createPostMessage(/*Search searchParameters*/) 
	{
		HttpPost postRequest = new HttpPost(JOB_SEEKER_SERVER);
		
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		HttpConnectionParams.setSoTimeout(params, 30000);
		postRequest.setParams(params);
		
		// Specify Content and Content-Type parameters for POST request
		InputStream dataIn = new StringBufferInputStream("Test Data");// TODO: searchParameters
		@SuppressWarnings("deprecation")
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("Content", new InputStreamBody(dataIn, "Content"));
		postRequest.setEntity(entity);
		
		return postRequest;
	}
	
	private static List<Job> parseJSONResponse(HttpResponse response) throws IOException 
	{
		Gson gson = new Gson();
		InputStreamReader isr = new InputStreamReader(response.getEntity().getContent());
        Type PostListings = new TypeToken<List<Job>>() {}.getType();
		List<Job> jobResults = gson.fromJson(isr, PostListings);
		return jobResults;
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
