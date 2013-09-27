package com.example.thenewboston;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GeoLocation extends Activity implements LocationListener{

	private TextView latitudeField;
	private TextView longitudeField;
	private LocationManager locationManager;

	private String provider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.geolocation);
		
		//bridge xml and java.
		latitudeField = (TextView)findViewById(R.id.TextView02);
		longitudeField = (TextView) findViewById(R.id.TextView04);
		
		//Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//define the criteria how to select the location provider, use default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		
		//initialize the location fields
		if(location != null){
			System.out.println("Provider " + provider + "has been selected.");
			onLocationChanged(location);
		}
		else
		{
			latitudeField.setText("Location not available");
			longitudeField.setText("Location not available");
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		float lat = (float) (arg0.getLatitude());
		float lng = (float) (arg0.getLongitude());
		latitudeField.setText(String.valueOf(lat));
		longitudeField.setText(String.valueOf(lng));
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Disabled provider "+ provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
