package com.example.thenewboston;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SigningDateSetup extends Activity implements View.OnClickListener {

	Button setTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.signing_date_setup);

		setTime = (Button)findViewById(R.id.bSetTime);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSetTime:
			//launch calendar activity
			break;
		}
	}
}
