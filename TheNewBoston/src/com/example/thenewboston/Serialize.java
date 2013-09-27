package com.example.thenewboston;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class Serialize extends Activity implements View.OnClickListener {

	Button save, load;

	SerializeStore data;
	
	TextView loaded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.serialize);
		save = (Button) findViewById(R.id.bSaveDataToDevice);
		load = (Button) findViewById(R.id.bLoadDataFromDevice);
		loaded = (TextView) findViewById(R.id.tvDisplayLoadedData);
		save.setOnClickListener(this);
		load.setOnClickListener(this);
		data = new SerializeStore(2, 2, 2);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSaveDataToDevice:
			Log.d("ButtonPress","Save Button Pressed");
			try {
				Log.d("Creating","FileOutputStream");
				FileOutputStream fos;
				Log.d("Created","FileOutputStream");
				Log.d("open","openFileOutput");
				fos = Serialize.this.openFileOutput("appointments",
						Context.MODE_PRIVATE);
				Log.d("creating","ObjectOutputStream");
				ObjectOutputStream os = new ObjectOutputStream(fos);
				Log.d("created","ObjectOutputStream");
				Log.d("Writing","to ObjectOutputStream");
				os.writeObject(data);
				Log.d("Writing Complete","to ObjectOutputStream");
				Log.d("Closing","ObjectOutputStream");
				os.close();
				Log.d("Closed","ObjectOutputStream");
				

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				Log.d("Complete","completed");
			}
			
			break;

		case R.id.bLoadDataFromDevice:
			Log.d("ButtonPress","Load Button Pressed");
				try {
				Log.d("Creating","FileInputStream");
				FileInputStream fis;
				Log.d("Created","FileInputStream");
				Log.d("Opening","openFileInput");
				fis = Serialize.this.openFileInput("appointments");
				Log.d("Creating","ObjectInputStream");
				ObjectInputStream is = new ObjectInputStream(fis);
				Log.d("Created","ObjectInputStream");
				Log.d("Reading","ObjectInputStream");
				SerializeStore simpleClass = (SerializeStore) is.readObject();
				Log.d("Reading","Finished Reading ObjectInputStream");
				Log.d("Closing","ObjectInputStream");
				is.close();
				Log.d("Closed","ObjectInputStream");
				Log.d("Set","Text");
				loaded.setText(simpleClass.getString());
				Log.d("Set","Text");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			break;

		}
	}

}
