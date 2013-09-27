package com.example.thenewboston;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HttpExample extends Activity {

	TextView httpStuff;
	String returned;
	HttpClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.httpex);
		httpStuff = (TextView) findViewById(R.id.tvHttp);
		GetMethodEx test = new GetMethodEx();

		try {
			URI website = new URI("http://www.google.com");
			returned = test.getInternetData(website);
			httpStuff.append(returned);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	
	}

}
